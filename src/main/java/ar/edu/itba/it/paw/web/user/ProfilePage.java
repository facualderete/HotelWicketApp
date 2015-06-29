package ar.edu.itba.it.paw.web.user;

import ar.edu.itba.it.paw.common.PictureHelper;
import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.comment.CommentListPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

public class ProfilePage extends BasePage{

    @SpringBean
    private UserRepo users;

    public ProfilePage(PageParameters pageParameters){

        StringValue userEmail = pageParameters.get("userEmail");
        User user = users.getByEmail(userEmail.toString());

        add(new Label("name", user.getName()));
        add(new Label("lastname", user.getLastname()));
        add(new Label("email", user.getEmail()));
        add(new Label("description", user.getDescription()));
        add(new Image("profilePicture", PictureHelper.getProfilePicture(user, "1")));

        SortedSet<Comment> comments = user.getComments();
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

        add(new CommentListPanel("commentListPanel", commentsListModel));
    }


}
