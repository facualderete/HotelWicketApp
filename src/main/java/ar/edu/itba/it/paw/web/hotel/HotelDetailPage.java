package ar.edu.itba.it.paw.web.hotel;

import ar.edu.itba.it.paw.common.PictureHelper;
import ar.edu.itba.it.paw.domain.*;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.comment.CommentFormPage;
import ar.edu.itba.it.paw.web.comment.CommentListPanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.LinkedList;
import java.util.List;


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

        add(new Image("photo", PictureHelper.getHotelPicture(hotelModel.getObject(), "1")));

        if (HotelWicketSession.get().isSignedIn()) {
            userModel.setObject(userRepo.getByEmail(HotelWicketSession.get().getUserEmail()));
        }

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
                target.add(this);
            }
        };

        final AjaxLink toggleOutstandingLink = new AjaxLink("toggleOutstanding") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                if(hotelModel.getObject().getOutstanding()){
                    this.add(new AttributeModifier("class", "btn btn-danger"));
                    hotelModel.getObject().setOutstanding(false);
                }else{
                    this.add(new AttributeModifier("class", "btn btn-success"));
                    hotelModel.getObject().setOutstanding(true);
                }
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

        if (hotelModel.getObject().getOutstanding()) {
            toggleOutstandingLink.add(new AttributeModifier("class", "btn btn-success"));
        } else {
            toggleOutstandingLink.add(new AttributeModifier("class", "btn btn-danger"));
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

        Link<Void> editHotelLink = new Link<Void>("editHotelLink") {
            @Override
            public void onClick() {
                setResponsePage(new HotelFormPage(new PageParameters().set("hotelId", hotelModel.getObject().getId())));
            }
        };

        if (!HotelWicketSession.get().isSignedIn()) {
            newCommentLink.setVisible(false);
            toggleFavouriteLink.setVisible(false);
            toggleActiveLink.setVisible(false);
            addPhotoLink.setVisible(false);
            editHotelLink.setVisible(false);
            toggleOutstandingLink.setVisible(false);
        }

        if (HotelWicketSession.get().isSignedIn() && !userModel.getObject().getAdmin()) {
            toggleActiveLink.setVisible(false);
            addPhotoLink.setVisible(false);
            editHotelLink.setVisible(false);
            toggleOutstandingLink.setVisible(false);
        }

        add(toggleFavouriteLink);
        add(toggleActiveLink);
        add(addPhotoLink);
        add(newCommentLink);
        add(editHotelLink);
        add(toggleOutstandingLink);

        add(new Label("hotelNameTitle", getDecoratedHotelName(hotelModel.getObject())));
        add(new Label("hotelCategory", hotelModel.getObject().getCategory()));
        add(new Label("hotelType", hotelModel.getObject().getType()));
        add(new Label("hotelPrice", hotelModel.getObject().getPrice()));
        add(new Label("hotelCity", hotelModel.getObject().getDestination().getDestination()));
        add(new Label("hotelAddress", hotelModel.getObject().getAddress()));
        add(new Label("hotelPhone", hotelModel.getObject().getPhone()));
        add(new ExternalLink("hotelWebsite", "http://" + hotelModel.getObject().getWebsite(), hotelModel.getObject().getWebsite()));
        String breakfast = hotelModel.getObject().getBreakfast() ? BREAKFAST_INCLUDED : BREAKFAST_NOT_INCLUDED;
        add(new Label("hotelBreakfast", breakfast));
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
                if (IS_ADMIN) {
                    return new LinkedList<Comment>(hotelModel.getObject().getComments());
                } else {
                    return new LinkedList<Comment>(hotelModel.getObject().getFilteredComments());
                }
            }
        };

        CommentListPanel commentListPanel = new CommentListPanel("commentListPanel", commentsListModel, userModel);
        if (commentsListModel.getObject().isEmpty()) commentListPanel.setVisible(false);
        add(commentListPanel);
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
