package com.example.evgeny.rssclient.rss.api.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "image", strict = false)
public class RssImage {
    @Element
    public String link;

    @Element
    public String url;

    @Element
    public String title;
}