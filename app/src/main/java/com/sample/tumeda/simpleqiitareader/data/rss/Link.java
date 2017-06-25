package com.sample.tumeda.simpleqiitareader.data.rss;

import org.simpleframework.xml.Attribute;

public class Link {
    @Attribute(name = "rel", required = false)
    private String rel;

    @Attribute(name = "type", required = false)
    private String type;

    @Attribute(name = "href", required = false)
    private String href;

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
