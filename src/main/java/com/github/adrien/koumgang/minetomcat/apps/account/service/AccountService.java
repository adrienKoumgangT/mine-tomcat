package com.github.adrien.koumgang.minetomcat.apps.account.service;

import com.github.adrien.koumgang.minetomcat.apps.account.dao.AccountDao;
import com.github.adrien.koumgang.minetomcat.apps.account.model.Account;
import com.github.adrien.koumgang.minetomcat.apps.account.model.AccountType;
import com.github.adrien.koumgang.minetomcat.apps.account.repository.AccountRepository;
import com.github.adrien.koumgang.minetomcat.apps.account.view.AccountTypeView;
import com.github.adrien.koumgang.minetomcat.apps.account.view.AccountView;
import com.github.adrien.koumgang.minetomcat.lib.authentication.user.UserToken;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.core.MongoAnnotationProcessor;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.redis.RedisInstance;
import com.github.adrien.koumgang.minetomcat.lib.log.MineLog;
import com.github.adrien.koumgang.minetomcat.lib.model.Pagination;
import com.github.adrien.koumgang.minetomcat.lib.service.BaseService;
import com.github.adrien.koumgang.minetomcat.lib.service.filter.Condition;
import com.github.adrien.koumgang.minetomcat.lib.service.request.delete.DeleteItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.get.GetItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.list.ListFilterRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.list.ListItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.post.PostItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.request.put.PutItemRequest;
import com.github.adrien.koumgang.minetomcat.lib.service.response.delete.DeleteItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.service.response.get.GetItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.service.response.list.ListItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.service.response.post.PostItemResponse;
import com.github.adrien.koumgang.minetomcat.lib.service.response.put.PutItemResponse;

import java.util.*;

public final class AccountService extends BaseService<AccountView> {

    public static AccountService getInstance() {
        return new AccountService(AccountRepository.getInstance());
    }


    private final AccountDao accountDao;

    private AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    private static final Long TTL_CACHE = 60 * 60L;


    // TODO: to check if cancel or use
    private static String formKey(UserToken userToken) {
        return "user:" + userToken.getIdUser() + ":account";
    }

    private static String formKey(UserToken userToken, String id) {
        return "user:" + userToken.getIdUser() + ":account:" + id;
    }

    private static String formNameKey(UserToken userToken, String name) {
        return "user:" + userToken.getIdUser() + ":account:name:" + name;
    }

    private AccountView getAccountFromCache(UserToken userToken, String id) {
        if (!MongoAnnotationProcessor.isValidObjectId(id)) {
            return null;
        }

        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [ACCOUNT] [CACHE] [GET] id: " + id);

        String key = formKey(userToken, id);

        try {
            AccountView accountView = RedisInstance.getInstance().get(key, AccountView.class);
            if (accountView != null) {
                try {
                    RedisInstance.getInstance().expire(key, TTL_CACHE);
                } catch (Exception ignored) {}

                timePrinter.log();
                return accountView;
            }
            timePrinter.missing();
        } catch (Exception e) {
            timePrinter.error(e.getMessage());
        }

        return null;
    }

    // TODO: to check if cancel or use
    private AccountView getAccountFromCacheUseName(UserToken userToken, String name) {

        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [ACCOUNT] [CACHE] [GET] name: " + name);

        String key = formNameKey(userToken, name);

        try {
            AccountView accountView = RedisInstance.getInstance().get(key, AccountView.class);
            if (accountView != null) {
                try {
                    RedisInstance.getInstance().expire(key, TTL_CACHE);
                } catch (Exception ignored) {}

                timePrinter.log();
                return accountView;
            }
            timePrinter.missing();
        } catch (Exception e) {
            timePrinter.error(e.getMessage());
        }

        return null;
    }

    private void cacheAccount(UserToken userToken, AccountView accountView) {
        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter(
                "[SERVICE] [ACCOUNT] [CACHE] [SET] data: " + gson.toJson(accountView)
        );

        String key = formKey(userToken, accountView.getIdAccount());
        String keyName = formNameKey(userToken, accountView.getName());

        try {
            RedisInstance.getInstance().set(key, accountView, TTL_CACHE);
            RedisInstance.getInstance().set(keyName, accountView, TTL_CACHE);
            timePrinter.log();
        } catch (Exception e) {
            timePrinter.error(e.getMessage());
        }
    }

    private void scacheAccount(UserToken userToken, String id) {
        if (!MongoAnnotationProcessor.isValidObjectId(id)) {
            return;
        }

        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [ACCOUNT] [CACHE] [DELETE] id: " + id);

        String key = formKey(userToken, id);

        try {
            RedisInstance.getInstance().delete(key);
            timePrinter.log();
        } catch (Exception e) {
            timePrinter.error(e.getMessage());
        }
    }

    private void scacheAccountByName(UserToken userToken, String name) {
        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [ACCOUNT] [CACHE] [DELETE] name: " + name);

        String key = formNameKey(userToken, name);

        try {
            RedisInstance.getInstance().delete(key);
            timePrinter.log();
        } catch (Exception e) {
            timePrinter.error(e.getMessage());
        }
    }


