package com.jbs.general.model.response.introduction;

public  class IntroductionData {
    private String title;
    private String description;
    private int introImage;

    public IntroductionData(String title, String description, int introImage) {
        this.title = title;
        this.description = description;
        this.introImage = introImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIntroImage() {
        return introImage;
    }

    public void setIntroImage(int introImage) {
        this.introImage = introImage;
    }

    @Override
    public String toString() {
        return "IntroductionData{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", introImage=" + introImage +
                '}';
    }
}
