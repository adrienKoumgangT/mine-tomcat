package com.github.adrien.koumgang.minetomcat.apps.metadata.view;

import com.github.adrien.koumgang.minetomcat.apps.metadata.model.Metadata;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.utils.StringIdConverter;
import com.github.adrien.koumgang.minetomcat.lib.view.BaseView;

public class MetadataSimpleView extends BaseView {

    private String idMetaData;

    private String name;

    private String description;

    private String colorLight;

    private String colorDark;

    private Boolean isDefault;

    public MetadataSimpleView() {}

    public MetadataSimpleView(String idMetaData, String name, String description) {
        this.idMetaData     = idMetaData;
        this.name           = name;
        this.description    = description;
    }

    public MetadataSimpleView(
            String idMetaData,
            String name,
            String description,
            String colorLight,
            String colorDark,
            Boolean isDefault
    ) {
        this.idMetaData     = idMetaData;
        this.name           = name;
        this.description    = description;
        this.colorLight     = colorLight;
        this.colorDark      = colorDark;
        this.isDefault      = isDefault;
    }

    public MetadataSimpleView(Metadata metaData) {
        this.idMetaData = StringIdConverter.getInstance().fromObjectId(metaData.getMetadataId());

        this.name           = metaData.getName();
        this.description    = metaData.getDescription();
        this.colorLight     = metaData.getColorLight();
        this.colorDark      = metaData.getColorDark();
        this.isDefault      = metaData.getIsDefault();
    }

    public MetadataSimpleView(MetadataSimpleView metaData) {
        this.idMetaData = metaData.getIdMetaData();

        this.name           = metaData.getName();
        this.description    = metaData.getDescription();
        this.colorLight     = metaData.getColorLight();
        this.colorDark      = metaData.getColorDark();
        this.isDefault      = metaData.getIsDefault();
    }


    public String getIdMetaData() {
        return idMetaData;
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
