package com.pelicula.peliculas;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import net.datafaker.Faker;

import com.pelicula.peliculas.model.Director;
import com.pelicula.peliculas.model.Directores;
import com.pelicula.peliculas.model.Genero;
import com.pelicula.peliculas.model.Generos;
import com.pelicula.peliculas.model.Idioma;
import com.pelicula.peliculas.model.Idiomas;
import com.pelicula.peliculas.model.Pelicula;
import com.pelicula.peliculas.model.SalasPelicula;
import com.pelicula.peliculas.repository.DirectorRepository;
import com.pelicula.peliculas.repository.DirectoresRepository;
import com.pelicula.peliculas.repository.GeneroRepository;
import com.pelicula.peliculas.repository.GenerosRepository;
import com.pelicula.peliculas.repository.IdiomaRepository;
import com.pelicula.peliculas.repository.IdiomasRepository;
import com.pelicula.peliculas.repository.PeliculaRepository;
import com.pelicula.peliculas.repository.SalasPeliculaRepository;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private IdiomaRepository idiomaRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private DirectoresRepository directoresRepository;

    @Autowired
    private GenerosRepository generosRepository;

    @Autowired
    private IdiomasRepository idiomasRepository;

    @Autowired
    private SalasPeliculaRepository salasPeliculaRepository;

    @Override
    public void run(String... args) throws Exception {

        if (peliculaRepository.count() > 0) {
            return;
        }

        Faker faker = new Faker();
        Random random = new Random();

        // 1. Directores (nombre requiere entre 20 y 50 caracteres)
        String[] nacionalidades = {"Chilena", "Estadounidense", "Francesa", "Británica", "Japonesa", "Mexicana", "Argentina", "Italiana"};

        for (int i = 0; i < 8; i++) {
            Director director = new Director();

            String nombre = faker.name().firstName() + " " + faker.name().lastName();
            while (nombre.length() < 20) {
                nombre += " " + faker.name().lastName();
            }
            if (nombre.length() > 50) {
                nombre = nombre.substring(0, 50);
            }
            director.setNombre(nombre);
            director.setNacionalidad(nacionalidades[random.nextInt(nacionalidades.length)]);

            directorRepository.save(director);
        }

        // 2. Géneros (nombre entre 10 y 50 caracteres)
        String[] generos = {"Ciencia Ficción", "Comedia Romántica", "Acción y Aventura", "Drama Histórico", "Terror Psicológico", "Animación Infantil", "Suspenso Policial"};
        for (String nombreGenero : generos) {
            Genero genero = new Genero();
            genero.setNombre(nombreGenero);
            generoRepository.save(genero);
        }

        // 3. Idiomas (nombre entre 10 y 20 caracteres)
        String[] idiomas = {"Español Latino", "Inglés Original", "Francés Doblado", "Alemán Original", "Coreano Subtit.", "Japonés Original"};
        for (String nombreIdioma : idiomas) {
            Idioma idioma = new Idioma();
            idioma.setNombre(nombreIdioma);
            idiomaRepository.save(idioma);
        }

        List<Director> listaDirectores = directorRepository.findAll();
        List<Genero> listaGeneros = generoRepository.findAll();
        List<Idioma> listaIdiomas = idiomaRepository.findAll();

        String[] estados = {"CARTELERA", "PROXIMAMENTE", "FINALIZADA"};

        // 4. Películas + relaciones puente
        for (int i = 0; i < 15; i++) {
            Pelicula pelicula = new Pelicula();

            String titulo = faker.book().title();
            if (titulo.length() > 50) {
                titulo = titulo.substring(0, 50);
            }
            if (titulo.length() < 4) {
                titulo = titulo + " Film";
            }
            pelicula.setTitulo(titulo);

            String descripcion = faker.lorem().paragraph(3);
            while (descripcion.length() < 100) {
                descripcion += " " + faker.lorem().sentence();
            }
            if (descripcion.length() > 250) {
                descripcion = descripcion.substring(0, 250);
            }
            pelicula.setDescripcion(descripcion);

            pelicula.setDuracion(faker.number().numberBetween(60, 240));
            pelicula.setAnioEstreno(faker.number().numberBetween(1990, 2026));
            pelicula.setEstado(estados[random.nextInt(estados.length)]);

            pelicula = peliculaRepository.save(pelicula);

            Directores directores = new Directores();
            directores.setDirector(listaDirectores.get(random.nextInt(listaDirectores.size())));
            directores.setPelicula(pelicula);
            directoresRepository.save(directores);

            Generos generosPuente = new Generos();
            generosPuente.setGenero(listaGeneros.get(random.nextInt(listaGeneros.size())));
            generosPuente.setPelicula(pelicula);
            generosRepository.save(generosPuente);

            Idiomas idiomasPuente = new Idiomas();
            idiomasPuente.setIdioma(listaIdiomas.get(random.nextInt(listaIdiomas.size())));
            idiomasPuente.setPelicula(pelicula);
            idiomasRepository.save(idiomasPuente);

            // Referencia cruzada al microservicio de salas (sin FK real, solo el id)
            SalasPelicula salasPelicula = new SalasPelicula();
            salasPelicula.setPelicula(pelicula);
            salasPelicula.setSalaId(faker.number().numberBetween(1, 10));
            salasPeliculaRepository.save(salasPelicula);
        }
    }
}