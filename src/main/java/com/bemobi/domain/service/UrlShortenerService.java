package com.bemobi.domain.service;

import com.bemobi.domain.exception.CustomAliasAlreadyExistsException;
import com.bemobi.domain.exception.UrlNotFoundException;
import com.bemobi.domain.model.UrlShortener;
import com.bemobi.domain.repository.UrlShortenerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlShortenerRepository repository;

    @Transactional
    public UrlShortener save(UrlShortener urlShortener){

        if (urlShortener.getCustomAlias() != null) {
            if (repository.existsByAlias(urlShortener.getCustomAlias())) {
                throw new CustomAliasAlreadyExistsException();
        }
            urlShortener.setAlias(urlShortener.getCustomAlias());
        }
        else {
            urlShortener.setAlias(generateRandomAlias());
        }
        return repository.save(urlShortener);
    }

    public UrlShortener findOrFail(String alias){
        return repository.findByAlias(alias)
                .orElseThrow(() -> new UrlNotFoundException(alias));
    }

    public String generateRandomAlias() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}