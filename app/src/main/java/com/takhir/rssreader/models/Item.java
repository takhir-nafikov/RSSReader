package com.takhir.rssreader.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.io.Serializable;

@Root(name = "item", strict = false)
public class Item implements Serializable {

    @Element(name = "title")
    private String title;

    @Element(name = "guid")
    private String guid;

    @Path("link")
    @Text(required=false)
    private String link;

    @Element(name = "description")
    private String description;

    @Element(required = false,name = "enclosure url")
    private String enclosure_url;

    @Element(name = "pubDate")
    private String pubDate;

    @Path("category")
    @Text(required=false)
    private String category;

    @Element(required = false,name = "creator")
    private String creator;

    public Item(){}

    public Item(String title, String guid, String link, String description, String enclosure_url, String pubDate, String category, String creator) {
        this.title = title;
        this.guid = guid;
        this.link = link;
        this.description = description;
        this.enclosure_url = enclosure_url;
        this.pubDate = pubDate;
        this.category = category;
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnclosure_url() {
        return enclosure_url;
    }

    public void setEnclosure_url(String enclosure_url) {
        this.enclosure_url = enclosure_url;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
