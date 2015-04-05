package ar.edu.itba.it.paw.web.hotel;

import ar.edu.itba.it.paw.domain.Destination;
import ar.edu.itba.it.paw.domain.Hotel;
import ar.edu.itba.it.paw.domain.HotelRepo;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class HotelListPage extends BasePage{

    private static final int LIST_SIZE = 5;

    private static final long serialVersionUID = 1L;

    @SpringBean
    private HotelRepo hotelRepo;
    @SpringBean
    private UserRepo userRepo;

    public HotelListPage(){

        final IModel<List<Hotel>> hotelsListModel = new LoadableDetachableModel<List<Hotel>>() {
            @Override
            protected List<Hotel> load() {
                return (List<Hotel>)hotelRepo.getNHotelsOrderedByComments(LIST_SIZE, HotelWicketSession.get().isAdmin(userRepo));
            }
        };

        add(new ListView<Hotel>("hotelsList", hotelsListModel) {
            @Override
            protected void populateItem(final ListItem<Hotel> item) {

                Hotel hotel = item.getModelObject();
                item.add(new Label("category", hotel.getCategory()));
                item.add(new Label("destination", hotel.getDestination().getDestination()));
                item.add(new Label("comments", hotel.getAmountOfComments()));

                Link hotelDetailLink = new Link("hotelDetailLink"){
                    public void onClick(){
                        setResponsePage(new HotelDetailPage(new PageParameters().set("hotelId", item.getModelObject().getId())));
                    }
                };

                hotelDetailLink.add(new Label("hotelName", hotel.getName()));

                item.add(hotelDetailLink);
            }
        });

        final IModel<List<Destination>> destinationsListModel = new LoadableDetachableModel<List<Destination>>() {
            @Override
            protected List<Destination> load() {
                return hotelRepo.getDestinations();
            }
        };

        add(new ListView<Destination>("destinationsList", destinationsListModel) {
            @Override
            protected void populateItem(ListItem<Destination> item) {

                Destination destination = item.getModelObject();
                //TODO: esto debería ser un link al detalle del destino.
                item.add(new Label("destinationName", destination.getDestination()));
            }
        });


    }



}
