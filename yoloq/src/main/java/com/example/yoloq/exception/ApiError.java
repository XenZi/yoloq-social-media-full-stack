package com.example.yoloq.exception;

import java.time.LocalDateTime;

public record ApiError(String path, String message, int statusCode, String localDateTime) {
}
