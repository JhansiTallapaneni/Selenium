import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class ParaBankAutomation {
  public static void main(String[] args) throws Exception{
    // Setup ChromeDriver
    WebDriverManager.chromedriver().setup();
    WebDriver driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    String baseUrl = "https://parabank.parasoft.com/parabank/index.htm?ConnType=JDBC";

    long ts = System.currentTimeMillis();
    String username = "seleniumUser" + ts;
    String password = "Password@" + (ts % 1000);

    driver.get(baseUrl);

    driver.findElement(By.linkText("Register")).click();

    driver.findElement(By.id("customer.firstName")).sendKeys("TestFirst");
    driver.findElement(By.id("customer.lastName")).sendKeys("TestLast");
    driver.findElement(By.id("customer.address.street")).sendKeys("1 Test Street");
    driver.findElement(By.id("customer.address.city")).sendKeys("TestCity");
    driver.findElement(By.id("customer.address.state")).sendKeys("TS");
    driver.findElement(By.id("customer.address.zipCode")).sendKeys("12345");
    driver.findElement(By.id("customer.phoneNumber")).sendKeys("9876543210");
    driver.findElement(By.id("customer.ssn")).sendKeys("1234");
    driver.findElement(By.id("customer.username")).sendKeys(username);
    driver.findElement(By.id("customer.password")).sendKeys(password);
    driver.findElement(By.id("repeatedPassword")).sendKeys(password);

    // Submit registration
    driver.findElement(By.cssSelector("input[value='Register']")).click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Accounts Overview"))).click();

    WebElement totalValue = wait.until(ExpectedConditions.visibilityOfElementLocated(
    By.xpath("//b[text()='Total']/parent::td/following-sibling::td/b")));

    String text = totalValue.getText();
    System.out.println("Total: " + text);
    driver.quit();
  }
}
