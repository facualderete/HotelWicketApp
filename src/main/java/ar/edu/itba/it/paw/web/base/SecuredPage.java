package ar.edu.itba.it.paw.web.base;

import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.user.LoginPage;

@SuppressWarnings("serial")
public abstract class SecuredPage extends BasePage{

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
