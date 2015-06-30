package ar.edu.itba.it.paw.web.comment;

import ar.edu.itba.it.paw.common.DateStringHelper;
import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.web.user.ProfilePage;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;

public class CommentListPanel extends Panel {
    public CommentListPanel(String id, IModel<List<Comment>> commentListModel) {
        super(id, commentListModel);

        Label noCommentsLabel = new Label("noCommentsTitle", this.getString("title_no_comments"));
        Label commentsLabel = new Label("commentsTitle", this.getString("title_comments"));
        add(noCommentsLabel);
        add(commentsLabel);
        commentsLabel.setVisible(false);

        if(commentListModel.getObject().size() > 0){
            noCommentsLabel.setVisible(false);
            commentsLabel.setVisible(true);

            add(new ListView<Comment>("commentsList", commentListModel) {
                @Override
                protected void populateItem(final ListItem<Comment> item) {

                    final Comment comment = item.getModelObject();
                    final User user = comment.getUser();

                    Button button = new Button("button");
                    button.add(new AjaxEventBehavior("onclick") {
                        @Override
                        protected void onEvent(final AjaxRequestTarget target) {
                            System.out.println("Event");
                        }
                    });
                    item.add(button);

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

                    Link hotelDetailLink = new Link("userProfileLink"){
                        public void onClick(){
                            setResponsePage(new ProfilePage(new PageParameters().set("userEmail", user.getEmail())));
                        }
                    };

                    hotelDetailLink.add(new Label("userEmail", comment.getUser().getEmail()));
                    item.add(hotelDetailLink);
                }
            });
        }
    }
}
