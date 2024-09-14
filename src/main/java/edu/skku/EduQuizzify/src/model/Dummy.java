package edu.skku.EduQuizzify.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Dummy {

    private String title;
    private String type;

    public Dummy(String title, String type) {
        this.title = title;
        this.type = type;
    }
}
