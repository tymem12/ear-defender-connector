package com.eardefender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TimestampServiceImpl implements TimestampService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @Override
    public String getCurrentTimestampString() {
        return getCurrentTimestamp().format(FORMATTER);
    }

    @Override
    public OffsetDateTime getCurrentTimestamp() {
        return OffsetDateTime.now();
    }

    @Override
    public OffsetDateTime getTimestampFromString(String timestamp) {
        return OffsetDateTime.parse(timestamp, FORMATTER);
    }
}
