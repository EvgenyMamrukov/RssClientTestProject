package com.example.evgeny.rssclient.rss.api.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "channel", strict = false)
public class RssChannel
{
    @Element
    public String title;

    @Element
    public RssImage image;

    @ElementList(inline = true, required = false)
    public List<RssItem> item;
}
