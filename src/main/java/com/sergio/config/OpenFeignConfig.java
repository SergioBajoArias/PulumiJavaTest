package com.sergio.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergio.dto.VanapaganResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class OpenFeignConfig {

    @Bean
    public HttpMessageConverter<VanapaganResponse> getHttpMessageConverter(ObjectMapper objectMapper) {
        return new HttpMessageConverter<>() {

            @Override
            public boolean canRead(Class<?> clazz, MediaType mediaType) {
                return getSupportedMediaTypes().contains(mediaType);
            }

            @Override
            public boolean canWrite(Class<?> clazz, MediaType mediaType) {
                return false;
            }

            @Override
            public List<MediaType> getSupportedMediaTypes() {
                return List.of(MediaType.valueOf("text/plain; charset=utf-8"));
            }

            @Override
            public VanapaganResponse read(Class<? extends VanapaganResponse> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
                String content = new BufferedReader(
                        new InputStreamReader(inputMessage.getBody(), StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));

                return objectMapper.readValue(content, VanapaganResponse.class);
            }

            @Override
            public void write(VanapaganResponse vanapaganResponse, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

            }
        };
    }
}
