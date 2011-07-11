package com.podprogramming.jobs.WeRecruit.demo;

import static util.Resources.loadProperties;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CurriculumDemoTest {

    private static RemoteWebDriver driver;
    
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
        WebElement searchMenu = driver.findElement(By.xpath("//div[@class='item search']/a"));
        pause();
        searchMenu.click();//appear
        pause();
        WebElement searchLink = driver.findElement(By.id("search"));
        searchLink.click();
        pause();
        searchMenu.click();//remove .hover + hide
        
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
        
        WebElement otop = driver.findElement(By.id("click-n-clear"));
        otop.click();
        pause(TimeUnit.SECONDS.toMillis(10));
        
        System.out.println("No more actions ctrl+c to quit");
        Thread.sleep(1000*stepLength);
    }

}
