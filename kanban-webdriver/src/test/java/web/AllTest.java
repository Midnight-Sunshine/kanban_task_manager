package web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AllTest {
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
    public void testSignupProcess() {
        // Open the home page
        driver.get("http://localhost:4200/home");

        // Click the "Let's explore ..." button
        WebElement exploreButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.sign-in")));
        exploreButton.click();

        // Wait for the login/signup page to load
        wait.until(ExpectedConditions.urlContains("/login"));

        // Test Case 1: Empty Fields
        registerUser("", "", "");
        validateErrorMessageById("signupErrorMessage", "All fields are required.");

        // Test Case 2: Duplicate username/email
        registerUser("existingUser", "existing@example.com", "password123");
        validateErrorMessageById("signupErrorMessage", "Username or email already exists. Please try again.");

        // Test Case 3: Invalid email
        fieldFiller("newUser", "invalid-email", "password123");
        validateErrorMessageById("emailError", "Please enter a valid email address.");

        // Test Case 4: Invalid password
        fieldFiller("newUser", "valid@example.com", "short");
        validateErrorMessageById("passwordError", "Password must be at least 6 characters long.");

        // Test Case 5: Successful registration
        registerUser("uniqueUser", "unique@example.com", "password123");
        handleAlert(); // Automatically handle the alert
        wait.until(ExpectedConditions.urlContains("/home"));
        assertTrue(driver.getCurrentUrl().contains("/home"));
        
        WebElement exploreButton2 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.sign-in")));
        exploreButton2.click();
        
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginLabel")));
        loginButton.click();
        
        login("", "");
        validateErrorMessageById("loginErrorMessage", "All fields are required.");
        
        login("nonexistent", "wrong");
        validateErrorMessageById("loginErrorMessage", "Username or password incorrect. Please try again.");
        
        login("uniqueUser", "password123");
        wait.until(ExpectedConditions.urlContains("/boards"));
        assertTrue(driver.getCurrentUrl().contains("/boards"));
        
        WebElement boardAddButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("boardAddButton")));
        boardAddButton.click();
        wait.until(ExpectedConditions.urlContains("/addboard"));
        assertTrue(driver.getCurrentUrl().contains("/addboard"));
        
        addBoard("test board", "description of test board");
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
        
     // Navigate to the sprints section
        WebElement sidebarButton2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("sidebar")));
        sidebarButton2.click();
        WebElement boardButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("boardsButton")));
        boardButton.click();

        // Wait for the sprints page
        wait.until(ExpectedConditions.urlContains("/boards"));
        assertTrue(driver.getCurrentUrl().contains("/boards"));
        
     // Navigate to the sprints section
        WebElement boardButton2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("boardContent")));
        boardButton2.click();
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

    private void registerUser(String username, String email, String password) {
        // Locate fields and fill the form using IDs
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("signupUsername")));
        WebElement emailField = driver.findElement(By.id("signupEmail"));
        WebElement passwordField = driver.findElement(By.id("signupPassword"));
        WebElement signupButton = driver.findElement(By.id("signupButton"));

        // Clear and enter values
        usernameField.clear();
        usernameField.sendKeys(username);
        emailField.clear();
        emailField.sendKeys(email);
        passwordField.clear();
        passwordField.sendKeys(password);

        // Click sign up
        signupButton.click();
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
    
    private void fieldFiller(String username, String email, String password) {
        // Locate fields and fill the form using IDs
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("signupUsername")));
        WebElement emailField = driver.findElement(By.id("signupEmail"));
        WebElement passwordField = driver.findElement(By.id("signupPassword"));

        // Clear and enter values
        usernameField.clear();
        usernameField.sendKeys(username);
        emailField.clear();
        emailField.sendKeys(email);
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    private void validateErrorMessageById(String errorId, String expectedMessage) {
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(errorId)));
        assertTrue(errorMessage.getText().contains(expectedMessage), "Expected error message not found!");
    }

    private void handleAlert() {
        // Wait for the alert to appear and then accept it
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
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
