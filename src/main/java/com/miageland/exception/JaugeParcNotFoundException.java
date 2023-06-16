package com.miageland.exception;

import java.util.Date;

public class JaugeParcNotFoundException extends RuntimeException{

    public JaugeParcNotFoundException(String message) {
        super(message);
    }

    public JaugeParcNotFoundException(Date date) { super("Could not find jaugeParc " + date) ;}

}
