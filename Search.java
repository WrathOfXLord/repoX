import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class Search {
    public static void main(String[] args) {
        // Chrome baslatma secenekleri
        ChromeOptions options = new ChromeOptions();
        // chrome browser path
        // String chromeBinary = "C:/Program Files/Google/Chrome/Application/chrome.exe";
        // chromium tabanli brave path
        String braveBinary = "C:/Program Files/BraveSoftware/Brave-Browser/Application/brave.exe";
        options.setBinary(braveBinary);

        // chromedriver
        WebDriver driver = new ChromeDriver(options);

        // zaman asimi icin
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        // sayfa url
        String appUrl = "https://www.n11.com";

        // tarayiciyi verilen url ile baslat
        driver.get(appUrl);

        // ekrani kapla
        driver.manage().window().maximize();
        
        // sekme basligi
        String actualTitle = driver.getTitle();
        String expectedTitle = "n11.com - Hayat Sana Gelir";


    // basliklari karsilastir
    if (expectedTitle.equalsIgnoreCase(actualTitle)) {
            System.out.println("Verification Successful - The correct title is displayed on the web page.");
    } else {
            System.out.println("Verification Failed - An incorrect title is displayed on the web page.");
    }

    // arama kutusu
    WebElement searchBox = driver.findElement(By.id("searchData"));
    WebElement searchButton = driver.findElement(By.className("searchBtn"));
    String searchText = "bilgisayar";
    searchBox.clear();
    searchBox.sendKeys(searchText);
    searchButton.click();

    try {
        // sonuc metnini ara
        WebElement resultText = driver.findElement(By.className("resultText"));

        //varsa sonuc metnine dogru sayfayi kaydir
        Actions scrollToElement = new Actions(driver);
        scrollToElement.moveToElement(resultText);
        scrollToElement.perform();

        System.out.println("Search Successful !");
        
        // metin daginik geldigi icin bir araya gelmesini sagladik.
        String[] arr = resultText.getText().split("\n");
        for(int len = 0; len < arr.length; ++len) {
                System.out.print(arr[len] + " ");
        }
        System.out.println();
                
    } catch (NoSuchElementException ex) {
        //sonuc bulunamazsa buraya gelecek, birkac saniye zaman alabiliyor
        System.out.println("Search Failed !\n" + "No Results For The Search");
    }
    

    // ekran goruntusu alabilmek icin 5 saniye beklemesini sagladik
    long milliseconds = 5000;

    // beklemeyi saglamak icin threadler senkronize olmali
    synchronized (driver) {
            try {
                    driver.wait(milliseconds);
                    System.out.println("Test script executed successfully.");
            } catch (InterruptedException e) {
                    System.err.println("Wait Failed");
                    e.printStackTrace();
            } finally {
                    // tarayiciyi kapatmak ve driver process'ini sonlandirmak icin
                    driver.quit();
            }
    }

        System.exit(0);
    }
}
