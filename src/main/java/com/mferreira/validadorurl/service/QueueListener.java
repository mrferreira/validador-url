package com.mferreira.validadorurl.service;

public interface QueueListener {

    public void onReceiveMessage(String json);
}
