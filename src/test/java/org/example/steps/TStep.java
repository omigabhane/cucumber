package org.example.steps;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.example.pages.TPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class TStep {

    private TPage tPage;

    @Given("^Visitor opens (.*)$")
    public void load(String url) {
        tPage = new TPage();
        tPage.initDriver().loadUrl(url);
        tPage = tPage.getModule(TPage.class);
    }

    @Then("^Visitor waits for (.*)$")
    public void waitForElm(final String cssSelector) {
        tPage.defaultWaitUntil().presenceOfElementLocated(By.cssSelector(cssSelector));
    }
    @And("^Visitor could see all links rendered correctly$")
    public void seePage() throws IOException {
        tPage.linkStatus();
    }

    @And("^Visitor could see '(.*)' at (.*) displayed correctly$")
    public void imageComparison(String imagePath, String cssSelector) throws Exception {
        tPage.getRemoteFile(cssSelector,imagePath);
        tPage.compareImages(imagePath);
    }

    @And("^Visitor adjust (.*) with bottom margin$")
    public void adjustment(String imagePath, String cssSelector) throws Exception {
        tPage.getRemoteFile(cssSelector,imagePath);
    }

    @Then("^the Visitor scrolls to (.*)$")
    public void scrollToAnElement(String element) {
        tPage.executeScript("arguments[0].scrollIntoView(true);", tPage.getWebDriver().findElement(By.cssSelector(element)));
    }

    @And("^Visitor could see (.*) has (.*) as (.*)$")
    public void textComparison(String cssSelector, String type, String text) throws Exception {
        WebElement elment = tPage.getWebDriver().findElement(By.cssSelector(cssSelector));
        if (type.equalsIgnoreCase("text")) {
            assertThat(elment.getText()).as("Text should be ", text).
                    isEqualTo(text);
        }else if (type.equalsIgnoreCase("hover")){
            new Actions(tPage.getWebDriver()).moveToElement(elment).perform();
            assertThat(elment.getCssValue("background-color")).as("hover effect is not matching").isEqualTo(text);
        }
    }

    @After
    public void close() {
        tPage.quit();
    }
}