package com.mferreira.validadorurl.dto;

public class ValidationInput {
    private String client;
    private String url;
    private Integer correlationId;

    public ValidationInput() {}

    public ValidationInput(String client, String url, Integer correlationId) {
        this.client = client;
        this.url = url;
        this.correlationId = correlationId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(Integer correlationId) {
        this.correlationId = correlationId;
    }
}
