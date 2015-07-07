package ar.edu.itba.it.paw.web;

import ar.edu.itba.it.paw.common.CookieService;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;

public class SessionProvider {

    @SpringBean
    private UserRepo users;
    private CookieService cookieService;

    public static final String REMEMBERME_USER = "rem_user";
    public static final String REMEMBERME_TOKEN = "rem_token";

    public SessionProvider(CookieService cookieService) {
        this.cookieService = cookieService;
        Injector.get().inject(this);
        Assert.state(users != null, "Can't inject users!");
    }

    public WebSession createNewSession(Request request) {
        HotelWicketSession session = new HotelWicketSession(request);
        Cookie cUserEmail = cookieService.loadCookie(request, REMEMBERME_USER);
        Cookie cToken = cookieService.loadCookie(request, REMEMBERME_TOKEN);
        User user = null;
        if (cUserEmail != null && cToken != null) {
            user = users.getByEmail(cUserEmail.getValue());
            if (user != null && user.validateToken(cToken.getValue())) {
                session.setUserEmailFromCookies(user.getEmail());
            }
        }

        return session;
    }
}