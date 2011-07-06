package com.podprogramming.jobs.WeRecruit.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.StringContains.containsString;
import static org.junit.Assume.assumeTrue;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class UsecaseTest {

    private static RemoteWebDriver driver;
    
    @BeforeClass
    public static void startDriver() throws Exception {
        // The chrome driver supports javascript? 
        driver = new ChromeDriver();
        driver.get("http://localhost:8080");
    }
    
    @AfterClass
    public static void stopDriver() throws Exception {
        driver.close();
    }

    @Test
    public void meta_pageId_isPresent () {
        WebElement pageId = driver.findElement(By.xpath("/html/head/meta[@name='page-id']"));
        assertThat(pageId.getAttribute("content"), equalTo("WeRecruit.html"));
    }
    
    @Test
    public void h1_weRecruit () {
        WebElement h1 = driver.findElement(By.xpath("/html/body/h1"));
        assertThat(h1.getText(), containsString("Nous recrutons!"));
    }
    
    @Test
    public void question_appears_in_time () throws InterruptedException {
        assumeTrue(driver.isJavascriptEnabled());
        driver.get("http://localhost:8080");
        
        WebElement div = driver.findElement(By.id("weRecruit"));
        
        int retryRemaining = 120;// 2mins to appear...
        List<WebElement> findElements = div.findElements(By.xpath("/*"));
        while(retryRemaining-->0 && findElements.isEmpty()) {
            Thread.sleep(1000);
            findElements = div.findElements(By.xpath("/*"));
        }
        System.out.println(driver.getPageSource());
        assertThat("Content was not loaded in time", findElements.isEmpty(), is(false));
    }

    
}
