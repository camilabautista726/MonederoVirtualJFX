package co.edu.uniquindio.poo.monederovirtualjfx.controller;

import co.edu.uniquindio.poo.monederovirtualjfx.model.*;

import java.time.LocalDate;
import java.util.Optional;

public class ProgramarTransaccionController {
    private final MonederoVirtual monederoVirtual;

    public ProgramarTransaccionController(MonederoVirtual monederoVirtual) {
        this.monederoVirtual = monederoVirtual;
    }

    public Optional<Monedero> buscarMonedero(String codigo) {
        return monederoVirtual.buscarMonederoPorCodigo(codigo);
    }

    public boolean existeMonedero(String codigo) {
        return buscarMonedero(codigo).isPresent();
    }

    public String programar(Usuario usuarioActual, String tipo, String codigoMonedero, String codigoDestinatario, double monto, LocalDate fecha, Frecuencia frecuencia)
            throws MontoInvalidoException, SaldoInsuficienteException {
        if (!existeMonedero(codigoMonedero)) {
            throw new IllegalArgumentException("No existe un monedero con ese código");
        }
        Monedero monedero = monederoVirtual.buscarMonederoPorCodigo(codigoMonedero).get();
        if ("Deposito".equals(tipo)) {
            return monederoVirtual.depositar(usuarioActual, monedero, monto, fecha, true, frecuencia);
        }
        if (!existeMonedero(codigoDestinatario)) {
            throw new IllegalArgumentException("No existe un monedero con ese código para realizar la transferencia");
        }
        Monedero monederoDestinatario = monederoVirtual.buscarMonederoPorCodigo(codigoDestinatario).get();
        if ("Transferencia".equals(tipo)) {
            if (monederoDestinatario == monedero) {
                throw new IllegalArgumentException("No puedes transferir al mismo monedero");
            }
            return monederoVirtual.transferir(usuarioActual, monedero, monto,
                    codigoDestinatario, fecha, true, frecuencia);
        }
        throw new IllegalArgumentException("Seleccione el tipo de transacción");
    }
}
