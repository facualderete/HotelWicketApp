package ar.edu.itba.it.paw.web.comment;

import ar.edu.itba.it.paw.common.DateStringHelper;
import ar.edu.itba.it.paw.domain.*;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.base.SecuredPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.model.*;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Arrays;
import java.util.List;

public class CommentFormPage extends SecuredPage {

    @SpringBean
    private UserRepo users;

    @SpringBean
    HotelRepo hotelRepo;

    private static final List<String> COMPANIONS_OPTIONS = Arrays.asList("Pareja", "Amigos", "Familia", "Solo");
    private static final List<String> REASON_OPTIONS = Arrays.asList("Negocios", "Turismo");
    private transient String fromDate;
    private transient String toDate;
    private transient String companions = "Pareja";
    private transient String reason = "Negocios";
    private transient String details;
    private transient Boolean enabledCompanions = false;
    private transient Boolean enabledReason = false;

    public CommentFormPage(final PageParameters parameters) {


        final IModel<Hotel> hotel = new EntityModel(Hotel.class, hotelRepo.get(parameters.get("hotelId").toInteger()));

        final DropDownChoice<String> companionsChoice = new DropDownChoice<String>("companions",
                new PropertyModel<String>(this, "companions"), COMPANIONS_OPTIONS);

        final DropDownChoice<String> reasonChoice = new DropDownChoice<String>("reason",
                new PropertyModel<String>(this, "reason"), REASON_OPTIONS);

        final AjaxCheckBox toggleEnabledCompanions = new AjaxCheckBox("enabledCompanions", new PropertyModel<Boolean>(this, "enabledCompanions")) {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                companionsChoice.setOutputMarkupId(true);
                companionsChoice.setEnabled(!this.getModelObject());
                target.add(companionsChoice);
            }
        };

        final AjaxCheckBox toggleEnabledReason = new AjaxCheckBox("enabledReason", new PropertyModel<Boolean>(this, "enabledReason")) {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                reasonChoice.setOutputMarkupId(true);
                reasonChoice.setEnabled(!this.getModelObject());
                target.add(reasonChoice);
            }
        };

        companionsChoice.setOutputMarkupId(true);
        reasonChoice.setOutputMarkupId(true);

        final RatingEvaluationPanel hygieneRatingPanel = new RatingEvaluationPanel("hygieneRatingPanel", Model.of(getString("hygiene")));
        final RatingEvaluationPanel facilitiesRatingPanel = new RatingEvaluationPanel("facilitiesRatingPanel", Model.of(getString("facilities")));
        final RatingEvaluationPanel servicesRatingPanel = new RatingEvaluationPanel("servicesRatingPanel", Model.of(getString("services")));
        final RatingEvaluationPanel locationRatingPanel = new RatingEvaluationPanel("locationRatingPanel", Model.of(getString("location")));
        final RatingEvaluationPanel priceRatingPanel = new RatingEvaluationPanel("priceRatingPanel", Model.of(getString("price")));
        final RatingEvaluationPanel comfortRatingPanel = new RatingEvaluationPanel("comfortRatingPanel", Model.of(getString("comfort")));

        Form<CommentFormPage> form = new Form<CommentFormPage>("commentForm", new CompoundPropertyModel<CommentFormPage>(this)) {

            @Override
            protected void onSubmit() {
                HotelWicketSession session = HotelWicketSession.get();
                User user = session.getUser();

                Integer hygiene = hygieneRatingPanel.getEnabled() ? hygieneRatingPanel.getSelected() : 0;
                Integer facilities = facilitiesRatingPanel.getEnabled() ? facilitiesRatingPanel.getSelected() : 0;
                Integer services = servicesRatingPanel.getEnabled() ? servicesRatingPanel.getSelected() : 0;
                Integer location = locationRatingPanel.getEnabled() ? locationRatingPanel.getSelected() : 0;
                Integer price = priceRatingPanel.getEnabled() ? priceRatingPanel.getSelected() : 0;
                Integer comfort = comfortRatingPanel.getEnabled() ? comfortRatingPanel.getSelected() : 0;

                Comment comment = new Comment(DateStringHelper.getDateFromString(fromDate), DateStringHelper.getDateFromString(toDate),
                        DateStringHelper.getNowDate(), reason, companions,
                        details, hotel.getObject(), user, new Rating(hygiene, facilities, services, location, price, comfort));

                user.addComment(comment);
                users.save(user);
            }
        };

        form.add(companionsChoice);
        form.add(toggleEnabledCompanions);
        form.add(reasonChoice);
        form.add(toggleEnabledReason);
        form.add(new TextField("fromDate"));
        form.add(new TextField("toDate"));
        form.add(new TextArea<String>("details"));
        form.add(hygieneRatingPanel);
        form.add(facilitiesRatingPanel);
        form.add(servicesRatingPanel);
        form.add(locationRatingPanel);
        form.add(priceRatingPanel);
        form.add(comfortRatingPanel);
        form.add(new Button("submit", new ResourceModel("submit")));
        add(form);
    }
}
