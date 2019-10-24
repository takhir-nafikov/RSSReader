package com.takhir.rssreader.models.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.io.Serializable;
import java.util.List;

@Root(name = "channel", strict = false)
public class Channel implements Serializable {

    @Element(name = "title")
    private String title;

    @Path("link")
    @Text(required=false)
    private String link;

    @Element(name = "image")
    private Image image;

    @ElementList(inline = true, name = "item")
    private List<Item> items;

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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
