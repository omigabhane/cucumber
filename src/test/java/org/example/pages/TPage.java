package org.example.pages;

import org.apache.commons.io.FileUtils;
import org.example.common.TemporaryFileSystem;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

public class TPage extends AbstractPage {


  public void linkStatus() throws IOException {
    Map<String,String> map = getListLinks();
    for (Map.Entry<String,String> entry:map.entrySet()) {
      String url = entry.getValue();
      if(url == null || url.isEmpty()){
        System.out.println("URL is either not configured for anchor tag or it is empty");
        continue;
      }
      URL myUrl = new URL(url);
      HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
      conn.setDoOutput(true);
      conn.setReadTimeout(30000);
      conn.setConnectTimeout(30000);
      conn.setUseCaches(false);
      conn.setAllowUserInteraction(false);
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setRequestProperty("Accept-Charset", "UTF-8");
      conn.setRequestMethod("GET");
      conn.connect();
      int respCode = conn.getResponseCode();
      System.out.println(String.format("Response '%s'  '%s':'%s'",respCode,entry.getKey(),entry.getValue()));
      assertThat(respCode).as("Response code should be 200 for %s",entry.getValue()).isEqualTo(200);
    }
  }

  public Map<String, String> getListLinks(){
    Map<String,String> map = new HashMap<>();
    List<WebElement> elms =  getWebDriver().findElements(By.tagName("a"));
    for (WebElement elm:elms) {
      map.put(elm.getText(),elm.getAttribute("href"));
    }
    return map;
  }

  public void getRemoteFile(String cssSelector, String resorcePath) throws Exception {
    WebElement elm = getWebDriver().findElement(By.cssSelector(cssSelector));
    TemporaryFileSystem temp = new TemporaryFileSystem();
    try {
      if (!elm.getAttribute("src").isEmpty()){
        String src = elm.getAttribute("src");
        //FileUtils.copyURLToFile(new URL(src), new File("remote/"+resorcePath), 30000, 30000);
        temp.writeFile(temp.readImageFrom(elm,getUrl()),"remote/"+resorcePath);
      }
    } catch (Exception e) {
      System.out.println("This is not url");
      String svg = elm.getAttribute("innerHTML");
      temp.writeFile(svg,"remote/"+resorcePath);
    }
  }

  public void compareImages(String resourcePath) throws Exception {
    TemporaryFileSystem temp = new TemporaryFileSystem();
    try {
      BufferedImage imageRemote = temp.createImageFromSVG("remote/"+resourcePath);
      BufferedImage imageLocal = temp.createImageFromSVG("local/"+resourcePath);
      assertThat(temp.compareImages(imageLocal,imageRemote)).as("logo not matched").isTrue();
    } catch (Exception e) {
      //create Base
      FileUtils.copyFile(new File("remote/"+resourcePath),new File("local/"+resourcePath));
      throw new Exception("created base");
    }
  }

  public String executeScript(String script, Object... args) {
    return String.valueOf(getJsExecutor().executeScript(script, args));
  }

  public JavascriptExecutor getJsExecutor() {
    getWebDriver().manage().timeouts().setScriptTimeout(3000, SECONDS);
    return (JavascriptExecutor) getWebDriver();
  }
}
