package ar.edu.itba.it.paw.web;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class HotelWicketSession extends WebSession{

    @SpringBean
    private UserRepo users;

    private String userEmail;

    public static HotelWicketSession get() {
        return (HotelWicketSession) Session.get();
    }

    public HotelWicketSession(Request request) {
        super(request);
    }

    public String getUserEmail() {
        return userEmail;
    }

    public boolean signIn(String userEmail, String password) {
        User user = users.getByEmail(userEmail);
        if (user != null && user.getPassword().equals(password)) {
            this.userEmail = userEmail;
            return true;
        }
        return false;
    }

    public boolean isSignedIn() {
        return userEmail != null;
    }

    public void signOut() {
        invalidate();
        clear();
    }

    public boolean isAdmin(){
        return isSignedIn() && users.getByEmail(userEmail).getAdmin();
    }
}
