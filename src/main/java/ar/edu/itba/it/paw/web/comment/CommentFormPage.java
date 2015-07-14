package ar.edu.itba.it.paw.web.comment;

import ar.edu.itba.it.paw.common.DateHelper;
import ar.edu.itba.it.paw.domain.*;
import ar.edu.itba.it.paw.web.HotelWicketSession;
import ar.edu.itba.it.paw.web.base.SecuredPage;
import ar.edu.itba.it.paw.web.feedback.CustomFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.*;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CommentFormPage extends SecuredPage {

    @SpringBean
    private UserRepo users;

    @SpringBean
    HotelRepo hotelRepo;

    @SpringBean
    CommentRepo commentRepo;

    //TODO: internacionalizar estooooo!!!
    private static final List<String> COMPANIONS_OPTIONS = Arrays.asList("Pareja", "Amigos", "Familia", "Solo");
    private static final List<String> REASON_OPTIONS = Arrays.asList("Negocios", "Turismo");

    private String companions = "Pareja";
    private String reason = "Negocios";
    private String comment;
    private Boolean enabledCompanions = false;
    private Boolean enabledReason = false;

    final IModel<Hotel> hotelModel = new EntityModel<Hotel>(Hotel.class);
    final IModel<User> userModel = new EntityModel<User>(User.class);
    final IModel<Comment> commentModel = new EntityModel<Comment>(Comment.class);

    public CommentFormPage(final PageParameters parameters) {

        if (parameters.get("commentId") != null) {
            commentModel.setObject(commentRepo.get(parameters.get("commentId").toInteger()));
            companions = commentModel.getObject().getCompanions();
            reason = commentModel.getObject().getReason();
            comment = commentModel.getObject().getDetails();
            if (companions != null) enabledCompanions = true;
            if (reason != null) enabledReason = true;
        } else if (parameters.get("hotelId") != null) {
            hotelModel.setObject(hotelRepo.get(parameters.get("hotelId").toInteger()));
        }

        userModel.setObject(users.getByEmail(HotelWicketSession.get().getUserEmail()));

        add(new CustomFeedbackPanel("feedbackPanel"));

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

        final DatePickerPanel fromDatePanel = new DatePickerPanel("fromDate");
        final DatePickerPanel toDatePanel = new DatePickerPanel("toDate");

        Form<CommentFormPage> form = new Form<CommentFormPage>("commentForm", new CompoundPropertyModel<CommentFormPage>(this)) {

            @Override
            protected void onSubmit() {

                Date fromDate = fromDatePanel.getDate();
                Date toDate = toDatePanel.getDate();

                if (validateDates(fromDate, toDate)) {
                    Integer hygiene = hygieneRatingPanel.getEnabled() ? hygieneRatingPanel.getSelected() : 0;
                    Integer facilities = facilitiesRatingPanel.getEnabled() ? facilitiesRatingPanel.getSelected() : 0;
                    Integer services = servicesRatingPanel.getEnabled() ? servicesRatingPanel.getSelected() : 0;
                    Integer location = locationRatingPanel.getEnabled() ? locationRatingPanel.getSelected() : 0;
                    Integer price = priceRatingPanel.getEnabled() ? priceRatingPanel.getSelected() : 0;
                    Integer comfort = comfortRatingPanel.getEnabled() ? comfortRatingPanel.getSelected() : 0;

//                    Comment comment = new Comment(DateHelper.getFromUtilDate(fromDate), DateHelper.getFromUtilDate(toDate),
//                        DateHelper.getNowDate(), reason, companions, details, hotelModel.getObject(),
//                        commentUserModel.getObject(), new Rating(hygiene, facilities, services, location, price, comfort));
//
//                    commentUserModel.getObject().addComment(comment);
                }
            }
        };

        form.add(companionsChoice);
        form.add(toggleEnabledCompanions);
        form.add(reasonChoice);
        form.add(toggleEnabledReason);
        form.add(new TextArea<String>("comment").setRequired(true));
        form.add(hygieneRatingPanel);
        form.add(facilitiesRatingPanel);
        form.add(servicesRatingPanel);
        form.add(locationRatingPanel);
        form.add(priceRatingPanel);
        form.add(comfortRatingPanel);
        form.add(fromDatePanel);
        form.add(toDatePanel);
        form.add(new Button("submit", new ResourceModel("submit")));
        add(form);
    }

    private boolean validateDates(Date fromDate, Date toDate) {
        if(fromDate != null && toDate != null){

            DateTime from = DateHelper.getFromUtilDate(fromDate);
            DateTime to = DateHelper.getFromUtilDate(toDate);
            DateTime now = DateHelper.getNowDate();

            if(fromDate.compareTo(toDate) > 0) {
                error(getString("date_error_range"));
                return false;
            }

            if(from.compareTo(now) > 0 || to.compareTo(now) > 0) {
                error(getString("date_error_future"));
                return false;
            }

//            for(Comment c : commentUserModel.getObject().getComments()){
//                if(c.getId() != form.getEditingCommentId() && !((c.getFromDate().compareTo(fromDate) < 0 && c.getFromDate().compareTo(toDate) < 0) ||
//                        (c.getToDate().compareTo(fromDate) > 0 && c.getToDate().compareTo(toDate) > 0))){
//
//                    error(getString("date_error_collision"));
//                    break;
//                }
//            }

            return true;
        } else {
            error(getString("mandatory_dates"));
            return false;
        }
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        hotelModel.detach();
        userModel.detach();
        commentModel.detach();
    }
}
