import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {

    //  Mail göndermek için Google SMTP server'ına bağlandığımız key ile giriş yapmak için
    private final String username;// email
    private final String password;// smtp şifresi
    private final Properties properties;
    public MailSender(String username, String password, String smtpHost, String smtpPort) {
        this.username = username;
        this.password = password;


        //  SMTP server'ına bağlanmak için gerekli bilgileri yerleştiriyoruz
        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
    }


    //  Mail gönderme metodumuz; göndereceğimiz adres, başlık ve içerik alıyor
    public void sendEmail(String recipient, String subject, String body) throws MessagingException {

        //  Mail göndermek üzere oturum doğrulama işlemi yapılıyor
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });


        //  Göndermek üzere mesaj oluşturuyoruz
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);
        message.setText(body);
        Transport.send(message);
    }
}
