package com.bemobi.repositories;

import com.bemobi.domain.model.UrlShortener;
import com.bemobi.domain.repository.UrlShortenerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource("/application-test.properties")
public class UrlShortenerRepositoryTest {

    @Autowired
    private UrlShortenerRepository repository;

    @Test
    public void testSaveAndFindByAlias() {
        UrlShortener urlShortener = new UrlShortener("http://www.padaria.com", "padaria", null);
        repository.save(urlShortener);

        Optional<UrlShortener> result = repository.findByAlias("padaria");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("http://www.padaria.com", result.get().getOriginalUrl());
    }

    @Test
    public void testExitsByAlias() {
        UrlShortener urlShortener = new UrlShortener("http://mercado.com", "mercado", null);
        repository.save(urlShortener);

        boolean exits = repository.existsByAlias("mercado");
        boolean notExits = repository.existsByAlias("inexistente");
        Assertions.assertFalse(notExits);
    }

}
