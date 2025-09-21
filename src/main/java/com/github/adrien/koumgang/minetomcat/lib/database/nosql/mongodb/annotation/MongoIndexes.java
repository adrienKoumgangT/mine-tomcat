package com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface MongoIndexes {

    MongoIndex[] value();

}