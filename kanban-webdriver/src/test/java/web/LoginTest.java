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

public class LoginTest {
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
}
