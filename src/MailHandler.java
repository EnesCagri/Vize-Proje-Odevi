import javax.mail.MessagingException;
import java.util.List;

public class MailHandler extends MailSender{

    //  constructor
    public MailHandler(String username, String password, String smtpHost, String smtpPort){
        super(username, password, smtpHost, smtpPort);
    }

    //  Overrdie işlemi sayesinde kime mail gönderdiğini ekledim
    @Override
    public void sendEmail(String recipient, String subject, String body) throws MessagingException {
        System.out.printf("%s adresine mail gönderildi.\n", recipient);
        super.sendEmail(recipient, subject, body);
    }


    //  Buradaki yeni metot sayesinde çoklu mail gönderiyoruz, tek bir mail yerine mail listesi vererek
    public void sendMulitpleEmail(List<String> recipients, String subject, String body) throws MessagingException{
        for(String recipient : recipients){
            sendEmail(recipient, subject, body);
        }
    }
}
