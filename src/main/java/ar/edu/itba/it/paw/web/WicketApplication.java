package ar.edu.itba.it.paw.web;

import ar.edu.itba.it.paw.common.CookieService;
import ar.edu.itba.it.paw.common.HibernateRequestCycleListener;
import ar.edu.itba.it.paw.web.hotel.HotelListPage;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see ar.edu.itba.it.paw.Start#main(String[])
 */

@Component
public class WicketApplication extends WebApplication {

	private final SessionFactory sessionFactory;
	private CookieService cookieService = new CookieService();
	private SessionProvider sessionProvider;
	public static final ResourceReference DEFAULT_PROFILE_IMAGE = new PackageResourceReference(WicketApplication.class, "resources/default_user_picture.png");

	@Autowired
	public WicketApplication(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HotelListPage.class;
	}

	@Override
	protected void init() {
		super.init();
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		getRequestCycleListeners().add(new HibernateRequestCycleListener(sessionFactory));
		sessionProvider = new SessionProvider(cookieService);
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new HotelWicketSession(request);
	}

	public CookieService getCookieService() {
		return cookieService;
	}
}
