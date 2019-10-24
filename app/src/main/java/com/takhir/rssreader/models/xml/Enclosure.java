package com.takhir.rssreader.models.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "enclosure", strict = false)
public class Enclosure implements Serializable {

    @Attribute
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
