package com.mferreira.validadorurl.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MappingUtils {

    public static final Logger LOGGER = LoggerFactory.getLogger(MappingUtils.class.getName());

    public <T> T mapJSONStringToType(String json, Class<T> type) {
        if(json == null || json.isEmpty()) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, type);
        } catch(JsonProcessingException e) {
            LOGGER.warn(String.format("Error mapping %s to %s: %s", json, type.getName(), e.getLocalizedMessage()));
        }
        return null;
    }

    public String mapObjectToJSON(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch(JsonProcessingException e) {
            LOGGER.warn(String.format("Error mapping %s to string: %s", object.getClass().getName(), e.getLocalizedMessage()));
        }
        return "";
    }
}
