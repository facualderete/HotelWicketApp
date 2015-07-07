package ar.edu.itba.it.paw.web.hotel;

import ar.edu.itba.it.paw.common.SHotel;
import ar.edu.itba.it.paw.domain.Hotel;
import ar.edu.itba.it.paw.domain.HotelRepo;
import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;
import com.googlecode.wicket.jquery.ui.form.autocomplete.AutoCompleteTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("serial")
public class HotelAutoCompleteTextField extends AutoCompleteTextField<SHotel> implements Serializable {

    @SpringBean
    HotelRepo hotelRepo;

    public HotelAutoCompleteTextField(String id, IModel<SHotel> iModel) {
        super(id, iModel, new SHotelRenderer(), SHotel.class);
    }

    @Override
    protected List<SHotel> getChoices(String input) {
        if (Strings.isEmpty(input) || input.length() < 1) {
            return Collections.emptyList();
        }
        List<SHotel> results = new ArrayList<SHotel>();
        input = ("*" + input + "*").replace('*', '%');
        for (Hotel hotel : hotelRepo.getAll()) {
            if (hotel.getName().toUpperCase().contains(input.toUpperCase())) {
                results.add(new SHotel(hotel));
                if (results.size() == 10) {
                    break;
                }
            }
        }
        return results;
    }

    public Hotel getSelection() {
        return getModelObject().getModel().getObject();
    }

    public IModel<Hotel> getSelected() {
        return  getModelObject().getModel();
    }


    private static class SHotelRenderer implements ITextRenderer<SHotel> {

        @Override
        public String getText(SHotel object) {
            if (object == null ) {
                return "";
            }
            return object.getModel().getObject().getName();
        }

        @Override
        public String getText(SHotel object, String expression) {
            return getText(object);
        }

        @Override
        public boolean match(SHotel sHotel, String s, boolean b) {
            return false;
        }

        @Override
        public String getTextField() {
            return null;
        }
    }
}
