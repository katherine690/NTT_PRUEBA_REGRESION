package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By btnIniciarSesionMenu = By.cssSelector(".user-info a");
    private By txtEmail = By.name("email");
    private By txtPassword = By.name("password");
    private By btnSubmit = By.id("submit-login");
    private By lblAccountName = By.cssSelector(".account .hidden-sm-down");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickIniciarSesion() {
        wait.until(ExpectedConditions.elementToBeClickable(btnIniciarSesionMenu)).click();
    }

    public void login(String user, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtEmail)).sendKeys(user);
        driver.findElement(txtPassword).sendKeys(pass);
        driver.findElement(btnSubmit).click();
    }

    public boolean isLoginSuccess() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(lblAccountName)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
