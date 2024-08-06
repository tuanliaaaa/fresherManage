package com.g11.FresherManage.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class CustomMessageConverterConfiguration {

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public HttpMessageConverter<Object> createGsonHttpMessageConverter() {
        GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter();
        gsonConverter.setGson(gson());
        return gsonConverter;
    }

    @Bean
    public List<HttpMessageConverter<?>> customMessageConverters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(createGsonHttpMessageConverter());
        return converters;
    }
}
