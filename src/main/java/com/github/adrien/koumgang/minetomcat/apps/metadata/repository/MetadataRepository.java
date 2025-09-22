package com.github.adrien.koumgang.minetomcat.apps.metadata.repository;

import com.github.adrien.koumgang.minetomcat.apps.metadata.model.Metadata;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.utils.StringIdConverter;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.github.adrien.koumgang.minetomcat.apps.metadata.dao.MetadataDao;
import com.github.adrien.koumgang.minetomcat.helpers.utils.DateTimeInitializer;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.MongoInstance;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.core.MongoAnnotationProcessor;
import com.github.adrien.koumgang.minetomcat.lib.repository.BaseRepository;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class MetadataRepository extends BaseRepository implements MetadataDao {


    public static MetadataRepository getInstance() {
        return new MetadataRepository(MongoInstance.getInstance().mongoDatabase());
    }


    private final MongoCollection<Document> metaDataCollection;
    private final Class<Metadata> metaDataClass = Metadata.class;

    public MetadataRepository(MongoDatabase database) {
        String collectionName = MongoAnnotationProcessor.getCollectionName(metaDataClass);
        this.metaDataCollection = database.getCollection(collectionName);
    }

    private Field getField(String fieldName) {
        try {
            return metaDataClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Field not found: " + fieldName, e);
        }
    }


    private Bson getFilter(ObjectId userId) {
        return eq(MongoAnnotationProcessor.getFieldName(getField("userId")), userId);
    }

    private Bson getFilter(ObjectId userId, String metaType) {
        return Filters.and(
                eq(MongoAnnotationProcessor.getFieldName(getField("userId")), userId),
                eq(MongoAnnotationProcessor.getFieldName(getField("metaType")), metaType)
        );
    }

    private Bson getFilter(ObjectId userId, String metaType, String name) {
        return Filters.and(
                eq(MongoAnnotationProcessor.getFieldName(getField("userId")), userId),
                eq(MongoAnnotationProcessor.getFieldName(getField("metaType")), metaType),
                eq(MongoAnnotationProcessor.getFieldName(getField("name")), name)
        );
    }

    private Optional<Metadata> getMetaData(FindIterable<Document> cursor) {
        List<Metadata> metadataList = getMetaDatas(cursor);

        if(metadataList.isEmpty()) return Optional.empty();
        return Optional.ofNullable(metadataList.getFirst());
    }

    private List<Metadata> getMetaDatas(FindIterable<Document> cursor) {
        List<Metadata> metadataList = new ArrayList<>();
        for (Document document : cursor) {
            metadataList.add(MongoAnnotationProcessor.fromDocument(document, metaDataClass));
        }

        return metadataList;
    }


    /**
     * @param id metadata id
     * @return metadata with this identification
     */
    @Override
    public Optional<Metadata> findById(String id) {
        ObjectId objectId = new ObjectId(id);
        Document document = metaDataCollection.find(eq("_id", objectId)).first();
        return Optional.ofNullable(MongoAnnotationProcessor.fromDocument(document, metaDataClass));
    }

    /**
     * @return all documents in metadata collection
     */
    @Override
    public List<Metadata> findByType() {
        FindIterable<Document> cursor = metaDataCollection.find();
        return getMetaDatas(cursor);
    }

    /**
     * @param page number of page to return
     * @param pageSize max number of element to return
     * @return max pageSize metadata
     */
    @Override
    public List<Metadata> findByType(int page, int pageSize) {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;

        int skip = (page - 1) * pageSize;

        FindIterable<Document> cursor = metaDataCollection
                .find()
                .skip(skip)
                .limit(pageSize);

        return getMetaDatas(cursor);
    }

    /**
     * @return number of documents in metadata collection
     */
    @Override
    public long count() {
        return metaDataCollection.estimatedDocumentCount();
    }

    @Override
    public List<Metadata> findByType(String idUser) {
        ObjectId userId = new ObjectId(idUser);
        Bson filter = getFilter(userId);

        FindIterable<Document> cursor = metaDataCollection.find(filter);
        return getMetaDatas(cursor);
    }

    @Override
    public List<Metadata> findByType(String idUser, int page, int pageSize) {
        ObjectId userId = new ObjectId(idUser);

        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;

        int skip = (page - 1) * pageSize;

        Bson filter = getFilter(userId);

        FindIterable<Document> cursor = metaDataCollection
                .find(filter)
                .skip(skip)
                .limit(pageSize);

        return getMetaDatas(cursor);
    }

    @Override
    public long count(String idUser) {
        ObjectId userId = new ObjectId(idUser);
        Bson filter = getFilter(userId);

        return metaDataCollection.countDocuments(filter);
    }

    /**
     * @param metadataType meta type
     * @return list of metadata associated at this meta type
     */
    @Override
    public List<Metadata> findByType(String idUser, String metadataType) {
        ObjectId userId = new ObjectId(idUser);
        Bson filter = getFilter(userId, metadataType);

        FindIterable<Document> cursor = metaDataCollection.find(filter);
        return getMetaDatas(cursor);
    }

    /**
     * @param metadataType meta type
     * @param page number of page to return
     * @param pageSize number of element to return
     * @return list of metadata associated at this meta type
     */
    @Override
    public List<Metadata> findByType(String idUser, String metadataType, int page, int pageSize) {
        ObjectId userId = new ObjectId(idUser);

        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;

        int skip = (page - 1) * pageSize;

        Bson filter = getFilter(userId, metadataType);

        FindIterable<Document> cursor = metaDataCollection
                .find(filter)
                .skip(skip)
                .limit(pageSize);

        return getMetaDatas(cursor);
    }

    /**
     * @param metadataType meta type
     * @return number of document in collection who have reference to this meta type
     */
    @Override
    public long count(String idUser, String metadataType) {
        ObjectId userId = new ObjectId(idUser);
        Bson filter = getFilter(userId, metadataType);

        return metaDataCollection.countDocuments(filter);
    }

    /**
     * @param metadataType meta type
     * @param name name of metadata
     * @return a metadata list with this specific type and name
     */
    @Override
    public List<Metadata> findByName(String idUser, String metadataType, String name) {
        ObjectId userId = new ObjectId(idUser);
        Bson filter = getFilter(userId, metadataType, name);

        FindIterable<Document> cursor = metaDataCollection.find(filter);
        return getMetaDatas(cursor);
    }

    @Override
    public List<Metadata> findByName(String idUser, String metadataType, String name, int page, int pageSize) {
        ObjectId userId = new ObjectId(idUser);

        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;

        int skip = (page - 1) * pageSize;

        Bson filter = getFilter(userId, metadataType, name);

        FindIterable<Document> cursor = metaDataCollection
                .find(filter)
                .skip(skip)
                .limit(pageSize);

        return getMetaDatas(cursor);
    }

    @Override
    public long count(String idUser, String metadataType, String name) {
        ObjectId userId = new ObjectId(idUser);
        Bson filter = getFilter(userId, metadataType, name);

        return metaDataCollection.countDocuments(filter);
    }

    /**
     * @param metadata new metadata
     * @return id of new document
     */
    @Override
    public String save(Metadata metadata) {
        DateTimeInitializer.initializeTimestamps(metadata);

        Document document = MongoAnnotationProcessor.toDocument(metadata);
        InsertOneResult result = metaDataCollection.insertOne(document);

        if(result.getInsertedId() == null) return null;

        ObjectId objectId = result.getInsertedId().asObjectId().getValue();

        return StringIdConverter.getInstance().fromObjectId(objectId);
    }

    /**
     * @param metadata meta data to update
     * @return true if updated else false
     */
    @Override
    public boolean update(Metadata metadata) {
        DateTimeInitializer.updateTimestamps(metadata);

        Document document = MongoAnnotationProcessor.toDocument(metadata);
        document.remove("_id"); // Remove ID for update

        UpdateResult result = metaDataCollection.updateOne(
                eq("_id", metadata.getMetadataId()),
                new Document("$set", document)
        );

        return result.getModifiedCount() > 0;
    }

    /**
     * @param id identification of meta data to delete
     * @return true if deleted else false
     */
    @Override
    public boolean delete(String id) {
        ObjectId objectId = new ObjectId(id);
        DeleteResult result = metaDataCollection.deleteOne(eq("_id", objectId));
        return result.getDeletedCount() > 0;
    }
}
