package com.example.evgeny.rssclient.rss.api.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class RssFeed {
    @Element
    public RssChannel channel;
}
