package co.edu.uniquindio.poo.monederovirtualjfx.controller;

import co.edu.uniquindio.poo.monederovirtualjfx.model.Monedero;
import co.edu.uniquindio.poo.monederovirtualjfx.model.MonederoVirtual;
import co.edu.uniquindio.poo.monederovirtualjfx.model.PuntosInsuficientesException;
import co.edu.uniquindio.poo.monederovirtualjfx.model.Usuario;

import java.util.Optional;

public class BeneficiosController {
    private final MonederoVirtual monederoVirtual;

    public BeneficiosController(MonederoVirtual monederoVirtual) {
        this.monederoVirtual = monederoVirtual;
    }
    public Optional<Monedero> buscarMonedero(String codigoDestinatario){
        return monederoVirtual.buscarMonederoPorCodigo(codigoDestinatario);
    }
    public boolean existeMonedero (String codigo){
        return buscarMonedero(codigo).isPresent();
    }
    public String canjear1000 (Usuario usuarioActual, String codigoMonedero)
    throws PuntosInsuficientesException {
        if(!existeMonedero(codigoMonedero)) {
            throw new IllegalArgumentException("No existe un monedero con ese código");
        }
        Monedero monedero=monederoVirtual.buscarMonederoPorCodigo(codigoMonedero).get();
        return monederoVirtual.aplicarbeneficio1000(usuarioActual, monedero);

    }
    public String canjear500 (Usuario usuarioActual)
            throws PuntosInsuficientesException {
        return monederoVirtual.aplicarbeneficio500(usuarioActual);
    }
    public String canjear100 (Usuario usuarioActual)
            throws PuntosInsuficientesException {
        return monederoVirtual.aplicarbeneficio100(usuarioActual);
    }
}
