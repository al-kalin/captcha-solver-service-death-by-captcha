import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;

public class ResolveCaptchaTest {

    private static String email;
    private static String password;

    /**
     * Some test with login which use captcha.
     */
    @Test
    void resolveCaptcha() throws Exception {
        String urlWithCaptcha = "https://www.YourSiteUrlWithCaptcha.com";
        login(email, password, urlWithCaptcha);
        DeathByCaptchaClient.reCaptcha(urlWithCaptcha);

        doSomeThing();
    }

    /**
     * Some method to login with captcha.
     *
     * @param logIn    - Site url with captcha.
     * @param password - Site url with captcha.
     * @param url      - Site url with captcha.
     */
    public static void login(String logIn, String password, String url) {
        open(url);
        $x("user login field locator").setValue(logIn);
        $x("password field locator").setValue(password);
        $x("submit button locator']").click();
    }

    /**
     * Some other checks.
     */
    public static void doSomeThing() {
        System.out.println("continue to do something");
    }
}
