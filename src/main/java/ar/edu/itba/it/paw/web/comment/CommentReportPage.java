package ar.edu.itba.it.paw.web.comment;


import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.CommentRepo;
import ar.edu.itba.it.paw.web.base.BasePage;

import com.googlecode.wickedcharts.highcharts.options.Options;

public class CommentReportPage extends BasePage {

	private static final List<String> CHOICES = Arrays.asList(new String[] {
			"Day", "Month", "Year" });
	private String fromDate;
	private String toDate;
	private String groupBy = "Day";
	private final Options options = new Options();
	private IModel<List<Comment>> modelComments;
	//private Chart chart = new Chart("chart", options);

	@SpringBean
	private CommentRepo comments;

	public CommentReportPage() {
		final Form<Void> form = new Form<Void>("form");
		final RadioChoice<String> radioChoice = new RadioChoice<String>(
				"radioChoice", new PropertyModel<String>(this, "groupBy"),
				CHOICES);
		final TextField<String> fromDateTxtField = new TextField<String>("fromDate");
		fromDateTxtField.setRequired(true);
		final TextField<String> toDateTxtField = new TextField<String>("toDate");
		toDateTxtField.setRequired(true);
		
		
		modelComments = new LoadableDetachableModel<List<Comment>>() {
			@Override
			protected List<Comment> load() {
				return comments.getWithinRange(fromDate, toDate);
			}
		};
		
		final Button confirmButton = new Button("confirm");
		
		form.add(fromDateTxtField);
		form.add(new ComponentFeedbackPanel("fromDate_error", fromDateTxtField));
		form.add(toDateTxtField);
		form.add(new ComponentFeedbackPanel("toDate_error", toDateTxtField));
		form.add(radioChoice);	
		form.add(confirmButton);
		add(form);
		//add(chart);
		
	}

}
