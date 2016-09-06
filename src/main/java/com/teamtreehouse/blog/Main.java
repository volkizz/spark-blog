package com.teamtreehouse.blog;

import com.teamtreehouse.blog.MockDatabase.Database;
import com.teamtreehouse.blog.dao.BlogDao;
import com.teamtreehouse.blog.model.BlogDAOImpl;
import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.model.Comment;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");
        BlogDao dao = new BlogDAOImpl();
        Database db = new Database();
        db.initializeData((BlogDAOImpl) dao);

        before((req, res) ->{
            if(req.cookie("password") != null){
                req.attribute("password", req.cookie("password"));
            }
        });
        before("index/new/edit", (req, res) -> {
            if(req.attribute("password") == null || !req.attribute("password").equals("admin")){
                res.redirect("/sign-in");
                halt();
            }
        });

        before("/index/:slug/edit", (req, res) ->{
            String title = req.params("slug");
            if(req.attribute("password") == null || !req.attribute("password").equals("admin")){
                if(Objects.equals(title, "new")){
                    res.redirect("/sign-in");
                    halt();
                }else{
                res.redirect("/index/"+title+"/sign-in");
                halt();}
            }
        });

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blogEntries", dao.findAllEntries());
           return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/index/:slug", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blog", dao.findEntryBySlug(req.params("slug")));
            return new ModelAndView(model, "blog.hbs");
        }, new HandlebarsTemplateEngine());

        post("/index/:slug/comment", (req, res) -> {
            String title = req.params("slug");
            BlogEntry blog = dao.findEntryBySlug(title);
            Comment comment = new Comment(req.queryParams("name"), req.queryParams("comment"));
            blog.addComment(comment);
            res.redirect("/index/"+title);
            return null;
        });

        get("/index/new/edit", (req, res) -> new ModelAndView(null, "new.hbs"), new HandlebarsTemplateEngine());

        post("/index/new/entry", (req, res) -> {
            BlogEntry blog = new BlogEntry(req.queryParams("title"), req.queryParams("content"),req.queryParams("tag"));
            dao.addEntry(blog);
            res.redirect("/");
            return null;
        });

        get("/index/:slug/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blog", dao.findEntryBySlug(req.params("slug")));
            return new ModelAndView(model, "edit.hbs");
        }, new HandlebarsTemplateEngine());

        post("/index/:slug/edit/save", (req, res) ->{
            String title = req.params("slug");
            BlogEntry blog = dao.findEntryBySlug(title);
            blog.setTitle(req.queryParams("title"));
            blog.setContent(req.queryParams("content"));
            blog.setTag(req.queryParams("tag"));
            res.redirect("/index/"+title);
            return null;
        });

        post("/index/:slug/edit/delete", (req, res) -> {
            String title = req.params("slug");
            BlogEntry blog = dao.findEntryBySlug(title);
            dao.deleteEntry(blog);
            res.redirect("/");
            return null;
        });

        get("/index/:slug/sign-in", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            BlogEntry blogEntry = dao.findEntryBySlug(req.params("slug"));
            model.put("password", req.attribute("password"));
            model.put("blog", blogEntry);
            return new ModelAndView(model, "password.hbs");
        }, new HandlebarsTemplateEngine());


       post("/index/:slug/password", (req, res) ->{
           String title = req.params("slug");
           res.cookie("password", req.queryParams("password"));
           res.redirect("/index/"+title+"/edit");
           return null;
       });



        get("/sign-in", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            BlogEntry blogEntry = new BlogEntry("", "", "");
            blogEntry.setSlug("new");
            model.put("password", req.attribute("password"));
            model.put("blog", blogEntry);
            return new ModelAndView(model, "password.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
