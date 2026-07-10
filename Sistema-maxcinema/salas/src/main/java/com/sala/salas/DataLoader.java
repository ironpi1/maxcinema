package com.sala.salas;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import net.datafaker.Faker;

import com.sala.salas.model.Asiento;
import com.sala.salas.model.Sala;
import com.sala.salas.model.TipoAsiento;
import com.sala.salas.model.TipoSala;
import com.sala.salas.model.TiposSalas;
import com.sala.salas.repository.AsientoRepository;
import com.sala.salas.repository.SalaRepository;
import com.sala.salas.repository.TipoAsientoRepository;
import com.sala.salas.repository.TipoSalaRepository;
import com.sala.salas.repository.TiposSalasRepository;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private TipoSalaRepository tipoSalaRepository;

    @Autowired
    private TipoAsientoRepository tipoAsientoRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private TiposSalasRepository tiposSalasRepository;

    @Autowired
    private AsientoRepository asientoRepository;

    @Override
    public void run(String... args) throws Exception {

        if (salaRepository.count() > 0) {
            return;
        }

        Faker faker = new Faker();
        Random random = new Random();

        // 1. Tipos de sala (min 3, max 17 caracteres)
        String[] tiposSala = {"Sala 2D", "Sala 3D", "Sala 4DX", "IMAX", "VIP"};
        for (String tipo : tiposSala) {
            TipoSala tipoSala = new TipoSala();
            tipoSala.setNombre(tipo);
            tipoSalaRepository.save(tipoSala);
        }

        // 2. Tipos de asiento
        String[] tiposAsiento = {"Estándar", "Premium", "VIP", "Discapacitado", "Pareja"};
        for (String tipo : tiposAsiento) {
            TipoAsiento tipoAsiento = new TipoAsiento();
            tipoAsiento.setTipo(tipo);
            tipoAsientoRepository.save(tipoAsiento);
        }

        List<TipoSala> listaTiposSala = tipoSalaRepository.findAll();
        List<TipoAsiento> listaTiposAsiento = tipoAsientoRepository.findAll();

        // 3. Salas
        for (int i = 0; i < 8; i++) {
            Sala sala = new Sala();
            sala.setNombre("Sala " + faker.company().name());
            if (sala.getNombre().length() > 50) {
                sala.setNombre(sala.getNombre().substring(0, 50));
            }
            sala.setNumeroDeSala(i + 1);
            salaRepository.save(sala);
        }

        List<Sala> listaSalas = salaRepository.findAll();

        // 4. Tabla puente Sala <-> TipoSala (min 3, max 14 caracteres)
        String[] etiquetasTiposSalas = {"Estándar", "Premium", "VIP", "Deluxe", "Estreno"};

        for (Sala sala : listaSalas) {
            TiposSalas tiposSalas = new TiposSalas();
            tiposSalas.setNombre(etiquetasTiposSalas[random.nextInt(etiquetasTiposSalas.length)]);
            tiposSalas.setSala(sala);
            tiposSalas.setTipoSala(listaTiposSala.get(random.nextInt(listaTiposSala.size())));
            tiposSalasRepository.save(tiposSalas);
        }

        // 5. Asientos (el modelo Asiento no tiene relación directa con Sala,
        // así que se generan como catálogo general: 10 filas x 8 columnas)
        String[] columnas = {"A", "B", "C", "D", "E", "F", "G", "H"};

        for (int fila = 1; fila <= 10; fila++) {
            for (String columna : columnas) {
                Asiento asiento = new Asiento();
                asiento.setFila(fila);
                asiento.setColumna(columna);
                asiento.setEstado(true);
                asiento.setTipoAsiento(listaTiposAsiento.get(random.nextInt(listaTiposAsiento.size())));
                asientoRepository.save(asiento);
            }
        }
    }
}