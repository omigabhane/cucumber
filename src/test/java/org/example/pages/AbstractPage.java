package org.example.pages;
import org.example.common.TemporaryFileSystem;
import org.example.common.Waits;
import org.example.driverutil.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public abstract class AbstractPage {
    private static WebDriver webDriver;
    private static String url;

    public AbstractPage initDriver(){
        this.webDriver = new DriverFactory().newDriver();
        return this;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public <T> T getModule(Class<T> pageClassProxy){
        return PageFactory.initElements(webDriver, pageClassProxy);
    }

    public <T> T getModule(WebDriver webDriver,Class<T> pageClassProxy){
        T page = PageFactory.initElements(webDriver, pageClassProxy);
        if (page instanceof AbstractPage){
            ((AbstractPage) page).setWebDriver(webDriver);
        }
        return page;
    }

    public <T> T getPage(Class<T> pageClassProxy){
        return PageFactory.initElements(webDriver, pageClassProxy);
    }

    public AbstractPage loadUrl(String url){
        this.url = url;
       webDriver.get(url);
       return this;
    }

    public static String getUrl() {
        return url;
    }

    public void quit(){
        if (webDriver!=null) {
            webDriver.close();
        }
    }
    /*********Waits***********/
    public void waitUntilLoaded(WebElement elm){
        defaultWaitUntil().elementToBeClickable(elm);
    }

    public Waits shortWaitUntil(){
        return new Waits(webDriver,2000);
    }

    public Waits longWaitUntil(){
        return new Waits(webDriver,10000);
    }

    public Waits tooLongWaitUntil(){
        return new Waits(webDriver,20000);
    }

    public Waits defaultWaitUntil(){
        return new Waits(webDriver,5000);
    }

    /***************** Scroll**********/
    public void scrollVertical(WebElement toElm){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", toElm);
    }

    /****************SwitchTo***********/
    public void switchToTab(){
        int count = webDriver.getWindowHandles().size();
        webDriver.switchTo().window(new ArrayList<>(webDriver.getWindowHandles()).get(count-1));
    }

    public void switchToParent(){
        webDriver.switchTo().defaultContent();
    }

    public void closeTab(){
        webDriver.close();
    }
}
