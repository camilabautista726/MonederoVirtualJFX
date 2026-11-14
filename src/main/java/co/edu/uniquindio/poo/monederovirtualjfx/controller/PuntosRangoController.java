package co.edu.uniquindio.poo.monederovirtualjfx.controller;

import co.edu.uniquindio.poo.monederovirtualjfx.model.Monedero;
import co.edu.uniquindio.poo.monederovirtualjfx.model.MonederoVirtual;
import co.edu.uniquindio.poo.monederovirtualjfx.model.Usuario;

import java.util.Optional;

public class PuntosRangoController {
    private final MonederoVirtual monederoVirtual;

    public PuntosRangoController(MonederoVirtual monederoVirtual) {
        this.monederoVirtual = monederoVirtual;
    }

    public String consultarPuntos (Usuario usuarioActual){
        String puntos=String.valueOf(usuarioActual.getPuntosAcumulados());
        return puntos;
    }
    public String consultarRango (Usuario usuarioActual){
        return usuarioActual.getRango().name();
    }
}
