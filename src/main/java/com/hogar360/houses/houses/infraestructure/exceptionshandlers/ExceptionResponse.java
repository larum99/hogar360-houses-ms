package com.hogar360.houses.houses.infraestructure.exceptionshandlers;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, LocalDateTime timeStamp) {
}
