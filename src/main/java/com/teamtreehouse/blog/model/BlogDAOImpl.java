package com.teamtreehouse.blog.model;

import com.teamtreehouse.blog.dao.BlogDao;

import java.util.ArrayList;
import java.util.List;

public class BlogDAOImpl implements BlogDao{
    private List<BlogEntry> blogEntries;

    public BlogDAOImpl() {
        blogEntries = new ArrayList<>();
    }

    @Override
    public boolean addEntry(BlogEntry blogEntry) {
        return blogEntries.add(blogEntry);
    }

    @Override
    public List<BlogEntry> findAllEntries() {
        return new ArrayList<>(blogEntries);
    }

    @Override
    public BlogEntry findEntryBySlug(String slug) {
        return blogEntries.stream()
                .filter(blogEntry -> blogEntry.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public boolean deleteEntry(BlogEntry blogEntry) {
        return blogEntries.remove(blogEntry);
    }
}
