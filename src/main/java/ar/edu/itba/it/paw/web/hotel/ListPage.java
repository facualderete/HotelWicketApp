package ar.edu.itba.it.paw.web.hotel;

import ar.edu.itba.it.paw.domain.Hotel;
import ar.edu.itba.it.paw.domain.HotelRepo;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class ListPage extends BasePage{

    private static final int LIST_SIZE = 5;

    private static final long serialVersionUID = 1L;
    @SpringBean
    private HotelRepo hotelRepo;

    public ListPage(){

        final IModel<List<Hotel>> hotelsListModel = new LoadableDetachableModel<List<Hotel>>() {
            @Override
            protected List<Hotel> load() {
                return (List<Hotel>)hotelRepo.getNHotelsOrderedByComments(LIST_SIZE, HotelWicketSession.get().isAdmin());
            }
        };

        add(new PropertyListView<Hotel>("hotelsList", hotelsListModel) {
            @Override
            protected void populateItem(ListItem<Hotel> item) {

                //TODO: esto debería ser un link al detalle de un hotel.
                item.add(new Label("name", new PropertyModel<String>(item.getModel().getObject().getName(), "name")));
                item.add(new Label("category", new PropertyModel<Integer>(item.getModel().getObject().getCategory(), "category")));
                item.add(new Label("destination", new PropertyModel<String>(item.getModel().getObject().getDestination(), "destination")));
                item.add(new Label("comments", new PropertyModel<Integer>(item.getModel().getObject().getAmountOfComments(), "comments")));
            }
        });






    }



}
