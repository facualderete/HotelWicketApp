package ar.edu.itba.it.paw.web.user;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.feedback.CustomFeedbackPanel;
import ar.edu.itba.it.paw.web.hotel.HotelListPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

public class ResetPasswordPage extends BasePage {

    @SpringBean
    UserRepo userRepo;

    IModel<User> userModel = new EntityModel<User>(User.class);

    String password;
    String password2;

    public ResetPasswordPage(PageParameters pageParameters) {

        StringValue userEmail = pageParameters.get("userEmail");
        userModel.setObject(userRepo.getByEmail(userEmail.toString()));
        if (userModel.getObject() == null) setResponsePage(HotelListPage.class);

        add(new CustomFeedbackPanel("feedbackPanel"));

        Form<ResetPasswordPage> form = new Form<ResetPasswordPage>(
                "resetPasswordForm",
                new CompoundPropertyModel<ResetPasswordPage>(this)) {

            @Override
            protected void onSubmit() {
                if (!password.equals(password2)) {
                    error(getString("password_nonmatch"));
                } else {
                    userModel.getObject().setPassword(password);
                    success(getString("successfully_changed_password"));
                }
            }
        };

        add(new Label("title", getString("title") + " " + userModel.getObject().getEmail()));
        form.add(new PasswordTextField("password").setRequired(true));
        form.add(new PasswordTextField("password2").setRequired(true));
        form.add(new Button("submit", new ResourceModel("submit")));
        add(form);
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        userModel.detach();
    }
}
