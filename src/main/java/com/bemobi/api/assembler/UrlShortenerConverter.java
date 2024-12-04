package com.bemobi.api.assembler;

import com.bemobi.api.dto.input.UrlShortenerInput;
import com.bemobi.api.dto.model.UrlShortenerModel;
import com.bemobi.domain.model.UrlShortener;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UrlShortenerConverter implements Converter<UrlShortener, UrlShortenerModel, UrlShortenerInput>{

    @Autowired
    private ModelMapper modelMapper;

    @Value("${app.shortener.base-url:http://localhost:8080}")
    private String baseUrl;

    @Override
    public UrlShortener toDomainObject(UrlShortenerInput input) {
        return modelMapper.map(input, UrlShortener.class);
    }

    @Override
    public UrlShortenerModel toDto(UrlShortener domain) {
        UrlShortenerModel dto = modelMapper.map(domain, UrlShortenerModel.class);
        dto.setUrl(baseUrl + "/" + domain.getAlias());
        return dto;
    }

    @Override
    public List<UrlShortenerModel> toCollectionDTO(Collection<UrlShortener> list) {
        return list.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void copyToDomainObject(UrlShortenerInput input, UrlShortener domain) {
        modelMapper.map(input, domain);
    }
}
