package com.teamtreehouse.blog.model;

import java.util.Date;

public class Comment {
    private String name;
    private Date date;
    private String content;

    public Comment(String name, String content) {
        this.name = name;
        this.date = new Date();
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (name != null ? !name.equals(comment.name) : comment.name != null) return false;
        if (date != null ? !date.equals(comment.date) : comment.date != null) return false;
        return content != null ? content.equals(comment.content) : comment.content == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
