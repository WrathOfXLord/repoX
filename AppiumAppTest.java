
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class AppiumAppTest {
    static  DesiredCapabilities dc = new DesiredCapabilities();
    static {
        dc.setCapability("deviceName", "Nexus_5_API_30");
        dc.setCapability("udid", "emulator-5554");
        dc.setCapability("platformName", "Android");
        dc.setCapability("platformVersion", "11.0");

        dc.setCapability("appPackage", "com.example.myapp");
        dc.setCapability("appActivity", "com.example.myapp.MainActivity");
    }
    static final String url = "http://127.0.0.1:4723/wd/hub";
    static int successfulTestCount;

    private AndroidDriver driver;
      

    public boolean isConnected() {
        if(driver == null)
            return false;
        return true;
    }
        
    public void connectServer() throws MalformedURLException {
        driver = new AndroidDriver(new URL(AppiumAppTest.url), dc);
        System.out.println("Connection established to server");
        
    }

    /** // 1. test: Anasayfa */
    public void test1() {
        // 1. test: Anasayfa
        if(!this.isConnected()) {
            System.err.println("Once server'a baglanin lutfen");
            return;
        }
        WebElement element = null;
        String currentPageText = new String();
        try {
            // tek erisim elemani xpath oldugundan onu kullandik, tavsiye edilmez
            element = driver.findElement(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.view.ViewGroup/android.widget.TextView"));
            currentPageText = element.getText();
            if (currentPageText.equalsIgnoreCase("anasayfa"))
                System.out.println("Anasayfa Testi Basarili");
            else
                System.out.println("Anasayfa Testi Basarisiz");
        } catch (Exception e) {
            System.err.println("Anasayfa Yazisi Bulunamadi");
        }
    }

    /** // 2.1. Test: Anasayfa slayt // 2.2 Bilgi Yazisi */
    public void test2() {
        
        if(!this.isConnected()) {
            System.err.println("Once server'a baglanin lutfen");
            return;
        }
        Actions actions = new Actions(driver);
        WebElement element = null;
        try {
            element = driver.findElement(AppiumBy.accessibilityId("Slayt"));
            String elementClass = element.getAttribute("class");
            if (elementClass.equalsIgnoreCase("android.widget.imageview")) {
                System.out.println("Slayt Bulundu, Butonlar Deneniyor...");
                try {
                    WebElement btn = driver.findElement(AppiumBy.id("com.example.myapp:id/btnSlideLeft"));
                    if (btn.getAttribute("clickable").equalsIgnoreCase("True")) {
                        actions.moveToElement(btn).click().perform();
                        System.out.println("Sol Buton Calisiyor");
                    } else {
                        System.out.println("Sol Buton Calismiyor");
                    }
                    btn = driver.findElement(AppiumBy.id("com.example.myapp:id/btnSlideRight"));
                    if (btn.getAttribute("clickable").equalsIgnoreCase("True")) {
                        actions.moveToElement(btn).click().perform();
                        System.out.println("Sag Buton Calisiyor");
                    } else {
                        System.out.println("Sag Buton Calismiyor");
                    }

                } catch (Exception e) {
                    System.out.println("Butonlar bulunurken hata");
                }

            }
        } catch (Exception e) {
            System.err.println("Anasayfa Slayti bulunamadi");
        }  
        
        try {
            element = driver.findElement(AppiumBy.id("com.example.myapp:id/tvMainAbout"));
            String text = "Bölümümüzün misyonu Bilgisayar Mühendisliği alanında bilgi "
                        + "ve teknoloji üretmek, üretilen değerleri medeniyetlerin "
                        + "ilerlemesi için kullanmak, ve yenilikçi ve araştırma eksenli "
                        + "bir eğitim vererek kendine güvenen, girişimci, eleştirel analiz "
                        + "yapabilen, teknik ve etik bilgilerle donatılmış, bilgisayar yazılım, "
                        + "donanım ve bilgi sistemleri tasarlayabilen, geliştirebilen ve "
                        + "sürdürebilen mühendisler yetiştirmektir.";
            
            if(element.getAttribute("text").equals(text)) {
                System.out.println("Bilgi yazisi dogru bir sekilde bulundu.");
                System.out.println("Yazi sinirlari: " + element.getAttribute("bounds"));
            }
        } catch (Exception e) {
            System.err.println("Bilgi Yazisi Bulunamadi");
        }
    }
    
    /** //open dropdown */
    public void test3a() {
        if(!this.isConnected()) {
            System.err.println("Once server'a baglanin lutfen");
            return;
        }
        WebElement element = null;
        try {
            System.out.println("Dropdown acilisi test ediliyor...");
            element = driver.findElement(AppiumBy.accessibilityId("Open navigation"));
            element.click();
            System.out.println("Dropdown aciliyor.");
        } catch (Exception e) {
            System.err.println("Dropdown Acilmiyor.");
        }
    }

    /** /close dropdown */
    public void test3b() {
        if(!this.isConnected()) {
            System.err.println("Once server'a baglanin lutfen");
            return;
        }
        WebElement element = null;
        try {
            System.out.println("Dropdown kapanisi test ediliyor...");
            element = driver.findElement(AppiumBy.accessibilityId("Close navigation"));
            element.click();
            System.out.println("Dropdown kapaniyor.");
        } catch (Exception e) {
            System.err.println("Dropdown Kapanmiyor.");
        }
    }

    /**
    //urun secenekleri
    //dropdown acmadan kullanmayin
     */
    public void test4() {
        if(!this.isConnected()) {
            System.err.println("Once server'a baglanin lutfen");
            return;
        }
        WebElement urun1 = null;
        WebElement urun2 = null;
        try { 
            System.out.println("Urunler kontrol ediliyor...");
            //yetiskin urun
            urun1 = driver.findElement(AppiumBy.id("com.example.myapp:id/miProductAdult"));
            
            if(urun1.getAttribute("clickable").equalsIgnoreCase("true"))
                System.out.println("Yetiskin urunleri bulundu");
            
            urun2 = driver.findElement(AppiumBy.id("com.example.myapp:id/miProductChild"));

            if(urun2.getAttribute("clickable").equalsIgnoreCase("true"))
                System.out.println("Cocuk urunleri bulundu");
            
            System.out.println("Urun secenekleri basarili.");
        } catch (Exception e) {
            System.err.println("Urunler Sekmesi Elemanlari Yetersiz ya da Bulunamadi");
        }

    }

    /**
    yetiskin urun sayfa sol-sag butonlari
    dropdown acmadan kullanmayin
     */
    public void test5a() {
        WebElement adltBtnLeft = null;
        WebElement adltBtnRight = null;
        WebElement urun1 = null;
        try {
            urun1 = driver.findElement(AppiumBy.id("com.example.myapp:id/miProductAdult"));
            urun1.click();
            System.out.println("Yetiskin butonlari deneniyor...");
            try {
                adltBtnRight = driver.findElement(AppiumBy.id("com.example.myapp:id/btnRight"));
                for (int i = 0; i < 6; i++) {
                    adltBtnRight.click();
                }
    
                if(driver.findElement(AppiumBy.xpath("/hierarchy/android.widget.Toast")).getText().equalsIgnoreCase("zaten son sayfadasınız."))
                    System.out.println("Ileri buton calisiyor");
    
                
            } catch (Exception e) {
                System.err.println("Ileri butonu bulunurken hata");
            }

            System.out.println("Geri butonu bekleniyor");
            try {                
                Thread.sleep(5000);
                adltBtnLeft = driver.findElement(AppiumBy.id("com.example.myapp:id/btnLeft"));
    
                for (int i = 0; i < 6; i++) {
                    adltBtnLeft.click();
                }
                
                if(driver.findElement(AppiumBy.xpath("/hierarchy/android.widget.Toast")).getText().equalsIgnoreCase("zaten ilk sayfadasınız."))
                    System.out.println("Geri buton calisiyor");
                 
            } catch (Exception e) {
                System.err.println("Geri buton bulunurken hata");
            }
        } catch (Exception e) {
            System.err.println("Yetiskin ekranina gidilemedi");
        }
    }

    /**
    cocuk urun sayfa sol-sag butonlari
    dropdown acmadan kullanmayin
     */
    public void test5b() {
        WebElement urun2 = null;
        WebElement chldBtnLeft = null;
        WebElement chldBtnRight = null;
        try {
            urun2 = driver.findElement(AppiumBy.id("com.example.myapp:id/miProductChild"));
            urun2.click();
            System.out.println("Cocuk butonlari deneniyor...");
            try {
                chldBtnLeft = driver.findElement(AppiumBy.id("com.example.myapp:id/btnLeftChild"));
                chldBtnRight = driver.findElement(AppiumBy.id("com.example.myapp:id/btnRightChild"));
                
                for (int i = 0; i < 6; i++) {
                    chldBtnRight.click();
                }
                if(driver.findElement(AppiumBy.xpath("/hierarchy/android.widget.Toast")).getText().equalsIgnoreCase("zaten son sayfadasınız."))
                    System.out.println("Ileri buton calisiyor");

            } catch (Exception e) {
                System.err.println("Ileri butonu bulunurken hata");
            }

            System.out.println("Geri butonu bekleniyor");
            try {
                Thread.sleep(5000);
                for (int i = 0; i < 6; i++) {
                    chldBtnLeft.click();
                }
                
                if(driver.findElement(AppiumBy.xpath("/hierarchy/android.widget.Toast")).getText().equalsIgnoreCase("zaten ilk sayfadasınız."))
                    System.out.println("Geri buton calisiyor");

            } catch (Exception e) {
                System.err.println("Geri butonu bulunurken hata");
            }
        } catch (Exception e) {
            System.err.println("Cocuk ekranina gidilemedi");
        }
    }

    public static void main(String[] args) {
        AppiumAppTest a1 = new AppiumAppTest();
        try {
            a1.connectServer();
            a1.test1();
            a1.test2();
            a1.test3a();
            a1.test4();
            a1.test5a();
            //dropdown acmak icin
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                System.out.println("Beklenmesi gereken sure atlandi");
            }
            a1.test3a();
            a1.test5b();
        } catch (MalformedURLException e1) {
            System.err.println("Baglanti Basarisiz");
        }
       
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

    }
}
