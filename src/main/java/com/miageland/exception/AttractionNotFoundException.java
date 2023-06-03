package com.miageland.exception;

import lombok.experimental.StandardException;

@StandardException
public class AttractionNotFoundException extends RuntimeException {
    public AttractionNotFoundException(Long id) {
        super("Could not find attraction " + id);
    }
}
