package com.hogar360.houses.houses.infrastructure.exceptionshandlers;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, LocalDateTime timeStamp) {
}
