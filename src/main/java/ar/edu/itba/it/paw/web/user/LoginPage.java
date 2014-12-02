package ar.edu.itba.it.paw.web.user;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class LoginPage extends BasePage{

    private static final long serialVersionUID = 1L;

    @SpringBean
    private UserRepo users;

    private transient String username;
    private transient String password;
    private transient boolean rememberMe;

    public LoginPage() {

        add(new FeedbackPanel("errorPanel"));
        Form<LoginPage> form = new Form<LoginPage>("loginForm",
                new CompoundPropertyModel<LoginPage>(this)) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit() {
                HotelWicketSession session = HotelWicketSession.get();

                if (session.signIn(username, password)) {
                    User loggedUser = users.getByEmail(session.getUserEmail());
                    continueToOriginalDestination();

                    //TODO: hacer la página del perfil de usuario!!
//                    setResponsePage(new ProfilePage(new PageParameters().set(
//                            "userEmail", loggedUser.getEmail())));
                } else {
                    error(getString("invalidCredentials"));
                }
            }
        };

        form.add(new TextField<String>("email").setRequired(true));
        form.add(new PasswordTextField("password"));
        form.add(new Button("login", new ResourceModel("login")));
        add(form);
    }

}
