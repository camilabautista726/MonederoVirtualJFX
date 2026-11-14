package co.edu.uniquindio.poo.monederovirtualjfx.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.monederovirtualjfx.app.Aplicacion;
import co.edu.uniquindio.poo.monederovirtualjfx.controller.BeneficiosController;
import co.edu.uniquindio.poo.monederovirtualjfx.model.Monedero;
import co.edu.uniquindio.poo.monederovirtualjfx.model.PuntosInsuficientesException;
import co.edu.uniquindio.poo.monederovirtualjfx.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class BeneficiosViewController {
    private static final String ESTILO_MENSAJE_ERROR = "-fx-text-fill: #b51515; -fx-font-size: 12px;";
    private static final String ESTILO_MENSAJE_EXITO = "-fx-text-fill: #16A34A; -fx-font-size: 12px;";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCanjear100;

    @FXML
    private Button btnCanjear1000;

    @FXML
    private Button btnCanjear500;

    @FXML
    private Button btnVolver;

    @FXML
    private ComboBox<String> cmbMonedero;

    @FXML
    private Label lblMensaje;
    private Aplicacion aplicacion;
    private BeneficiosController beneficiosController;

    @FXML
    void canjear100(ActionEvent event) {
        Usuario usuario = Aplicacion.getUsuarioActual();
        if (usuario == null) {
            mostrarMensaje("No hay una sesión activa.", true);
            return;
        }
        try {
            String resultado= beneficiosController.canjear100(usuario);
            mostrarMensaje(resultado, false);
        } catch (PuntosInsuficientesException e) {
            mostrarMensaje(e.getMessage(), true);
        } catch (IllegalArgumentException e) {
            mostrarMensaje(e.getMessage(), true);
        }

    }

    @FXML
    void canjear1000(ActionEvent event) {
        Usuario usuario = Aplicacion.getUsuarioActual();
        if (usuario == null) {
            mostrarMensaje("No hay una sesión activa.", true);
            return;
        }
        String codigoMonedero = cmbMonedero.getValue();

        if ( codigoMonedero == null) {
            mostrarMensaje("Ingrese el monedero para canjear el beneficio1000.", true);
            return;
        }
        try {
            String resultado= beneficiosController.canjear1000(usuario, codigoMonedero);
            mostrarMensaje(resultado, false);
        } catch (PuntosInsuficientesException e) {
            mostrarMensaje(e.getMessage(), true);
        } catch (IllegalArgumentException e) {
            mostrarMensaje(e.getMessage(), true);
        }
    }

    @FXML
    void canjear500(ActionEvent event) {
        Usuario usuario = Aplicacion.getUsuarioActual();
        if (usuario == null) {
            mostrarMensaje("No hay una sesión activa.", true);
            return;
        }
        try {
            String resultado = beneficiosController.canjear500(usuario);
            mostrarMensaje(resultado, false);
        } catch (PuntosInsuficientesException e) {
            mostrarMensaje(e.getMessage(), true);
        } catch (IllegalArgumentException e) {
            mostrarMensaje(e.getMessage(), true);
        }
    }

    @FXML
    void volver(ActionEvent event) {
        aplicacion.openViewMenu();
    }
    public void setApp(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
        this.beneficiosController =
                new BeneficiosController(aplicacion.monederoVirtual);
        cargarMonederos();
    }
    private void mostrarMensaje(String mensaje, boolean error) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle(error ? ESTILO_MENSAJE_ERROR : ESTILO_MENSAJE_EXITO);
    }
    private void cargarMonederos() {
        cmbMonedero.getItems().clear();
        Usuario usuario = Aplicacion.getUsuarioActual();
        if (usuario == null) {
            return;
        }
        for (Monedero monedero : usuario.getListaMonederos()) {
            cmbMonedero.getItems().add(monedero.getCodigo());
        }
    }

    @FXML
    void initialize() {
        assert btnCanjear100 != null : "fx:id=\"btnCanjear100\" was not injected: check your FXML file 'Beneficios.fxml'.";
        assert btnCanjear1000 != null : "fx:id=\"btnCanjear1000\" was not injected: check your FXML file 'Beneficios.fxml'.";
        assert btnCanjear500 != null : "fx:id=\"btnCanjear500\" was not injected: check your FXML file 'Beneficios.fxml'.";
        assert btnVolver != null : "fx:id=\"btnVolver\" was not injected: check your FXML file 'Beneficios.fxml'.";
        assert cmbMonedero != null : "fx:id=\"cmbMonedero\" was not injected: check your FXML file 'Beneficios.fxml'.";
        assert lblMensaje != null : "fx:id=\"lblMensaje\" was not injected: check your FXML file 'Beneficios.fxml'.";

    }

}

