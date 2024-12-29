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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {

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
    public void testTaskCreate() {
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
        WebElement boardButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("boardContent")));
        boardButton.click();
        wait.until(ExpectedConditions.urlContains("/tasks/board"));
        assertTrue(driver.getCurrentUrl().contains("/tasks/board"));
        WebElement addTaskButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("addTask")));
        addTaskButton.click();
        wait.until(ExpectedConditions.urlContains("/addtask"));
        assertTrue(driver.getCurrentUrl().contains("/addtask"));

        addTask(
        	    "Develop Login Feature",          // Task Name
        	    "TODO",                           // Status
        	    "Implement the login functionality", // Description
        	    "8"                            // Estimation
        	);


        wait.until(ExpectedConditions.urlContains("/tasks/board"));
        assertTrue(driver.getCurrentUrl().contains("/tasks/board"));
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

    private void addTask(String taskName, String status, String description, String estimation) {
        // Locate fields and fill the form using IDs
        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("name")));
        WebElement statusField = driver.findElement(By.id("status"));
        WebElement descriptionField = driver.findElement(By.id("description"));
        WebElement estimationField = driver.findElement(By.id("estimation"));
        WebElement addButton = driver.findElement(By.cssSelector("button[type='submit']"));

        // Clear and enter values
        nameField.clear();
        nameField.sendKeys(taskName);

        // Select the status
        statusField.click();
        WebElement statusOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//option[@value='" + status + "']")));
        statusOption.click();

        descriptionField.clear();
        descriptionField.sendKeys(description);

        estimationField.clear();
        estimationField.sendKeys(estimation);

        // Select users (represented as chips)
            try {
                WebElement userChip = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/app-root/html/body/app-addtask/div/div[2]/form/div[2]/div/div[3]/div/div[1]")
                ));
                userChip.click();
            } catch (Exception e) {
                System.out.println("User chip not found.");
            }
        

        // Select sprints
            try {
                WebElement sprintOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/app-root/html/body/app-addtask/div/div[2]/form/div[2]/div/div[4]/select/option[1]")
                ));
                sprintOption.click();
            } catch (Exception e) {
                System.out.println("Sprint option not found.");
            }
        

        // Click the Add Task button
        addButton.click();
    }

}
