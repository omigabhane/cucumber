package org.example.common;

import com.google.common.base.Throwables;
import com.google.common.io.Resources;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by ogabhane on 2/10/2020.
 */
public class TemporaryFileSystem {
    public static final String USER_DIR = System.getProperty("user.dir");
    private static final String imagePath = USER_DIR + "/" + "input/";

    public InputStream readImageFrom(WebElement imgElm,String url) throws IOException {
        String src = imgElm.getAttribute("src");
        URL myUrl = new URL(src);
        HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
        conn.setDoOutput(true);
        conn.setReadTimeout(30000);
        conn.setConnectTimeout(30000);
        conn.setUseCaches(false);
        conn.setAllowUserInteraction(false);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setRequestMethod("GET");
        return conn.getInputStream();
    }

    public BufferedImage readImageFrom(File f) throws IOException {
        return ImageIO.read(f);
    }

    public BufferedImage readImageFrom(String fileString) throws IOException {
        return ImageIO.read(new File(fileString));
    }

    public File getLogoFromResource(String s) {
        return new File(Resources.getResource("input/" + s).getFile());
    }


    public File getFileFromResource(String s) {
        return new File(Resources.getResource("input/" + s).getFile());
    }

    public boolean compareImages(BufferedImage originalScreenshot, BufferedImage currentScreenshot) throws Exception {
        ByteArrayOutputStream baosOriginal = new ByteArrayOutputStream();
        ByteArrayOutputStream baosCurrent = new ByteArrayOutputStream();
        byte[] imageInByteOriginal = new byte[0];
        byte[] imageInByteCurrent = new byte[0];
        try {
            ImageIO.write(originalScreenshot, "png", baosOriginal);
            baosOriginal.flush();
            imageInByteOriginal = baosOriginal.toByteArray();
            baosOriginal.close();
            ImageIO.write(currentScreenshot, "png", baosCurrent);
            baosCurrent.flush();
            imageInByteCurrent = baosCurrent.toByteArray();
            baosCurrent.close();
        } catch (IOException e) {
            throw new Exception("IO exception", e);
        }
        boolean difference = Arrays.equals(imageInByteOriginal, imageInByteCurrent);
        return difference;
    }

    public BufferedImage createImageFromSVG(String svg) throws IOException {
        return ImageIO.read(new File(imagePath+svg));
    }

    public void writeFile(String imageContent, String filePath) throws Exception {
        File myObj = new File(imagePath+filePath);
        if (myObj.exists()){
            myObj.delete();
        }
        myObj.createNewFile();
        try {
            FileWriter myWriter = new FileWriter(imagePath+filePath);
            myWriter.write(imageContent);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            throw new Exception(e);
        }
    }
    public void writeFile(InputStream imageContent, String filePath) throws Exception {
        File myObj = new File(imagePath+filePath);
        if (myObj.exists()){
            myObj.delete();
        }
        myObj.createNewFile();
        try {
            FileUtils.copyInputStreamToFile(imageContent,myObj);
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            throw new Exception(e);
        }
    }
}
