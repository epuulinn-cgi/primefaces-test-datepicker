package org.primefaces.test;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Data;

@Data
@Named
@ViewScoped
public class TestView implements Serializable {
    
    private String string;
    private Integer integer;
    private BigDecimal decimal;
    private LocalDateTime localDateTime;

    private Date date;
    private Date minDate;

    public static final String dateTimePattern = "dd.MM.yyyy HH:mm:ss";


    public String getDateTimePattern() {
        return dateTimePattern;
    }

    @PostConstruct
    public void init() {

        string = "Welcome to PrimeFaces!!!";
        minDate = new Date();
    }

    public String getCurrentLocale() {
        return "en";
    }

}
