package com.takhir.rssreader.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "enclosure", strict = false)
public class Enclosure {

    @Attribute
    private String url;

    public String getUrl() {
        return url;
    }
}
