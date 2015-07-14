package ar.edu.itba.it.paw.web.user;

import ar.edu.itba.it.paw.common.PictureHelper;
import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.WicketApplication;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.comment.CommentListPanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import java.util.LinkedList;
import java.util.List;

/*
    This page can be accessed vía URL user/profile/<USER_EMAIL>
 */

public class ProfilePage extends BasePage {

    @SpringBean
    private UserRepo users;

    private IModel<User> userModel = new EntityModel<User>(User.class);
    private IModel<User> loggedUserModel = new EntityModel<User>(User.class);

    public ProfilePage(PageParameters pageParameters){

        StringValue userEmail = pageParameters.get("userEmail");
        userModel.setObject(users.getByEmail(userEmail.toString()));
        if (userModel.getObject() == null) setResponsePage(WicketApplication.get().getHomePage());

        if (HotelWicketSession.get().isSignedIn())  loggedUserModel.setObject(users.getByEmail(HotelWicketSession.get().getUserEmail()));

        add(new Label("name", userModel.getObject().getName()));
        add(new Label("lastname", userModel.getObject().getLastname()));
        add(new Label("email", userModel.getObject().getEmail()));
        add(new Label("description", userModel.getObject().getDescription()));
        add(new Image("profilePicture", PictureHelper.getProfilePicture(userModel.getObject(), "1")));

        final IModel<List<Comment>> commentsListModel = new LoadableDetachableModel<List<Comment>>() {
            @Override
            protected List<Comment> load() {
                if (IS_ADMIN) {
                    return new LinkedList<Comment>(userModel.getObject().getComments());
                } else {
                    return new LinkedList<Comment>(userModel.getObject().getFilteredComments());
                }
            }
        };

        CommentListPanel commentListPanel = new CommentListPanel("commentListPanel", commentsListModel, loggedUserModel);

        if ((IS_ADMIN && userModel.getObject().getComments().size() > 0) || (!IS_ADMIN && userModel.getObject().getFilteredComments().size() > 0)) {
            commentListPanel.setVisible(true);
        } else {
            commentListPanel.setVisible(false);
        }

        add(commentListPanel);

        Link<Void> editProfileLink = new Link<Void>("editProfileLink") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                setResponsePage(EditProfilePage.class);
            }
        };

        if (!HotelWicketSession.get().isSignedIn()) editProfileLink.setVisible(false);

        add(editProfileLink);

        final AjaxLink toggleAdminLink = new AjaxLink("toggleAdmin") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                if(userModel.getObject().getAdmin()) {
                    this.add(new AttributeModifier("class", "btn btn-danger"));

                    userModel.getObject().setAdmin(false);
                } else {
                    this.add(new AttributeModifier("class", "btn btn-success"));
                    userModel.getObject().setAdmin(true);
                }

                target.add(this);
            }
        };

        if (userModel.getObject().getAdmin()) {
            toggleAdminLink.add(new AttributeModifier("class", "btn btn-success"));
        } else {
            toggleAdminLink.add(new AttributeModifier("class", "btn btn-danger"));
        }

        if (!IS_ADMIN || userModel.getObject().equals(loggedUserModel.getObject())) toggleAdminLink.setVisible(false);
        add(toggleAdminLink);

        //TODO: agregar la lista de los hoteles favoritos!! Es una pelotudez...
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        userModel.detach();
        loggedUserModel.detach();
    }
}
