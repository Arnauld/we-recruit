package com.podprogramming.jobs.WeRecruit.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionContaining.hasItem;
import static org.hamcrest.text.StringContains.containsString;
import static org.junit.Assume.assumeTrue;
import static util.Resources.loadProperties;
import static util.Resources.loadXMLProperties;
import static util.Seleniums.elemToText;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.google.common.collect.Lists;

public class UsecaseTest {

    private static int NB_QUESTIONS = 5;
    private static Properties resources;
    private static RemoteWebDriver driver;
    
    @BeforeClass
    public static void loadResources() throws Exception {
        resources = loadXMLProperties("/labels.xml");
    }

    @BeforeClass
    public static void startDriver() throws Exception {
        String driverClass = loadProperties("/settings.properties").getProperty("webdriver.class");
        Class<?> klazz = Class.forName(driverClass);
        driver = (RemoteWebDriver)klazz.newInstance();
        driver.get("http://localhost:8080");
    }

    @AfterClass
    public static void stopDriver() throws Exception {
        driver.quit();
    }

    @Test
    public void meta_pageId_isPresent() {
        WebElement pageId = driver.findElement(By.xpath("//head/meta[@name='page-id']"));
        assertThat(pageId.getAttribute("content"), equalTo("WeRecruit.html"));
    }

    @Test
    public void h1_weRecruit() {
        WebElement h1 = driver.findElement(By.xpath("//div[@id='content']/h1"));
        assertThat(h1.getText(), containsString("Nous recrutons!"));
    }

    @Test
    public void question_appears_in_time() throws InterruptedException {
        assumeTrue(driver.isJavascriptEnabled());
        driver.get("http://localhost:8080");

        WebElement div = driver.findElement(By.id("weRecruit"));

        // wait until questions appears or timeout occurs
        waitForChildrenToLoadOrAssert(div);

        // collect questions
        List<WebElement> questionElems = div.findElements(By.className("weRecruitView-question"));
        List<String> questions = Lists.transform(questionElems, elemToText());
        
        // check their labels match expected ones according to the bundle
        for(int i=1;i<=NB_QUESTIONS;i++) {
            String labelKey = "q"+i+".label";
            String label = resources.getProperty(labelKey);
            assertThat("Missing questions", questions, hasItem(label));
        }
    }

    @Test
    public void mail_is_not_visible() throws InterruptedException {
        assumeTrue(driver.isJavascriptEnabled());
        driver.get("http://localhost:8080");

        WebElement div = driver.findElement(By.id("weRecruit"));

        // wait until questions appears or timeout occurs
        waitForChildrenToLoadOrAssert(div);
        
        WebElement emailHolder = div.findElement(By.className("weRecruitView-mail"));
        List<WebElement> content = emailHolder.findElements(By.xpath("/*"));
        assertThat("Mail should not appear by default", content.isEmpty(), is(true));
    }
    
    @Test
    public void mail_appears_with_good_responses() throws InterruptedException, IOException {
        assumeTrue(driver.isJavascriptEnabled());
        driver.get("http://localhost:8080");

        WebElement div = driver.findElement(By.id("weRecruit"));

        // wait until questions appears or timeout occurs
        waitForChildrenToLoadOrAssert(div);
        
        // select input based on their labels according to the bundle
        for(int i=1;i<=NB_QUESTIONS;i++) {
            String labelKey = "q"+i+".answer.label";
            String label = resources.getProperty(labelKey);
        
            WebElement input = findInputOfLabelOrAssert(label);
            input.click();
        }
        
        // now check that mail appeared
        WebElement emailContainer = div.findElement(By.className("weRecruitView-mail"));
        waitForChildrenToLoadOrAssert(emailContainer);
        
        WebElement mailTo = emailContainer.findElement(By.xpath("//a"));
        assertThat(mailTo, notNullValue());
        assertThat(mailTo.getAttribute("href"), equalTo("mailto:recruitment@pod-programming.com"));
    }

    private WebElement findInputOfLabelOrAssert(String text) {
        WebElement label = driver.findElement(By.xpath("//label[text()=\""+escape(text)+"\"]"));
        assertThat("No label found with text <"+text+">", label, notNullValue());
        String inputId = label.getAttribute("for");
        WebElement input = driver.findElement(By.id(inputId));
        assertThat("No matching input with id <"+inputId+">", input, notNullValue());
        return input;
    }

    private static String escape(String text) {
        return text.replace("\"", "\\\"");
    }

    private static void waitForChildrenToLoadOrAssert(WebElement div) throws InterruptedException {
        
        long waitingTimeThreshold = TimeUnit.MINUTES.toMillis(2);// two minutes... yes! gwt js can take time to load
        long waitingTimePerIter = 300;// millis

        List<WebElement> findElements = Collections.emptyList();
        for (int waitingTime = 0; waitingTime < waitingTimeThreshold; waitingTime += waitingTimePerIter) {
            findElements = div.findElements(By.xpath("/*"));
            if (findElements.isEmpty())
                Thread.sleep(waitingTimePerIter);
            else
                break;
        }
        assertThat("Content was not loaded in time", findElements.isEmpty(), is(false));
    }

}
