package com.github.adrien.koumgang.minetomcat.apps.item.model;

import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.annotation.MongoCollectionName;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.annotation.MongoDateTime;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.annotation.MongoEmbedded;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.annotation.MongoId;
import com.github.adrien.koumgang.minetomcat.lib.model.BaseModel;
import com.github.adrien.koumgang.minetomcat.lib.model.annotation.ModelField;
import org.bson.types.ObjectId;

import java.util.Date;

@MongoCollectionName("deleted-items")
public class DeletedItem extends BaseModel {

    @MongoId
    private ObjectId deletedItemId;

    @ModelField("original_id")
    private ObjectId originalId;

    @MongoEmbedded("embed")
    private NamespaceItem namespace;

    @ModelField("deleted_at")
    @MongoDateTime(utc = true)
    private Date deletedAt;

    @ModelField("deleted_by")
    private String deletedBy;

    @ModelField("reason")
    private String reason;

    @MongoEmbedded("metadata")
    private MetadataItem metadata;

    @MongoEmbedded("data")
    private Object data;



}
