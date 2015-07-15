package ar.edu.itba.it.paw.web.hotel.form;

import ar.edu.itba.it.paw.domain.Hotel;
import ar.edu.itba.it.paw.domain.HotelRepo;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

import java.util.Locale;

public class HotelConverter implements IConverter<Hotel> {

    @SpringBean
    HotelRepo hotelRepo;

    @Override
    public Hotel convertToObject(String s, Locale locale) throws ConversionException {
        if (hotelRepo == null) {
            Injector.get().inject(this);
        }
        return hotelRepo.getByName(s);
    }

    @Override
    public String convertToString(Hotel hotel, Locale locale) {
        return hotel.getName();
    }
}