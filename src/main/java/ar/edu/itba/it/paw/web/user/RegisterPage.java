package ar.edu.itba.it.paw.web.user;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class RegisterPage extends BasePage {

    @SpringBean
    private UserRepo users;

    private transient String name;
    private transient String lastname;
    private transient String description;
    private transient String email;
    private transient String password;
    private transient String password2;

    public RegisterPage() {

        add(new FeedbackPanel("feedbackPanel"));

        Form<RegisterPage> form = new Form<RegisterPage>(
                "registerForm",
                new CompoundPropertyModel<RegisterPage>(this)) {

            @Override
            protected void onSubmit() {
                if (users.getByEmail(email) != null) {
                    error(getString("email_already_used"));
                } else if (!password.equals(password2)) {
                    error(getString("password_nonmatch"));
                } else {
                    User newUser = new User(name, lastname, description, email, password);
                    users.save(newUser);
                    HotelWicketSession session = HotelWicketSession.get();
                    session.signIn(email, password, users);
                    continueToOriginalDestination();
                    setResponsePage(getApplication().getHomePage());
                }
            }
        };

        form.add(new TextField<String>("name").setRequired(true));
        form.add(new TextField<String>("lastname").setRequired(true));
        form.add(new TextArea<String>("description").setRequired(true));
        form.add(new TextField<String>("email").setRequired(true));
        form.add(new PasswordTextField("password").setRequired(true));
        form.add(new PasswordTextField("password2").setRequired(true));
        form.add(new Button("register", new ResourceModel("register")));
        add(form);
    }
}
