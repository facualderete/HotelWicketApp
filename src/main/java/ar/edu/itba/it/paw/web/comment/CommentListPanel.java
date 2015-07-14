package ar.edu.itba.it.paw.web.comment;

import ar.edu.itba.it.paw.common.DateHelper;
import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.user.ProfilePage;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class CommentListPanel extends Panel {

    @SpringBean
    UserRepo userRepo;

    private final boolean IS_ADMIN = HotelWicketSession.get().isAdmin(userRepo);

    public CommentListPanel(String id, final IModel<List<Comment>> commentListModel, final IModel<User> userModel) {
        super(id, commentListModel);

        Label noCommentsLabel = new Label("noCommentsTitle", this.getString("title_no_comments"));
        Label commentsLabel = new Label("commentsTitle", this.getString("title_comments"));
        add(noCommentsLabel);
        add(commentsLabel);
        commentsLabel.setVisible(false);

        ListView<Comment> commentListView = new ListView<Comment>("commentsList", commentListModel) {
            @Override
            protected void populateItem(final ListItem<Comment> item) {

                final AjaxLink deleteCommentLink = new AjaxLink("deleteComment") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        item.getModelObject().getUser().getComments().remove(item.getModelObject());
                        target.add(this);
                        setResponsePage(getPage());
                    }
                };

                if (!IS_ADMIN) deleteCommentLink.setVisible(false);
                item.add(deleteCommentLink);

                final AjaxLink toggleForbiddenLink = new AjaxLink("toggleForbidden") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        if(item.getModelObject().getForbidden()){
                            this.add(new AttributeModifier("class", "btn btn-warning"));
                            item.getModelObject().setForbidden(false);
                        }else{
                            this.add(new AttributeModifier("class", "btn btn-success"));
                            item.getModelObject().setForbidden(true);
                            if (!IS_ADMIN) setResponsePage(getPage());
                        }

                        target.add(this);
                    }
                };

                if (item.getModelObject().getForbidden()) {
                    toggleForbiddenLink.add(new AttributeModifier("class", "btn btn-success"));
                } else {
                    toggleForbiddenLink.add(new AttributeModifier("class", "btn btn-warning"));
                }

                if (!HotelWicketSession.get().isSignedIn() ||
                        (item.getModelObject().getUser().equals(HotelWicketSession.get().getUser())) && !IS_ADMIN) toggleForbiddenLink.setVisible(false);
                item.add(toggleForbiddenLink);

                item.add(new Label("fromDate", DateHelper.getStringFromDate(item.getModelObject().getFromDate())));
                item.add(new Label("toDate", DateHelper.getStringFromDate(item.getModelObject().getToDate())));
                item.add(new Label("commentDate", DateHelper.getStringFromDate(item.getModelObject().getCommentDate())));
                item.add(new Label("reason", item.getModelObject().getReason()));
                item.add(new Label("companions", item.getModelObject().getCompanions()));
                item.add(new Label("details", item.getModelObject().getDetails()));
                item.add(new Label("hygiene", item.getModelObject().getRating().getHygiene() == 0 ? "-" : item.getModelObject().getRating().getHygiene()));
                item.add(new Label("facilities", item.getModelObject().getRating().getFacilities() == 0 ? "-" : item.getModelObject().getRating().getFacilities()));
                item.add(new Label("service", item.getModelObject().getRating().getService() == 0 ? "-" : item.getModelObject().getRating().getService()));
                item.add(new Label("location", item.getModelObject().getRating().getLocation() == 0 ? "-" : item.getModelObject().getRating().getLocation()));
                item.add(new Label("price", item.getModelObject().getRating().getPrice() == 0 ? "-" : item.getModelObject().getRating().getPrice()));
                item.add(new Label("comfort", item.getModelObject().getRating().getComfort() == 0 ? "-" : item.getModelObject().getRating().getComfort()));
                item.add(new Label("general", item.getModelObject().getRating().getAverageRating() == 0 ? "-" : item.getModelObject().getRating().getAverageRating()));

                Link userProfileLink = new Link("userProfileLink"){
                    public void onClick(){
                        setResponsePage(new ProfilePage(new PageParameters().set("userEmail", item.getModelObject().getUser().getEmail())));
                    }
                };

                userProfileLink.add(new Label("userEmail", item.getModelObject().getUser().getEmail()));
                item.add(userProfileLink);

                Link<Void> editComment = new Link<Void>("editComment") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onClick() {
                        setResponsePage(new CommentFormPage(new PageParameters().set("commentId", item.getModelObject().getId())));
                    }
                };

                if (!item.getModelObject().getUser().equals(userModel.getObject())) {
                    editComment.setVisible(false);
                }

                item.add(editComment);
            }
        };

        add(commentListView.setVisible(false));

        if(commentListModel.getObject().size() > 0){
            noCommentsLabel.setVisible(false);
            commentsLabel.setVisible(true);
            commentListView.setVisible(true);
        }
    }
}
