package com.ebanx.challenge.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class EventRequest {
    private String type;
    private String destination;
    private String origin;
    private int amount;

}
