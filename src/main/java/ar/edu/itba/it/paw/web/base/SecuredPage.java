package ar.edu.itba.it.paw.web.base;

import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.user.LoginPage;

public class SecuredPage extends BasePage{

    private static final long serialVersionUID = 1L;

    public SecuredPage() {
        HotelWicketSession session = getHotelAppSession();
        if (!session.isSignedIn()) {
            redirectToInterceptPage(new LoginPage());

        }
    }

    protected HotelWicketSession getHotelAppSession() {
        return (HotelWicketSession) getSession();
    }

}
