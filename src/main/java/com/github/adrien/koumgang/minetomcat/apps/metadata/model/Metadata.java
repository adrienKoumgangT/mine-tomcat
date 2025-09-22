package com.github.adrien.koumgang.minetomcat.apps.metadata.model;

import com.github.adrien.koumgang.minetomcat.apps.metadata.view.MetadataSimpleView;
import com.github.adrien.koumgang.minetomcat.apps.metadata.view.MetadataView;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.annotation.MongoCollectionName;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.annotation.MongoId;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.utils.StringIdConverter;
import com.github.adrien.koumgang.minetomcat.lib.model.BaseModel;
import com.github.adrien.koumgang.minetomcat.lib.model.annotation.ModelField;
import org.bson.types.ObjectId;

@MongoCollectionName("metadata")
public class Metadata extends BaseModel {

    @MongoId
    private ObjectId metadataId;

    @ModelField("user_id")
    private ObjectId userId;

    @ModelField("metadata_type")
    private String metadataType;

    @ModelField("name")
    private String name;

    @ModelField("description")
    private String description;

    @ModelField("color_light")
    private String colorLight;

    @ModelField("color_dark")
    private String colorDark;

    @ModelField("is_default")
    private Boolean isDefault;

    public Metadata() {}

    public Metadata(MetadataSimpleView metaData) {
        this.metadataId = StringIdConverter.getInstance().toObjectId(metaData.getIdMetaData());

        this.name           = metaData.getName();
        this.description    = metaData.getDescription();
        this.colorLight     = metaData.getColorLight();
        this.colorDark      = metaData.getColorDark();
        this.isDefault      = metaData.getIsDefault();
    }

    public Metadata(MetadataSimpleView metaData, String metadataType) {
        this(metaData);

        this.metadataType = metadataType;
    }

    public Metadata(MetadataView metaData) {
        this((MetadataSimpleView) metaData);

        this.metadataType = metaData.getMetaType();
    }

    public void update(MetadataView metaData) {
        this.name           = metaData.getName();
        this.description    = metaData.getDescription();
        this.colorLight     = metaData.getColorLight();
        this.colorDark      = metaData.getColorDark();
        this.isDefault      = metaData.getIsDefault();
    }

    public ObjectId getMetadataId() {
        return metadataId;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public String getMetadataType() {
        return metadataType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getColorLight() {
        return colorLight;
    }

    public String getColorDark() {
        return colorDark;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }
}
