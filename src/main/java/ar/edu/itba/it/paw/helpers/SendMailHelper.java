package ar.edu.itba.it.paw.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class SendMailHelper {

    @Autowired
    AppConfigurationHelper configuration;

    public void sendMailTo(String mailTo, String messageBody, String subject) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", configuration.getHost());
        props.put("mail.smtp.port", configuration.getPort());

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(configuration.getUsername(), configuration.getPassword());
                    }
                });

        Message message = new MimeMessage(session);
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(mailTo));
        message.setSubject(subject);
        message.setText(messageBody);
        Transport.send(message);
    }

    public void sendErrorReport(String hostName, String exception) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", configuration.getHost());
        props.put("mail.smtp.port", configuration.getPort());

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(configuration.getUsername(), configuration.getPassword());
                    }
                });

        Message message = new MimeMessage(session);
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(configuration.getUsername()));
        message.setSubject("Reporte de error");
        message.setText(exception);
        Transport.send(message);
    }

    private String getEmailBody(String hostName, String mailTo, String contextPath) {

        StringBuilder message = new StringBuilder();
        message.append("Ingrese a ");
        message.append("http://"+hostName+contextPath+"/bin/user/resetPassword?email="+mailTo);
        message.append(" para configurar una nueva contraseña.");
        return message.toString();
    }
}