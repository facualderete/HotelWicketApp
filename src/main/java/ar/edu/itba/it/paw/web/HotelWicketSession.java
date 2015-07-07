package ar.edu.itba.it.paw.web;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import org.apache.wicket.Session;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class HotelWicketSession extends WebSession {

    private String userEmail;

    private IModel<User> user;

    public static HotelWicketSession get() {
        return (HotelWicketSession) Session.get();
    }

    public HotelWicketSession(Request request) {
        super(request);
    }

    public String getUserEmail() {
        return userEmail;
    }

    public User getUser() {
        return (user != null) ? user.getObject() : null;
    }

    public boolean signIn(String email, String password, UserRepo users) {
        if (this.userEmail != null)
            return true;
        User user = users.getByEmail(email);
        if (user != null && user.checkPassword(password)) {
            this.userEmail = email;
            this.user = new EntityModel<User>(User.class, user);
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

    public boolean isAdmin(UserRepo users){
        return isSignedIn() && users.getByEmail(userEmail).getAdmin();
    }

    protected void setUserEmailFromCookies(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public void detach() {
        super.detach();
        if (user != null)
            user.detach();
    }
}
