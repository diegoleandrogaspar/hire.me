package com.bemobi.services;

import com.bemobi.domain.exception.CustomAliasAlreadyExistsException;
import com.bemobi.domain.exception.UrlNotFoundException;
import com.bemobi.domain.model.UrlShortener;
import com.bemobi.domain.repository.UrlShortenerRepository;
import com.bemobi.domain.service.UrlShortenerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UrlShortenerServiceTest {

    @Mock
    private UrlShortenerRepository repository;

    @InjectMocks
    private UrlShortenerService service;

    @Test
    public void testSaveWithCustomAlias() {
        String customAlias = "customAlias";
        UrlShortener urlShortener = new UrlShortener("http://www.example.com", "example", customAlias);

        when(repository.existsByAlias(customAlias)).thenReturn(false);
        when(repository.save(urlShortener)).thenReturn(urlShortener);

        UrlShortener savedUrl = service.save(urlShortener);

        Assertions.assertNotNull(savedUrl);
        Assertions.assertEquals(customAlias, savedUrl.getAlias());

        verify(repository, times(1)).save(savedUrl);
    }

    @Test
    public void testSaveWithRandomAliasWhenCustomAliasExists() {
        String customAlias = "customAlias";
        UrlShortener urlShortener = new UrlShortener("http://www.bemobi.com", "bemobi", customAlias);

        when(repository.existsByAlias(customAlias)).thenReturn(true);

        Assertions.assertThrows(CustomAliasAlreadyExistsException.class, () -> {
            service.save(urlShortener);
        });
    }

    @Test
    public void testFindOrFail() {
        String alias = "validAlias";
        UrlShortener urlShortener = new UrlShortener("http://www.bemobi.com", alias, null);

        when(repository.findByAlias(alias)).thenReturn(Optional.of(urlShortener));

        UrlShortener foundUrl = service.findOrFail(alias);

        Assertions.assertNotNull(foundUrl);
        Assertions.assertEquals(alias, foundUrl.getAlias());
    }

    @Test
    public void testFindOrFailThrowsExceptionWhenUrlNotFound() {
        String alias = "invalidAlias";

        when(repository.findByAlias(alias)).thenReturn(Optional.empty());

        Assertions.assertThrows(UrlNotFoundException.class, () -> {
            service.findOrFail(alias);
        });
    }
}