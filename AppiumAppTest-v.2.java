
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
                    "has??ryzk1", "YvrlkYzk1", "DgmYzk1", "incezincir1", "MrsVnsYzk",
                    "YunusBlkYzk04", "??nceHltYzk03",
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

                System.out.println("K??smi isimle ??r??n arama: "
                        + ((result.getText().equals("has??ryzk1")) ? "Ba??ar??l??" : "Ba??ar??s??z"));
            } catch (Exception e) {
                System.err.println("??r??n bulunamad??");
            }

        } catch (Exception e) {
            System.err.println("Test1 Ba??ar??s??z Aran??lan baz?? ??geler bulunamad??.");
        }
    }

    // test 3 i??in farkl?? bir yol izleyece??iz, yol ??u:
    // tam bir barkod girece??iz tek bir ??r??n gelmesi laz??m xpath de??i??keninde
    // bir alt sat??ra gelecek degeri girece??iz e??er ki driver o eleman?? bulamazsa
    // test ba??ar??l??d??r.
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
            // burada xpath kullanarak ilk eleman?? bulaca????z ikinciyi bulmamam??z gerekiyor
            WebElement elementExist = driver.findElement(AppiumBy.xpath(xpathPrefixBarcode + 1 + xpathPostfixBarcode));
            System.out.println("??lk eleman bulundu. Eleman barkod: " + elementExist.getText());
            WebElement elementNonExist = null;
            // bu eleman bulunamayaca???? i??in catch blo??una girmeli
            // normale g??re ters bir ??ekilde i??liyor
            try {
                elementNonExist = driver.findElement(AppiumBy.xpath(xpathPrefixBarcode + 2 + xpathPostfixBarcode));
                System.err.println("Test3 Hatal??. Eleman: " + elementNonExist.getText());
            } catch (Exception e) {
                System.out.println("Verilen barkod ile ba??ka eleman bulunmad??");
                System.out.println("Test3 Ba??ar??l??");
            }
        } catch (Exception e) {
            System.err.println("Aran??lan eleman/elemanlar bulunamad??. Test3 Ba??ar??s??z.");
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
            System.out.println("Eleman bulundu. Ekran kayd??rmadan bulunan eleman say??s??: " + elements.size());
            System.out.println("Test4 ba??ar??l??.");
        } catch (Exception e) {
            System.err.println("Eleman bulunamad??. Test4 Ba??ar??s??z.");
        }
    }

    /** test5 - devam?? var */
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
            System.out.println("Listenin 5. eleman??n??n barkodu: " + text);
            System.out.println("Listenin 5. s??radaki eleman?? i??in sil butonu aran??yor..");
            element = driver.findElement(AppiumBy.xpath(
                    "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[5]/android.widget.Button"));
            System.out.println("Eleman bulundu buton ad??: " + element.getText());
            element.click();
            System.out.println("Buton t??kland??. Eleman silindi mi kontrol ediliyor...");
            System.out.println("Ana men??ye d??n??l??yor...");
            driver.navigate().back();
            System.out.println("Tekrar ??r??nler ekran??na gidiliyor...");
            element = driver.findElement(AppiumBy.xpath(
                    "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.widget.LinearLayout"));
            element.click();
            System.out.println("Silinen ??r??n aran??yor...");
            Thread.sleep(1000);
            removedElement = driver.findElement(AppiumBy.xpath(
                    "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[5]/android.widget.TextView[1]"));
            
            String text_2 = removedElement.getAttribute("text");
            System.out.println("Listenin yeni 5. eleman??n??n barkodu: " + text_2);
            System.out.println(!(text.equalsIgnoreCase(text_2))?"Test5 Ba??ar??l??":"Test5 Ba??ar??s??z");
        } catch (Exception e) {
            System.err.println("Eleman bulunamad??. Test5 Ba??ar??s??z");
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
            System.out.println((counter == 0) ? "Test6 Ba??ar??s??z" : "Test6 Ba??ar??l??");
        } catch (Exception e) {
            System.err.println("Test6 Ba??ar??s??z");
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
            System.out.println("Galeriden se?? butonu t??kland??, galeriye giriliyor...");
            activity = driver.currentActivity();
            System.out.println("Resim se??iliyor...");
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
                    System.err.println("Resim bulunamad?? tekrar deneniyor...");
                }
            }

            System.out.println(((activity.equals(".picker.external.ExternalPickerActivity")) ? "Test7 Ba??ar??l??"
                    : "Test7 Ba??ar??s??z") + ". Mevcut Ekran Aktivitesi: " + activity);
        } catch (Exception e) {
            System.err.println("Test7 Ba??ar??s??z");
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
                    System.err.println("Barkod girdisi bulunamad?? tekrar deneniyor");
                }
            }
            element.sendKeys("bosluklu id");
            System.out.println("barkod girildi...");
            element = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/newKayit"));
            element.click();
            System.out.println("buton t??kland??, mesaj aran??yor...");
            String warningMessage = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
            System.out.println("Uyar?? mesaj??,\n" + warningMessage);
            System.out.println("Test8 Ba??ar??l??");

        } catch (Exception e) {
            System.err.println("Test8 Ba??ar??s??z");
        }
    }

    public void test9() {
        System.out.println("\nTest9");
        String prefix = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.EditText[";
        final int inputCount = 6;
        String inputs[] = { "??r??nadi", "??r??n216", "22", "Beyaz", "??r??nmarka", "32" };
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
        System.out.println("Girdi alanlar?? dolduruldu.");
        try {
            element = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/newKayit"));
            element.click();
            System.out.println("??r??n Kaydedildi.");
            System.out.println("Mevcut ekran kontrol ediliyor...");
            System.out.println("Ekran aktivitesi: " + driver.currentActivity());
            System.out.println("Test9 Ba??ar??l??.");
        } catch (Exception e) {
            System.err.println("Test 9 Ba??ar??s??z.");
        }
    }

    public void test10() {
        // ana men??ye d??n

        WebElement stockButton = null;
        WebElement inputs[] = new WebElement[6];
        System.out.println("\nTest10");
        // back to main menu
        System.out.println("Ana men??ye d??n??l??yor...");
        for (int back = 0; back < 3; ++back) {
            driver.navigate().back();
        }

        try {
            Thread.sleep(400);
            System.out.println("Stok giri?? ekran??na ge??iliyor...");
            stockButton = driver.findElement(AppiumBy.xpath(
                    "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[3]/android.widget.LinearLayout"));
            stockButton.click();
            System.out.println("Giri??ler yap??l??yor...");
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
                    System.err.println("stok ekran??na giri?? tekrar deneniyor...");
                }
            }
            Thread.sleep(1000);
            System.out.println("Stok ekran?? kontrol ediliyor...");
            WebElement testElement = driver.findElement(AppiumBy.xpath(
                    "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.EditText[1]"));
            System.out.println("Girdi kutucu??u de??eri: " + testElement.getAttribute("text"));
            System.out.println((testElement.getAttribute("text").equalsIgnoreCase("??rnek: 2020"))
                    ? "Yertutucu de??erine e??it yani Test10 Ba??ar??l??"
                    : "Yertutucuyla e??it de??il, Test10 Ba??ar??s??z");
            // ana men??ye d??n
            driver.navigate().back();
        } catch (InterruptedException e) {
            System.err.println("Beklemede hata");
        } catch (Exception e) {
            System.err.println("Test10 Ba??ar??s??z.");
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
            System.out.println("Test11 Ba??ar??l??");
        } catch (Exception e) {
            System.err.println("Test11 Ba??ar??s??z.");
        }

        try {
            System.out.println("\nTest12");
            System.out.println("Kaydet butonuna bas??ld??ktan sonra alanlar temizlendi mi kontrol ediliyor...");
            WebElement element = driver.findElement(AppiumBy.xpath(prefix + 1 + "]"));
            System.out.println("Girdi kutucu??u de??eri: " + element.getAttribute("text"));
            System.out.println("Test12 " + ((element.getAttribute("text").equalsIgnoreCase("??rnek: 2020")) ? "ba??ar??l??."
                    : "yar?? ba??ar??l??. ????nk?? alan temizlenmemi??."));

        } catch (Exception e) {
            System.err.println("Test12 ba??ar??s??z. Eleman bulunamad??.");
        }

    }

    public void test13_14() {
        // ana men??ye d??n
        driver.navigate().back();
        String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[4]/android.widget.LinearLayout";
        String prefix = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.EditText[";
        WebElement element = null;
        WebElement saveButton = null;
        String inputList[] = { "2022", "10", "02", "3432", "??stege Bagli" };
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
                    System.out.println("Bo?? alan i??in verilen hata: " + warningText);
                    driver.switchTo().alert().accept();
                    ++index;
                } catch (Exception e) {
                    System.err.println("Eleman bulunurken hata. Tekrar deneniyor...");
                }
            }
        } catch (Exception e) {
            System.err.println("Test13 Ba??ar??s??z eleman bulunamad??");
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
            Boolean result = element.getAttribute("text").equalsIgnoreCase("??RNEK: 2021");
            System.out.println("Test14 " + ((result) ? "Ba??ar??l??" : "Ba??ar??s??z, kay??t tamamlanmad??"));
            driver.navigate().back();
        } catch (Exception e) {
            System.err.println("Test14 Ba??ar??s??z, aran??lan eleman bulunamad??.");
        }
    }

    /** Geri butonlar??n?? tespit etme */
    public void test15() {
        WebElement element = null;
        final int count = 3;
        int counter = 0;
        System.out.println("\nTest15");
        String prefix = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[";
        String postfix = "]/android.widget.LinearLayout";
        System.out.println("??r??nler ekran?? geri butonu kontrol ediliyor...");
        try {
            element = driver.findElement(AppiumBy.xpath(prefix + 1 + postfix));
            element.click();
            Thread.sleep(1000);
            for (int attempt = 0; attempt < count; ++attempt) {
                try {
                    element = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
                    System.out.println("Eleman bulundu. A????klama: " + element.getAttribute("content-desc"));
                    ++counter;
                    break;
                } catch (Exception e) {
                    System.err.println("Eleman bulunamad?? tekrar deneniyor");
                }
            }

            element = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/kaydet"));
            element.click();
            System.out.println("??r??n ekleme ekran?? geri butonu kontrol ediliyor...");
            Thread.sleep(1000);
            for (int attempt = 0; attempt < count; ++attempt) {
                try {
                    element = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
                    System.out.println("Eleman bulundu. A????klama: " + element.getAttribute("content-desc"));
                    ++counter;
                    break;
                } catch (Exception e) {
                    System.err.println("Eleman bulunamad?? tekrar deneniyor");
                }
            }

            System.out.println("Ana sayfaya d??n??l??yor...");
            driver.navigate().back();
            driver.navigate().back();

            System.out.println("Sipari?? ekran??na giriliyor...");
            element = driver.findElement(AppiumBy.xpath(prefix + 2 + postfix));
            element.click();
            System.out.println("Sipari?? ekleme ekran?? geri butonu kontrol ediliyor...");
            Thread.sleep(1000);
            for (int attempt = 0; attempt < count; ++attempt) {
                try {
                    element = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
                    System.out.println("Eleman bulundu. A????klama: " + element.getAttribute("content-desc"));
                    ++counter;
                    break;
                } catch (Exception e) {
                    System.err.println("Eleman bulunamad?? tekrar deneniyor");
                }
            }
            System.out.println("Ana sayfaya d??n??l??yor...");
            driver.navigate().back();

            System.out.println("Stok giri?? ekran??na giriliyor...");
            element = driver.findElement(AppiumBy.xpath(prefix + 3 + postfix));
            element.click();
            System.out.println("Stok giri?? ekran?? geri butonu kontrol ediliyor...");
            Thread.sleep(1000);
            for (int attempt = 0; attempt < count; ++attempt) {
                try {
                    element = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
                    System.out.println("Eleman bulundu. A????klama: " + element.getAttribute("content-desc"));
                    ++counter;
                    break;
                } catch (Exception e) {
                    System.err.println("Eleman bulunamad?? tekrar deneniyor");
                }
            }
            System.out.println("Ana sayfaya d??n??l??yor...");
            driver.navigate().back();

            System.out.println("Harcamalar giri?? ekran??na giriliyor...");
            element = driver.findElement(AppiumBy.xpath(prefix + 4 + postfix));
            element.click();
            System.out.println("Harcamalar giri?? ekran?? geri butonu kontrol ediliyor...");
            Thread.sleep(1000);
            for (int attempt = 0; attempt < count; ++attempt) {
                try {
                    element = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
                    System.out.println("Eleman bulundu. A????klama: " + element.getAttribute("content-desc"));
                    ++counter;
                    break;
                } catch (Exception e) {
                    System.err.println("Eleman bulunamad?? tekrar deneniyor");
                }
            }
            System.out.println("Ana sayfaya d??n??l??yor...");
            driver.navigate().back();

            System.out.println("Net kar ekran??na giriliyor...");
            element = driver.findElement(AppiumBy.xpath(prefix + 5 + postfix));
            element.click();
            System.out.println("Net kar ekran?? geri butonu kontrol ediliyor...");
            Thread.sleep(1000);
            for (int attempt = 0; attempt < count; ++attempt) {
                try {
                    element = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
                    System.out.println("Eleman bulundu. A????klama: " + element.getAttribute("content-desc"));
                    ++counter;
                    break;
                } catch (Exception e) {
                    System.err.println("Eleman bulunamad?? tekrar deneniyor");
                }
            }
            System.out.println("Ana sayfaya d??n??l??yor...");
            driver.navigate().back();
            System.out.println("Ba??ar??l?? geri butonu testi say??s?? : " + counter + "/6");
        } catch (Exception e) {
            System.err.println("Test15 ba??ar??s??z");
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
            System.err.println("Sleep ??al????mad??");
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
