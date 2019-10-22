package com.takhir.rssreader.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "image", strict = false)
public class Image implements Serializable {

    @Element(name = "link")
    private String link;

    @Element(name = "url")
    private String url;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
