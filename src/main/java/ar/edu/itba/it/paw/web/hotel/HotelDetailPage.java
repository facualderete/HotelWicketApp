package ar.edu.itba.it.paw.web.hotel;

import ar.edu.itba.it.paw.domain.*;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.comment.CommentFormPage;
import ar.edu.itba.it.paw.web.comment.CommentListPanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;


public class HotelDetailPage extends BasePage {

    private static final long serialVersionUID = 1L;

    @SpringBean
    HotelRepo hotelRepo;

    @SpringBean
    UserRepo userRepo;

    private final IModel<Hotel> hotelModel = new EntityModel<Hotel>(Hotel.class);
    private final IModel<User> userModel = new EntityModel<User>(User.class);

    public HotelDetailPage(final PageParameters parameters){

        hotelModel.setObject(hotelRepo.get(parameters.get("hotelId").toInteger()));
        userModel.setObject(HotelWicketSession.get().getUser());

        final AjaxLink toggleFavouriteLink = new AjaxLink("toggleFavourite") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                if(userModel.getObject().isFavourite(hotelModel.getObject())){
                    this.add(new AttributeModifier("class", "btn btn-success"));
                    userModel.getObject().removeFavourite(hotelModel.getObject());
                }else{
                    this.add(new AttributeModifier("class", "btn btn-danger"));
                    userModel.getObject().addFavourite(hotelModel.getObject());
                }

                userRepo.save(userModel.getObject());
                target.add(this);
            }
        };

        final AjaxLink toggleActiveLink = new AjaxLink("toggleActive") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                if(hotelModel.getObject().getActive()){
                    this.add(new AttributeModifier("class", "btn btn-danger"));
                    hotelModel.getObject().setActive(false);
                }else{
                    this.add(new AttributeModifier("class", "btn btn-success"));
                    hotelModel.getObject().setActive(true);
                }

                hotelRepo.save(hotelModel.getObject());
                target.add(this);
            }
        };

        if (userModel.getObject() != null && userModel.getObject().isFavourite(hotelModel.getObject())) {
            toggleFavouriteLink.add(new AttributeModifier("class", "btn btn-danger"));
        } else {
            toggleFavouriteLink.add(new AttributeModifier("class", "btn btn-success"));
        }

        if (hotelModel.getObject().getActive()) {
            toggleActiveLink.add(new AttributeModifier("class", "btn btn-success"));
        } else {
            toggleActiveLink.add(new AttributeModifier("class", "btn btn-danger"));
        }

        Link<Void> newCommentLink = new Link<Void>("newCommentLink") {
            @Override
            public void onClick() {
                setResponsePage(new CommentFormPage(new PageParameters().set("hotelId", hotelModel.getObject().getId())));
            }
        };

        Link<Void> addPhotoLink = new Link<Void>("addPhotoLink") {
            @Override
            public void onClick() {
                setResponsePage(new AddHotelPhotoPage(new PageParameters().set("hotelId", hotelModel.getObject().getId())));
            }
        };

        if (userModel.getObject() == null || !userModel.getObject().getAdmin()) {
            toggleFavouriteLink.setVisible(false);
            toggleActiveLink.setVisible(false);
            newCommentLink.setVisible(false);
            addPhotoLink.setVisible(false);
        }

        add(toggleFavouriteLink);
        add(toggleActiveLink);
        add(addPhotoLink);
        add(newCommentLink);

        add(new Label("hotelNameTitle", hotelModel.getObject().getName()));
        add(new Label("hotelCategory", hotelModel.getObject().getCategory()));
        add(new Label("hotelType", hotelModel.getObject().getType()));
        add(new Label("hotelPrice", hotelModel.getObject().getPrice()));
        add(new Label("hotelCity", hotelModel.getObject().getDestination().getDestination()));
        add(new Label("hotelAddress", hotelModel.getObject().getAddress()));
        add(new Label("hotelPhone", hotelModel.getObject().getPhone()));
        add(new ExternalLink("hotelWebsite", "http://" + hotelModel.getObject().getWebsite(), hotelModel.getObject().getWebsite()));
        String breakfastLabel;
        if(hotelModel.getObject().getBreakfast()){
            breakfastLabel = "breakfast_included";
        }else{
            breakfastLabel = "breakfast_not_included";
        }

        add(new Label("hotelBreakfast", this.getString(breakfastLabel)));
        add(new Label("hotelViews", hotelModel.getObject().getAccessCounter()));

        HotelEvaluation evaluation = hotelModel.getObject().getEvaluation();
        add(new Label("hygieneEvaluation", evaluation.getHygiene() == 0? "-":evaluation.getHygiene()));
        add(new Label("facilitiesEvaluation", evaluation.getFacilities() == 0? "-":evaluation.getFacilities()));
        add(new Label("serviceEvaluation", evaluation.getService() == 0? "-":evaluation.getService()));
        add(new Label("locationEvaluation", evaluation.getLocation() == 0? "-":evaluation.getLocation()));
        add(new Label("priceEvaluation", evaluation.getPrice() == 0? "-":evaluation.getPrice()));
        add(new Label("comfortEvaluation", evaluation.getComfort() == 0? "-":evaluation.getComfort()));
        add(new Label("generalEvaluation", evaluation.getGeneral() == 0? "-":evaluation.getGeneral()));

        final IModel<List<Comment>> commentsListModel = new LoadableDetachableModel<List<Comment>>() {
            @Override
            protected List<Comment> load() {
                //TODO: esto es una villereada...
                return new LinkedList<Comment>((SortedSet<Comment>)hotelRepo.getCommentsOnHotel(hotelModel.getObject()));
            }
        };

        add(new CommentListPanel("commentListPanel", commentsListModel));
        HotelPhotoSliderPanel photoSliderPanel = new HotelPhotoSliderPanel("slider", hotelModel);
        if (hotelModel.getObject().getPictures().size() == 0) photoSliderPanel.setVisible(false);
        add(photoSliderPanel);

    }

    @Override
    protected void onDetach() {
        super.onDetach();
        hotelModel.detach();
        userModel.detach();
    }
}
