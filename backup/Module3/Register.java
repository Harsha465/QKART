package QKART_SANITY_LOGIN.Module1;

import java.sql.Timestamp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Register {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app/register";
    public String lastGeneratedUsername = "";

    public Register(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToRegisterPage() {
        if (!driver.getCurrentUrl().equals(this.url)) {
            driver.get(this.url);
        }
    }

    public Boolean registerUser(String Username, String Password, Boolean makeUsernameDynamic)
            throws InterruptedException {
        // Find the Username Text Box
        WebElement username_txt_box = this.driver.findElement(By.id("username"));

        // Get time stamp for generating a unique username
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String test_data_username;
        if (makeUsernameDynamic){
            test_data_username = Username + "_" + String.valueOf(timestamp.getTime());
            lastGeneratedUsername = test_data_username;
        }
            // Concatenate the timestamp to string to form unique timestamp
            
        else
        test_data_username = Username;

        username_txt_box.sendKeys(test_data_username);
        WebElement password_txt_box = driver.findElement(By.id("password"));
        String test_data_password = Password;
        password_txt_box.sendKeys(test_data_password);
        WebElement confirm_password_txt_box;
        confirm_password_txt_box = this.driver.findElement(By.id("confirmPassword"));
        confirm_password_txt_box.sendKeys(test_data_password);
        WebElement register_now_button = driver.findElement(By.className("button"));
        register_now_button.click();
        Thread.sleep(5000);

        try {
                WebDriverWait wait = new WebDriverWait(driver, 30);
                wait.until(ExpectedConditions.urlContains("/login"));
        } catch (Exception e) {
                return false;
        }

        return driver.getCurrentUrl().endsWith("/login");
    }
}
