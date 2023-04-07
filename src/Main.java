import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, MessagingException {
        //  Programımız için gereken objeleri türettik
        Scanner scanner = new Scanner(System.in);
        DataHandler fileHandler = new DataHandler("Kullanıcılar.txt");

        //  Mail gönderebilmemiz için Google server API anahtarı çıkarttım kendi hesabıma, siz tercih ettiğiniz SMTP server bilgilerinizi bu değişkenlere girin
        final String senderEmail = "enesbayraktutancagri@gmail.com";
        final String password = "gjqooujrbikqheex";
        MailHandler mailHandler = new MailHandler(senderEmail, password, "smtp.gmail.com", "587");
        List<String> emailList;

        //  Program loop'u ve gereksiz obje oluşmaması için değişkenlerimi burada tanımlıyorum
        int choice = 1;
        String isim, soyisim, email, mailBilgisi, mailBaslik;


        //  Program döngüm iç içe 2 enhanced switch case'ten oluşuyor
        while(choice != 0){
            System.out.print("\n1:Elit üye ekleme\n2:Genel üye ekleme\n3:Mail gönderme\n0:Çıkış\nSeçim: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            //  ilk 3 seçeneğin olduğu ekran
            switch (choice) {

                //  çıkış case
                case 0 -> {
                    System.out.println("Çıkış yapılıyor...");
                    break;
                }

                //  Elit ekleme case
                case 1 -> {
                    System.out.print("Ekleyeceğiniz elit üyenin bilgileri\nİsim:");
                    isim = scanner.nextLine();

                    System.out.print("Soyisim:");
                    soyisim = scanner.nextLine();

                    System.out.print("Email:");
                    email = scanner.nextLine();

                    try{
                        fileHandler.addUser(isim, soyisim, email, 1);
                        System.out.printf("Sisteme elit üye eklendi\nBilgiler:\nİsim: %s\nSoyisim: %s\nEmail: %s\n", isim, soyisim, email);
                    } catch (IOException e){
                        System.out.println("Hata oluştu: " + e.getMessage());
                    }
                    break;

                //  Genel ekleme case
                }
                case 2 -> {
                    System.out.print("Ekleyeceğiniz genel üyenin bilgileri\nİsim:");
                    isim = scanner.nextLine();

                    System.out.print("Soyisim:");
                    soyisim = scanner.nextLine();

                    System.out.print("Email:");
                    email = scanner.nextLine();

                    try{
                        fileHandler.addUser(isim, soyisim, email, 2);
                        System.out.printf("Sisteme genel üye eklendi\nBilgiler:\nİsim: %s\nSoyisim: %s\nEmail: %s\n", isim, soyisim, email);
                    } catch (IOException e){
                        System.out.println("Hata oluştu: " + e.getMessage());
                    }
                    break;

                //  Mail gönderme case
                }
                case 3 -> {
                    System.out.print("Mail Gönderme Ekranı: \n1: Elit üyelere mail gönder\n2: Genel üyelere mail gönder\n3: Tüm üyelere mail gönder\n4: Geri \nSeçim: ");
                    choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice){

                        //  Ana menüye dönme case
                        case 0 -> {
                            System.out.println("Geri");
                            break;
                        }

                        //  Elit mail gönderme case
                        case 1 ->{

                            //  Mail bilgilerini alıyoruz
                            System.out.println("Elit üyelere atılacak mailin başlığını giriniz: ");
                            mailBaslik = scanner.nextLine();
                            System.out.println("Elit üyelere atılacak mailin içeriğini giriniz: ");
                            mailBilgisi = scanner.nextLine();

                            //  fileHandler ile kullanıcılar.txt dosyamızın içerisinden Elit üyeler bölümündeki mail adreslerini listemize aktardık
                            emailList = fileHandler.getEmails("ELİT ÜYELER");

                            //  mailSender class'ından miras alan mailHander class'ını kullanarak çoklu mail gönderdik
                            mailHandler.sendMulitpleEmail(emailList, mailBaslik, mailBilgisi);


                            System.out.printf("\nMail gönderildi, Mail İçeriği: \nKONU:%s\nİÇERİK:%s", mailBaslik, mailBilgisi);

                            break;
                        }

                        //  Genel mail gönderme case
                        case 2 ->{
                            System.out.println("Genel üyelere atılacak mailin başlığını giriniz: ");
                            mailBaslik = scanner.nextLine();
                            System.out.println("Genel üyelere atılacak mailin içeriğini giriniz: ");
                            mailBilgisi = scanner.nextLine();

                            emailList = fileHandler.getEmails("GENEL ÜYELER");
                            mailHandler.sendMulitpleEmail(emailList, mailBaslik, mailBilgisi);

                            System.out.printf("\nMail gönderildi, Mail İçeriği: \nKONU:%s\nİÇERİK:%s", mailBaslik, mailBilgisi);
                            break;
                        }

                        //  Tüm üyelere mail gönderme case
                        case 3 ->{
                            System.out.println("Tüm üyelere atılacak mailin başlığını giriniz: ");
                            mailBaslik = scanner.nextLine();
                            System.out.println("Tüm üyelere atılacak mailin içeriğini giriniz: ");
                            mailBilgisi = scanner.nextLine();

                            emailList = fileHandler.getEmails("ALL");
                            mailHandler.sendMulitpleEmail(emailList, mailBaslik, mailBilgisi);

                            System.out.printf("\nMail gönderildi, Mail İçeriği: \nKONU:%s\nİÇERİK:%s", mailBaslik, mailBilgisi);
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }
}