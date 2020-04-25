package com.mferreira.validadorurl.service;

import com.mferreira.validadorurl.dto.ValidationInput;
import com.mferreira.validadorurl.dto.ValidationOutput;
import com.mferreira.validadorurl.model.Whitelist;
import com.mferreira.validadorurl.repository.GlobalWhitelistRepository;
import com.mferreira.validadorurl.repository.WhitelistRepository;
import com.mferreira.validadorurl.utils.MappingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RabbitListener(queues = "${VALIDATION_QUEUE}")
public class ValidationQueueListener implements QueueListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(ValidationQueueListener.class.getName());
    WhitelistRepository whitelistRepository;
    GlobalWhitelistRepository globalWhitelistRepository;
    PublisherService publisherService;

    public ValidationQueueListener() {}

    @Autowired
    public ValidationQueueListener(WhitelistRepository whitelistRepository,
                                   GlobalWhitelistRepository globalWhitelistRepository,
                                   PublisherService publisherService) {
        this.whitelistRepository = whitelistRepository;
        this.globalWhitelistRepository = globalWhitelistRepository;
        this.publisherService = publisherService;
    }

    @Override
    @RabbitHandler
    public void onReceiveMessage(String json) {
        LOGGER.info("Received message: " + json);
        validate(json);
    }

    public void validate(String json) {
        ValidationInput input = new MappingUtils()
                .mapJSONStringToType(json, ValidationInput.class);
        if(input == null) {
            return;
        }
        List regexList = fetchFromDatabase(input);
        ValidationOutput output = validate(regexList, input);
        publisherService.publish(new MappingUtils().mapObjectToJSON(output));
    }

    public ValidationOutput validate(List<Whitelist> regexList, ValidationInput input) {
        boolean isMatch = regexList.stream().anyMatch(r -> r.getRegExp().matches(input.getUrl()));
        ValidationOutput output = new ValidationOutput(isMatch, input.getUrl(), input.getCorrelationId());
        LOGGER.info("Validation output: " + output.isMatch());
        return output;
    }

    public List<Whitelist> fetchFromDatabase(ValidationInput object) {
        List<Whitelist> result = new ArrayList();
        if(object.getClient() == null) {
            Whitelist forClient = whitelistRepository.findByClient(object.getClient());
            if(forClient != null) {
                result.add(forClient);
            }
        }

        result.addAll(globalWhitelistRepository.findAll()
                .stream().map(m -> new Whitelist(null, m.getRegExp()))
                .collect(Collectors.toList()));

        return result;
    }
}
