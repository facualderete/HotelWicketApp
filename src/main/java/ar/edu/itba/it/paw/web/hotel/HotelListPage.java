package ar.edu.itba.it.paw.web.hotel;

import ar.edu.itba.it.paw.domain.*;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.comment.CommentReportPage;
import ar.edu.itba.it.paw.web.feedback.CustomFeedbackPanel;
import ar.edu.itba.it.paw.web.hotel.form.HotelAutoCompleteField;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class HotelListPage extends BasePage {

    private static final int LIST_SIZE = 5;

    private static final long serialVersionUID = 1L;

    @SpringBean
    private HotelRepo hotelRepo;

    @SpringBean
    private UserRepo userRepo;

    private final IModel<Hotel> hotelSearchModel = new EntityModel<Hotel>(Hotel.class);

    public HotelListPage() {

        add(new CustomFeedbackPanel("feedbackPanel"));

        final IModel<List<Hotel>> hotelsListModel = new LoadableDetachableModel<List<Hotel>>() {
            @Override
            protected List<Hotel> load() {
                return (List<Hotel>)hotelRepo.getNHotelsOrderedByComments(LIST_SIZE, IS_ADMIN);
            }
        };

        Link<Void> addHotelLink = new Link<Void>("addHotelLink") {
            @Override
            public void onClick() {
                setResponsePage(new HotelFormPage(new PageParameters()));
            }
        };
        if(!IS_ADMIN) addHotelLink.setVisible(false);
        add(addHotelLink);

        add(new ListView<Hotel>("hotelsList", hotelsListModel) {
            @Override
            protected void populateItem(final ListItem<Hotel> item) {

                Hotel hotel = item.getModelObject();
                item.add(new Label("category", hotel.getCategory()));
                item.add(new Label("destination", hotel.getDestination().getDestination()));
                item.add(new Label("comments", hotel.getAmountOfComments()));

                Link hotelDetailLink = new Link("hotelDetailLink") {
                    public void onClick() {
                        setResponsePage(new HotelDetailPage(new PageParameters().set("hotelId", item.getModelObject().getId())));
                    }
                };

                hotelDetailLink.add(new Label("hotelName", getDecoratedHotelName(hotel)));

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
                final Integer destinationId = item.getModelObject().getId();

                Link<Void> destinationPageLink = new Link<Void>("destinationPageLink") {
                    @Override
                    public void onClick() {
                        setResponsePage(new DestinationPage(new PageParameters().set("destinationId", destinationId)));
                    }

                };
                destinationPageLink.add(new Label("destinationName", item.getModelObject().getDestination()));
                item.add(destinationPageLink);
            }
        });

        add(new Link("advancedSearchLink"){
            public void onClick(){
                setResponsePage(new HotelAdvancedSearchPage());
            }
        });

        Form<Void> form = new Form<Void>("autoCompleteForm");

        HotelAutoCompleteField autoCompleteField = new HotelAutoCompleteField("autoCompleteField", hotelSearchModel);
        autoCompleteField.add(new AjaxFormSubmitBehavior(form, "change") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                if (hotelSearchModel.getObject() == null) {
                    error(getString("invalid_hotel_name_error"));
                } else {
                    setResponsePage(new HotelDetailPage(new PageParameters().set("hotelId", hotelSearchModel.getObject().getId())));
                }
            }
        });

        form.add(autoCompleteField);
        add(form);

        Link<Void> commentReportLink = new Link<Void>("commentReportLink") {
            @Override
            public void onClick() {
                setResponsePage(CommentReportPage.class);
            }

        };
        if(!IS_ADMIN) commentReportLink.setVisible(false);
        add(commentReportLink);
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        hotelSearchModel.detach();
    }
}
