package ar.edu.itba.it.paw.web.hotel;

import ar.edu.itba.it.paw.domain.Hotel;
import ar.edu.itba.it.paw.domain.HotelRepo;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class ListPage extends BasePage{

    private static final int LIST_SIZE = 5;

    private static final long serialVersionUID = 1L;
    @SpringBean
    private HotelRepo hotelRepo;

    public ListPage(){










        final IModel<List<Hotel>> hotels = new LoadableDetachableModel<List<Hotel>>() {
            private static final long serialVersionUID = 1L;

            @Override
            protected List<Hotel> load() {
                return (List<Hotel>)hotelRepo.getNHotelsOrderedByComments(LIST_SIZE, HotelWicketSession.get().isAdmin());
            }
        };








    }



}
