package QKART_SANITY_LOGIN.Module1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResult {
    WebElement parentElement;

    public SearchResult(WebElement SearchResultElement) {
        this.parentElement = SearchResultElement;
    }

    /*
     * Return title of the parentElement denoting the card content section of a
     * search result
     */
    public String getTitleofResult() {
        
        String titleOfSearchResult = "";

        titleOfSearchResult += parentElement.getText();
        // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
        // Find the element containing the title (product name) of the search result and
        // assign the extract title text to titleOfSearchResult
        System.out.println(titleOfSearchResult);
        return titleOfSearchResult;
    }

    /*
     * Return Boolean denoting if the open size chart operation was successful
     */
    public Boolean openSizechart() {
        try {
           WebElement sizecharbutton = this.parentElement.findElement(By.className("css-1qw96cp")).findElement(By.tagName("button"));
           if (sizecharbutton.isEnabled()) {
              sizecharbutton.click();
              return true;
           } else {
              return null;
           }
        } catch (Exception var2) {
           System.out.println("Exception while opening Size chart: " + var2.getMessage());
           return false;
        }
     }

    /*
     * Return Boolean denoting if the close size chart operation was successful
     */
    public Boolean closeSizeChart(WebDriver driver) {
        try {
           Thread.sleep(2000L);
           Actions action = new Actions(driver);
           action.sendKeys(new CharSequence[]{Keys.ESCAPE}).perform();;
        //    action.perform();
           Thread.sleep(2000L);
           return true;
        } catch (Exception var3) {
           System.out.println("Exception while closing the size chart: " + var3.getMessage());
           return false;
        }
     }

    /*
     * Return Boolean based on if the size chart exists
     */
    public Boolean verifySizeChartExists() {
        Boolean status = false;
    
        try {
            if (this.parentElement.findElement(By.className("css-1qw96cp")).findElement(By.tagName("button"))
                .getText().equals("SIZE CHART")) {
                status = true;
            }
    
            return status;
        } catch (Exception var3) {
            return status;
        }
    }
    
    /*
     * Return Boolean if the table headers and body of the size chart matches the
     * expected values
     */
    public Boolean validateSizeChartContents(List<String> expectedTableHeaders, List<List<String>> expectedTableBody, WebDriver driver) {
        Boolean status = true;
    
        try {
            WebElement sizeChartTable = driver.findElement(By.className("css-uhb5lp"));
            WebElement tableElement = sizeChartTable.findElement(By.tagName("table"));
            List<WebElement> tableHeaderElements = tableElement.findElement(By.tagName("thead")).findElements(By.tagName("th"));
            String tempHeaderValue;

            for(int i=0;i<expectedTableHeaders.size();i++){
                tempHeaderValue = tableHeaderElements.get(i).getText();
    
            if (!expectedTableHeaders.get(i).equals(tempHeaderValue)) {
                status = false;
                System.out.println("Table Headers do not match.");
            } 
        }
                List<WebElement> tableBodyRows = tableElement.findElement(By.tagName("tbody"))
                .findElements(By.tagName(("tr")));
    
                if (tableBodyRows.isEmpty()) {
                    status = false;
                    System.out.println("No rows found in the table.");
                } else {
                        for(int i=0;i<expectedTableBody.size();i++){
                            for(int j=1;j<=expectedTableBody.get(i).size();j++){
                                if(!tableBodyRows.get(i).findElement(By.xpath("td["+j+"]")).getText()
                                .equalsIgnoreCase(expectedTableBody.get(i).get(j-1))){
                                    return false;
                                }
                            }
                        }
                    }
        } catch (Exception e) {
            System.out.println("Error while validating chart contents: " + e.getMessage());
            return false;
        }
    
        return status;
    }

    /*
     * Return Boolean based on if the Size drop down exists
     */
    public Boolean verifyExistenceofSizeDropdown(WebDriver driver) {
        Boolean status = false;
  
        try {
           if (this.parentElement.findElement(By.className("css-3zukih"))
           .findElement(By.className("css-13sljp9"))
           .findElement(By.tagName("select")).isEnabled()) {
              status = true;
           }
  
           return status;
        } catch (Exception var4) {
           return status;
        }
     }
}