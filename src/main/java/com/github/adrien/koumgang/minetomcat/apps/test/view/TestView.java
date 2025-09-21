package com.github.adrien.koumgang.minetomcat.apps.test.view;

import io.swagger.v3.oas.annotations.media.Schema;
import com.github.adrien.koumgang.minetomcat.apps.test.model.Test;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.mongodb.utils.StringIdConverter;
import com.github.adrien.koumgang.minetomcat.lib.view.BaseView;
import com.github.adrien.koumgang.minetomcat.lib.view.Required;

import java.util.List;

public class TestView extends BaseView {

    @Schema(title = "Test ID", example = "68b28e50c8c86a733de632d8")
    private String idTest;

    @Required
    @Schema(title = "Product name", example = "Test 1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    private String description;

    private Boolean isActive;

    private TestEmbedView embed;

    private List<TestEmbedView> listEmbed;

    public TestView() {}

    public TestView(Test test) {
        this.idTest = StringIdConverter.getInstance().fromObjectId(test.getTestId());

        this.name           = test.getName();
        this.description    = test.getDescription();
        this.isActive       = test.getActive();

        if(test.getEmbed() != null) {
            this.embed = new TestEmbedView(test.getEmbed());
        }
        if(test.getListEmbed() != null) {
            this.listEmbed = test.getListEmbed().stream().map(TestEmbedView::new).toList();

        }
    }

    public String getIdTest() {
        return idTest;
    }

    public void setIdTest(String idTest) {
        this.idTest = idTest;
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

    public TestEmbedView getEmbed() {
        return embed;
    }

    public void setEmbed(TestEmbedView embed) {
        this.embed = embed;
    }

    public List<TestEmbedView> getListEmbed() {
        return listEmbed;
    }

    public void setListEmbed(List<TestEmbedView> listEmbed) {
        this.listEmbed = listEmbed;
    }
}
