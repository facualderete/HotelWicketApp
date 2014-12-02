package ar.edu.itba.it.paw.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class HelloWorldPage extends WebPage{

    public HelloWorldPage() {
        add(new Label("message", "HOLA MUNDO (imprime la clase)"));
    }
}
