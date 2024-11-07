package com.eardefender.service;

import java.time.OffsetDateTime;

public interface TimestampService {

    String getCurrentTimestampString();

    OffsetDateTime getCurrentTimestamp();

    OffsetDateTime getTimestampFromString(String timestamp);
}
