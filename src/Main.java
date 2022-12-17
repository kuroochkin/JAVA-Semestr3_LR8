import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class Main {
    private static String myEmail; // Моя почта
    private static String pass; // Пароль моей мочты
    private static String recipientEmail; // Адрес отправки
    private static Properties settings; // Конфигурация
    private static String subject; // Тема письма
    private static String text; // Тело письма

    static void modelDefineAllData(){

        myEmail = "pin11kurochkin@gmail.com";
        pass = "";
        recipientEmail = "pin11kurochkin@gmail.com";
        subject = "Дюхе";
        text = "Сагу братик";

        settings = new Properties();
        settings.put("mail.smtp.auth", "true");
        settings.put("mail.smtp.starttls.enable", "true");
        settings.put("mail.smtp.host", "smtp.gmail.com");
        settings.put("mail.smtp.port", "587");
        settings.put("mail.smtp.ssl.protocols", "TLSv1.2");
    }

    static void view(String msg){
        System.out.println(msg);
    }

    static void controller(){
        try {
            modelDefineAllData();
            view("Preparing");
            Email.SendEmail(recipientEmail, myEmail, pass, settings, subject, text);
            view("Done");
        }catch (Exception e){
            view("Error in controller(): " +e.getMessage());
        }


        modelDefineAllData();
        Session session = Session.getInstance(settings, new Authenticator() { //получаем данны об аутентификации
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, pass); // сама аутентификация
            }
        });
        try {
            //составное сообщение
            MimeMessage message = new MimeMessage(session);

            //от кому тема
            message.setFrom(new InternetAddress(myEmail));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(recipientEmail));
            message.setSubject("Это от Владоса!");

            //тело сообщение
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Обязательно прочитай текстовый файл!");

            //файл
            BodyPart filePart = new MimeBodyPart();
            String filename = "test.txt";
            DataSource source = new FileDataSource(filename);
            filePart.setDataHandler(new DataHandler(source));
            filePart.setFileName(filename);


            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(filePart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("finish");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        Preloader settings = new Preloader("Settings.ini", props);
        view("Hello, "+props.getProperty("Login")+"! It is Emailsander");
        controller();
    }
}


