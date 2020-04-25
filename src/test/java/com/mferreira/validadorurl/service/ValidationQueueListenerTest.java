package com.mferreira.validadorurl.service;

import com.mferreira.validadorurl.dto.ValidationInput;
import com.mferreira.validadorurl.model.Whitelist;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
class ValidationQueueListenerTest {

    private ValidationQueueListener listener = new ValidationQueueListener();

    @Test
    public void shouldValidateList() {
        List<Whitelist> list = Arrays.asList(
                new Whitelist("Client1", "www.client1.com"),
                new Whitelist("Client1", "www.client1.1.com"),
                new Whitelist("Client1", "www.client1.2.com")
        );

        ValidationInput input = new ValidationInput("Client1", "www.client1.com", 10);
        assertTrue(listener.validate(list, input).isMatch());
        input.setUrl("www.client1.1.com");
        assertTrue(listener.validate(list, input).isMatch());
        input.setUrl("www.c1ient1.h4x0r.com");
        assertFalse(listener.validate(list, input).isMatch());
    }
}