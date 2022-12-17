import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class Email {
    public static void SendEmail(String recipientEmail, String myEmail, String pass, Properties settings, String subject, String text) throws Exception {
        Session session = Session.getInstance(settings, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, pass);
            }
        });
        Message msg = prepareMessage(session, myEmail, recipientEmail, subject, text);

        // Отправить сообщение
        Transport.send(msg);
    }

    private static Message prepareMessage(Session session, String myEmail, String recipientEmail, String subject, String text){
        try{
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(myEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            msg.setSubject(subject);
            msg.setText(text);
            return msg;
        } catch (Exception ex){
            System.out.println("Error: "+ ex.getMessage());
        }
        return null;
    }
}
