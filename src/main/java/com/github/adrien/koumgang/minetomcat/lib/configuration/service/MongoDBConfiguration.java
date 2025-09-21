package com.github.adrien.koumgang.minetomcat.lib.configuration.service;

import com.github.adrien.koumgang.minetomcat.lib.configuration.BaseConfiguration;
import com.github.adrien.koumgang.minetomcat.lib.configuration.annotation.ConfigFile;
import com.github.adrien.koumgang.minetomcat.lib.configuration.annotation.ConfigValue;

@ConfigFile(fileName = "config/mongodb.properties")
public class MongoDBConfiguration extends BaseConfiguration {

    @ConfigValue(key = "mongodb.uri", defaultValue = "mongodb://localhost:27017/")
    private String mongodbUri;

    @ConfigValue(key = "mongodb.database")
    private String mongodbDatabase;

    public MongoDBConfiguration() throws Exception {init();}


    public String getMongodbUri() {
        return mongodbUri;
    }

    public String getMongodbDatabase() {
        return mongodbDatabase;
    }
}
