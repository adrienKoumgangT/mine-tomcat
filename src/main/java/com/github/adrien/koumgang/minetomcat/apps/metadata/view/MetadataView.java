package com.github.adrien.koumgang.minetomcat.apps.metadata.view;


import com.github.adrien.koumgang.minetomcat.apps.metadata.model.Metadata;

public class MetadataView extends MetadataSimpleView {

    private String metaType;

    public MetadataView() {}

    public MetadataView(MetadataSimpleView metaData) {
        super(metaData);
    }

    public MetadataView(MetadataSimpleView metaData, String metaType) {
        super(metaData);

        this.metaType = metaType;
    }

    public MetadataView(Metadata metaData) {
        super(metaData);

        this.metaType = metaData.getMetadataType();
    }

    public MetadataView(
            String idMetaData,
            String metaType,
            String name,
            String description,
            String colorLight,
            String colorDark,
            Boolean isDefault
    ) {

        super(idMetaData, name, description, colorLight, colorDark, isDefault);
        this.metaType = metaType;
    }

    public String getMetaType() {
        return metaType;
    }
}
