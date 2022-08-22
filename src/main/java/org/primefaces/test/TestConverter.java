package org.primefaces.test;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("TestConverter")
public class TestConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        int length = value.length();
        int intValue = Integer.parseInt(value);
        GregorianCalendar now = new GregorianCalendar();
        if (length == 1 || length == 2) {
            GregorianCalendar cal = new GregorianCalendar();
            if (intValue < 32) {
                // Assume this month and this year. Go back one (or more) month if it must be a past date
                int monthBack = 0;
                while (true) {
                    cal.set(GregorianCalendar.DAY_OF_MONTH, Integer.parseInt(value));
                    if (!cal.after(now)) {
                        break;
                    }
                    cal = new GregorianCalendar();
                    cal.add(GregorianCalendar.MONTH, --monthBack);
                }
            }
            return cal.getTime();
        } else {
            DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().appendPattern(TestView.dateTimePattern)
                    .toFormatter(Locale.getDefault());
            return Date.from(LocalDate.parse(value,dateTimeFormatter.withResolverStyle(ResolverStyle.STRICT)).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        if (!(value instanceof Date)) {
            return null;
        }
        return new SimpleDateFormat(TestView.dateTimePattern).format(value);
    }
}
