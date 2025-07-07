package com.ebanx.challenge.adapter.in.web;

import com.ebanx.challenge.domain.model.Account;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventResponse {

    private Account origin;
    private Account destination;

    public EventResponse() {}

    public EventResponse(Account destination) {
        this.destination = destination;
    }

    public EventResponse(Account origin, Account destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public static EventResponse fromSingle(Account account, boolean isOrigin) {
        return isOrigin ? new EventResponse(account, null) : new EventResponse(null, account);
    }

    public static EventResponse fromTransfer(Account origin, Account destination) {
        return new EventResponse(origin, destination);
    }

    }

