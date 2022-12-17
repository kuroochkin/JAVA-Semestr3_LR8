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

    // Метод заполнения полей класса
    static void model(){

        myEmail = "pin11kurochkin@gmail.com";
        pass = "xnpg aqrt ukfa qflw";
        recipientEmail = "kuroochkin2003@mail.ru";
        subject = "Напоминание";
        text = "Вам пришел текстовый файл в другом сообщении.";

        // Настройки подключения к Google Account
        settings = new Properties();
        settings.put("mail.smtp.auth", "true");
        settings.put("mail.smtp.starttls.enable", "true");
        settings.put("mail.smtp.host", "smtp.gmail.com");
        settings.put("mail.smtp.port", "587");
        settings.put("mail.smtp.ssl.protocols", "TLSv1.2");
    }

    static void view(String msg){
        System.out.println(msg);
    } // Метод для удобства представления

    static void controller(){
        try {
            model(); // Подключаем модель, с которой будем работать
            view("Данные загружены");
            // Отправляем наши данные в метод SendMail() и отправляем первое сообщение
            Email.SendEmail(recipientEmail, myEmail, pass, settings, subject, text);
        }catch (Exception e){
            view("Error in controller(): " +e.getMessage());
        }


        model();
        Session session = Session.getInstance(settings, new Authenticator() { //получаем данны об аутентификации
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, pass); // сама аутентификация
            }
        });
        try {
            // Создаем составное сообщение
            MimeMessage message = new MimeMessage(session);

            // Настраиваем свою почту и адресат
            message.setFrom(new InternetAddress(myEmail));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(recipientEmail));
            message.setSubject("Важная информация!");

            // Тело сообщения
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Обязательно прочитай текстовый файл!");

            // Файл, который отправляем
            BodyPart filePart = new MimeBodyPart();
            String filename = "test.txt";
            DataSource source = new FileDataSource(filename);
            filePart.setDataHandler(new DataHandler(source));
            filePart.setFileName(filename);


            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(filePart);

            message.setContent(multipart);

            // Отправляем сообщение с файлом
            Transport.send(message);
            view("Данные отправлены");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        controller();
    }
}


