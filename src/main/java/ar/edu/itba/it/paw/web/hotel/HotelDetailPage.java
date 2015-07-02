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


public class HotelDetailPage extends BasePage{

    private static final long serialVersionUID = 1L;

    @SpringBean
    HotelRepo hotelRepo;

    @SpringBean
    UserRepo userRepo;

    public HotelDetailPage(final PageParameters parameters){

        final IModel<Hotel> hotel = new EntityModel(Hotel.class, hotelRepo.get(parameters.get("hotelId").toInteger()));
        final User user = HotelWicketSession.get().getUser();

        final AjaxLink toggleFavouriteLink = new AjaxLink("toggleFavourite") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                if(user.isFavourite(hotel.getObject())){
                    this.add(new AttributeModifier("class", "btn btn-success"));
                    user.removeFavourite(hotel.getObject());
                }else{
                    this.add(new AttributeModifier("class", "btn btn-danger"));
                    user.addFavourite(hotel.getObject());
                }

                userRepo.save(user);
                target.add(this);
            }
        };

        final AjaxLink toggleActiveLink = new AjaxLink("toggleActive") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                if(hotel.getObject().getActive()){
                    this.add(new AttributeModifier("class", "btn btn-danger"));
                    hotel.getObject().setActive(false);
                }else{
                    this.add(new AttributeModifier("class", "btn btn-success"));
                    hotel.getObject().setActive(true);
                }

                hotelRepo.save(hotel.getObject());
                target.add(this);
            }
        };

        if (user != null && user.isFavourite(hotel.getObject())) {
            toggleFavouriteLink.add(new AttributeModifier("class", "btn btn-danger"));
        } else {
            toggleFavouriteLink.add(new AttributeModifier("class", "btn btn-success"));
        }

        if (hotel.getObject().getActive()) {
            toggleActiveLink.add(new AttributeModifier("class", "btn btn-success"));
        } else {
            toggleActiveLink.add(new AttributeModifier("class", "btn btn-danger"));
        }

        Link<Void> newCommentLink = new Link<Void>("newCommentLink") {
            @Override
            public void onClick() {
                setResponsePage(new CommentFormPage(new PageParameters().set("hotelId", hotel.getObject().getId())));
            }
        };

        Link<Void> addPhotoLink = new Link<Void>("addPhotoLink") {
            @Override
            public void onClick() {
                setResponsePage(new AddHotelPhotoPage(new PageParameters().set("hotelId", hotel.getObject().getId())));
            }
        };

        if (user == null || !user.getAdmin()) {
            toggleFavouriteLink.setVisible(false);
            toggleActiveLink.setVisible(false);
            newCommentLink.setVisible(false);
            addPhotoLink.setVisible(false);
        }

        add(toggleFavouriteLink);
        add(toggleActiveLink);
        add(addPhotoLink);
        add(newCommentLink);

        add(new Label("hotelNameTitle", hotel.getObject().getName()));
        add(new Label("hotelCategory", hotel.getObject().getCategory()));
        add(new Label("hotelType", hotel.getObject().getType()));
        add(new Label("hotelPrice", hotel.getObject().getPrice()));
        add(new Label("hotelCity", hotel.getObject().getDestination().getDestination()));
        add(new Label("hotelAddress", hotel.getObject().getAddress()));
        add(new Label("hotelPhone", hotel.getObject().getPhone()));
        add(new ExternalLink("hotelWebsite", "http://" + hotel.getObject().getWebsite(), hotel.getObject().getWebsite()));
        String breakfastLabel;
        if(hotel.getObject().getBreakfast()){
            breakfastLabel = "breakfast_included";
        }else{
            breakfastLabel = "breakfast_not_included";
        }

        add(new Label("hotelBreakfast", this.getString(breakfastLabel)));
        add(new Label("hotelViews", hotel.getObject().getAccessCounter()));

        HotelEvaluation evaluation = hotel.getObject().getEvaluation();
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
                return new LinkedList<Comment>((SortedSet<Comment>)hotelRepo.getCommentsOnHotel(hotel.getObject()));
            }
        };

        add(new CommentListPanel("commentListPanel", commentsListModel));
    }
}
