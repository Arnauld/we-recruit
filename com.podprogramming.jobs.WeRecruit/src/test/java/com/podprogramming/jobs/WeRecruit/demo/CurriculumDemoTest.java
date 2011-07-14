package com.podprogramming.jobs.WeRecruit.demo;

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

public class CurriculumDemoTest {

    private static int NB_QUESTIONS = 5;
    private static Properties resources;
    private static RemoteWebDriver driver;
    
    @BeforeClass
    public static void loadResources() throws Exception {
        resources = loadXMLProperties("/labels.xml");
    }
    
    @BeforeClass
    public static void startDriver() throws Exception {
        Properties settings = loadProperties("/settings.properties");
        String driverClass = settings.getProperty("webdriver.class");
        
        if(driverClass.contains("chrome")) {
            String chromeDriver = settings.getProperty("webdriver.chrome.driver");
            System.setProperty("webdriver.chrome.driver", chromeDriver);
        }
        Class<?> klazz = Class.forName(driverClass);
        driver = (RemoteWebDriver)klazz.newInstance();
        driver.get("http://localhost:8080");
    }

    @AfterClass
    public static void stopDriver() throws Exception {
        driver.quit();
    }
    
    private long stepLength = TimeUnit.SECONDS.toMillis(3);
    private void pause () throws InterruptedException {
        pause(stepLength);
    }
    
    private void pause(long millis) throws InterruptedException {
        Thread.sleep(millis);
    }
    
    @Test
    public void execute() throws IOException, InterruptedException {
        WebElement div = driver.findElement(By.id("weRecruit"));

        // wait until questions appears or timeout occurs
        waitForChildrenToLoadOrAssert(div);

        // activate hacking
        WebElement activateHack = driver.findElement(By.id("activate-hack"));
        activateHack.click();
        
        // select input based on their labels according to the bundle
        for(int i=1;i<=NB_QUESTIONS;i++) {
            String labelKey = "q"+i+".answer.label";
            String label = resources.getProperty(labelKey);
        
            WebElement input = findInputOfLabelOrAssert(label);
            input.click();
        }
        pause();

        // --- click the search
        WebElement searchMenu = driver.findElement(By.xpath("//div[@class='item search']/a"));
        pause();
        searchMenu.click();//appear
        pause();
        WebElement searchLink = driver.findElement(By.id("search"));
        searchLink.click();
        pause();
        searchMenu.click();//remove .hover + hide
        
        // --- start the node
        WebElement cloudMenu = driver.findElement(By.xpath("//div[@class='item cloud']/a"));
        cloudMenu.click();//appear
        pause();
        WebElement startNode = driver.findElement(By.id("start-node"));
        startNode.click();
        pause();
        cloudMenu.click();//hide
        pause();
        
        // select cv
        WebElement link = driver.findElement(By.className("instance-link"));
        link.click();
        
        // cleanup
        WebElement otop = driver.findElement(By.id("click-n-clear"));
        otop.click();
        
        // deactivate hacking
        WebElement deactivateHack = driver.findElement(By.id("activate-hack"));
        deactivateHack.click();
        
        pause(TimeUnit.SECONDS.toMillis(10));
        System.out.println("==============================");
        System.out.println("Demo is done! ctrl+c to quit");
        System.out.println("==============================");
        Thread.sleep(1000*stepLength);
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
