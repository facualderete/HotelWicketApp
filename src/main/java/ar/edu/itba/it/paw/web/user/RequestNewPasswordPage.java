package ar.edu.itba.it.paw.web.user;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.helpers.SendMailHelper;
import ar.edu.itba.it.paw.web.WicketApplication;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.feedback.CustomFeedbackPanel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;

import javax.mail.MessagingException;

public class RequestNewPasswordPage extends BasePage {

    @SpringBean
    UserRepo userRepo;

    @SpringBean
    SendMailHelper sendMailHelper;

    String userEmail;

    public RequestNewPasswordPage() {

        add(new CustomFeedbackPanel("feedbackPanel"));

        Form<RequestNewPasswordPage> form = new Form<RequestNewPasswordPage>(
                "requestPasswordForm",
                new CompoundPropertyModel<RequestNewPasswordPage>(this)) {

            @Override
            protected void onSubmit() {
                User user = userRepo.getByEmail(userEmail);
                if (user != null) {

                    StringBuilder url = new StringBuilder();
                    url.append(RequestCycle.get().getUrlRenderer().getBaseUrl().getHost());
                    url.append(":");
                    url.append(RequestCycle.get().getUrlRenderer().getBaseUrl().getPort().toString());

                    StringBuilder message = new StringBuilder();
                    message.append(getString("message_body"));
                    message.append(" ");
                    message.append("http://"+url.toString()+"/bin/"+ WicketApplication.RESET_PASSWORD_URL+userEmail);

                    try {
                        sendMailHelper.sendMailTo(userEmail, message.toString(), getString("message_subject"));
                        success(getString("sending_message_success") + " " + userEmail);
                    } catch (MessagingException e) {
                        error(getString("sending_message_error"));
                    }
                } else {
                    error(getString("invalid_user_error"));
                }
            }
        };

        form.add(new TextField<String>("userEmail").setRequired(true));
        form.add(new Button("submit", new ResourceModel("submit")));
        add(form);
    }
}
