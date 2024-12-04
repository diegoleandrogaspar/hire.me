package com.bemobi.api.dto.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UrlShortenerModel {

    private String alias;
    private String url;
    private String originalUrl;
    private Statistics statistics;

}
