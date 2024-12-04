package com.bemobi.api.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class UrlShortenerInput {

    @NotBlank
    private String originalUrl;

    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "O alias personalizado deve conter apenas letras e números.")
    private String customAlias;


}
