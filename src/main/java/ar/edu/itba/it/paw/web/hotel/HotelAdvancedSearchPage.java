package ar.edu.itba.it.paw.web.hotel;

import ar.edu.itba.it.paw.domain.*;
import ar.edu.itba.it.paw.domain.definitions.HotelType;
import ar.edu.itba.it.paw.domain.filters.SearchHotelFilter;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.hotel.form.DestinationDropDownChoice;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HotelAdvancedSearchPage extends BasePage {

    private static final long serialVersionUID = 1L;

    @SpringBean
    HotelRepo hotelRepo;

    @SpringBean
    private UserRepo userRepo;

    private String selectedHotelType;
    private Integer selectedStarsFrom;
    private Integer selectedStarsTo;
    private String selectedBreakfast;
    private String hotelNameSearch;
    private Integer priceFrom;
    private Integer priceTo;

    private final List<HotelType> hotelTypeList = new ArrayList<HotelType>(Arrays.asList(HotelType.values()));
    private final List<Integer> stars = Arrays.asList(1, 2, 3, 4, 5);

    private final IModel<Destination> destinationModel = new EntityModel<Destination>(Destination.class);


    private SearchHotelFilter searchHotelFilter;
    private ListView<Hotel> hotelListView;

    private WebMarkupContainer wmc = new WebMarkupContainer("tableContainer");

    public HotelAdvancedSearchPage() {

        Form<HotelAdvancedSearchPage> form = new Form<HotelAdvancedSearchPage>(
                "advancedSearchForm",
                new CompoundPropertyModel<HotelAdvancedSearchPage>(this));

        searchHotelFilter = new SearchHotelFilter();

        List<String> breakfastTypes = new LinkedList<String>();
        breakfastTypes.add(getString("breakfast_included"));
        breakfastTypes.add(getString("breakfast_not_included"));

        List<String> typeStringList = new LinkedList<String>();
        for (HotelType hotelType : hotelTypeList) {
            typeStringList.add(hotelType.getName());
        }

        final IModel<List<Destination>> destinationsListModel = new LoadableDetachableModel<List<Destination>>() {
            @Override
            protected List<Destination> load() {
                return hotelRepo.getDestinations();
            }
        };

        DropDownChoice<String> hotelTypeDropDownChoice = new DropDownChoice<String>("hotelTypeDropDownChoice",
                new PropertyModel(this, "selectedHotelType"), typeStringList) {
            @Override
            protected String getNullKeyDisplayValue() {
                return getString("hotel_type_null_option");
            }
        };

        DropDownChoice<Integer> starsFromDropDownChoice = new DropDownChoice<Integer>("starsFromDropDownChoice",
                new PropertyModel(this, "selectedStarsFrom"), stars) {
            @Override
            protected String getNullKeyDisplayValue() {
                return getString("hotel_stars_from_null_option");
            }
        };

        DropDownChoice<Integer> starsToDropDownChoice = new DropDownChoice<Integer>("starsToDropDownChoice",
                new PropertyModel(this, "selectedStarsTo"), stars) {
            @Override
            protected String getNullKeyDisplayValue() {
                return getString("hotel_stars_to_null_option");
            }
        };

        DropDownChoice<String> breakfastDropDownChoice = new DropDownChoice<String>("breakfastDropDownChoice",
                new PropertyModel(this, "selectedBreakfast"), breakfastTypes) {
            @Override
            protected String getNullKeyDisplayValue() {
                return getString("hotel_breakfast_null_option");
            }
        };

        TextField<String> hotelNameSearchField = new TextField<String>("hotelNameSearchField",
                new PropertyModel(this, "hotelNameSearch"));
        NumberTextField<Integer> priceFromField = new NumberTextField<Integer>("priceFromField",
                new PropertyModel(this, "priceFrom"));
        NumberTextField<Integer> priceToField = new NumberTextField<Integer>("priceToField",
                new PropertyModel(this, "priceTo"));

        hotelNameSearchField.add(new AttributeModifier("placeholder", getString("search_name_box_placeholder")));
        priceFromField.add(new AttributeModifier("placeholder", getString("search_min_box_placeholder")));
        priceToField.add(new AttributeModifier("placeholder", getString("search_max_box_placeholder")));

        final AjaxSubmitLink submit = new AjaxSubmitLink("submit") {
            @Override
            public void onSubmit(AjaxRequestTarget target, Form<?> form) {

                List<String> errors = validateFilter();
                if (errors.isEmpty()) {
                    Integer priceFromInt = (priceFrom == null)? null : Integer.valueOf(priceFrom);
                    Integer priceToInt = (priceTo == null)? null : Integer.valueOf(priceTo);
                    String destination = (destinationModel.getObject() != null) ? destinationModel.getObject().getDestination() : "";

                    searchHotelFilter = new SearchHotelFilter(hotelNameSearch, destination,
                            priceFromInt, priceToInt, selectedStarsFrom, selectedStarsTo,
                            selectedHotelType, selectedBreakfast);
                    target.add(wmc);
                } else {
                    //mostrar errores!!
                }
            }
        };

        final IModel<List<Hotel>> hotelsListModel = new LoadableDetachableModel<List<Hotel>>() {
            @Override
            protected List<Hotel> load() {
                return (List<Hotel>)hotelRepo.getAllFiltered(searchHotelFilter, IS_ADMIN);
            }
        };

        hotelListView = new ListView<Hotel>("hotelsList", hotelsListModel) {
            @Override
            protected void populateItem(final ListItem<Hotel> item) {

                Hotel hotel = item.getModelObject();
                item.add(new Label("category", hotel.getCategory()));
                item.add(new Label("destination", hotel.getDestination().getDestination()));
                item.add(new Label("price", hotel.getPrice()));
                item.add(new Label("type", hotel.getType()));
                item.add(new Label("breakfast", hotel.getBreakfast() ? BREAKFAST_INCLUDED : BREAKFAST_NOT_INCLUDED));

                Link hotelDetailLink = new Link("hotelDetailLink") {
                    public void onClick() {
                        setResponsePage(new HotelDetailPage(new PageParameters().set("hotelId", item.getModelObject().getId())));
                    }
                };

                hotelDetailLink.add(new Label("hotelName", getDecoratedHotelName(hotel)));

                item.add(hotelDetailLink);
            }
        };

        wmc.setOutputMarkupId(true);
        hotelListView.setOutputMarkupId(true);
        wmc.add(hotelListView);

        form.add(hotelTypeDropDownChoice);
        form.add(starsFromDropDownChoice);
        form.add(starsToDropDownChoice);
        form.add(breakfastDropDownChoice);
        form.add(hotelNameSearchField);
        form.add(new DestinationDropDownChoice("destination", destinationModel, destinationsListModel) {
            @Override
            protected String getNullKeyDisplayValue() {
                return getString("search_city_box_placeholder");
            }
        });
        form.add(priceFromField);
        form.add(priceToField);
        form.add(submit);
        add(wmc);
        add(form);
    }

    private List<String> validateFilter() {

        List<String> errors = new LinkedList<String>();
        Integer categoryFrom = null;
        Integer categoryTo = null;

        if(selectedStarsFrom != null){
            categoryFrom = Integer.valueOf(selectedStarsFrom);
        }

        if(selectedStarsTo != null){
            categoryTo = Integer.valueOf(selectedStarsTo);
        }

        if (priceFrom != null && priceTo != null) {
            if (priceFrom > priceTo) {
                errors.add(getString("search_max_box_placeholder") + " - " + getString("range_error"));
            }
        }

        if (categoryFrom != null && categoryTo != null) {
            if (categoryFrom > categoryTo) {
                errors.add(getString("hotel_stars_from_null_option") + " - " + getString("range_error"));
            }
        }

        return errors;
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        destinationModel.detach();
    }
}
