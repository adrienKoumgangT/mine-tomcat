package com.github.adrien.koumgang.minetomcat.apps.test.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.github.adrien.koumgang.minetomcat.apps.test.dao.TestDao;
import com.github.adrien.koumgang.minetomcat.apps.test.model.Test;
import com.github.adrien.koumgang.minetomcat.helpers.utils.DateTimeInitializer;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.MongoInstance;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.annotation.MongoRepository;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.core.MongoAnnotationProcessor;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.utils.StringIdConverter;
import com.github.adrien.koumgang.minetomcat.lib.repository.BaseRepository;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@MongoRepository
public class TestRepository extends BaseRepository implements TestDao {

    private final MongoCollection<Document> testCollection;
    private final Class<Test> testClass = Test.class;

    public TestRepository(MongoDatabase db) {
        String collectionName = MongoAnnotationProcessor.getCollectionName(testClass);
        this.testCollection = db.getCollection(collectionName);
    }


    public static TestRepository getInstance() {
        return new TestRepository(MongoInstance.getInstance().mongoDatabase());
    }


    private Field getField(String fieldName) {
        try {
            return testClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Field not found: " + fieldName, e);
        }
    }

    private Bson getFilterByName(String name) {
        return Filters.eq(
                MongoAnnotationProcessor.getFieldName(getField("name")),
                name
        );
    }

    @Override
    public Optional<Test> findById(String id) {
        if (!MongoAnnotationProcessor.isValidObjectId(id)) {
            return Optional.empty();
        }

        ObjectId objectId = new ObjectId(id);
        Document document = testCollection.find(Filters.eq("_id", objectId)).first();
        return Optional.ofNullable(MongoAnnotationProcessor.fromDocument(document, testClass));
    }

    @Override
    public List<Test> findAll() {
        List<Test> tests = new ArrayList<>();
        for (Document document : testCollection.find()) {
            tests.add(MongoAnnotationProcessor.fromDocument(document, testClass));
        }
        return tests;
    }

    /**
     * @return list of all test IDs
     */
    @Override
    public List<String> findAllIds() {
        List<String> ids = new ArrayList<>();
        // Only project the _id field
        for (Document document : testCollection.find().projection(new Document("_id", 1))) {
            ids.add(StringIdConverter.getInstance().fromObjectId((ObjectId) document.get("_id")));
        }
        return ids;
    }

    @Override
    public List<Test> findAll(int page, int pageSize) {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;

        int skip = (page - 1) * pageSize;

        List<Test> tests = new ArrayList<>();
        for (Document document : testCollection.find().skip(skip).limit(pageSize)) {
            tests.add(MongoAnnotationProcessor.fromDocument(document, testClass));
        }
        return tests;
    }

    @Override
    public List<String> findAllIds(int page, int pageSize) {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;

        int skip = (page - 1) * pageSize;

        List<String> ids = new ArrayList<>();
        FindIterable<Document> cursor = testCollection.find()
                .projection(new Document("_id", 1))
                .skip(skip)
                .limit(pageSize);

        for (Document document : cursor) {
            ids.add(StringIdConverter.getInstance().fromObjectId((ObjectId) document.get("_id")));
        }

        return ids;
    }

    @Override
    public long countByName(String name) {
        return testCollection.countDocuments(getFilterByName(name));
    }

    @Override
    public List<Test> findByName(String name) {
        FindIterable<Document> cursor = testCollection
                .find(getFilterByName(name));
        List<Test> tests = new ArrayList<>();
        for (Document document : cursor) {
            tests.add(MongoAnnotationProcessor.fromDocument(document, testClass));
        }
        return tests;
    }

    @Override
    public List<Test> findByName(String name, int page, int pageSize) {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;

        int skip = (page - 1) * pageSize;

        FindIterable<Document> cursor = testCollection
                .find(getFilterByName(name))
                .skip(skip)
                .limit(pageSize);

        List<Test> tests = new ArrayList<>();
        for (Document document : cursor) {
            tests.add(MongoAnnotationProcessor.fromDocument(document, testClass));
        }
        return tests;
    }

    @Override
    public List<String> findIdsByName(String name) {
        FindIterable<Document> cursor = testCollection
                .find(getFilterByName(name))
                .projection(new Document("_id", 1));

        List<String> ids = new ArrayList<>();
        for (Document document : cursor) {
            ids.add(StringIdConverter.getInstance().fromObjectId((ObjectId) document.get("_id")));
        }

        return ids;
    }

    @Override
    public List<String> findIdsByName(String name, int page, int pageSize) {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;

        int skip = (page - 1) * pageSize;

        FindIterable<Document> cursor = testCollection
                .find(getFilterByName(name))
                .projection(new Document("_id", 1))
                .skip(skip)
                .limit(pageSize);

        List<String> ids = new ArrayList<>();
        for (Document document : cursor) {
            ids.add(StringIdConverter.getInstance().fromObjectId((ObjectId) document.get("_id")));
        }

        return ids;
    }

    @Override
    public String save(Test test) {
        // Initialize timestamps before saving
        DateTimeInitializer.initializeTimestamps(test);

        Document document = MongoAnnotationProcessor.toDocument(test);
        InsertOneResult result = testCollection.insertOne(document);

        if(result.getInsertedId() == null) return null;

        ObjectId testId = result.getInsertedId().asObjectId().getValue();

        return StringIdConverter.getInstance().fromObjectId(testId);
    }

    @Override
    public boolean update(Test test) {
        // Update timestamps before updating
        DateTimeInitializer.updateTimestamps(test);

        Document document = MongoAnnotationProcessor.toDocument(test);
        document.remove("_id"); // Remove ID for update

        UpdateResult result = testCollection.updateOne(
                Filters.eq("_id", test.getTestId()),
                new Document("$set", document)
        );

        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean delete(String id) {
        if (!MongoAnnotationProcessor.isValidObjectId(id)) {
            return false;
        }

        ObjectId objectId = new ObjectId(id);
        DeleteResult result = testCollection.deleteOne(Filters.eq("_id", objectId));
        return result.getDeletedCount() > 0;
    }

    @Override
    public long count() {
        return testCollection.countDocuments();
    }
}
