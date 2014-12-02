package ar.edu.itba.it.paw.helpers;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class SendMailHelper{

    private EmailServerConfigurationHelper smtpConfig = new EmailServerConfigurationHelper();

    public void sendMailTo(String mailTo, String hostName, String contextPath) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpConfig.getHost());
        props.put("mail.smtp.port", smtpConfig.getPort());

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(smtpConfig.getUsername(), smtpConfig.getPassword());
                    }
                });

        Message message = new MimeMessage(session);
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(mailTo));
        message.setSubject("Recuperación de contraseña");
        message.setText(this.getEmailBody(hostName, mailTo, contextPath));
        Transport.send(message);
    }

    public void sendErrorReport(String hostName, String exception) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpConfig.getHost());
        props.put("mail.smtp.port", smtpConfig.getPort());

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(smtpConfig.getUsername(), smtpConfig.getPassword());
                    }
                });

        Message message = new MimeMessage(session);
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(smtpConfig.getUsername()));
        message.setSubject("Reporte de error");
        message.setText(exception);
        Transport.send(message);
    }

    private String getEmailBody(String hostName, String mailTo, String contextPath){

        StringBuilder message = new StringBuilder();
        message.append("Ingrese a ");
        message.append("http://"+hostName+contextPath+"/bin/user/resetPassword?email="+mailTo);
        message.append(" para configurar una nueva contraseña.");
        return message.toString();
    }
}