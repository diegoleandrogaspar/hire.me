package com.bemobi.api.controller;

import com.bemobi.api.assembler.UrlShortenerConverter;
import com.bemobi.api.dto.model.UrlShortenerModel;
import com.bemobi.api.dto.model.Statistics;
import com.bemobi.domain.model.UrlShortener;
import com.bemobi.domain.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shortener")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService service;

    @Autowired
    private UrlShortenerConverter converter;

    @PutMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UrlShortenerModel create(@RequestParam String url, @RequestParam(required = false) String customAlias) {
        long startTime = System.currentTimeMillis();

        UrlShortener shortener = new UrlShortener();
        shortener.setOriginalUrl(url);
        shortener.setCustomAlias(customAlias);

        UrlShortener savedUrl = service.save(shortener);
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        Statistics statistics = new Statistics();
        statistics.setTimeTaken(timeTaken + "ms");

        UrlShortenerModel response = converter.toDto(savedUrl);
        response.setStatistics(statistics);
        return response;
    }

    @GetMapping("/{alias}")
    public ResponseEntity<?> retrieveUrl(@PathVariable String alias) {
        UrlShortener shortener = service.findOrFail(alias);
        String originalUrl = shortener.getOriginalUrl();

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", originalUrl)
                .build();
    }
}