package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class StorePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By lblPrecioUnitario = By.cssSelector(".current-price span");
    private By btnAgregarCarrito = By.cssSelector(".add-to-cart");
    private By lblPopupTitulo = By.id("myModalLabel");
    private By lblPopupTotal = By.cssSelector(".cart-content .product-total .value");
    private By btnFinalizarCompraPopup = By.cssSelector(".cart-content-btn .btn-primary");
    private By lblTituloCarrito = By.cssSelector(".card-title");
    private By lblPrecioFinalCarrito = By.cssSelector(".cart-summary-totals .cart-total .value");

    public StorePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navegarMenu(String categoria, String subcategoria) {
        By menuCategoria = By.xpath("//a[contains(text(), '" + categoria + "')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(menuCategoria)).click();

        By menuSubcategoria = By.xpath("//a[contains(text(), '" + subcategoria + "')]");
        wait.until(ExpectedConditions.elementToBeClickable(menuSubcategoria)).click();
    }

    public double seleccionarPrimerProductoYObtenerPrecio(int cantidad) {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".thumbnail-container"))).click();

        String textoPrecio = wait.until(ExpectedConditions.visibilityOfElementLocated(lblPrecioUnitario)).getAttribute("content");
        double precio = Double.parseDouble(textoPrecio);

        WebElement inputCant = driver.findElement(By.id("quantity_wanted"));
        inputCant.clear();
        inputCant.sendKeys(String.valueOf(cantidad));

        driver.findElement(btnAgregarCarrito).click();
        return precio;
    }

    public String obtenerTextoPopup() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(lblPopupTitulo)).getText();
    }

    public double obtenerTotalPopup() {
        String textoTotal = wait.until(ExpectedConditions.visibilityOfElementLocated(lblPopupTotal)).getText();
        return Double.parseDouble(textoTotal.replace("S/ ", "").trim());
    }

    public void finalizarCompra() {
        driver.findElement(btnFinalizarCompraPopup).click();
    }

    public String obtenerTituloCarrito() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(lblTituloCarrito)).getText();
    }

    public double obtenerTotalCarrito() {
        String textoTotal = wait.until(ExpectedConditions.visibilityOfElementLocated(lblPrecioFinalCarrito)).getText();
        return Double.parseDouble(textoTotal.replace("S/ ", "").trim());
    }
}
