package com.bemobi.controller;

import com.bemobi.api.assembler.UrlShortenerConverter;
import com.bemobi.api.controller.UrlShortenerController;
import com.bemobi.api.dto.model.Statistics;
import com.bemobi.api.dto.model.UrlShortenerModel;
import com.bemobi.domain.model.UrlShortener;
import com.bemobi.domain.service.UrlShortenerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class UrlShortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UrlShortenerService service;

    @Mock
    private UrlShortenerConverter converter;

    @InjectMocks
    private UrlShortenerController controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testCreateShortUrlWithCustomAlias() throws Exception {
        String url = "http://www.bemobi.com";
        String alias = "bemobi";

        UrlShortener urlShortener = new UrlShortener(url, alias, null);

        when(service.save(any(UrlShortener.class))).thenReturn(urlShortener);
        UrlShortenerModel urlShortenerModel = new UrlShortenerModel();
        urlShortenerModel.setAlias(alias);
        urlShortenerModel.setUrl("http://localhost:8080/" + alias);
        urlShortenerModel.setOriginalUrl(url);
        urlShortenerModel.setStatistics(new Statistics());
        urlShortenerModel.getStatistics().setTimeTaken("100ms");
        when(converter.toDto(urlShortener)).thenReturn(urlShortenerModel);

        mockMvc.perform(put("/shortener/create")
                        .param("url", url)
                        .param("customAlias", alias)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.alias").value(alias))
                .andExpect(jsonPath("$.url").value("http://localhost:8080/" + alias))
                .andExpect(jsonPath("$.statistics.timeTaken").exists());
    }

    @Test
    public void testCreateShortUrlWIthDefaultAlias() throws Exception {
        String url = "http://www.bemobi.com";
        String customAlias = null;

        UrlShortener urlShortener = new UrlShortener(url, "defaultAlias", customAlias);

        when(service.save(any(UrlShortener.class))).thenReturn(urlShortener);
        UrlShortenerModel urlShortenerModel = new UrlShortenerModel();
        urlShortenerModel.setAlias("defaultAlias");
        urlShortenerModel.setUrl("http://localhost:8080/defaultAlias");
        urlShortenerModel.setOriginalUrl(url);
        urlShortenerModel.setStatistics(new Statistics());
        urlShortenerModel.getStatistics().setTimeTaken("150ms");
        when(converter.toDto(urlShortener)).thenReturn(urlShortenerModel);

        mockMvc.perform(put("/shortener/create")
                        .param("url", url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.alias").value("defaultAlias"))
                .andExpect(jsonPath("$.url").value("http://localhost:8080/defaultAlias"))
                .andExpect(jsonPath("$.statistics.timeTaken").exists());
    }

}