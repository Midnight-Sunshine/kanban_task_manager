package web;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.netty.handler.timeout.TimeoutException;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*public class TestMain {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        
        try {
            driver.get("https://www.google.com");
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Increase timeout to 20 seconds
            
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("q")));
            searchBox.sendKeys("Selenium WebDriver tutorial");
            searchBox.submit();
            
            // Wait for the first search result link to become visible
            WebElement firstResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".g a")));
            System.out.println("First result title: " + firstResult.getText());

            // Verify if results are displayed
            boolean resultsDisplayed = driver.findElements(By.cssSelector(".g")).size() > 0;
            if (resultsDisplayed) {
                System.out.println("Results are displayed successfully.");
            } else {
                System.out.println("No results found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}*/

/*public class TestMain {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe"); 
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); 

        WebDriver driver = new ChromeDriver(options);
        
        try {
            driver.get("http://localhost:8000/connexion.html");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))); 
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))); 

            usernameField.sendKeys("user");
            passwordField.sendKeys("password");

            WebElement submitButton = driver.findElement(By.xpath("//button[@type='button']")); 
            submitButton.click();
            
            WebElement loginMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginMessage"))); 
            
            if (loginMessage.getText().equals("Connexion réussie !")) {
                System.out.println("Success: The form was submitted successfully.");
            } else if (loginMessage.getText().equals("Nom d'utilisateur ou mot de passe incorrect.")) {
                System.out.println("Error: Incorrect username or password.");
            } else {
                System.out.println("No success or error message found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}*/


/*public class TestMain {

    public static void main(String[] args) {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

        WebDriver driver = null;

        try {
            // Initialize the WebDriver and set implicit wait
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            String product = "laptop";

            // Search for the product on Amazon and print the prices
            searchAmazon(driver, product);

            // Search for the product on eBay and print the prices
            searchEbay(driver, product);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Quit the WebDriver
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private static void searchAmazon(WebDriver driver, String product) {
        try {
            driver.get("https://www.amazon.fr");

            // Locate the search box, enter the product name, and submit the search
            WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
            searchBox.sendKeys(product);
            searchBox.submit();

            // Retrieve the product prices
            List<WebElement> prices = driver.findElements(By.cssSelector(".a-price-whole"));
            System.out.println("Prix trouvés sur Amazon :");
            for (int i = 0; i < Math.min(5, prices.size()); i++) {
                System.out.println("- " + prices.get(i).getText() + " €");
            }

        } catch (Exception e) {
            System.out.println("An error occurred while searching Amazon: " + e.getMessage());
        }
    }

    private static void searchEbay(WebDriver driver, String product) {
        try {
            driver.get("https://www.ebay.fr");

            // Locate the search box, enter the product name, and submit the search
            WebElement searchBox = driver.findElement(By.id("gh-ac"));
            searchBox.sendKeys(product);
            searchBox.submit();

            // Retrieve the product prices
            List<WebElement> prices = driver.findElements(By.cssSelector(".s-item__price"));
            System.out.println("\nPrix trouvés sur eBay :");
            for (int i = 0; i < Math.min(5, prices.size()); i++) {
                System.out.println("- " + prices.get(i).getText());
            }

        } catch (Exception e) {
            System.out.println("An error occurred while searching eBay: " + e.getMessage());
        }
    }
}*/




/*public class TestMain {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

        WebDriver driver = null;
        try {
            driver = new ChromeDriver();
            driver.manage().window().maximize();

            driver.get("https://www.pagesjaunes.fr");

            handleCookiesPopup(driver);

            performSearch(driver, "informatique", "Paris");

            displaySearchResults(driver);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    
    private static void handleCookiesPopup(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#didomi-notice-agree-button")));
            acceptButton.click();
            System.out.println("Cookies popup accepted.");
        } catch (Exception e) {
            System.out.println("No cookies popup found.");
        }
    }

    
    private static void performSearch(WebDriver driver, String sector, String region) {
        WebElement searchSector = driver.findElement(By.name("quoiqui"));
        searchSector.sendKeys(sector);

        WebElement searchRegion = driver.findElement(By.name("ou"));
        searchRegion.sendKeys(region);

        WebElement searchButton = driver.findElement(By.cssSelector("#findId"));
        searchButton.click();
    }

    
    private static void displaySearchResults(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".bi-list")),
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".no-results"))
            ));

            if (driver.findElements(By.cssSelector(".bi-list")).size() > 0) {
                List<WebElement> entreprises = driver.findElements(By.cssSelector(".bi-denomination"));
                List<WebElement> adresses = driver.findElements(By.cssSelector(".bi-address a"));

                System.out.println("Entreprises trouvées:");
                for (int i = 0; i < Math.min(30, entreprises.size()); i++) {
                    String nomEntreprise = entreprises.get(i).getText();
                    String adresse = (i < adresses.size()) 
                            ? adresses.get(i).getText().replace("Voir le plan", "").trim() 
                            : "Adresse non disponible";
                    System.out.println("- " + nomEntreprise + " : " + adresse);
                }
            } else {
                System.out.println("Aucun résultat trouvé.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching search results.");
        }
    }
}*/

public class TestMain {
    public static void main(String[] args) {
        
    }
}



