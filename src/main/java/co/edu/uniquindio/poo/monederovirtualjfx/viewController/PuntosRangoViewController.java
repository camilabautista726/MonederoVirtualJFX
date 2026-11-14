package co.edu.uniquindio.poo.monederovirtualjfx.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.monederovirtualjfx.app.Aplicacion;
import co.edu.uniquindio.poo.monederovirtualjfx.controller.PuntosRangoController;
import co.edu.uniquindio.poo.monederovirtualjfx.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class PuntosRangoViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnVolver;

    @FXML
    private Label lblDetalle;

    @FXML
    private Label lblPuntos;

    @FXML
    private Label lblRango;

    @FXML
    private TextArea txtResultado;
    private Aplicacion aplicacion;
    private PuntosRangoController puntosRangoController;

    @FXML
    void actualizar(ActionEvent event) {
        cargarDatos();
    }

    @FXML
    void volver(ActionEvent event) {
        aplicacion.openViewMenu();
    }
    public void setApp(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
        this.puntosRangoController =
                new PuntosRangoController(aplicacion.monederoVirtual);
        cargarDatos();
    }
    private void cargarDatos() {
        Usuario usuario = Aplicacion.getUsuarioActual();

        if (usuario == null) {
            lblPuntos.setText("-");
            lblRango.setText("-");
            return;
        }
        String resultado1= puntosRangoController.consultarPuntos(usuario);
        String resultado2= puntosRangoController.consultarRango(usuario);
        lblPuntos.setText(resultado1);
        lblRango.setText(resultado2);
        lblDetalle.setText("Usuario: " + usuario.getNombreCompleto());

        txtResultado.setText(
                "Puntos acumulados: " + usuario.getPuntosAcumulados()
                        + "\nRango actual: " + usuario.getRango().name()
                        + "\nMonederos registrados: " + usuario.getListaMonederos().size());

    }

    @FXML
    void initialize() {
        assert btnActualizar != null : "fx:id=\"btnActualizar\" was not injected: check your FXML file 'PuntosRango.fxml'.";
        assert btnVolver != null : "fx:id=\"btnVolver\" was not injected: check your FXML file 'PuntosRango.fxml'.";
        assert lblDetalle != null : "fx:id=\"lblDetalle\" was not injected: check your FXML file 'PuntosRango.fxml'.";
        assert lblPuntos != null : "fx:id=\"lblPuntos\" was not injected: check your FXML file 'PuntosRango.fxml'.";
        assert lblRango != null : "fx:id=\"lblRango\" was not injected: check your FXML file 'PuntosRango.fxml'.";
        assert txtResultado != null : "fx:id=\"txtResultado\" was not injected: check your FXML file 'PuntosRango.fxml'.";

    }

}
