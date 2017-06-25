package com.sample.tumeda.simpleqiitareader.data.rss;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "entry", strict = false)
public class Entry {
    @Element(name = "id")
    private String id;

    @Element(name = "author")
    private Author author;

    @Element(name = "title")
    private String title;

    @Element(name = "updated")
    private String updated;

    @Element(name = "link")
    private Link link;

    @Element(name = "published")
    private String published;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

}
