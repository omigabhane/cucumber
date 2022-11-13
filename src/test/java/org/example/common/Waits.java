package org.example.common;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by ogabhane on 2/4/2020.
 */
public class Waits {

    private WebDriverWait wait;

    public  Waits(WebDriver driver, int waitTime){
        wait =new WebDriverWait(driver, waitTime);
    }

    public void presenceOfElementLocated(By locator){
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void elementToBeClickable(WebElement elm){
        wait.until(ExpectedConditions.elementToBeClickable(elm));
    }

    public void visibilityOfElementLocated(WebElement locator){
        wait.until(ExpectedConditions.visibilityOf(locator));
    }
}
