package com.pelicula.peliculas.Service;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pelicula.peliculas.DTO.PeliculaDTO;
import com.pelicula.peliculas.model.Pelicula;
import com.pelicula.peliculas.repository.PeliculaRepository;
import com.pelicula.peliculas.service.PeliculaService;
 
import net.datafaker.Faker;
 
@ExtendWith(MockitoExtension.class)
class PeliculasApplicationTests {
 
    @Mock
    private PeliculaRepository peliculaRepository;
 
    @InjectMocks
    private PeliculaService peliculaService;
 
    private Faker faker = new Faker();
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    private Pelicula crearPeliculaFalsa(Integer id) {
        Pelicula pelicula = new Pelicula(
            id,
            faker.book().title().substring(0, Math.min(faker.book().title().length(), 50)),
            faker.lorem().characters(100, 200),
            faker.number().numberBetween(60, 240),
            faker.number().numberBetween(2000, 2025),
            "EN_CARTELERA",
            List.of(),  
            List.of(),  
            List.of(),  
            List.of()   
        );
        return pelicula;
    }
 
    @Test
    void testBuscarPorId_Exitoso() {

        Integer id = 1;
        Pelicula peliculaFalsa = crearPeliculaFalsa(id);
 
        when(peliculaRepository.findById(id)).thenReturn(Optional.of(peliculaFalsa));
 
        PeliculaDTO resultado = peliculaService.buscarPorId(id);

        assertNotNull(resultado, "El DTO resultante no debería ser nulo");
        assertEquals(peliculaFalsa.getTitulo(), resultado.getTitulo());
        assertEquals(peliculaFalsa.getDescripcion(), resultado.getDescripcion());
        assertEquals(peliculaFalsa.getDuracion(), resultado.getDuracion());
        assertEquals(peliculaFalsa.getEstado(), resultado.getEstado());
        verify(peliculaRepository, times(1)).findById(id);
    }
 
    @Test
    void testBuscarPorId_NoExiste_LanzaExcepcion() {

        Integer id = 99;
        when(peliculaRepository.findById(id)).thenReturn(Optional.empty());
 
        assertThrows(RuntimeException.class, () -> peliculaService.buscarPorId(id));
        verify(peliculaRepository, times(1)).findById(id);
    }
 
    @Test
    void testObtenerTodas_RetornaLista() {

        List<Pelicula> peliculas = List.of(
            crearPeliculaFalsa(1),
            crearPeliculaFalsa(2),
            crearPeliculaFalsa(3)
        );
        when(peliculaRepository.findAll()).thenReturn(peliculas);
 
        List<PeliculaDTO> resultado = peliculaService.obtenerTodas();
 
        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        verify(peliculaRepository, times(1)).findAll();
    }
 
    @Test
    void testObtenerTodas_ListaVacia() {

        when(peliculaRepository.findAll()).thenReturn(List.of());
 
        List<PeliculaDTO> resultado = peliculaService.obtenerTodas();
 
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
 
    @Test
    void testGuardarPelicula_Exitoso() {

        Pelicula nueva = crearPeliculaFalsa(null);
        Pelicula guardada = crearPeliculaFalsa(1);
        guardada.setTitulo(nueva.getTitulo());
 
        when(peliculaRepository.save(any(Pelicula.class))).thenReturn(guardada);
 
        PeliculaDTO resultado = peliculaService.guardarPelicula(nueva);
 
        assertNotNull(resultado);
        assertEquals(guardada.getTitulo(), resultado.getTitulo());
        verify(peliculaRepository, times(1)).save(nueva);
    }

 
    @Test
    void testActualizarPelicula_Exitoso() {

        Integer id = 1;
        Pelicula existente = crearPeliculaFalsa(id);
        Pelicula cambios = crearPeliculaFalsa(null);
        Pelicula actualizada = crearPeliculaFalsa(id);
        actualizada.setTitulo(cambios.getTitulo());
 
        when(peliculaRepository.findById(id)).thenReturn(Optional.of(existente));
        when(peliculaRepository.save(any(Pelicula.class))).thenReturn(actualizada);

        PeliculaDTO resultado = peliculaService.actualizarPelicula(id, cambios);
 
        assertNotNull(resultado);
        assertEquals(cambios.getTitulo(), resultado.getTitulo());
        verify(peliculaRepository, times(1)).findById(id);
        verify(peliculaRepository, times(1)).save(existente);
    }
 
    @Test
    void testActualizarPelicula_NoExiste_LanzaExcepcion() {
        Integer id = 99;
        when(peliculaRepository.findById(id)).thenReturn(Optional.empty());
 
        assertThrows(RuntimeException.class,
            () -> peliculaService.actualizarPelicula(id, new Pelicula()));
        verify(peliculaRepository, never()).save(any());
    }
 
 
    @Test
    void testEliminar_Exitoso() {

        Integer id = 1;
        Pelicula pelicula = crearPeliculaFalsa(id);
        when(peliculaRepository.findById(id)).thenReturn(Optional.of(pelicula));

        assertDoesNotThrow(() -> peliculaService.eliminar(id));
        verify(peliculaRepository, times(1)).delete(pelicula);
    }
 
    @Test
    void testEliminar_NoExiste_LanzaExcepcion() {

        Integer id = 99;
        when(peliculaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> peliculaService.eliminar(id));
        verify(peliculaRepository, never()).delete(any());
    }
 
 
    @Test
    void testBuscarPorTitulo_Exitoso() {
        String titulo = "Inception";
        List<Pelicula> peliculas = List.of(crearPeliculaFalsa(1));
        when(peliculaRepository.findByTitulo(titulo)).thenReturn(peliculas);

        List<Pelicula> resultado = peliculaService.buscarPorTitulo(titulo);
 
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(peliculaRepository, times(1)).findByTitulo(titulo);
    }
 
    @Test
    void testBuscarPorTitulo_TituloVacio_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
            () -> peliculaService.buscarPorTitulo(""));
        assertThrows(IllegalArgumentException.class,
            () -> peliculaService.buscarPorTitulo("   "));
        assertThrows(IllegalArgumentException.class,
            () -> peliculaService.buscarPorTitulo(null));
        verifyNoInteractions(peliculaRepository);
    }
 
 
    @Test
    void testConvertirADTO_SinRelaciones_RetornaDefaults() {
        Integer id = 5;
        Pelicula peliculaFalsa = crearPeliculaFalsa(id);
 
        when(peliculaRepository.findById(id)).thenReturn(Optional.of(peliculaFalsa));
 
        PeliculaDTO resultado = peliculaService.buscarPorId(id);
 
        assertEquals("Sin género asignado", resultado.getNombreGeneros());
        assertEquals("Director desconocido", resultado.getNombreDirectores());
        assertEquals("Idioma no definido", resultado.getNombreIdiomas());
        assertEquals("Sala no asignada", resultado.getNombreSalasPelicula());
    }
}
 