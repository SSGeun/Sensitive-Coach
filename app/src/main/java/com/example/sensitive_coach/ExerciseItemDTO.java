package com.example.sensitive_coach;

import android.graphics.drawable.Drawable;

public class ExerciseItemDTO {
    Drawable image;
    String title;
    String type;
    String recommend;
    public ExerciseItemDTO(Drawable inImage, String inTitle, String inType, String inRecommend){
        this.image = inImage;
        this.title = inTitle;
        this.type = inType;
        this.recommend = inRecommend;
    }

    public ExerciseItemDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String sub) {
        this.type = type;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
