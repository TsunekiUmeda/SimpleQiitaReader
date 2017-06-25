package com.sample.tumeda.simpleqiitareader.data.rss;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "feed", strict = false)
public class Feed {
    @Element(name = "id", required = false)
    private String id;

    @Element(name = "title", required = false)
    private String title;

    @Element(name = "updated", required = false)
    private String updated;

    @ElementList(inline = true, required = false)
    private List<Entry> entry;

    @Element(name = "xmlns", required = false)
    private String xmlns;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

}