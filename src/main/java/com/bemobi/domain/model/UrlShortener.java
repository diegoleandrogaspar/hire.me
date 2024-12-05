package com.bemobi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "url_shorteners")
public class UrlShortener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalUrl;

    @Column(unique = true, name = "url_alias", nullable = false)
    private String alias;

    @Column(unique = true)
    private String customAlias;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime createdAt;

    public UrlShortener() {
    }

    public UrlShortener(String originalUrl, String alias, String customAlias) {
        this.originalUrl = originalUrl;
        this.alias = alias;
        this.customAlias = customAlias;
    }
}