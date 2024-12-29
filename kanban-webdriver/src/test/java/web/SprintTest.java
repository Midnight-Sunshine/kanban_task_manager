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

public class SprintTest {

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
    public void testSprintCreate() {
        // Open the home page
        driver.get("http://localhost:4200/home");

        // Click the "Let's explore ..." button
        WebElement exploreButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.sign-in")));
        exploreButton.click();

        // Wait for the login/signup page to load
        wait.until(ExpectedConditions.urlContains("/login"));

        // Perform login
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginLabel")));
        loginButton.click();
        login("reguser", "password123");

        // Wait for navigation to the boards page
        wait.until(ExpectedConditions.urlContains("/boards"));
        assertTrue(driver.getCurrentUrl().contains("/boards"));

        // Navigate to the sprints section
        WebElement sidebarButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("sidebar")));
        sidebarButton.click();
        WebElement sprintButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("sprintsButton")));
        sprintButton.click();

        // Wait for the sprints page
        wait.until(ExpectedConditions.urlContains("/sprints"));
        assertTrue(driver.getCurrentUrl().contains("/sprints"));

        // Click to add a new sprint
        WebElement addSprintButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("addSprintButton")));
        addSprintButton.click();

        // Wait for the add sprint page
        wait.until(ExpectedConditions.urlContains("/addsprint"));
        assertTrue(driver.getCurrentUrl().contains("/addsprint"));

        // Add a new sprint
        addsprint("test sprint", "01-12-2024", "15-12-2024");

        // Verify redirection to the sprints page
        wait.until(ExpectedConditions.urlContains("/sprints"));
        assertTrue(driver.getCurrentUrl().contains("/sprints"));
        
        // Reload the page to ensure changes are reflected
        driver.navigate().refresh();
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

        // Click login
        login.click();
    }

    private void addsprint(String nameVal, String sdateVal, String edateVal) {
        // Locate fields and fill the form using IDs
        WebElement name = wait.until(ExpectedConditions.elementToBeClickable(By.id("name")));
        WebElement startDate = driver.findElement(By.id("startDate"));
        WebElement endDate = driver.findElement(By.id("endDate"));
        WebElement add = driver.findElement(By.id("sprintAddButton"));

        // Clear and enter values
        name.clear();
        name.sendKeys(nameVal);
        startDate.clear();
        startDate.sendKeys(sdateVal);
        endDate.clear();
        endDate.sendKeys(edateVal);

        // Click add sprint button
        add.click();
    }
}
