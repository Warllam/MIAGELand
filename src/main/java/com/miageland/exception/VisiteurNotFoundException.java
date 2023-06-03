package com.miageland.exception;

import lombok.experimental.StandardException;

@StandardException
public class VisiteurNotFoundException extends RuntimeException {
    public VisiteurNotFoundException(Long id) {
        super("Could not find visiteur " + id);
    }
}
