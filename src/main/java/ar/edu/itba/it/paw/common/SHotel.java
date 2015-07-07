package ar.edu.itba.it.paw.common;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.Hotel;
import org.apache.wicket.model.IModel;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SHotel implements Serializable {

    private IModel<Hotel> hotelModel = new EntityModel<Hotel>(Hotel.class);


    public SHotel(Hotel user) {
        hotelModel.setObject(user);
    }

    public SHotel(IModel<Hotel> hotelModel) {
        this.hotelModel = hotelModel;
    }

    public IModel<Hotel> getModel() {
        return hotelModel;
    }
}
