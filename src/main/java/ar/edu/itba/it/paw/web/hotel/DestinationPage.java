package ar.edu.itba.it.paw.web.hotel;

import ar.edu.itba.it.paw.common.PictureHelper;
import ar.edu.itba.it.paw.domain.*;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.LinkedList;
import java.util.List;

public class DestinationPage extends BasePage {

    @SpringBean
    HotelRepo hotelRepo;

    IModel<Destination> destinationModel = new EntityModel<Destination>(Destination.class);

    public DestinationPage(final PageParameters parameters) {

        destinationModel.setObject(hotelRepo.getDestination(parameters.get("destinationId").toInteger()));

        add(new Label("title", getString("title") + " " + destinationModel.getObject().getDestination()));
        add(new Image("photo", PictureHelper.getDestinationPicture(destinationModel.getObject(), "1")));
        add(new Label("details", destinationModel.getObject().getDetails()));

        Link<Void> editDestinationLink = new Link<Void>("editDestinationLink") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                setResponsePage(new EditDestinationPage(new PageParameters().set("destinationId", destinationModel.getObject().getId())));
            }
        };

        if (!HotelWicketSession.get().isSignedIn() || !IS_ADMIN) editDestinationLink.setVisible(false);
        add(editDestinationLink);

        final IModel<List<Hotel>> hotelsListModel = new LoadableDetachableModel<List<Hotel>>() {
            @Override
            protected List<Hotel> load() {
                return new LinkedList<Hotel>(destinationModel.getObject().getHotels());
            }
        };

        add(new ListView<Hotel>("hotelsList", hotelsListModel) {
            @Override
            protected void populateItem(final ListItem<Hotel> item) {
                Hotel hotel = item.getModelObject();
                Link hotelDetailLink = new Link("hotelDetailLink") {
                    public void onClick() {
                        setResponsePage(new HotelDetailPage(new PageParameters().set("hotelId", item.getModelObject().getId())));
                    }
                };
                hotelDetailLink.add(new Label("hotelName", getDecoratedHotelName(hotel)));
                item.add(hotelDetailLink);

                item.add(new Label("category", hotel.getCategory()));
                item.add(new Label("price", hotel.getPrice()));
                item.add(new Label("address", hotel.getAddress()));
                String breakfast = hotel.getBreakfast() ? BREAKFAST_INCLUDED : BREAKFAST_NOT_INCLUDED;
                item.add(new Label("breakfast", breakfast));

            }
        });
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        destinationModel.detach();
    }
}