    public GetItemResponse<AccountView> getItem(GetItemRequest request) {
        if(request.hasUserToken() && request.userToken().isPresent() && request.id() != null) {

            AccountView cachedData = getAccountFromCache(request.userToken().get(), request.id());
            if(cachedData != null){
                return GetItemResponse.<AccountView>builder().item(cachedData).build();
            }

            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [ACCOUNT] [GET] request: " + request);

            try {

                Optional<Account> optAccount = accountDao.findById(request.id());

                if(optAccount.isPresent()) {
                    AccountView accountView = new AccountView(optAccount.get());
                    if(accountView.getAccountType() != null) {
                        GetItemRequest getItemRequest = GetItemRequest.builder().userToken(request.userToken().get()).id(accountView.getAccountType().getIdAccountType()).build();
                        GetItemResponse<AccountTypeView> getItemResponse = AccountTypeService.getInstance().getItem(getItemRequest);
                        if(getItemResponse.hasItem()) {
                            accountView.setAccountType(getItemResponse.item());
                        }
                    }

                    cacheAccount(request.userToken().get(), accountView);

                    timePrinter.log();

                    return GetItemResponse.<AccountView>builder().item(accountView).build();
                }

                timePrinter.missing("Account not found");
            } catch (IllegalArgumentException e) {
                timePrinter.error(e.getMessage());
            }
        }

        return GetItemResponse.<AccountView>builder().build();
    }


    public PostItemResponse<AccountView> postItem(PostItemRequest<AccountView> request) {
        if(request.hasItem() && request.hasUserToken() && request.userToken().isPresent()) {
            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter(
                    "[SERVICE] [ACCOUNT] [SAVE] request: " + request
            );

            try {
                Account account = new Account(request.item());
                GetItemRequest getItemRequest = GetItemRequest.builder().userToken(request.userToken().get()).id(request.item().getAccountType().getIdAccountType()).build();
                GetItemResponse<AccountTypeView> response = AccountTypeService.getInstance().getItem(getItemRequest);
                if(response.hasItem()) {
                    AccountType accountType = new AccountType(response.item());
                    account.setAccountType(accountType);
                }

                String accountId = accountDao.save(account);

                if(accountId != null) {
                    Optional<Account> optAccount = accountDao.findById(accountId);

                    if(optAccount.isPresent()) {
                        AccountView accountView = new AccountView(optAccount.get());

                        cacheAccount(request.userToken().get(), accountView);

                        timePrinter.log();

                        return PostItemResponse.<AccountView>builder().item(accountView).created(true).build();
                    }
                }

                timePrinter.error("Error saving account");
            } catch (IllegalArgumentException e) {
                timePrinter.error(e.getMessage());
            }
        }

        return PostItemResponse.<AccountView>builder().item(request.item()).created(false).build();
    }


    public PutItemResponse<AccountView> updateItem(PutItemRequest<AccountView> request) {
        if(request.hasItem() && request.id() != null && request.hasUserToken() && request.userToken().isPresent()) {

            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter(
                    "[SERVICE] [ACCOUNT] [UPDATE] request: " + request
            );

            try {
                Optional<Account> existingAccount = accountDao.findById(request.id());

                if(existingAccount.isPresent()) {
                    Account account = existingAccount.get();
                    account.update(request.item());
                    boolean updated = accountDao.update(account);

                    if(updated) {
                        scacheAccount(request.userToken().get(), request.id());
                        scacheAccountByName(request.userToken().get(), account.getName());

                        Optional<Account> optAccount = accountDao.findById(request.id());

                        if(optAccount.isPresent()) {
                            AccountView accountView = new AccountView(optAccount.get());

                            timePrinter.log();

                            return PutItemResponse.<AccountView>builder().item(accountView).updated(true).build();
                        }
                    }

                    timePrinter.error("Error during update account");
                } else {
                    timePrinter.missing("Account not found");
                }
            } catch (IllegalArgumentException e) {
                timePrinter.error(e.getMessage());
            }

        }

        return PutItemResponse.<AccountView>builder().item(request.item()).updated(false).build();
    }


    public DeleteItemResponse<AccountView> deleteItem(DeleteItemRequest request) {
        if(request.id() != null && request.hasUserToken() && request.userToken().isPresent()) {

            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [ACCOUNT] [DELETE] request: " + request);

            try {
                Optional<Account> optAccount = accountDao.findById(request.id());

                if(optAccount.isPresent()) {
                    boolean deleted = accountDao.delete(request.id());

                    if(deleted) {
                        scacheAccount(request.userToken().get(), request.id());
                        scacheAccountByName(request.userToken().get(), optAccount.get().getName());

                        AccountView accountView = new AccountView(optAccount.get());

                        timePrinter.log();

                        return DeleteItemResponse.<AccountView>builder().item(accountView).deleted(true).build();
                    }

                    timePrinter.error("Error during delete account");
                } else {
                    timePrinter.missing("Account not found");
                }
            } catch (IllegalArgumentException e) {
                timePrinter.error(e.getMessage());
            }

        }

        return DeleteItemResponse.<AccountView>builder().deleted(false).build();
    }


