package com.sala.salas.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sala.salas.DTO.SalaDTO;
import com.sala.salas.model.Sala;
import com.sala.salas.repository.SalaRepository;
import com.sala.salas.service.SalaService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
class SalaServiceTest {

    @Mock
    private SalaRepository salaRepository;

    @InjectMocks
    private SalaService salaService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Sala crearSalaFalsa(Integer id) {
        Sala sala = new Sala();
        sala.setId(id);
        sala.setNombre(faker.lorem().characters(5, 12));
        sala.setNumeroDeSala(faker.number().numberBetween(1, 25));
        return sala;
    }

    private SalaDTO crearSalaDTOFalso() {
        SalaDTO dto = new SalaDTO();
        dto.setNombre(faker.lorem().characters(5, 12));
        dto.setNumeroDeSala(faker.number().numberBetween(1, 25));
        return dto;
    }

    // Buscar por ID

    @Test
    void testBuscarPorId_Exitoso() {
        Integer id = 1;
        Sala salaFalsa = crearSalaFalsa(id);

        when(salaRepository.findById(id)).thenReturn(Optional.of(salaFalsa));

        SalaDTO resultado = salaService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(salaFalsa.getNombre(), resultado.getNombre());
        assertEquals(salaFalsa.getNumeroDeSala(), resultado.getNumeroDeSala());
        verify(salaRepository, times(1)).findById(id);
    }

    @Test
    void testBuscarPorId_NoExiste_LanzaExcepcion() {
        Integer id = 99;
        when(salaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> salaService.buscarPorId(id));
        verify(salaRepository, times(1)).findById(id);
    }

    // Listar salas

    @Test
    void testListarSalas_RetornaLista() {
        List<Sala> salas = List.of(crearSalaFalsa(1), crearSalaFalsa(2), crearSalaFalsa(3));
        when(salaRepository.findAll()).thenReturn(salas);

        List<SalaDTO> resultado = salaService.listarSalas();

        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        verify(salaRepository, times(1)).findAll();
    }

    @Test
    void testListarSalas_ListaVacia() {
        when(salaRepository.findAll()).thenReturn(List.of());

        List<SalaDTO> resultado = salaService.listarSalas();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // Guardar sala

    @Test
    void testGuardarSala_Exitoso() {
        SalaDTO dto = crearSalaDTOFalso();
        Sala guardada = crearSalaFalsa(1);
        guardada.setNombre(dto.getNombre());
        guardada.setNumeroDeSala(dto.getNumeroDeSala());

        when(salaRepository.save(any(Sala.class))).thenReturn(guardada);

        SalaDTO resultado = salaService.guardarSala(dto);

        assertNotNull(resultado);
        assertEquals(dto.getNombre(), resultado.getNombre());
        verify(salaRepository, times(1)).save(any(Sala.class));
    }

    // Actualizar sala

    @Test
    void testActualizarSala_Exitoso() {
        Integer id = 1;
        Sala existente = crearSalaFalsa(id);
        SalaDTO dto = crearSalaDTOFalso();
        Sala actualizada = crearSalaFalsa(id);
        actualizada.setNombre(dto.getNombre());

        when(salaRepository.findById(id)).thenReturn(Optional.of(existente));
        when(salaRepository.save(any(Sala.class))).thenReturn(actualizada);

        SalaDTO resultado = salaService.actualizarSala(id, dto);

        assertNotNull(resultado);
        verify(salaRepository, times(1)).save(existente);
    }

    @Test
    void testActualizarSala_NoExiste_LanzaExcepcion() {
        Integer id = 99;
        when(salaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
            () -> salaService.actualizarSala(id, new SalaDTO()));
    }

   // Eliminar sala

    @Test
    void testEliminarSala_Exitoso() {
        Integer id = 1;
        Sala sala = crearSalaFalsa(id);
        when(salaRepository.findById(id)).thenReturn(Optional.of(sala));

        assertDoesNotThrow(() -> salaService.eliminarSala(id));
        verify(salaRepository, times(1)).delete(sala);
    }

    @Test
    void testEliminarSala_NoExiste_LanzaExcepcion() {
        Integer id = 99;
        when(salaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> salaService.eliminarSala(id));
    }
}
