package com.podprogramming.jobs.WeRecruit.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionContaining.hasItem;
import static org.hamcrest.text.StringContains.containsString;
import static org.junit.Assume.assumeTrue;
import static util.Seleniums.elemToText;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.google.common.collect.Lists;

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
        driver.quit();
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
        
        // wait until questions appears or timeout occurs
        List<WebElement> findElements = waitForQuestions(div);
        System.out.println(driver.getPageSource());
        assertThat("Content was not loaded in time", findElements.isEmpty(), is(false));
        
        // collect questions
        List<WebElement> questionElems = div.findElements(By.className("weRecruitView-question"));
        List<String> questions = Lists.transform(questionElems, elemToText());
        assertThat(questions, hasItem("Quelle annotation existe dans Guice ?"));
        assertThat(questions, hasItem("double number = (Double)null;"));
        assertThat(questions, hasItem("GoF signifie :"));
        assertThat(questions, hasItem("IoC signifie :"));
    }

    private List<WebElement> waitForQuestions(WebElement div) throws InterruptedException {
        int retryRemaining = 120;// 2mins to appear...
        List<WebElement> findElements = div.findElements(By.xpath("/*"));
        while(retryRemaining-->0 && findElements.isEmpty()) {
            Thread.sleep(1000);
            findElements = div.findElements(By.xpath("/*"));
        }
        return findElements;
    }

    
}
