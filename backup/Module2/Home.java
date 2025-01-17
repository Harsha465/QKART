package QKART_SANITY_LOGIN.Module1;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app";

    public Home(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHome() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean PerformLogout() throws InterruptedException {
        try {
            // Find and click on the Logout Button
            WebElement logout_button = driver.findElement(By.className("MuiButton-text"));
            logout_button.click();

            // Wait for Logout to Complete
            Thread.sleep(3000);

            return true;
        } catch (Exception e) {
            // Error while logout
            return false;
        }
    }

    /*
     * Returns Boolean if searching for the given product name occurs without any errors
     */
    public Boolean searchForProduct(String product) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Clear the contents of the search box and Enter the product name in the search
            // box
            WebElement searchbar = driver.findElement(By.name("search"));
            searchbar.clear();
            searchbar.sendKeys(product);
            Thread.sleep(1000);
            return true;
        } catch (Exception e) {
            System.out.println("Error while searching for a product: " + e.getMessage());
            return false;
        }
    }

    /*
     * Returns Array of Web Elements that are search results and return the same
     */
    public List<WebElement> getSearchResults() {

        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Find all webelements corresponding to the card content section of each of
            // search results
            List<WebElement> searchResults = new ArrayList<WebElement>();
            WebElement par_res = driver.findElement(By.className("css-1msksyp"));
            searchResults = par_res.findElements(By.className("css-sycj1h"));
            return searchResults;
        } catch (Exception var3) {
            System.out.println("There were no search results: " + var3.getMessage());
        }
        return null;
    }


    /*
     * Returns Boolean based on if the "No products found" text is displayed
     */
    public Boolean isNoResultFound() {
        Boolean status = false;
        try {
            String str = "No products found";
            if (getSearchResults().size() == 0) {
                if (str.equals(this.driver
                        .findElement(By.xpath("//*[contains(text(),'No products found')]"))
                        .getText())) {
                    status = true;
                }
            }
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Check the presence of "No products found" text in the web page. Assign status
            // = true if the element is *displayed* else set status = false
            return status;
        } catch (Exception e) {
            return status;
        }
    }

    /*
     * Return Boolean if add product to cart is successful
     */
    public Boolean addProductToCart(String productName) {
        try {

            Boolean status = true;
            List<WebElement> results = driver.findElements(By.className("css-sycj1h"));

            for (WebElement each : results) {
                if (each.findElement(By.className("css-yg30e6")).getText().contains(productName)) {
                    Thread.sleep(1000);
                    each.findElement(By.tagName("button")).click();
                    Thread.sleep(2000);
                    return status;
                }
            }

            System.out.println("Unable to find the given product");
            return false;
        } catch (Exception e) {
            System.out.println("Exception while performing add to cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting the status of clicking on the checkout button
     */
    public Boolean clickCheckout() {
        Boolean status = false;
        try {
            WebElement cart = driver.findElement(By.className("cart"));
            WebElement checkout =
                    cart.findElement(By.xpath("//div[contains(@class, 'cart-footer')]//button"));
            checkout.click();
            status = true;
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            // Find and click on the the Checkout button
            return status;
        } catch (Exception e) {
            System.out.println("Exception while clicking on Checkout: " + e.getMessage());
            return status;
        }
    }

    /*
     * Return Boolean denoting the status of change quantity of product in cart operation
     */
    public Boolean changeProductQuantityinCart(String productName, int quantity) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 06: MILESTONE 5

            // Find the item on the cart with the matching productName
            List<WebElement> par_ele =
                    driver.findElements(By.xpath("//div[@class='MuiBox-root css-1gjj37g']"));
            for (int i = 0; i < par_ele.size(); i++) {
                String pro_name = par_ele.get(i).findElement(By.xpath("./div[1]")).getText();
                if (pro_name.equals(productName)) {
                    WebElement qty_list = par_ele.get(i).findElement(By.xpath("./div[2]"))
                            .findElement(By.className("css-u4p24i"));
                    WebElement qty_minus = qty_list.findElement(By.xpath("./button[1]"));
                    int qty = 0;
                while(qty!=quantity){
                    String qty_no = qty_list.findElement(By.xpath("./div")).getText();
                    WebElement qty_plus = qty_list.findElement(By.xpath("./button[2]"));
                    qty = Integer.parseInt(qty_no);
                    
                        if(qty>quantity){
                            qty_minus.click();
                            Thread.sleep(2000);
                        }else if(qty<quantity){
                            qty_plus.click();
                            Thread.sleep(2000);
                        }else{
                            continue;
                        }
                    }
                }
            }


            // Increment or decrement the quantity of the matching product until the current
            // quantity is reached (Note: Keep a look out when then input quantity is 0,
            // here we need to remove the item completely from the cart)


            return true;
        } catch (Exception e) {
            if (quantity == 0)
                return true;
            System.out.println("exception occurred when updating cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the cart contains items as expected
     */
    public Boolean verifyCartContents(List<String> expectedCartContents) {
        try {
            WebElement cartParent = driver.findElement(By.className("cart"));
            List<WebElement> cartContents = cartParent.findElements(By.className("css-zgtx0t"));

            ArrayList<String> actualCartContents = new ArrayList<String>() {};
            for (WebElement cartItem : cartContents) {
                actualCartContents.add(
                        cartItem.findElement(By.className("css-1gjj37g")).getText().split("\n")[0]);
            }

            for (String expected : expectedCartContents) {
                if (!actualCartContents.contains(expected)) {
                    return false;
                }
            }

            return true;

        } catch (Exception e) {
            System.out.println("Exception while verifying cart contents: " + e.getMessage());
            return false;
        }
    }
}
