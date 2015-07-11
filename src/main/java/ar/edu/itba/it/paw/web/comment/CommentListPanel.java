package ar.edu.itba.it.paw.web.comment;

import ar.edu.itba.it.paw.common.DateHelper;
import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.EntityModel;
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

    IModel<User> userModel = new EntityModel<User>(User.class);
    IModel<Comment> commentModel = new EntityModel<Comment>(Comment.class);

    public CommentListPanel(String id, IModel<List<Comment>> commentListModel) {
        super(id, commentListModel);

        Label noCommentsLabel = new Label("noCommentsTitle", this.getString("title_no_comments"));
        Label commentsLabel = new Label("commentsTitle", this.getString("title_comments"));
        add(noCommentsLabel);
        add(commentsLabel);
        commentsLabel.setVisible(false);

        ListView<Comment> commentListView = new ListView<Comment>("commentsList", commentListModel) {
            @Override
            protected void populateItem(final ListItem<Comment> item) {

                commentModel.setObject(item.getModelObject());
                userModel.setObject(commentModel.getObject().getUser());

                Button button = new Button("button");
                button.add(new AjaxEventBehavior("onclick") {
                    @Override
                    protected void onEvent(final AjaxRequestTarget target) {
                        System.out.println("Event");
                    }
                });
                item.add(button);

                item.add(new Label("fromDate", DateHelper.getStringFromDate(commentModel.getObject().getFromDate())));
                item.add(new Label("toDate", DateHelper.getStringFromDate(commentModel.getObject().getToDate())));
                item.add(new Label("commentDate", DateHelper.getStringFromDate(commentModel.getObject().getCommentDate())));
                item.add(new Label("reason", commentModel.getObject().getReason()));
                item.add(new Label("companions", commentModel.getObject().getCompanions()));
                item.add(new Label("details", commentModel.getObject().getDetails()));
                item.add(new Label("hygiene", commentModel.getObject().getRating().getHygiene() == 0 ? "-" : commentModel.getObject().getRating().getHygiene()));
                item.add(new Label("facilities", commentModel.getObject().getRating().getFacilities() == 0 ? "-" : commentModel.getObject().getRating().getFacilities()));
                item.add(new Label("service", commentModel.getObject().getRating().getService() == 0 ? "-" : commentModel.getObject().getRating().getService()));
                item.add(new Label("location", commentModel.getObject().getRating().getLocation() == 0 ? "-" : commentModel.getObject().getRating().getLocation()));
                item.add(new Label("price", commentModel.getObject().getRating().getPrice() == 0 ? "-" : commentModel.getObject().getRating().getPrice()));
                item.add(new Label("comfort", commentModel.getObject().getRating().getComfort() == 0 ? "-" : commentModel.getObject().getRating().getComfort()));
                item.add(new Label("general", commentModel.getObject().getRating().getAverageRating() == 0 ? "-" : commentModel.getObject().getRating().getAverageRating()));

                Link userProfileLink = new Link("userProfileLink"){
                    public void onClick(){
                        setResponsePage(new ProfilePage(new PageParameters().set("userEmail", userModel.getObject().getEmail())));
                    }
                };

                userProfileLink.add(new Label("userEmail", commentModel.getObject().getUser().getEmail()));
                item.add(userProfileLink);
            }
        };

        add(commentListView.setVisible(false));

        if(commentListModel.getObject().size() > 0){
            noCommentsLabel.setVisible(false);
            commentsLabel.setVisible(true);
            commentListView.setVisible(true);
        }
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        userModel.detach();
        commentModel.detach();
    }
}
