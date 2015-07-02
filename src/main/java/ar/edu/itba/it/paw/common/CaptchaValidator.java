package ar.edu.itba.it.paw.common;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import ar.edu.itba.it.paw.web.HotelWicketSession;

import com.google.code.kaptcha.Constants;

public class CaptchaValidator implements IValidator<String> {

    private static final long serialVersionUID = 1L;

    public void validate(IValidatable<String> validatable) {

        String kaptchaReceived = validatable.getValue();

        HotelWicketSession session = HotelWicketSession.get();

        ValidationError error = new ValidationError();

        String kaptchaExpected = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (kaptchaReceived == null
                || !kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
            error.addKey("captcha_error");
            validatable.error(error);
        }

    }
}
