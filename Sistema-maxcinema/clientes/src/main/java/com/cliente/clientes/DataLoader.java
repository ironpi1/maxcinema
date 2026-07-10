package com.cliente.clientes;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import net.datafaker.Faker;

import com.cliente.clientes.model.Cliente;
import com.cliente.clientes.model.Entrada;
import com.cliente.clientes.model.MetodoPago;
import com.cliente.clientes.model.TipoCliente;
import com.cliente.clientes.repository.ClienteRepository;
import com.cliente.clientes.repository.EntradaRepository;
import com.cliente.clientes.repository.MetodoPagoRepository;
import com.cliente.clientes.repository.TipoClienteRepository;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private TipoClienteRepository tipoClienteRepository;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EntradaRepository entradaRepository;

    @Override
    public void run(String... args) throws Exception {

        if (tipoClienteRepository.count() > 0) {
            return;
        }

        Faker faker = new Faker();
        Random random = new Random();

        // 1. Tipos de cliente
        String[] tipos = {"Regular", "Estudiante", "Adulto Mayor", "VIP"};
        Float[] descuentos = {0.0f, 0.15f, 0.20f, 0.10f};

        for (int i = 0; i < tipos.length; i++) {
            TipoCliente tipoCliente = new TipoCliente();
            tipoCliente.setTipo(tipos[i]);
            tipoCliente.setDescuento(descuentos[i]);
            tipoClienteRepository.save(tipoCliente);
        }

        // 2. Métodos de pago
        String[] formasPago = {"Efectivo", "Tarjeta Débito", "Tarjeta Crédito", "Transferencia", "Webpay"};
        for (String forma : formasPago) {
            MetodoPago metodoPago = new MetodoPago();
            metodoPago.setTipoPago(forma);
            metodoPagoRepository.save(metodoPago);
        }

        List<TipoCliente> listaTipos = tipoClienteRepository.findAll();
        List<MetodoPago> listaMetodos = metodoPagoRepository.findAll();

        // 3. Clientes
        for (int i = 0; i < 25; i++) {
            Cliente cliente = new Cliente();

            String nombreCompleto = faker.name().firstName() + " " + faker.name().lastName();
            cliente.setNombre(nombreCompleto);

            String digitosRut = String.valueOf(faker.number().numberBetween(1000000, 25000000));
            String dv = faker.options().option("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "K");
            cliente.setRut(digitosRut + "-" + dv);

            cliente.setEmail(faker.internet().emailAddress());

            cliente.setTelefono("+569" + faker.number().digits(8));

            cliente.setTipoCliente(listaTipos.get(random.nextInt(listaTipos.size())));

            clienteRepository.save(cliente);
        }

        List<Cliente> listaClientes = clienteRepository.findAll();

        // 4. Entradas
        String[] tiposEntrada = {"General", "VIP", "Estudiante", "Preventa", "Niño"};

        for (int i = 0; i < 30; i++) {
            Entrada entrada = new Entrada();

            entrada.setTipoEntrada(tiposEntrada[random.nextInt(tiposEntrada.length)]);
            entrada.setPrecio((float) faker.number().numberBetween(2500, 9000));
            entrada.setCantidad(faker.number().numberBetween(1, 4));

            int hora = faker.number().numberBetween(12, 23);
            entrada.setHorario(String.format("%02d:00 - Sala %d", hora, faker.number().numberBetween(1, 10)));

            entrada.setPeliculaId(faker.number().numberBetween(1, 30));

            entrada.setMetodoPago(listaMetodos.get(random.nextInt(listaMetodos.size())));
            entrada.setCliente(listaClientes.get(random.nextInt(listaClientes.size())));

            entradaRepository.save(entrada);
        }
    }
}