    public ListItemResponse<AccountView> list(ListItemRequest request) {
        if(request.hasUserToken() && request.userToken().isPresent()) {

            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [ACCOUNT] [LIST] request: " + request);

            List<Account> accounts;

            if(request.pagination()) {
                accounts = accountDao.findAll(request.userToken().get().getIdUser(), request.page(), request.pageSize());
            } else {
                accounts = accountDao.findAll(request.userToken().get().getIdUser());
            }

            long total = accountDao.count();

            Pagination pagination = Pagination.builder()
                    .page(request.page())
                    .pageSize(request.pageSize())
                    .total(total)
                    .build();

            List<AccountView> accountViews = accounts.stream()
                    .map(AccountView::new)
                    .toList();

            timePrinter.log();

            return ListItemResponse.<AccountView>builder()
                    .items(accountViews)
                    .pagination(pagination)
                    .build();

        }

        Pagination pagination = Pagination.builder()
                .page(request.page())
                .pageSize(request.pageSize())
                .total(0L)
                .build();

        return ListItemResponse.<AccountView>builder().pagination(pagination).build();
    }

    private static Map<String, Object> extractNameAndIsArchivedFromCondition(Condition condition) {
        String name = null;
        Boolean isArchived = null;

        switch (condition.comparisonOperator()) {
            case EQ: {
                if(condition.fieldValue() != null && condition.fieldValue().name() != null && condition.fieldValue().value() != null) {
                    AccountFilter filter = AccountFilter.getByValue(condition.fieldValue().name());
                    switch (filter) {
                        case NAME ->  name = condition.fieldValue().value().s();
                        case IS_ARCHIVED -> isArchived = condition.fieldValue().value().bool();
                        case null -> {}
                    }
                }
            } break;
            case AND: {
                if(condition.hasCondition() && condition.conditions() != null && !condition.conditions().isEmpty()) {
                    for(Condition innerCondition : condition.conditions()) {
                        Map<String, Object> neFromCondition = extractNameAndIsArchivedFromCondition(innerCondition);
                        if(neFromCondition.containsKey(AccountFilter.NAME.getValue()) && neFromCondition.get(AccountFilter.NAME.getValue()) != null) {
                            name = (String) neFromCondition.get(AccountFilter.NAME.getValue());
                        }
                        if(neFromCondition.containsKey(AccountFilter.IS_ARCHIVED.getValue()) && neFromCondition.get(AccountFilter.IS_ARCHIVED.getValue()) != null) {
                            isArchived = (Boolean) neFromCondition.get(AccountFilter.IS_ARCHIVED.getValue());
                        }
                    }
                }
            } break;
            case null, default: {}
        }

        Map<String, Object> map = new HashMap<>();
        map.put(AccountFilter.NAME.getValue(), name);
        map.put(AccountFilter.IS_ARCHIVED.getValue(), isArchived);
        return map;
    }

    @Override
    public ListItemResponse<AccountView> filter(ListFilterRequest request) {
        if(request.hasUserToken() && request.userToken().isPresent()) {
            boolean callList = !request.hasCondition() || request.conditions() == null || request.conditions().isEmpty();

            if(callList) {
                return this.list(request.toListRequest());
            }

            String name = "";
            Boolean isArchived = null;

            for(Condition condition : request.conditions()) {
                Map<String, Object> map = extractNameAndIsArchivedFromCondition(condition);
                if(map.containsKey(AccountFilter.NAME.getValue()) && map.get(AccountFilter.NAME.getValue()) != null) {
                    name = (String) map.get(AccountFilter.NAME.getValue());
                }
                if(map.containsKey(AccountFilter.IS_ARCHIVED.getValue()) && map.get(AccountFilter.IS_ARCHIVED.getValue()) != null) {
                    isArchived = (Boolean) map.get(AccountFilter.IS_ARCHIVED.getValue());
                }
            }

            if(name.isBlank() && isArchived == null) {
                return this.list(request.toListRequest());
            }

            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [ACCOUNT] [LIST CONDITION] request: " + request);

            UserToken userToken = request.userToken().get();
            String idUser = userToken.getIdUser();

            List<Account> accounts = !name.isBlank() ?
                    (
                            request.pagination()
                                    ? accountDao.findByName(idUser, name, request.page(), request.pageSize())
                                    : accountDao.findByName(idUser, name)
                    ) : (
                            request.pagination()
                                    ? accountDao.findByArchived(idUser, isArchived, request.page(), request.pageSize())
                                    : accountDao.findByArchived(idUser, isArchived)
            );
            long total = !name.isBlank() ? accountDao.count(idUser, name) : accountDao.count(idUser, isArchived);

            List<AccountView> accountViews = accounts.stream().map(AccountView::new).toList();

            Pagination pagination = Pagination.builder()
                    .page(request.page())
                    .pageSize(request.pageSize())
                    .total(total)
                    .build();

            timePrinter.log();

            return ListItemResponse.<AccountView>builder()
                    .items(accountViews)
                    .pagination(pagination)
                    .paginated(request.pagination())
                    .build();
        }

        return ListItemResponse.<AccountView>builder().build();
    }

}
