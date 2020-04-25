package com.mferreira.validadorurl.utils;

import com.mferreira.validadorurl.dto.ValidationInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MappingUtilsTest {

    @Test
    public void shouldMapJSONStringToObject() {
        String input = "{\"client\": \"Client Corp\", \"url\": \"www.clientsite.com\", \"correlationId\": 10}";
        ValidationInput object = new MappingUtils().mapJSONStringToType(input, ValidationInput.class);
        assertEquals("www.clientsite.com", object.getUrl());
        assertEquals("Client Corp", object.getClient());
        assertEquals(10, object.getCorrelationId());
    }

    @Test
    public void nullOrEmptyObjectShouldReturnNull() {
        MappingUtils mapper = new MappingUtils();
        ValidationInput object = mapper.mapJSONStringToType(null, ValidationInput.class);
        assertEquals(null, object);
        object = mapper.mapJSONStringToType("", ValidationInput.class);
    }
}