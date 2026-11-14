package co.edu.uniquindio.poo.monederovirtualjfx.controller;

import co.edu.uniquindio.poo.monederovirtualjfx.model.MonederoVirtual;
import co.edu.uniquindio.poo.monederovirtualjfx.model.Usuario;

public class HistorialController {
    private final MonederoVirtual monederoVirtual;

    public HistorialController(MonederoVirtual monederoVirtual) {
        this.monederoVirtual = monederoVirtual;
    }
    public String consultar(Usuario usuarioActual, String codigoMonedero){
        return monederoVirtual.consultarHistorialTransacciones(usuarioActual, codigoMonedero);
    }
}
