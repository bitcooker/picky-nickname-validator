package com.example.bdc_backend.model;

import java.util.List;

public record NegotiationResponse(
        List<ReasonCode> reasons,
        String explanation,
        List<String> suggestions
) {}
