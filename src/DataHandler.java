import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataHandler {

    //  Dosyamda hangi bölümlerin olacağını değişkene attım
    private final String elitSection = "ELİT ÜYELER";
    private final String genelSection = "GENEL ÜYELER";
    private final String fileName;

    //  constructor
    public DataHandler(String fileName){
        this.fileName = fileName;
    }


    //  Yeni kullanıcı eklemek için gerekli bilgileri ve hangi bölüme ekleyeceğimin bilgisini 0 1 değerleri ile genel ya da elit olduğunu belirttik
    public void addUser(String isim, String soyisim, String email, int choice) throws IOException{
        String sectionHeader = (choice == 1) ? elitSection : genelSection;
        String newUserData = isim + "\t" + soyisim + "\t" + email;

        //  Tüm dosyanın satırlarını listeye aldım bu şekilde istediğim satıra random access kazandım
        List<String> lines = Files.readAllLines(Paths.get(fileName));


        //  section satrılarımı buldum
        int sectionIndex = -1;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).equals(sectionHeader)) {
                sectionIndex = i;
                break;
            }
        }

        if (sectionIndex == -1) {
            sectionIndex = lines.size();
            lines.add("");
            lines.add(sectionHeader);
        }


        //  yeni kullanıcıyı dosyaya yazmak üzere listeyi ekledik
        lines.add(sectionIndex + 1, newUserData);

        //  dosyaya tüm satırları yazdık
        Files.write(Paths.get(fileName), lines, StandardCharsets.UTF_8);
    }


    //  Dosyadan verileri okumamızı sağlayan fonksiyon hangi bölümü istedğimizi buraya yazıyoruz; Elit, Genel ya da All
    public List<String> getEmails(String section) throws FileNotFoundException {
        List<String> emails = new ArrayList<>();
        Scanner scanner = new Scanner(new File(fileName));
        boolean foundSection = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.equalsIgnoreCase(section)) {
                foundSection = true;
            }
            else if (foundSection && !line.trim().isEmpty()) {
                //  Verilerden sadece email'i almak için verileri tabla bölüyoruz
                String[] parts = line.trim().split("\\s+");

                //  ve sondaki parçayı alıyoruz çünkü mail bilgisi bu kısımda
                if (parts.length >= 2) {
                    emails.add(parts[parts.length - 1]);
                }
            }
            else if (foundSection) {
                break;
            }
        }

        //  Tüm maillerin istenmesi durumunda iki kez fonksiyonumuzu genel ve elit diye çağırıyoruz ve listeye ekliyoruz
        if (section.equalsIgnoreCase("all")) {
            List<String> allEmails = new ArrayList<String>();
            allEmails.addAll(getEmails("ELİT ÜYELER"));
            allEmails.addAll(getEmails("GENEL ÜYELER"));

            scanner.close();
            return allEmails;
        }

        scanner.close();
        return emails;
    }
}
