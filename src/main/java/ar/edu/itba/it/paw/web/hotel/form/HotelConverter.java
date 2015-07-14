package ar.edu.itba.it.paw.web.hotel.form;

import ar.edu.itba.it.paw.domain.Hotel;
import ar.edu.itba.it.paw.domain.HotelRepo;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class HotelConverter implements IConverter<Hotel> {

    @Autowired
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