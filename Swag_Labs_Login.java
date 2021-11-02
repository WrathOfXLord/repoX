package Swag_Labs_Login;

import java.awt.Toolkit;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Swag_Labs_Login {
        /**
         * @param args
         */
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

                //zaman asimi icin
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

                // sayfa url
                String appUrl = "https://www.saucedemo.com/";

                // tarayiciyi verilen url ile baslat
                driver.get(appUrl);

                // tarayiciyi sola çek ve ekranin yarisini al
                driver.manage().window().setPosition(new Point(0,0));
                int screenX = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
                int screenY = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
                driver.manage().window().setSize(new Dimension(screenX/2, screenY));

                // beklenen sekme basligi
                String expectedTitle = "Swag Labs";

                // gercek sekme basligi
                String actualTitle = driver.getTitle();

                // basliklari karsilastir
                if (expectedTitle.equalsIgnoreCase(actualTitle)) {
                        System.out.println("Verification Successful - The correct title is displayed on the web page.");
                } else {
                        System.out.println("Verification Failed - An incorrect title is displayed on the web page.");
                }

                // kullanici adi boslugu ve ileri butonu
                WebElement username = driver.findElement(By.id("user-name"));
                WebElement userLoginButton = driver.findElement(By.id("login-button"));
                username.clear();
                username.sendKeys("standard_user");

                // parola boslugu
                WebElement password = driver.findElement(By.id("password"));
                // String correctPassword = "secret_sauce";
                String wrongPassword = "IamSoWrong";
                password.clear();
                password.sendKeys(wrongPassword);
                userLoginButton.click();

                // ekran goruntusu alabilmek icin 5 saniye beklemesini sagladik
                long milliseconds = 5000;
                // threadler senkronize olsun diye blokta
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

                // system.exit kullanilma sebebi main'imiz void oldugundan return degerine sahip
                // olmamasi
                System.exit(0);
        }
}