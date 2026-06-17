package com.pelicula.peliculas.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pelicula.peliculas.DTO.PeliculaDTO;
import com.pelicula.peliculas.repository.PeliculaRepository;
import com.pelicula.peliculas.model.Pelicula;
import com.pelicula.peliculas.service.PeliculaService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
class PeliculasApplicationTests {

	@Mock
	private PeliculaRepository peliculaRepository; 
	
	@InjectMocks
	private PeliculaService peliculaService; 
	private Faker faker = new Faker(); // Nuestro generador de datos
	@BeforeEach
	void setUp() {
		
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testBuscarPorId_Exitoso() {

		Integer idSimulado = 1;
		String tituloAleatorio = faker.book().title(); // Genera títulos de películas aleatorios de prueba
		Pelicula peliculaFalsa = new Pelicula();
		peliculaFalsa.setId(idSimulado);
		peliculaFalsa.setTitulo(tituloAleatorio);
		peliculaFalsa.setDescripcion(faker.lorem().characters(100, 200));
		peliculaFalsa.setDuracion(faker.number().numberBetween(60, 240));
		peliculaFalsa.setAnioEstreno(faker.number().numberBetween(2000, 2025));
		peliculaFalsa.setEstado("EN_CARTELERA");

		when(peliculaRepository.findById(idSimulado)).thenReturn(Optional.of(peliculaFalsa));

		PeliculaDTO resultado = peliculaService.buscarPorId(idSimulado);
        
		assertNotNull(resultado, "El DTO resultante no debería ser nulo");
		assertEquals(tituloAleatorio, resultado.getTitulo(), "El título transformado al DTO debe coincidir con el de la DB");

		verify(peliculaRepository, times(1)).findById(idSimulado);
	}

}