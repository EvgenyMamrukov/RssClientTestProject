package com.example.evgeny.rssclient.rss.api.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "enclosure", strict = false)
public class Enclosure {
    @Attribute(name = "url")
    public String url;

    @Attribute(name = "length")
    public long length;

    @Attribute(name = "type")
    public String type;
}
