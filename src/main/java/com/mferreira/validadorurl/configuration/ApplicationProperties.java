package com.mferreira.validadorurl.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApplicationProperties {

    Map<String, String> cache;

    @Autowired
    public ApplicationProperties() {
        this.cache = new HashMap<>();
    }

    public String getProperty(Options opt) {
        String key = opt.name();
        if(cache.get(key) == null) {
            cache.put(key, System.getenv(key));
        }
        String result = cache.get(key);
        return result;
    }

    public void addProperty(String key, String value) {
        this.cache.put(key, value);
    }
}
