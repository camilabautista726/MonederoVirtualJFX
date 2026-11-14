package co.edu.uniquindio.poo.monederovirtualjfx.controller;

import co.edu.uniquindio.poo.monederovirtualjfx.model.Monedero;
import co.edu.uniquindio.poo.monederovirtualjfx.model.MonederoVirtual;
import co.edu.uniquindio.poo.monederovirtualjfx.model.Usuario;

import java.util.Optional;

public class NotificacionesController {
    private final MonederoVirtual monederoVirtual;

    public NotificacionesController(MonederoVirtual monederoVirtual) {
        this.monederoVirtual = monederoVirtual;
    }
    public Optional<Monedero> buscarMonedero(String codigo){
        return monederoVirtual.buscarMonederoPorCodigo(codigo);
    }
    public boolean existeMonedero (String codigo){
        return buscarMonedero(codigo).isPresent();
    }
    public String consultar(Usuario usuarioActual, String codigoMonedero){
        if(!existeMonedero(codigoMonedero)) {
            throw new IllegalArgumentException("No existe un monedero con ese código");
        }
        return monederoVirtual.consultarNotificaciones(usuarioActual, codigoMonedero);
    }
}
