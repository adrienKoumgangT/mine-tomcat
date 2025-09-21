package com.github.adrien.koumgang.minetomcat.apps.test.model;


import com.github.adrien.koumgang.minetomcat.apps.test.view.TestView;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.annotation.*;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.utils.StringIdConverter;
import com.github.adrien.koumgang.minetomcat.lib.model.BaseModel;
import com.github.adrien.koumgang.minetomcat.lib.model.annotation.ModelField;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@MongoCollectionName("tests")
@MongoIndex(fields = {"is_active:1", "created_at:-1"})
@MongoIndex(fields = {"name:1"}, unique = true)   // unique name
@MongoIndex(fields = {"description:1"})           // another single index
public class Test extends BaseModel {

    @MongoId
    private ObjectId testId;

    @ModelField("name")
    @MongoIndex(unique = true)
    private String name;

    @ModelField("description")
    private String description;

    @ModelField("is_active")
    private Boolean isActive;

    @MongoEmbedded("embed")
    private TestEmbed embed;

    @MongoEmbeddedList("list_embed")
    private List<TestEmbed> listEmbed;


    public Test() {}

    public Test(TestView testView) {
        this.testId         = StringIdConverter.getInstance().toObjectId(testView.getIdTest());
        this.name           = testView.getName();
        this.description    = testView.getDescription();
        this.isActive       = testView.getActive();

        if(testView.getEmbed() != null) this.embed = new TestEmbed(testView.getEmbed());
        if(testView.getListEmbed() != null) this.listEmbed = testView.getListEmbed().stream().map(TestEmbed::new).toList();
    }

    public void update(TestView testView) {
        this.description    = testView.getDescription();
        this.isActive       = testView.getActive();
    }

    public ObjectId getTestId() {
        return testId;
    }

    public void setTestId(ObjectId testId) {
        this.testId = testId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public TestEmbed getEmbed() {
        return embed;
    }

    public void setEmbed(TestEmbed embed) {
        this.embed = embed;
    }

    public List<TestEmbed> getListEmbed() {
        return listEmbed;
    }

    public void setListEmbed(List<TestEmbed> listEmbed) {
        this.listEmbed = listEmbed;
    }

    public void addEmbed(TestEmbed testEmbed) {
        if(this.listEmbed == null) {
            this.listEmbed = new ArrayList<>();
        }
        this.listEmbed.add(testEmbed);
    }
}
