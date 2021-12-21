
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class AppiumAppTest {

    public static final String defaultUrl = "http://127.0.0.1:4723/wd/hub";
    public AndroidDriver driver;
    public DesiredCapabilities dc;

    public static class AppiumAppBuilder {
        private String deviceName;
        private String udid;
        private String platformName;
        private String platformVersion;
        private String appPackage;
        private String appActivity;

        public static final String defaultDevice = "Pixel_3a_API_30";
        public static final String default_udid = "emulator-5554";
        public static final String defaultPlatform = "Android";
        public static final String defaultVersion = "11.0";
        public static final String defaultPackage = "com.edasinar.profitability_proje_2";
        public static final String defaultActivity = "com.edasinar.profitability_proje_2.MainMenuActivity";

        /** build pattern - device name */
        public AppiumAppBuilder(String deviceName) {
            this.deviceName = deviceName;
        }

        public AppiumAppBuilder udid(String udid) {
            this.udid = udid;
            return this;
        }

        public AppiumAppBuilder platformName(String platformName) {
            this.platformName = platformName;
            return this;
        }

        public AppiumAppBuilder platformVersion(String platformVersion) {
            this.platformVersion = platformVersion;
            return this;
        }

        public AppiumAppBuilder appPackage(String appPackage) {
            this.appPackage = appPackage;
            return this;
        }

        public AppiumAppBuilder appActivity(String appActivity) {
            this.appActivity = appActivity;
            return this;
        }

        public AppiumAppTest build() {
            AppiumAppTest app = new AppiumAppTest();

            app.dc.setCapability("deviceName", this.deviceName);
            app.dc.setCapability("udid", this.udid);
            app.dc.setCapability("platformName", this.platformName);
            app.dc.setCapability("autoGrantPermissions", true);
            app.dc.setCapability("platformVersion", this.platformVersion);
            app.dc.setCapability("appPackage", this.appPackage);
            app.dc.setCapability("appActivity", this.appActivity);
            return app;
        }

    }

    private AppiumAppTest() {
        dc = new DesiredCapabilities();
    }

    public boolean isConnected() {
        if (driver == null)
            return false;
        return true;
    }

    public void connectServer(String url) throws MalformedURLException {
        driver = new AndroidDriver(new URL(url), dc);
        System.out.println("Connection established to server");

    }

    /** // 1. test: Anasayfa */
    public void test1_2() {
        // 1. test: Anasayfa
        if (!this.isConnected()) {
            System.err.println("Once server'a baglanin lutfen");
            return;
        }
        WebElement element = null;
        String elementText = new String();
        try {
            System.out.println("Test1 ve Test2");
            // anasayfa basligini kontrol et
            element = driver.findElement(AppiumBy.xpath(
                    "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.view.ViewGroup/android.widget.TextView"));
            elementText = element.getText();
            System.out.println("element: " + elementText);
            System.out.println("result : " + elementText.equalsIgnoreCase("            KARLILIK HESAPLAMA"));

            // urunler ekranina git
            element = driver.findElement(AppiumBy.xpath(
                    "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.widget.LinearLayout"));
            element.click();
            // barkod basligini bul
            Thread.sleep(500);
            WebElement productAttributes[] = new WebElement[5];
            for (int index = 0; index < 5; ++index) {
                try {
                    productAttributes[index] = driver.findElement(AppiumBy.xpath(
                            "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.TextView["
                                    + (index + 1) + "]"));
                    System.out.println("element found: " + productAttributes[index].getText());
                } catch (Exception e) {
                    System.err.println("error on index: " + index);
                }
            }

            // test2a
            WebElement elementFilter1 = driver
                    .findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/barkodEditText"));
            String barcodeList[] = { "3lugoldset", "4LuGoldset4", "4LuGoldset3",
                    "5LiGoldset7", "asimetrikcentik",
                    "hasıryzk1", "YvrlkYzk1", "DgmYzk1", "incezincir1", "MrsVnsYzk",
                    "YunusBlkYzk04", "İnceHltYzk03",
                    "GozlukYzk02", "MinimalKalp" };
            WebElement filterButton = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/button"));

            for (int index = 0; index < barcodeList.length; ++index) {
                elementFilter1.sendKeys(barcodeList[index]);
                filterButton.click();
                try {
                    WebElement barcodeFound = driver.findElement(AppiumBy.xpath(
                            ("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout/android.widget.TextView[1]")));
                    System.out.println(barcodeFound.getText());
                    System.out.println("Barkod " + (index + 1) + " urunu : "
                            + ((barcodeList[index].equals(barcodeFound.getText())) ? "uyusuyor" : "uyusmuyor"));
                } catch (Exception e) {
                    System.err.println("barkod " + (index + 1) + " bulunamadi");
                }

            }

            // test2b
            WebElement elementFilter2 = driver
                    .findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/isimEditText"));
            elementFilter2.sendKeys("has");
            filterButton.click();
            try {
                WebElement result = driver
                        .findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/barkodTextViewR"));

                System.out.println("Kısmi isimle ürün arama: "
                        + ((result.getText().equals("hasıryzk1")) ? "Başarılı" : "Başarısız"));
            } catch (Exception e) {
                System.err.println("Ürün bulunamadı");
            }

        } catch (Exception e) {
            System.err.println("Test1 Başarısız Aranılan bazı ögeler bulunamadı.");
        }
    }

    // test 3 için farklı bir yol izleyeceğiz, yol şu:
    // tam bir barkod gireceğiz tek bir ürün gelmesi lazım xpath değişkeninde
    // bir alt satıra gelecek degeri gireceğiz eğer ki driver o elemanı bulamazsa
    // test başarılıdır.
    /** // test3 */
    public void test3() {
        if (!this.isConnected()) {
            System.err.println("Once server'a baglanin lutfen");
            return;
        }
        System.out.println("\nTest3");
        WebElement barcodeInput = null;
        WebElement filterButton = null;
        try {
            barcodeInput = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/barkodEditText"));
            filterButton = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/button"));
            barcodeInput.sendKeys("4LuGoldset4");
            filterButton.click();
            String xpathPrefixBarcode = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[";
            String xpathPostfixBarcode = "]/android.widget.TextView[1]";
            // burada xpath kullanarak ilk elemanı bulacağız ikinciyi bulmamamız gerekiyor
            WebElement elementExist = driver.findElement(AppiumBy.xpath(xpathPrefixBarcode + 1 + xpathPostfixBarcode));
            System.out.println("İlk eleman bulundu. Eleman barkod: " + elementExist.getText());
            WebElement elementNonExist = null;
            // bu eleman bulunamayacağı için catch bloğuna girmeli
            // normale göre ters bir şekilde işliyor
            try {
                elementNonExist = driver.findElement(AppiumBy.xpath(xpathPrefixBarcode + 2 + xpathPostfixBarcode));
                System.err.println("Test3 Hatalı. Eleman: " + elementNonExist.getText());
            } catch (Exception e) {
                System.out.println("Verilen barkod ile başka eleman bulunmadı");
                System.out.println("Test3 Başarılı");
            }
        } catch (Exception e) {
            System.err.println("Aranılan eleman/elemanlar bulunamadı. Test3 Başarısız.");
        }

    }

    /** Test4 */
    public void test4() {
        if (!this.isConnected()) {
            System.err.println("Once server'a baglanin lutfen");
            return;
        }
        WebElement filterButton = null;
        List<WebElement> elements = null;

        try {
            System.out.println("\nTest4");
            filterButton = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/button"));
            filterButton.click();
            elements = driver
                    .findElements(AppiumBy.id("com.edasinar.profitability_proje_2:id/barkodTextViewR"));
            System.out.println("Eleman bulundu. Ekran kaydırmadan bulunan eleman sayısı: " + elements.size());
            System.out.println("Test4 başarılı.");
        } catch (Exception e) {
            System.err.println("Eleman bulunamadı. Test4 Başarısız.");
        }
    }

    /** test5 - devamı var */
    public void test5() {
        if (!this.isConnected()) {
            System.err.println("Once server'a baglanin lutfen");
            return;
        }
        WebElement element = null;
        WebElement removedElement = null;
        try {
            System.out.println("\nTest5");
            removedElement = driver.findElement(AppiumBy.xpath(
                    "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[5]/android.widget.TextView[1]"));
            String text = removedElement.getAttribute("text");
            System.out.println("Listenin 5. elemanının barkodu: " + text);
            System.out.println("Listenin 5. sıradaki elemanı için sil butonu aranıyor..");
            element = driver.findElement(AppiumBy.xpath(
                    "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[5]/android.widget.Button"));
            System.out.println("Eleman bulundu buton adı: " + element.getText());
            element.click();
            System.out.println("Buton tıklandı. Eleman silindi mi kontrol ediliyor...");
            System.out.println("Ana menüye dönülüyor...");
            driver.navigate().back();
            System.out.println("Tekrar ürünler ekranına gidiliyor...");
            element = driver.findElement(AppiumBy.xpath(
                    "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.widget.LinearLayout"));
            element.click();
            System.out.println("Silinen ürün aranıyor...");
            Thread.sleep(1000);
            removedElement = driver.findElement(AppiumBy.xpath(
                    "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[5]/android.widget.TextView[1]"));
            
            String text_2 = removedElement.getAttribute("text");
            System.out.println("Listenin yeni 5. elemanının barkodu: " + text_2);
            System.out.println(!(text.equalsIgnoreCase(text_2))?"Test5 Başarılı":"Test5 Başarısız");
        } catch (Exception e) {
            System.err.println("Eleman bulunamadı. Test5 Başarısız");
        }
    }

    
    public void test6() {
        if (!this.isConnected()) {
            System.err.println("Once server'a baglanin lutfen");
            return;
        }
        WebElement element = null;

        try {
            System.out.println("\nTest6");
            element = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/kaydet"));
            element.click();
            String prefix = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.TextView[";
            int counter = 0;
            for (int i = 1; i <= 6; ++i) {
                try {
                    element = driver.findElement(AppiumBy.xpath(prefix + i + "]"));
                    ++counter;
                    System.out.println("element found: " + element.getText());
                } catch (Exception e) {
                    System.err.println("element not found: " + i);
                }
            }
            element = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/galeridenSecButton"));
            System.out.println(element.getText() + " butonu bulundu");
            System.out.println((counter == 0) ? "Test6 Başarısız" : "Test6 Başarılı");
        } catch (Exception e) {
            System.err.println("Test6 Başarısız");
        }
    }

    public void test7() {
        try {
            System.out.println("\nTest7");
            String activity = driver.currentActivity();
            System.out.println("Mevcut ekran aktivitesi: " + activity);
            WebElement element = driver
                    .findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/galeridenSecButton"));
            element.click();
            System.out.println("Galeriden seç butonu tıklandı, galeriye giriliyor...");
            activity = driver.currentActivity();
            System.out.println("Resim seçiliyor...");
            element = driver.findElement(AppiumBy.id("com.google.android.apps.photos:id/image"));
            element.click();
            Boolean done = false;
            while (!done) {
                try {
                    element = driver.findElement(AppiumBy.accessibilityId("Photo taken on Dec 18, 2021 1:52:27 AM"));
                    element.click();
                    done = true;
                    System.out.println("Resim Bulundu");
                } catch (Exception e) {
                    System.err.println("Resim bulunamadı tekrar deneniyor...");
                }
            }

            System.out.println(((activity.equals(".picker.external.ExternalPickerActivity")) ? "Test7 Başarılı"
                    : "Test7 Başarısız") + ". Mevcut Ekran Aktivitesi: " + activity);
        } catch (Exception e) {
            System.err.println("Test7 Başarısız");
        }
    }

    public void test8() {
        try {
            System.out.println("\nTest8");
            WebElement element = null;
            Boolean done = false;
            while (!done) {
                try {
                    element = driver
                            .findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/newBarkodEditText"));
                    System.out.println("Barkod girdisi bulundu.");
                    done = true;
                } catch (Exception e) {
                    System.err.println("Barkod girdisi bulunamadı tekrar deneniyor");
                }
            }
            element.sendKeys("bosluklu id");
            System.out.println("barkod girildi...");
            element = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/newKayit"));
            element.click();
            System.out.println("buton tıklandı, mesaj aranıyor...");
            String warningMessage = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
            System.out.println("Uyarı mesajı,\n" + warningMessage);
            System.out.println("Test8 Başarılı");

        } catch (Exception e) {
            System.err.println("Test8 Başarısız");
        }
    }

    public void test9() {
        System.out.println("\nTest9");
        String prefix = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.EditText[";
        final int inputCount = 6;
        String inputs[] = { "ürünadi", "ürün216", "22", "Beyaz", "ürünmarka", "32" };
        WebElement element = null;
        System.out.println("Ekran aktivitesi: " + driver.currentActivity());
        for (int inputIndex = 1; inputIndex <= inputCount;) {
            try {
                element = driver.findElement(AppiumBy.xpath(prefix + inputIndex + "]"));
                element.sendKeys(inputs[inputIndex - 1]);
                ++inputIndex;
            } catch (Exception e) {
                System.err.println("Error on element: " + inputIndex + ". Retrying...");
            }
        }
        System.out.println("Girdi alanları dolduruldu.");
        try {
            element = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/newKayit"));
            element.click();
            System.out.println("Ürün Kaydedildi.");
            System.out.println("Mevcut ekran kontrol ediliyor...");
            System.out.println("Ekran aktivitesi: " + driver.currentActivity());
            System.out.println("Test9 Başarılı.");
        } catch (Exception e) {
            System.err.println("Test 9 Başarısız.");
        }
    }

    public void test10() {
        // ana menüye dön

        WebElement stockButton = null;
        WebElement inputs[] = new WebElement[6];
        System.out.println("\nTest10");
        // back to main menu
        System.out.println("Ana menüye dönülüyor...");
        for (int back = 0; back < 3; ++back) {
            driver.navigate().back();
        }

        try {
            Thread.sleep(400);
            System.out.println("Stok giriş ekranına geçiliyor...");
            stockButton = driver.findElement(AppiumBy.xpath(
                    "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[3]/android.widget.LinearLayout"));
            stockButton.click();
            System.out.println("Girişler yapılıyor...");
            String prefix = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.EditText[";
            String inputList[] = { "2022", "01", "01", "3lugoldset", "19.99", "10" };
            for (int index = 1; index <= 6;) {
                try {
                    inputs[index - 1] = driver.findElement(AppiumBy.xpath(prefix + index + "]"));
                    inputs[index - 1].sendKeys(inputList[index - 1]);
                    System.out.println("input : " + inputList[index - 1]);
                    ++index;
                } catch (Exception e) {
                    System.err.println("Error on index: " + index + ". Retrying...");
                }
            }
            WebElement saveButton = driver
                    .findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/stokKaydetButton"));
            saveButton.click();
            System.out.println("Kaydedildi.");
            Thread.sleep(400);
            for (int i = 0; i < 3; ++i) {
                try {
                    stockButton = driver.findElement(AppiumBy.xpath(
                            "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[3]/android.widget.LinearLayout"));
                    stockButton.click();
                    break;
                } catch (Exception e) {
                    System.err.println("stok ekranına giriş tekrar deneniyor...");
                }
            }
            Thread.sleep(1000);
            System.out.println("Stok ekranı kontrol ediliyor...");
            WebElement testElement = driver.findElement(AppiumBy.xpath(
                    "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.EditText[1]"));
            System.out.println("Girdi kutucuğu değeri: " + testElement.getAttribute("text"));
            System.out.println((testElement.getAttribute("text").equalsIgnoreCase("Örnek: 2020"))
                    ? "Yertutucu değerine eşit yani Test10 Başarılı"
                    : "Yertutucuyla eşit değil, Test10 Başarısız");
            // ana menüye dön
            driver.navigate().back();
        } catch (InterruptedException e) {
            System.err.println("Beklemede hata");
        } catch (Exception e) {
            System.err.println("Test10 Başarısız.");
        }
    }

    public void test11_12() {
        System.out.println("\nTest11");
        String prefix = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.EditText[";
        String inputList[] = { "3lugoldset", "20220201", "73456822", "ayse yilmaz", "1", "19.99" };
        try {
            WebElement ordersMenu = driver.findElement(AppiumBy.xpath(
                    "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout"));
            ordersMenu.click();
            WebElement element = null;
            for (int index = 1; index <= inputList.length;) {
                try {
                    element = driver.findElement(AppiumBy.xpath(prefix + index + "]"));
                    System.out.println("Girdi veriliyor: " + inputList[index - 1]);
                    element.sendKeys(inputList[index - 1]);
                    ++index;
                } catch (Exception e) {
                    System.err.println("Error on index: " + index + ". Retrying...");
                }
            }
            WebElement saveButton = driver
                    .findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/siparisKaydetButton"));
            saveButton.click();
            System.out.println("Test11 Başarılı");
        } catch (Exception e) {
            System.err.println("Test11 Başarısız.");
        }

        try {
            System.out.println("\nTest12");
            System.out.println("Kaydet butonuna basıldıktan sonra alanlar temizlendi mi kontrol ediliyor...");
            WebElement element = driver.findElement(AppiumBy.xpath(prefix + 1 + "]"));
            System.out.println("Girdi kutucuğu değeri: " + element.getAttribute("text"));
            System.out.println("Test12 " + ((element.getAttribute("text").equalsIgnoreCase("Örnek: 2020")) ? "başarılı."
                    : "yarı başarılı. Çünkü alan temizlenmemiş."));

        } catch (Exception e) {
            System.err.println("Test12 başarısız. Eleman bulunamadı.");
        }

    }

    public void test13_14() {
        // ana menüye dön
        driver.navigate().back();
        String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[4]/android.widget.LinearLayout";
        String prefix = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.EditText[";
        WebElement element = null;
        WebElement saveButton = null;
        String inputList[] = { "2022", "10", "02", "3432", "İstege Bagli" };
        try {
            System.out.println("\nTest13");
            element = driver.findElement(AppiumBy.xpath(xpath));
            element.click();
            Thread.sleep(1000);
            saveButton = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/kaydetButton"));
            Thread.sleep(1000);
            for (int index = 1; index <= inputList.length;) {
                try {
                    element = driver.findElement(AppiumBy.xpath(prefix + index + "]"));
                    element.sendKeys(inputList[index - 1]);
                    saveButton.click();
                    System.out.println("Verilen girdi: " + inputList[index - 1]);
                    String warningText = driver.switchTo().alert().getText();
                    System.out.println("Boş alan için verilen hata: " + warningText);
                    driver.switchTo().alert().accept();
                    ++index;
                } catch (Exception e) {
                    System.err.println("Eleman bulunurken hata. Tekrar deneniyor...");
                }
            }
        } catch (Exception e) {
            System.err.println("Test13 Başarısız eleman bulunamadı");
        }

        try {
            System.out.println("\nTest14");
            for (int index = 1; index <= inputList.length;) {
                try {
                    element = driver.findElement(AppiumBy.xpath(prefix + index + "]"));
                    element.sendKeys(inputList[index - 1]);
                    System.out.println("Verilen girdi: " + inputList[index - 1]);
                    ++index;
                } catch (Exception e) {
                    System.err.println("Eleman bulunurken hata. Tekrar deneniyor...");
                }
            }
            saveButton.click();
            element = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/yilEditText"));
            Boolean result = element.getAttribute("text").equalsIgnoreCase("ÖRNEK: 2021");
            System.out.println("Test14 " + ((result) ? "Başarılı" : "Başarısız, kayıt tamamlanmadı"));
            driver.navigate().back();
        } catch (Exception e) {
            System.err.println("Test14 Başarısız, aranılan eleman bulunamadı.");
        }
    }

    /** Geri butonlarını tespit etme */
    public void test15() {
        WebElement element = null;
        final int count = 3;
        int counter = 0;
        System.out.println("\nTest15");
        String prefix = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[";
        String postfix = "]/android.widget.LinearLayout";
        System.out.println("Ürünler ekranı geri butonu kontrol ediliyor...");
        try {
            element = driver.findElement(AppiumBy.xpath(prefix + 1 + postfix));
            element.click();
            Thread.sleep(1000);
            for (int attempt = 0; attempt < count; ++attempt) {
                try {
                    element = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
                    System.out.println("Eleman bulundu. Açıklama: " + element.getAttribute("content-desc"));
                    ++counter;
                    break;
                } catch (Exception e) {
                    System.err.println("Eleman bulunamadı tekrar deneniyor");
                }
            }

            element = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/kaydet"));
            element.click();
            System.out.println("Ürün ekleme ekranı geri butonu kontrol ediliyor...");
            Thread.sleep(1000);
            for (int attempt = 0; attempt < count; ++attempt) {
                try {
                    element = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
                    System.out.println("Eleman bulundu. Açıklama: " + element.getAttribute("content-desc"));
                    ++counter;
                    break;
                } catch (Exception e) {
                    System.err.println("Eleman bulunamadı tekrar deneniyor");
                }
            }

            System.out.println("Ana sayfaya dönülüyor...");
            driver.navigate().back();
            driver.navigate().back();

            System.out.println("Sipariş ekranına giriliyor...");
            element = driver.findElement(AppiumBy.xpath(prefix + 2 + postfix));
            element.click();
            System.out.println("Sipariş ekleme ekranı geri butonu kontrol ediliyor...");
            Thread.sleep(1000);
            for (int attempt = 0; attempt < count; ++attempt) {
                try {
                    element = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
                    System.out.println("Eleman bulundu. Açıklama: " + element.getAttribute("content-desc"));
                    ++counter;
                    break;
                } catch (Exception e) {
                    System.err.println("Eleman bulunamadı tekrar deneniyor");
                }
            }
            System.out.println("Ana sayfaya dönülüyor...");
            driver.navigate().back();

            System.out.println("Stok giriş ekranına giriliyor...");
            element = driver.findElement(AppiumBy.xpath(prefix + 3 + postfix));
            element.click();
            System.out.println("Stok giriş ekranı geri butonu kontrol ediliyor...");
            Thread.sleep(1000);
            for (int attempt = 0; attempt < count; ++attempt) {
                try {
                    element = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
                    System.out.println("Eleman bulundu. Açıklama: " + element.getAttribute("content-desc"));
                    ++counter;
                    break;
                } catch (Exception e) {
                    System.err.println("Eleman bulunamadı tekrar deneniyor");
                }
            }
            System.out.println("Ana sayfaya dönülüyor...");
            driver.navigate().back();

            System.out.println("Harcamalar giriş ekranına giriliyor...");
            element = driver.findElement(AppiumBy.xpath(prefix + 4 + postfix));
            element.click();
            System.out.println("Harcamalar giriş ekranı geri butonu kontrol ediliyor...");
            Thread.sleep(1000);
            for (int attempt = 0; attempt < count; ++attempt) {
                try {
                    element = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
                    System.out.println("Eleman bulundu. Açıklama: " + element.getAttribute("content-desc"));
                    ++counter;
                    break;
                } catch (Exception e) {
                    System.err.println("Eleman bulunamadı tekrar deneniyor");
                }
            }
            System.out.println("Ana sayfaya dönülüyor...");
            driver.navigate().back();

            System.out.println("Net kar ekranına giriliyor...");
            element = driver.findElement(AppiumBy.xpath(prefix + 5 + postfix));
            element.click();
            System.out.println("Net kar ekranı geri butonu kontrol ediliyor...");
            Thread.sleep(1000);
            for (int attempt = 0; attempt < count; ++attempt) {
                try {
                    element = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
                    System.out.println("Eleman bulundu. Açıklama: " + element.getAttribute("content-desc"));
                    ++counter;
                    break;
                } catch (Exception e) {
                    System.err.println("Eleman bulunamadı tekrar deneniyor");
                }
            }
            System.out.println("Ana sayfaya dönülüyor...");
            driver.navigate().back();
            System.out.println("Başarılı geri butonu testi sayısı : " + counter + "/6");
        } catch (Exception e) {
            System.err.println("Test15 başarısız");
        }
    }

    public static void main(String[] args) {
        AppiumAppTest a1 = new AppiumAppBuilder(AppiumAppBuilder.defaultDevice)
                .udid(AppiumAppBuilder.default_udid)
                .platformName(AppiumAppBuilder.defaultPlatform)
                .platformVersion(AppiumAppBuilder.defaultVersion)
                .appPackage(AppiumAppBuilder.defaultPackage)
                .appActivity(AppiumAppBuilder.defaultActivity)
                .build();
        try {
            a1.connectServer(AppiumAppTest.defaultUrl);
            a1.test1_2();
            Thread.sleep(400);
            a1.test3();
            Thread.sleep(400);
            a1.test4();
            Thread.sleep(400);
            a1.test5();
            Thread.sleep(400);
            a1.test6();
            Thread.sleep(400);
            a1.test7();
            Thread.sleep(400);
            a1.test8();
            Thread.sleep(400);
            a1.test9();
            Thread.sleep(400);
            a1.test10();
            Thread.sleep(400);
            a1.test11_12();
            Thread.sleep(400);
            a1.test13_14();
            Thread.sleep(400);
            a1.test15();
            Thread.sleep(400);

        } catch (MalformedURLException e1) {
            System.err.println("Baglanti Basarisiz");
        } catch (InterruptedException e) {
            System.err.println("Sleep Çalışmadı");
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
