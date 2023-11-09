package QKART_SANITY_LOGIN.Module1;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Checkout {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app/checkout";

    public Checkout(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToCheckout() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    /*
     * Return Boolean denoting the status of adding a new address
     */
    public Boolean addNewAddress(String addresString) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            /*
             * Click on the "Add new address" button, enter the addressString in the address
             * text box and click on the "ADD" button to save the address
             */
            WebElement addAddressBtn = driver.findElement(By.id("add-new-btn"));
            addAddressBtn.click();
            WebElement addAddress_Parent = driver.findElement(By.className("css-j7qwjs"));
            WebElement addAddress = addAddress_Parent.findElement(By.tagName("textarea"));
            addAddress.sendKeys(addresString);
            Thread.sleep(1000);
            addAddress_Parent.findElement(By.tagName("button")).click();
            return true;
        } catch (Exception e) {
            System.out.println("Exception occurred while entering address: " + e.getMessage());
            return false;

        }
    }

    /*
     * Return Boolean denoting the status of selecting an available address
     */
    public Boolean selectAddress(String addressToSelect) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            /*
             * Iterate through all the address boxes to find the address box with matching
             * text, addressToSelect and click on it
             */
            
            List<WebElement> addressList = driver.findElements(By.xpath("//*[text()='"+addressToSelect+"']"));
            // if(addressList.isDisplayed()){
            //     addressList.click();
            Thread.sleep(1000);
            // return true;
            // }
            for(int i=0;i<addressList.size();i++){
                Thread.sleep(1000);
                if(addressToSelect.equals(addressList.get(i).getText())){
                    // Thread.sleep(1000);
                    WebElement elements = driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div[1]/div/div[1]/div/div[1]/span/input"));
                    elements.click();
                    Thread.sleep(2000);
                    return true;
                }
            }
            System.out.println("Unable to find the given address");
            return false;
        } catch (Exception e) {
            System.out.println("Exception Occurred while selecting the given address: " + e.getMessage());
            return false;
        }

    }

    /*
     * Return Boolean denoting the status of place order action
     */
    public Boolean placeOrder() {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            // Find the "PLACE ORDER" button and click on it
           if(driver.findElement(By.xpath("//button[text()='PLACE ORDER']")).isEnabled()){
            driver.findElement(By.xpath("//button[text()='PLACE ORDER']")).click();
            return true;
           }
           return false;
        } catch (Exception e) {
            System.out.println("Exception while clicking on PLACE ORDER: " + e.getMessage());
            return false;
        }
        
    }

    /*
     * Return Boolean denoting if the insufficient balance message is displayed
     */
    public Boolean verifyInsufficientBalanceMessage() {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 07: MILESTONE 6
            String expectedText = "You do not have enough balance in your wallet for this purchase";

            Boolean status = true;

            WebElement error = driver.findElement(By.xpath("//div[@class='SnackbarItem-message']"));
            if(error.isDisplayed()){
                if(error.getText().equals(expectedText)){
                    return status;
                }
            }    
            return false;
        } catch (Exception e) {
            System.out.println("Exception while verifying insufficient balance message: " + e.getMessage());
            return false;
        }
    }
}
