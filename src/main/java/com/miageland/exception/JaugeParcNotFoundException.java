package com.miageland.exception;

import java.time.LocalDate;
import java.util.Date;

public class JaugeParcNotFoundException extends RuntimeException{

    public JaugeParcNotFoundException(LocalDate date) { super("Could not find jaugeParc " + date) ;}

}
