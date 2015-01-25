package ar.edu.itba.it.paw.web.hotel;

import ar.edu.itba.it.paw.common.DateStringHelper;
import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Hotel;
import ar.edu.itba.it.paw.domain.HotelEvaluation;
import ar.edu.itba.it.paw.domain.HotelRepo;
import ar.edu.itba.it.paw.web.base.BasePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
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

    public HotelDetailPage(final PageParameters parameters){

        final Hotel hotel = hotelRepo.get(parameters.get("hotelId").toInteger());

        add(new Label("hotelNameTitle", hotel.getName()));
        add(new Label("hotelCategory", hotel.getCategory()));
        add(new Label("hotelType", hotel.getType()));
        add(new Label("hotelPrice", hotel.getPrice()));
        add(new Label("hotelCity", hotel.getDestination().getDestination()));
        add(new Label("hotelAddress", hotel.getAddress()));
        add(new Label("hotelPhone", hotel.getPhone()));
        //TODO: esto debería ser un link a una ventana externa!!
        add(new Label("hotelWebsite", hotel.getWebsite()));
        String breakfastLabel;
        if(hotel.getBreakfast()){
            breakfastLabel = "breakfast_included";
        }else{
            breakfastLabel = "breakfast_not_included";
        }

        add(new Label("hotelBreakfast", this.getString(breakfastLabel)));
        add(new Label("hotelViews", hotel.getAccessCounter()));

        Label noCommentsLabel = new Label("noCommentsTitle", this.getString("title_no_comments"));
        Label commentsLabel = new Label("commentsTitle", this.getString("title_comments"));
        add(noCommentsLabel);
        add(commentsLabel);
        commentsLabel.setVisible(false);

        //TODO: esto es una villereada... Sólo para salir del paso por ahora!
        SortedSet<Comment> comments = (SortedSet<Comment>)hotelRepo.getCommentsOnHotel(hotel);
        final LinkedList<Comment> commentsList = new LinkedList<Comment>();
        for(Comment c : comments){
            commentsList.add(c);
        }

        final IModel<List<Comment>> commentsListModel = new LoadableDetachableModel<List<Comment>>() {
            @Override
            protected List<Comment> load() {
                return commentsList;
            }
        };

        HotelEvaluation evaluation = hotel.getEvaluation();
        add(new Label("hygieneEvaluation", evaluation.getHygiene() == 0? "-":evaluation.getHygiene()));
        add(new Label("facilitiesEvaluation", evaluation.getFacilities() == 0? "-":evaluation.getFacilities()));
        add(new Label("serviceEvaluation", evaluation.getService() == 0? "-":evaluation.getService()));
        add(new Label("locationEvaluation", evaluation.getLocation() == 0? "-":evaluation.getLocation()));
        add(new Label("priceEvaluation", evaluation.getPrice() == 0? "-":evaluation.getPrice()));
        add(new Label("comfortEvaluation", evaluation.getComfort() == 0? "-":evaluation.getComfort()));
        add(new Label("generalEvaluation", evaluation.getGeneral() == 0? "-":evaluation.getGeneral()));


        if(commentsListModel.getObject().size() > 0){
            noCommentsLabel.setVisible(false);
            commentsLabel.setVisible(true);

            add(new ListView<Comment>("commentsList", commentsListModel) {
                @Override
                protected void populateItem(final ListItem<Comment> item) {

                    Comment comment = item.getModelObject();
                    item.add(new Label("fromDate", DateStringHelper.getStringFromDate(comment.getFromDate())));
                    item.add(new Label("toDate", DateStringHelper.getStringFromDate(comment.getToDate())));
                    item.add(new Label("commentDate", DateStringHelper.getStringFromDate(comment.getCommentDate())));
                    item.add(new Label("reason", comment.getReason()));
                    item.add(new Label("companions", comment.getCompanions()));
                    item.add(new Label("details", comment.getDetails()));
                    item.add(new Label("hygiene", comment.getRating().getHygiene() == 0 ? "-" : comment.getRating().getHygiene()));
                    item.add(new Label("facilities", comment.getRating().getFacilities() == 0 ? "-" : comment.getRating().getFacilities()));
                    item.add(new Label("service", comment.getRating().getService() == 0 ? "-" : comment.getRating().getService()));
                    item.add(new Label("location", comment.getRating().getLocation() == 0 ? "-" : comment.getRating().getLocation()));
                    item.add(new Label("price", comment.getRating().getPrice() == 0 ? "-" : comment.getRating().getPrice()));
                    item.add(new Label("comfort", comment.getRating().getComfort() == 0 ? "-" : comment.getRating().getComfort()));
                    item.add(new Label("general", comment.getRating().getAverageRating() == 0 ? "-" : comment.getRating().getAverageRating()));
                    item.add(new Label("user", comment.getUser().getName()));

                    //TODO: un link hacia el perfil del usuario!
//                    Link hotelDetailLink = new Link("hotelDetailLink"){
//                        public void onClick(){
//                            setResponsePage(new HotelDetailPage(new PageParameters().set("hotelId", item.getModelObject().getId())));
//                        }
//                    };
//
//                    hotelDetailLink.add(new Label("hotelName", hotel.getName()));
//
//                    item.add(hotelDetailLink);
                }
            });

        }
    }
}
