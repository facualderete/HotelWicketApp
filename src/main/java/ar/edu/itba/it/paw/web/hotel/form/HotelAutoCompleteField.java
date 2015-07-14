package ar.edu.itba.it.paw.web.hotel.form;

import ar.edu.itba.it.paw.domain.Hotel;
import ar.edu.itba.it.paw.domain.HotelRepo;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class HotelAutoCompleteField extends AutoCompleteTextField<Hotel> {

    @SpringBean
    HotelRepo hotelRepo;

    public HotelAutoCompleteField(String id, IModel<Hotel> model) {
        super(id, model, Hotel.class, new AutoCompleteSettings());
    }

    @Override
    protected Iterator<Hotel> getChoices(String s) {
        if (Strings.isEmpty(s)) {
            List<Hotel> emptyList = Collections.emptyList();
            return emptyList.iterator();
        }

        List<Hotel> choices = new ArrayList<Hotel>(10);
        List<Hotel> hotels = hotelRepo.getAll();

        for (final Hotel h : hotels) {
            final String hotel = h.getName();

            if (hotel.toUpperCase().startsWith(s.toUpperCase())) {
                choices.add(h);
                if (choices.size() == 10) {
                    break;
                }
            }
        }

        return choices.iterator();
    }
}
