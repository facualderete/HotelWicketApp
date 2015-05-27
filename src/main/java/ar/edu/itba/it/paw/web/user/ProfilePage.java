package ar.edu.itba.it.paw.web.user;

import ar.edu.itba.it.paw.common.PictureHelper;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.base.BasePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

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
    }


}
