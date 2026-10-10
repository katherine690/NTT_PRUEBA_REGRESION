package stepdefinitions;

import io.cucumber.java.es.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.StorePage;

public class ProductStoreSteps {
    private WebDriver driver = Hooks.getDriver();
    private LoginPage loginPage = new LoginPage(driver);
    private StorePage storePage = new StorePage(driver);

    private double precioUnitario;
    private final int cantidadSeleccionada = 2;

    @Dado("estoy en la página de la tienda")
    public void estoyEnLaPaginaDeLaTienda() {
        driver.get("https://bensg.com");
    }

    @Y("me logueo con mi usuario {string} y clave {string}")
    public void meLogueoConMiUsuarioYClave(String usuario, String clave) {
        loginPage.clickIniciarSesion();
        loginPage.login(usuario, clave);
        Assert.assertTrue("Falla en Autenticación: Credenciales incorrectas o página caída.", loginPage.isLoginSuccess());
    }

    @Cuando("navego a la categoria {string} y subcategoria {string}")
    public void navegoALaCategoriaYSubcategoria(String cat, String subCat) {
        try {
            storePage.navegarMenu(cat, subCat);
        } catch (Exception e) {
            Assert.fail("Falla de Navegación: La categoría '" + cat + "' no existe en el sistema.");
        }
    }

    @Y("agrego 2 unidades del primer producto al carrito")
    public void agregoUnidadesDelPrimerProductoAlCarrito() {
        precioUnitario = storePage.seleccionarPrimerProductoYObtenerPrecio(cantidadSeleccionada);
    }

    @Entonces("valido en el popup la confirmación del producto agregado")
    public void validoEnElPopupLaConfirmacionDelProductoAgregado() {
        String tituloPopup = storePage.obtenerTextoPopup();
        Assert.assertTrue(tituloPopup.contains("Producto añadido correctamente"));
    }

    @Y("valido en el popup que el monto total sea calculado correctamente")
    public void validoEnElPopupQueElMontoTotalSeaCalculadoCorrectamente() {
        double totalEsperado = precioUnitario * cantidadSeleccionada;
        double totalObtenido = storePage.obtenerTotalPopup();
        Assert.assertEquals("El cálculo del precio total en el Popup es incorrecto.", totalEsperado, totalObtenido, 0.01);
    }

    @Cuando("finalizo la compra")
    public void finalizoLaCompra() {
        storePage.finalizarCompra();
    }

    @Entonces("valido el titulo de la pagina del carrito")
    public void validoElTituloDeLaPaginaDelCarrito() {
        String titulo = storePage.obtenerTituloCarrito();
        Assert.assertEquals("CARRITO", titulo.toUpperCase());
    }

    @Y("vuelvo a validar el calculo de precios en el carrito")
    public void vuelvoAValidarElCalculoDePreciosEnElCarrito() {
        double totalEsperado = precioUnitario * cantidadSeleccionada;
        double totalObtenido = storePage.obtenerTotalCarrito();
        Assert.assertEquals("El cálculo del precio total en la página del Carrito es incorrecto.", totalEsperado, totalObtenido, 0.01);
    }
}
