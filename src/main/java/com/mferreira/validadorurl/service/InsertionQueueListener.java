package com.mferreira.validadorurl.service;

import com.mferreira.validadorurl.model.GlobalWhitelist;
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

@Service
@RabbitListener(queues = "${INSERTION_QUEUE}")
public class InsertionQueueListener implements QueueListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsertionQueueListener.class.getName());

    WhitelistRepository whitelistRepository;
    GlobalWhitelistRepository globalWhitelistRepository;

    public InsertionQueueListener() {}

    @Autowired
    public InsertionQueueListener(WhitelistRepository whitelistRepository,
                                  GlobalWhitelistRepository globalWhitelistRepository) {
        this.whitelistRepository = whitelistRepository;
        this.globalWhitelistRepository = globalWhitelistRepository;
    }

    @Override
    @RabbitHandler
    public void onReceiveMessage(String json) {
        LOGGER.info("Received message: " + json);
        saveMessage(json);
    }

    public void saveMessage(String json) {
        Whitelist object = new MappingUtils().mapJSONStringToType(json, Whitelist.class);
        if(object == null) {
            return;
        }

        if(object.getClient() == null) {
            GlobalWhitelist globalWhitelist = new GlobalWhitelist(object.getRegExp());
            globalWhitelistRepository.save(globalWhitelist);
        } else {
            whitelistRepository.save(object);
        }
    }
}
