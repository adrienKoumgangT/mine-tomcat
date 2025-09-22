package com.github.adrien.koumgang.minetomcat.apps.account.repository;

import com.github.adrien.koumgang.minetomcat.apps.account.dao.AccountDao;
import com.github.adrien.koumgang.minetomcat.apps.account.model.Account;
import com.github.adrien.koumgang.minetomcat.helpers.utils.DateTimeInitializer;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.MongoInstance;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.core.MongoAnnotationProcessor;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.utils.StringIdConverter;
import com.github.adrien.koumgang.minetomcat.lib.repository.BaseRepository;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class AccountRepository extends BaseRepository implements AccountDao {


    public static AccountRepository getInstance() {
        return new AccountRepository(MongoInstance.getInstance().mongoDatabase());
    }


    private final MongoCollection<Document> accountCollection;
    private final Class<Account> accountClass = Account.class;


    public AccountRepository(MongoDatabase database) {
        String collectionName = MongoAnnotationProcessor.getCollectionName(accountClass);
        this.accountCollection = database.getCollection(collectionName);
    }


    private Field getField(String fieldName) {
        try {
            return accountClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Field not found: " + fieldName, e);
        }
    }


    private Bson getFilter(ObjectId userId) {
        return eq(MongoAnnotationProcessor.getFieldName(getField("userId")), userId);
    }

    private Bson getFilter(ObjectId userId, Boolean isArchived) {
        return Filters.and(
                eq(MongoAnnotationProcessor.getFieldName(getField("userId")), userId),
                eq(MongoAnnotationProcessor.getFieldName(getField("isArchived")), isArchived)
        );
    }

    private Bson getFilter(ObjectId userId, String name) {
        return Filters.and(
                eq(MongoAnnotationProcessor.getFieldName(getField("userId")), userId),
                eq(MongoAnnotationProcessor.getFieldName(getField("name")), name)
        );
    }

    private Optional<Account> getAccount(FindIterable<Document> cursor) {
        List<Account> accounts = getAccounts(cursor);
        if(accounts.isEmpty()) return Optional.empty();
        return Optional.ofNullable(accounts.getFirst());
    }

    private List<Account> getAccounts(FindIterable<Document> cursor) {
        List<Account> accounts = new ArrayList<>();
        for (Document document : cursor) {
            accounts.add(MongoAnnotationProcessor.fromDocument(document, accountClass));
        }

        return accounts;
    }


    @Override
    public Optional<Account> findById(String id) {
        if (!MongoAnnotationProcessor.isValidObjectId(id)) {
            return Optional.empty();
        }

        ObjectId objectId = new ObjectId(id);

        Document document = accountCollection.find(eq("_id", objectId)).first();
        return Optional.ofNullable(MongoAnnotationProcessor.fromDocument(document, accountClass));
    }

    @Override
    public List<Account> findAll() {
        FindIterable<Document> cursor = accountCollection.find();
        return getAccounts(cursor);
    }

    @Override
    public List<Account> findAll(int page, int pageSize) {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;

        int skip = (page - 1) * pageSize;

        FindIterable<Document> cursor = accountCollection
                .find()
                .skip(skip)
                .limit(pageSize);

        return getAccounts(cursor);
    }

    @Override
    public long count() {
        return accountCollection.estimatedDocumentCount();
    }

    @Override
    public List<Account> findAll(String idUser) {
        ObjectId userId = new ObjectId(idUser);
        Bson filter = getFilter(userId);
        FindIterable<Document> cursor = accountCollection.find(filter);

        return getAccounts(cursor);
    }

    @Override
    public List<Account> findAll(String idUser, int page, int pageSize) {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;

        int skip = (page - 1) * pageSize;

        ObjectId userId = new ObjectId(idUser);
        Bson filter = getFilter(userId);

        FindIterable<Document> cursor = accountCollection
                .find(filter)
                .skip(skip)
                .limit(pageSize);

        return getAccounts(cursor);
    }

    @Override
    public long count(String idUser) {
        ObjectId userId = new ObjectId(idUser);
        Bson filter = getFilter(userId);

        return accountCollection.countDocuments(filter);
    }

    @Override
    public List<Account> findByArchived(String idUser, Boolean isArchived) {
        ObjectId userId = new ObjectId(idUser);
        Bson filter = getFilter(userId, isArchived);

        FindIterable<Document> cursor = accountCollection.find(filter);

        return getAccounts(cursor);
    }

    @Override
    public List<Account> findByArchived(String idUser, Boolean isArchived, int page, int pageSize) {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;

        int skip = (page - 1) * pageSize;

        ObjectId userId = new ObjectId(idUser);
        Bson filter = getFilter(userId, isArchived);

        FindIterable<Document> cursor = accountCollection
                .find(filter)
                .skip(skip)
                .limit(pageSize);

        return getAccounts(cursor);
    }

    @Override
    public long count(String idUser, Boolean isArchived) {
        ObjectId userId = new ObjectId(idUser);
        Bson filter = getFilter(userId, isArchived);

        return accountCollection.countDocuments(filter);
    }

    @Override
    public List<Account> findByName(String idUser, String name) {
        ObjectId userId = new ObjectId(idUser);
        Bson filter = getFilter(userId, name);

        FindIterable<Document> cursor = accountCollection.find(filter);

        return getAccounts(cursor);
    }

    @Override
    public List<Account> findByName(String idUser, String name, int page, int pageSize) {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;

        int skip = (page - 1) * pageSize;

        ObjectId userId = new ObjectId(idUser);
        Bson filter = getFilter(userId, name);

        FindIterable<Document> cursor = accountCollection
                .find(filter)
                .skip(skip)
                .limit(pageSize);

        return getAccounts(cursor);
    }

    @Override
    public long count(String idUser, String name) {
        ObjectId userId = new ObjectId(idUser);
        Bson filter = getFilter(userId, name);

        return accountCollection.countDocuments(filter);
    }

    @Override
    public String save(Account account) {
        DateTimeInitializer.initializeTimestamps(account);

        Document document = MongoAnnotationProcessor.toDocument(account);
        InsertOneResult result = accountCollection.insertOne(document);

        if(result.getInsertedId() == null) return null;

        ObjectId accountId = result.getInsertedId().asObjectId().getValue();

        return StringIdConverter.getInstance().fromObjectId(accountId);
    }

    @Override
    public boolean update(Account account) {
        DateTimeInitializer.updateTimestamps(account);

        Document document = MongoAnnotationProcessor.toDocument(account);
        document.remove("_id"); // Remove ID for update

        UpdateResult result = accountCollection.updateOne(
                eq("_id", account.getAccountId()),
                new Document("$set", document)
        );

        return result.getModifiedCount() > 0;
    }


    @Override
    public boolean delete(String id) {
        ObjectId objectId = new ObjectId(id);
        DeleteResult result = accountCollection.deleteOne(eq("_id", objectId));
        return result.getDeletedCount() > 0;
    }
}
