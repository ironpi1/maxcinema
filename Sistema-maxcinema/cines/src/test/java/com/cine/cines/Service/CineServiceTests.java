package com.cine.cines.Service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.cine.cines.DTO.CineDTO;
import com.cine.cines.model.Cine;
import com.cine.cines.repository.CineRepository;
import com.cine.cines.service.CineService;

import net.datafaker.Faker;


@ExtendWith(MockitoExtension.class)
class CineServiceTests {
    
    @Mock
    private CineRepository cineRepository;

    @InjectMocks
    private CineService cineService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Cine crearCineFalso(Integer id) {
        Cine cine = new Cine();
        cine.setId(id);
        cine.setNombre(faker.lorem().characters(5, 12));
        cine.setDireccion(faker.address().fullAddress());
        return cine;
    }

    private CineDTO crearCineDTOFalso() {
        CineDTO dto = new CineDTO();
        dto.setNombre(faker.lorem().characters(5, 12));
        dto.setDireccion(faker.address().fullAddress());
        return dto;
    }

    // Buscar por ID

    @Test
    void testBuscarPorId_Exitoso() {
        Integer id = 1;
        Cine cineFalso = crearCineFalso(id);

        when(cineRepository.findById(id)).thenReturn(Optional.of(cineFalso));

        CineDTO resultado = cineService.buscarCinePorId(id);

        assertNotNull(resultado);
        assertEquals(cineFalso.getNombre(), resultado.getNombre());
        assertEquals(cineFalso.getDireccion(), resultado.getDireccion());
        verify(cineRepository, times(1)).findById(id);
    }

    @Test
    void testBuscarPorId_NoExiste_LanzaExcepcion() {
        Integer id = 99;
        when(cineRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cineService.buscarCinePorId(id));
        verify(cineRepository, times(1)).findById(id);
    }

    // Listar cines

    @Test
    void testListarCines_RetornaLista() {
        List<Cine> cines = List.of(crearCineFalso(1), crearCineFalso(2), crearCineFalso(3));
        when(cineRepository.findAll()).thenReturn(cines);

        List<CineDTO> resultado = cineService.listarCine();

        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        verify(cineRepository, times(1)).findAll();
    }

    @Test
    void testListarCines_ListaVacia() {
        when(cineRepository.findAll()).thenReturn(List.of());

        List<CineDTO> resultado = cineService.listarCine();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // Guardar cine

    @Test
    void testGuardarCine_Exitoso() {
        CineDTO dto = crearCineDTOFalso();
        Cine guardado = crearCineFalso(1);
        guardado.setNombre(dto.getNombre());
        guardado.setDireccion(dto.getDireccion());

        when(cineRepository.save(any(Cine.class))).thenReturn(guardado);

        CineDTO resultado = cineService.guardarCine(dto);

        assertNotNull(resultado);
        assertEquals(dto.getNombre(), resultado.getNombre());
        verify(cineRepository, times(1)).save(any(Cine.class));
    }

    // Actualizar cine

    @Test
    void testActualizarCine_Exitoso() {
        Integer id = 1;
        Cine existente = crearCineFalso(id);
        CineDTO dto = crearCineDTOFalso();
        Cine actualizado = crearCineFalso(id);
        actualizado.setNombre(dto.getNombre());
        actualizado.setDireccion(dto.getDireccion());

        when(cineRepository.findById(id)).thenReturn(Optional.of(existente));
        when(cineRepository.save(any(Cine.class))).thenReturn(actualizado);

        CineDTO resultado = cineService.actualizarCine(id, dto);

        assertNotNull(resultado);
        verify(cineRepository, times(1)).save(existente);
    }

    @Test
    void testActualizarCine_NoExiste_LanzaExcepcion() {
        Integer id = 99;
        when(cineRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
            () -> cineService.actualizarCine(id, new CineDTO()));
    }

   // Eliminar cine

    @Test
    void testEliminarCine_Exitoso() {
        Integer id = 1;
        Cine cine = crearCineFalso(id);
        when(cineRepository.findById(id)).thenReturn(Optional.of(cine));

        assertDoesNotThrow(() -> cineService.eliminarCine(id));
        verify(cineRepository, times(1)).delete(cine);
    }

    @Test
    void testEliminarCine_NoExiste_LanzaExcepcion() {
        Integer id = 99;
        when(cineRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cineService.eliminarCine(id));
    }
}
