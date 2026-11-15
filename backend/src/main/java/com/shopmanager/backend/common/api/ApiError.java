package com.shopmanager.backend.common.api;

import java.time.Instant;
import java.util.List;

public record ApiError(
        Instant timestamp,
        int status,
        String code,
        String message,
        String path,
        List<FieldIssue> errors) {

    public record FieldIssue(String field, String message) {
    }
}