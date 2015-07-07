package ar.edu.itba.it.paw.web.comment;


import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import java.util.Arrays;
import java.util.List;

public class RatingEvaluationPanel extends Panel {

    private static final List<Integer> TYPES = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    private Integer selected = 1;
    private Boolean enabled = false;

    public RatingEvaluationPanel(String id, IModel<String> evaluationLabel) {
        super(id);

        final RadioChoice<Integer> evaluation = new RadioChoice<Integer>("evaluation",
                new PropertyModel<Integer>(this, "selected"), TYPES).setSuffix("");

        final AjaxCheckBox toggleEnabled = new AjaxCheckBox("enabled", new PropertyModel<Boolean>(this, "enabled")) {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                evaluation.setOutputMarkupId(true);
                evaluation.setEnabled(!this.getModelObject());
                target.add(evaluation);
            }
        };

        evaluation.setOutputMarkupId(true);
        add(toggleEnabled);
        add(evaluation);
        add(new Label("label", evaluationLabel));
        add(new Label("noAnswerLabel", getString("noAnswer")));
    }

    public Integer getSelected() {
        return selected;
    }

    public Boolean getEnabled() {
        return enabled;
    }
}
