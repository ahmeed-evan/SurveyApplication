package com.example.SurveyApplication.Model;

public class QuestionCategoryModel {
    private String categoryImageURL;
    private String categoryName;

    public QuestionCategoryModel() {
    }

    public String getCategoryImageURL() {
        return categoryImageURL;
    }

    public void setCategoryImageURL(String categoryImageURL) {
        this.categoryImageURL = categoryImageURL;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public QuestionCategoryModel(String categoryImageURL, String categoryName) {
        this.categoryImageURL = categoryImageURL;
        this.categoryName = categoryName;
    }
}
