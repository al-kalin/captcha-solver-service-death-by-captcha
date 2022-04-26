import com.DeathByCaptcha.Captcha;
import com.DeathByCaptcha.Client;
import com.DeathByCaptcha.HttpClient;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class DeathByCaptchaClient {

    // CAPTCHA solving service.
    // https://www.deathbycaptcha.com/

    /**
     * Solver for captcha.
     *
     * @param captchaPageUrl - Site url with captcha.
     */
    public static void reCaptcha(String captchaPageUrl) {
        // Put your DBC username & password here:
        // Client client = (Client)(new SocketClient(args[0], args[1]));
        String username = "deathByCaptchaUserName";
        String password = "deathByCaptchaUserPassword";


        // Using username/password combination
        Client client = (Client) (new HttpClient(username, password));

        // Using token
        // String authToken = "deathByCaptchaAuthToken";
        // Client client = (Client)(new HttpClient(authToken));
        client.isVerbose = true;

        try {
            try {
                System.out.println("Your balance is " + client.getBalance() + " US cents");
            } catch (IOException e) {
                System.out.println("Failed fetching balance: " + e.toString());
                return;
            }

            // Get data-SiteKey of captcha
            String dataSiteKey;
            dataSiteKey = $x("//*[@id='recaptcha']").getAttribute("data-sitekey");

            Captcha captcha = null;
            try {
                // Upload a reCAPTCHA and poll for its status with 120 seconds timeout.
                // Put your proxy, proxy type, page googlekey, page url and solving timeout (in seconds)
                // 0 or nothing for the default timeout value.

                // deathByCaptchaUserName and deathByCaptchaUserPassword - login and password for deathByCaptcha
                // service account
                captcha = client.decode("http://deathByCaptchaUserName:deathByCaptchaUserPassword@127.0.0.1:1234",
                        "http", dataSiteKey, captchaPageUrl);

                // Other method is to send a json with the parameters
                //
                // JSONObject json_params = new JSONObject();
                // json_params.put("proxy",proxy);
                // json_params.put("proxytype",proxytype);
                // json_params.put("googlekey",sitekey);
                // json_params.put("pageurl",pageurl);
                // captcha = client.decode(json_params);
            } catch (IOException e) {
                System.out.println("Failed uploading CAPTCHA");
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (null != captcha) {

                String captchaId = captcha.text;

                // Set response of solving captcha to g-recaptcha-response
                executeJavaScript("document.getElementById('g-recaptcha-response').value='" + captchaId + "'");
                executeJavaScript("googleRecaptchaCallback()");
                System.out.println("CAPTCHA " + captcha.id + " solved: " + captcha.text);
                // Report incorrectly solved CAPTCHA if necessary.
                // Make sure you've checked if the CAPTCHA was in fact incorrectly
                // solved, or else you might get banned as abuser.
                /*try {
                    if (client.report(captcha)) {
                        System.out.println("Reported as incorrectly solved");
                    } else {
                        System.out.println("Failed reporting incorrectly solved CAPTCHA");
                    }
                } catch (IOException e) {
                    System.out.println("Failed reporting incorrectly solved CAPTCHA: " + e.toString());
                }*/
            } else {
                System.out.println("Failed solving CAPTCHA");
            }
        } catch (com.DeathByCaptcha.Exception e) {
            System.out.println(e);
        }
    }
}
