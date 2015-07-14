package ar.edu.itba.it.paw.web.hotel;

import ar.edu.itba.it.paw.domain.Destination;
import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.Hotel;
import ar.edu.itba.it.paw.domain.HotelRepo;
import ar.edu.itba.it.paw.domain.definitions.HotelType;
import ar.edu.itba.it.paw.web.base.SecuredPage;
import ar.edu.itba.it.paw.web.feedback.CustomFeedbackPanel;
import ar.edu.itba.it.paw.web.hotel.form.DestinationDropDownChoice;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HotelFormPage extends SecuredPage {

    @SpringBean
    HotelRepo hotelRepo;

    private final IModel<Hotel> hotelModel = new EntityModel<Hotel>(Hotel.class);

    private static final List<Integer> CATEGORY = Arrays.asList(1, 2, 3, 4, 5);
    private static final LinkedList<String> TYPES = new LinkedList<String>();

    private String name;
    private Integer category;
    private String type;
    private Integer price;
    private IModel<Destination> destinationModel = new EntityModel<Destination>(Destination.class);
    private String address;
    private String phone;
    private String website;
    private boolean breakfast;

    public HotelFormPage(PageParameters parameters) {

        for (HotelType ht : HotelType.values()) {
            TYPES.add(ht.getName());
        }

        final IModel<List<Destination>> destinationsListModel = new LoadableDetachableModel<List<Destination>>() {
            @Override
            protected List<Destination> load() {
                return hotelRepo.getDestinations();
            }
        };

        String title;

        if (parameters.get("hotelId").isNull()) {
            title = getString("new_title");
            type = TYPES.getFirst();
            destinationModel.setObject(destinationsListModel.getObject().get(0));
            category = 1;
        } else {
            hotelModel.setObject(hotelRepo.get(parameters.get("hotelId").toInteger()));
            title = getString("editing_title");
            name = hotelModel.getObject().getName();
            category = hotelModel.getObject().getCategory();
            type = hotelModel.getObject().getType();
            price = hotelModel.getObject().getPrice();
            destinationModel.setObject(hotelModel.getObject().getDestination());
            address = hotelModel.getObject().getAddress();
            phone = hotelModel.getObject().getPhone();
            website = hotelModel.getObject().getWebsite();
            breakfast = hotelModel.getObject().getBreakfast();
        }

        add(new Label("title", title));
        add(new CustomFeedbackPanel("feedbackPanel"));

        Form<HotelFormPage> form = new Form<HotelFormPage>(
                "hotelForm",
                new CompoundPropertyModel<HotelFormPage>(this)) {

            @Override
            protected void onSubmit() {

                if (price <= 0) {
                    error(getString("negative_price_error"));
                } else if (hotelRepo.getByName(name) != null) {
                    error(getString("repeated_name_error"));
                } else {
                    if (hotelModel.getObject() == null) {
                        Hotel newHotel = new Hotel(name, category, type, price, destinationModel.getObject(),
                                address, phone, website, breakfast);
                        hotelRepo.save(newHotel);
                    } else {
                        hotelModel.getObject().setName(name);
                        hotelModel.getObject().setCategory(category);
                        hotelModel.getObject().setType(type);
                        hotelModel.getObject().setPrice(price);
                        hotelModel.getObject().setDestination(destinationModel.getObject());
                        hotelModel.getObject().setAddress(address);
                        hotelModel.getObject().setPhone(phone);
                        hotelModel.getObject().setWebsite(website);
                        hotelModel.getObject().setBreakfast(breakfast);
                    }
                    setResponsePage(HotelListPage.class);
                }
            }
        };

        form.add(new TextField<String>("name").setRequired(true));
        form.add(new DropDownChoice<Integer>("category", CATEGORY));
        form.add(new DropDownChoice<String>("type", TYPES));
        form.add(new NumberTextField<Integer>("price").setRequired(true));
        form.add(new DestinationDropDownChoice("destination", destinationModel, destinationsListModel));
        form.add(new TextField<String>("address").setRequired(true));
        form.add(new TextField<String>("phone").setRequired(true));
        form.add(new TextField<String>("website").setRequired(true));
        form.add(new CheckBox("breakfast"));
        form.add(new Button("submit", new ResourceModel("submit")));
        add(form);
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        hotelModel.detach();
        destinationModel.detach();
    }
}