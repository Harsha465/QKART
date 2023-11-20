package QKART_SANITY_LOGIN.Module1;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class Login {
        RemoteWebDriver driver;
        String url = "https://crio-qkart-frontend-qa.vercel.app/login";

        public Login(RemoteWebDriver driver) {
                this.driver = driver;
        }

        public void navigateToLoginPage() {
                if (!this.driver.getCurrentUrl().equals(this.url)) {
                        this.driver.get(this.url);
                }
        }

        public Boolean PerformLogin(String Username, String Password) throws InterruptedException {
                // Find the Username Text Box
                WebElement username_txt_box = driver.findElement(By.id("username"));

                username_txt_box.sendKeys(Username);
                Thread.sleep(1000);

                WebElement password_txt_box = driver.findElement(By.id("password"));

                password_txt_box.sendKeys(Password);

                WebElement login_button =
                                driver.findElement(By.xpath("//button[text() = 'Login to QKart']"));

                login_button.click();

                // SLEEP_STMT_13: Wait for Login to Complete
                // Wait for Login action to complete
                FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                                .withTimeout(Duration.ofSeconds(30))
                                .pollingEvery(Duration.ofMillis(250))
                                .ignoring(NoSuchElementException.class);

                wait.until(ExpectedConditions.invisibilityOf(login_button));

                return this.VerifyUserLoggedIn(Username);
        }

        public Boolean VerifyUserLoggedIn(String Username) {
                try {
                        WebElement username_label;
                        username_label = driver.findElement(By.className("username-text"));
                        return username_label.getText().equals(Username);
                } catch (Exception e) {
                        return false;
                }

        }

}
