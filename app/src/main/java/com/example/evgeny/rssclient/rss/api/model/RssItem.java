package com.example.evgeny.rssclient.rss.api.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class RssItem {
    @Element
    public String title;

    @Element
    public String link;

    @Element
    public String guid;

    @Element(required = false)
    public Enclosure enclosure;

    @Element(required = false)
    public String description;

    @Element
    public String pubDate;
}
