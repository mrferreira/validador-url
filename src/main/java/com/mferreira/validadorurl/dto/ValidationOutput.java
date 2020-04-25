package com.mferreira.validadorurl.dto;

public class ValidationOutput {
    private boolean match;
    private String regex;
    private Integer correlationId;

    public ValidationOutput() {}

    public ValidationOutput(boolean match, String regex,
                            Integer correlationId) {
        this.match = match;
        this.regex = regex;
        this.correlationId = correlationId;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Integer getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(Integer correlationId) {
        this.correlationId = correlationId;
    }
}
