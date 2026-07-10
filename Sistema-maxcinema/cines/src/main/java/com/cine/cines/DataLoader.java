package com.cine.cines;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import net.datafaker.Faker;

import com.cine.cines.model.Cine;
import com.cine.cines.model.Comuna;
import com.cine.cines.model.Region;
import com.cine.cines.repository.CineRepository;
import com.cine.cines.repository.ComunaRepository;
import com.cine.cines.repository.RegionRepository;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private CineRepository cineRepository;

    @Override
    public void run(String... args) throws Exception {

        if (regionRepository.count() > 0) {
            return;
        }

        Faker faker = new Faker();
        Random random = new Random();

        String[] regiones = {
                "Región Metropolitana",
                "Región de Valparaíso",
                "Región del Biobío",
                "Región de Coquimbo",
                "Región de Los Lagos"
        };

        for (String nombreRegion : regiones) {
            Region region = new Region();
            region.setNombre(nombreRegion);
            regionRepository.save(region);
        }

        List<Region> listaRegiones = regionRepository.findAll();

        String[] comunasMetropolitana = {"Santiago", "Providencia", "Las Condes", "Puente Alto", "La Florida"};
        String[] comunasValparaiso = {"Valparaíso", "Viña del Mar", "Quilpué", "San Antonio"};
        String[] comunasBiobio = {"Concepción", "Talcahuano", "Chiguayante"};
        String[] comunasCoquimbo = {"La Serena", "Coquimbo", "Ovalle"};
        String[] comunasLosLagos = {"Puerto Montt", "Osorno", "Castro"};

        for (Region region : listaRegiones) {
            String[] nombresComunas;
            switch (region.getNombre()) {
                case "Región Metropolitana" -> nombresComunas = comunasMetropolitana;
                case "Región de Valparaíso" -> nombresComunas = comunasValparaiso;
                case "Región del Biobío" -> nombresComunas = comunasBiobio;
                case "Región de Coquimbo" -> nombresComunas = comunasCoquimbo;
                default -> nombresComunas = comunasLosLagos;
            }

            for (String nombreComuna : nombresComunas) {
                Comuna comuna = new Comuna();
                comuna.setNombre(nombreComuna);
                comuna.setRegion(region);
                comunaRepository.save(comuna);
            }
        }

        List<Comuna> listaComunas = comunaRepository.findAll();

        String[] nombresCines = {"CineMax", "CineSur", "CineNorte", "CineCentro", "CineWest", "CinePlus", "CineVIP", "CineStar"};

        for (int i = 0; i < 10; i++) {
            Cine cine = new Cine();
            cine.setNombre(nombresCines[random.nextInt(nombresCines.length)]);

            String direccion = faker.address().streetName();
            if (direccion.length() > 20) {
                direccion = direccion.substring(0, 20);
            }
            if (direccion.length() < 3) {
                direccion = direccion + " 123";
            }
            cine.setDireccion(direccion);

            cine.setComuna(listaComunas.get(random.nextInt(listaComunas.size())));

            cineRepository.save(cine);
        }
    }
}
