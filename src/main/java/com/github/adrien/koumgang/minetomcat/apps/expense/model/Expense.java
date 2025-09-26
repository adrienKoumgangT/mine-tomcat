package com.github.adrien.koumgang.minetomcat.apps.expense.model;

import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.annotation.MongoCollectionName;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.annotation.MongoDateTime;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.annotation.MongoId;
import com.github.adrien.koumgang.minetomcat.lib.model.BaseModel;
import com.github.adrien.koumgang.minetomcat.lib.model.annotation.ModelField;
import org.bson.types.ObjectId;

import java.util.Date;

@MongoCollectionName("expenses")
public class Expense extends BaseModel {

    @MongoId
    private ObjectId expenseId;

    @ModelField("operation_date")
    @MongoDateTime(utc = true)
    private Date operationDate;

    @ModelField("operation")
    private String operation;

    @ModelField("details")
    private String details;

    @ModelField("card")
    private String card;



}
