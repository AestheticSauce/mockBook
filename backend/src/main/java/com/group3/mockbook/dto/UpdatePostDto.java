package com.group3.mockbook.dto;

public class UpdatePostDto {
    private String title;
    private String content;

    public UpdatePostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public UpdatePostDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
