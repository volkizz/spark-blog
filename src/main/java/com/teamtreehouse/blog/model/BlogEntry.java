package com.teamtreehouse.blog.model;

import com.github.slugify.Slugify;

import java.io.IOException;
import java.util.*;

public class BlogEntry {
    private String slug;
    private String title;
    private Date date;
    private String content;
    private Set<String> tags;
    private List<Comment> comments;

    public BlogEntry(String title, Date date, String content, List<Comment> comments, Set<String> tags) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.comments = comments;
        this.tags = tags;
        try {
            Slugify slugify = new Slugify();
            slug = slugify.slugify(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BlogEntry(String title, String content, Set<String> tags) {
        this.title = title;
        this.date = new Date();
        this.content = content;
        this.comments = new ArrayList<>();
        this.tags = tags;
        try {
            Slugify slugify = new Slugify();
            slug = slugify.slugify(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public String getSlug() { return slug; }

    public String getContent() {
        return content;
    }

    public Set<String> getTags() {
        return tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogEntry blogEntry = (BlogEntry) o;

        if (title != null ? !title.equals(blogEntry.title) : blogEntry.title != null) return false;
        return date != null ? date.equals(blogEntry.date) : blogEntry.date == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    public boolean addComment(Comment comment) {
        if (Objects.equals(comment.getName(), "")) {
            comment.setName("Anonymous");
        }
        return !Objects.equals(comment.getContent(), "") && comments.add(comment);
    }
}
