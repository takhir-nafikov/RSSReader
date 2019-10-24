package com.takhir.rssreader.models.xml;

import com.takhir.rssreader.models.xml.Channel;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "rss", strict = false)
public class RSS implements Serializable {

    @Element(name = "channel")
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
