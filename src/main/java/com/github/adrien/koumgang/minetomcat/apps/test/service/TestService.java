package com.github.adrien.koumgang.minetomcat.apps.test.service;

import com.github.adrien.koumgang.minetomcat.lib.model.Pagination;
import com.github.adrien.koumgang.minetomcat.lib.service.BaseService;
import com.github.adrien.koumgang.minetomcat.lib.service.FieldValue;
import com.github.adrien.koumgang.minetomcat.lib.service.filter.ComparisonOperator;
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
import com.github.adrien.koumgang.minetomcat.apps.test.dao.TestDao;
import com.github.adrien.koumgang.minetomcat.apps.test.model.Test;
import com.github.adrien.koumgang.minetomcat.apps.test.repository.TestRepository;
import com.github.adrien.koumgang.minetomcat.apps.test.view.TestView;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.redis.RedisInstance;
import com.github.adrien.koumgang.minetomcat.lib.log.MineLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TestService extends BaseService<TestView> {

    private final TestDao testDao;

    private TestService(TestDao dao) { this.testDao = dao; }


    public static TestService getInstance() {
        return new TestService(TestRepository.getInstance());
    }


    private static String formTestKey(String id) {
        return "test:" + id;
    }


    public GetItemResponse<TestView> getItem(GetItemRequest request) {
        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [TEST] [GET] request: " + request);

        String testKey = formTestKey(request.id());

        try {
            TestView cacheData = RedisInstance.getInstance().get(testKey, TestView.class);
            if(cacheData != null) {
                timePrinter.log();


                return GetItemResponse.<TestView>builder().item(cacheData).build();
            }
        } catch (Exception e) {
            timePrinter.error(e.getMessage());
        }

        try {
            Optional<Test> optTest = testDao.findById(request.id());

            if(optTest.isPresent()) {
                try {
                    RedisInstance.getInstance().set(testKey, optTest.get(), 60*10);
                } catch (Exception e) {
                    timePrinter.error(e.getMessage());
                }

                TestView testView = new TestView(optTest.get());

                timePrinter.log();

                return GetItemResponse.<TestView>builder().item(testView).build();
            } else {
                timePrinter.missing("Test not found");
            }
        } catch (IllegalArgumentException e) {
            timePrinter.error(e.getMessage());
        }


        return GetItemResponse.<TestView>builder().build();
    }


    public PostItemResponse<TestView> postItem(PostItemRequest<TestView> request) {
        if(request.hasItem()) {
            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [TEST] [SAVE] test: " + request);

            try {
                Test test = new Test(request.item());
                String testId = testDao.save(test);

                if(testId == null) {
                    timePrinter.error("Error saving test");
                    return null;
                }

                Optional<Test> optTest = testDao.findById(testId);

                if(optTest.isEmpty()) {
                    timePrinter.error("Error saving test");
                    return null;
                }

                TestView testView = new TestView(optTest.get());

                timePrinter.log();

                return PostItemResponse.<TestView>builder().item(testView).created(true).build();
            } catch (IllegalArgumentException e) {
                timePrinter.error(e.getMessage());
            }
        }

        return PostItemResponse.<TestView>builder().item(request.item()).created(false).build();
    }

    public PutItemResponse<TestView> updateItem(PutItemRequest<TestView> request) {
        if(request.hasItem() && request.id() != null) {
            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [TEST] [UPDATE] test: " + request);

            try {
                Optional<Test> existingTest = testDao.findById(request.id());

                if (existingTest.isPresent()) {
                    Test test = existingTest.get();
                    test.update(request.item());
                    boolean updated = testDao.update(test);

                    if(updated) {
                        Optional<Test> optTest = testDao.findById(request.id());

                        if(optTest.isPresent()) {
                            TestView testView = new TestView(optTest.get());

                            timePrinter.log();

                            return PutItemResponse.<TestView>builder().item(testView).updated(true).build();
                        }
                    }

                    timePrinter.error("Error during update test");
                } else {
                    timePrinter.missing("Test not found");
                }
            } catch (IllegalArgumentException e) {
                timePrinter.error(e.getMessage());
            }
        }

        return PutItemResponse.<TestView>builder().item(request.item()).updated(false).build();
    }


    public DeleteItemResponse<TestView> deleteItem(DeleteItemRequest request) {
        if(request.id() != null) {
            MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [TEST] [DELETE] id: " + request);

            try {
                Optional<Test> optTest = testDao.findById(request.id());

                if(optTest.isPresent()) {
                    TestView testView = new TestView(optTest.get());

                    boolean deleted = testDao.delete(request.id());

                    if(deleted) {
                        try {
                            String testKey = formTestKey(request.id());
                            RedisInstance.getInstance().delete(testKey);
                        } catch (Exception e) {
                            timePrinter.error(e.getMessage());
                        }
                    }

                    timePrinter.log();

                    return DeleteItemResponse.<TestView>builder().item(testView).deleted(deleted).build();
                }
            } catch (IllegalArgumentException e) {
                timePrinter.error(e.getMessage());
            }
        }

        return DeleteItemResponse.<TestView>builder().deleted(false).build();
    }


    public ListItemResponse<TestView> list(ListItemRequest request) {
        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [TEST] [LIST] request: " + request);

        List<Test> tests;
        List<String> ids;
        boolean paginated = false;

        if(request.ids()) {
            tests = new ArrayList<>();
            if(request.pagination()) {
                paginated = true;
                ids = testDao.findAllIds(request.page(), request.pageSize());
            } else {
                ids = testDao.findAllIds();
            }
        } else {
            ids = new ArrayList<>();
            if(request.pagination()) {
                paginated = true;
                tests = testDao.findAll(request.page(), request.pageSize());
            } else {
                tests = testDao.findAll();
            }
        }

        long total = testDao.count();

        Pagination pagination = Pagination.builder()
                .page(request.page())
                .pageSize(request.pageSize())
                .total(total)
                .build();
        List<TestView> testViews = tests.stream()
                .map(TestView::new)
                .toList();


        timePrinter.log();

        return ListItemResponse.<TestView>builder()
                .ids(ids)
                .items(testViews)
                .pagination(pagination)
                .paginated(paginated)
                .build();
    }

    public ListItemResponse<TestView> filter(ListFilterRequest request) {

        boolean callList = !request.hasCondition()
                || request.conditions().size() != 1
                || !Objects.equals(request.conditions().getFirst().comparisonOperator(), ComparisonOperator.EQ);

        String name = "";
        if(!callList) {
            FieldValue fieldValue = request.conditions().getFirst().fieldValue();
            if(fieldValue == null || fieldValue.value() == null || fieldValue.value().s() == null) {
                callList = true;
            } else name = fieldValue.value().s();
        }

        if(callList || name.isBlank()) {
            return this.list(request.toListRequest());
        }


        MineLog.TimePrinter timePrinter = new MineLog.TimePrinter("[SERVICE] [TEST] [LIST CONDITION] request: " + request);

        List<Test> tests;
        List<String> ids;
        boolean paginated = false;

        if(request.ids()) {
            tests = new ArrayList<>();
            if(request.pagination()) {
                paginated = true;
                ids = testDao.findIdsByName(name, request.page(), request.pageSize());
            } else {
                ids = testDao.findIdsByName(name);
            }
        } else {
            ids = new ArrayList<>();
            if(request.pagination()) {
                paginated = true;
                tests = testDao.findByName(name, request.page(), request.pageSize());
            } else {
                tests = testDao.findByName(name);
            }
        }

        long total = testDao.countByName(name);

        Pagination pagination = Pagination.builder()
                .page(request.page())
                .pageSize(request.pageSize())
                .total(total)
                .build();
        List<TestView> testViews = tests.stream()
                .map(TestView::new)
                .toList();


        timePrinter.log();

        return ListItemResponse.<TestView>builder()
                .ids(ids)
                .items(testViews)
                .pagination(pagination)
                .paginated(paginated)
                .build();
    }

}