package web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {

	
	 private WebDriver driver;
	    private WebDriverWait wait;

	    @BeforeEach
	    public void setup() {
	        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
	        driver = new ChromeDriver();
	        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    }

	    @AfterEach
	    public void teardown() {
	        if (driver != null) {
	            driver.quit();
	        }
	    }

	    
	    @Test
	    public void testBoardCreate() {
	        // Open the home page
	        driver.get("http://localhost:4200/home");

	        // Click the "Let's explore ..." button
	        WebElement exploreButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.sign-in")));
	        exploreButton.click();

	        // Wait for the login/signup page to load
	        wait.until(ExpectedConditions.urlContains("/login"));
	        
	        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginLabel")));
	        loginButton.click();
	        
	        login("reguser", "password123");
	        wait.until(ExpectedConditions.urlContains("/boards"));
	        assertTrue(driver.getCurrentUrl().contains("/boards"));
	        
	        WebElement boardAddButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("boardAddButton")));
	        boardAddButton.click();
	        wait.until(ExpectedConditions.urlContains("/addboard"));
	        assertTrue(driver.getCurrentUrl().contains("/addboard"));
	        
	        addBoard("test board", "description of test board");
	        wait.until(ExpectedConditions.urlContains("/boards"));
	        assertTrue(driver.getCurrentUrl().contains("/boards"));
	    
	    }
	    
	    private void login(String username, String password) {
	        // Locate fields and fill the form using IDs
	        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginUsername")));
	        WebElement passwordField = driver.findElement(By.id("loginPassword"));
	        WebElement login = driver.findElement(By.id("loginButton"));

	        // Clear and enter values
	        usernameField.clear();
	        usernameField.sendKeys(username);
	        passwordField.clear();
	        passwordField.sendKeys(password);

	        // Click sign up
	        login.click();
	    }
	    
	    
	    private void addBoard(String nameVal, String descVal) {
	        // Locate fields and fill the form using IDs
	        WebElement name = wait.until(ExpectedConditions.elementToBeClickable(By.id("name")));
	        WebElement desc = driver.findElement(By.id("description"));
	        WebElement add = driver.findElement(By.id("boardAddButton"));

	        // Clear and enter values
	        name.clear();
	        name.sendKeys(nameVal);
	        desc.clear();
	        desc.sendKeys(descVal);

	        // Click sign up
	        add.click();
	    }

}
