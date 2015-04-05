package ar.edu.itba.it.paw.web.user;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class LoginPage extends BasePage {

    @SpringBean
    private UserRepo users;

    private transient String email;
    private transient String password;

    public LoginPage() {

        final TextField<String> emailField = new TextField<String>("email");
        final PasswordTextField passwordField = new PasswordTextField("password");

        if (((HotelWicketSession) getSession()).isSignedIn()) {
            HotelWicketSession session = HotelWicketSession.get();
            setResponsePage(new ProfilePage(new PageParameters().set("username", users.getByEmail(session.getUserEmail()).getEmail())));
        }

        Form<LoginPage> form = new Form<LoginPage>(
                "loginForm",
                new CompoundPropertyModel<LoginPage>(this)) {

            @Override
            protected void onSubmit() {
                User user = users.getByEmail(email);
                if (user != null && user.getPassword().equals(password)) {
                    HotelWicketSession session = HotelWicketSession.get();
                    session.signIn(user.getEmail(), user.getPassword(), users);
                    setResponsePage(new ProfilePage(new PageParameters().set("username", users.getByEmail(session.getUserEmail()).getEmail())));
                } else {
                    error(getString("invalidCredentials"));
                }
            }
        };

        form.add(new TextField<String>("email").setRequired(true));
        form.add(new PasswordTextField("password").setRequired(true));
        form.add(new Button("login", new ResourceModel("login")));
        add(form);
    }

}
