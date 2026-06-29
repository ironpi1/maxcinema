package com.cliente.clientes.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cliente.clientes.DTO.EntradasDTO;
import com.cliente.clientes.model.Cliente;
import com.cliente.clientes.model.Entrada;
import com.cliente.clientes.repository.EntradaRepository;
import com.cliente.clientes.service.EntradaService;
 
 
@ExtendWith(MockitoExtension.class)
class EntradaServiceTests {
 
    @Mock
    private EntradaRepository entradaRepository;
 
    @InjectMocks
    private EntradaService entradaService;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    private Entrada buildEntradaFalsa(Integer id, String tipo, Float precio, Integer cantidad, String horario, Integer peliculaId) {
        Cliente clienteFalso = new Cliente();
        clienteFalso.setId(1);
        clienteFalso.setNombre("Cliente Test");
        clienteFalso.setRut("12345678-9");
        clienteFalso.setEmail("test@email.com");
        clienteFalso.setTelefono("987654321");
 
        Entrada entrada = new Entrada();
        entrada.setId(id);
        entrada.setTipoEntrada(tipo);
        entrada.setPrecio(precio);
        entrada.setCantidad(cantidad);
        entrada.setHorario(horario);
        entrada.setPeliculaId(peliculaId);
        entrada.setCliente(clienteFalso);
        return entrada;
    }
 
    @Test
    void testBuscarPorId_Exitoso() {
        Entrada entradaFalsa = buildEntradaFalsa(1, "General", 5000f, 2, "20:00", 1);
        when(entradaRepository.findById(1)).thenReturn(Optional.of(entradaFalsa));

        EntradasDTO resultado = entradaService.buscarPorId(1);

        assertNotNull(resultado, "El DTO no debería ser nulo");
        assertEquals(1, resultado.getId());
        assertEquals("General", resultado.getTipoEntrada());
        assertEquals("12345678-9", resultado.getCliente(), "El RUT del cliente debe estar en el DTO");
        verify(entradaRepository, times(1)).findById(1);
    }
 
    @Test
    void testBuscarPorId_NoEncontrado_LanzaExcepcion() {
        Integer idInexistente = 99;
        when(entradaRepository.findById(idInexistente)).thenReturn(Optional.empty());
 
        RuntimeException excepcion = assertThrows(RuntimeException.class,
                () -> entradaService.buscarPorId(idInexistente));
        assertEquals("Entrada no encontrada", excepcion.getMessage());
        verify(entradaRepository, times(1)).findById(idInexistente);
    }
 
    @Test
    void testObtenerTodos_RetornaLista() {
        Entrada entrada1 = buildEntradaFalsa(1, "General", 5000f, 1, "18:00", 1);
        Entrada entrada2 = buildEntradaFalsa(2, "VIP", 12000f, 2, "21:00", 2);
 
        when(entradaRepository.findAll()).thenReturn(Arrays.asList(entrada1, entrada2));
 
        List<EntradasDTO> resultado = entradaService.obtenerTodos();
 
        assertNotNull(resultado);
        assertEquals(2, resultado.size(), "Debe retornar dos entradas");
        verify(entradaRepository, times(1)).findAll();
    }
 
    @Test
    void testObtenerTodos_ListaVacia() {
        when(entradaRepository.findAll()).thenReturn(List.of());
 
        List<EntradasDTO> resultado = entradaService.obtenerTodos();
 
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty(), "La lista debería estar vacía");
    }
 
    @Test
    void testCrearEntrada_Exitoso() {
        Entrada nuevaEntrada = buildEntradaFalsa(null, "Preferente", 8000f, 1, "22:00", 3);
        Entrada entradaGuardada = buildEntradaFalsa(5, "Preferente", 8000f, 1, "22:00", 3);
 
        when(entradaRepository.save(nuevaEntrada)).thenReturn(entradaGuardada);
 
        Entrada resultado = entradaService.crearEntrada(nuevaEntrada);
 
        assertNotNull(resultado);
        assertEquals(5, resultado.getId(), "El ID generado debe ser 5");
        assertEquals("Preferente", resultado.getTipoEntrada());
        verify(entradaRepository, times(1)).save(nuevaEntrada);
    }
 
    @Test
    void testEditarEntrada_Exitoso() {
        Integer id = 1;
        Entrada entradaExistente = buildEntradaFalsa(id, "General", 5000f, 1, "18:00", 1);
 
        Entrada datosNuevos = new Entrada();
        datosNuevos.setTipoEntrada("VIP");
        datosNuevos.setPrecio(12000f);
        datosNuevos.setCantidad(2);
        datosNuevos.setHorario("21:00");
 
        Entrada entradaActualizada = buildEntradaFalsa(id, "VIP", 12000f, 2, "21:00", 1);
 
        when(entradaRepository.findById(id)).thenReturn(Optional.of(entradaExistente));
        when(entradaRepository.save(any(Entrada.class))).thenReturn(entradaActualizada);
 

        EntradasDTO resultado = entradaService.editarEntrada(id, datosNuevos);

        assertNotNull(resultado);
        assertEquals("VIP", resultado.getTipoEntrada(), "El tipo debe haberse actualizado");
        verify(entradaRepository, times(1)).save(any(Entrada.class));
    }
 
    @Test
    void testEditarEntrada_NoEncontrada_LanzaExcepcion() {

        Integer idInexistente = 99;
        Entrada datosNuevos = new Entrada();
        datosNuevos.setTipoEntrada("VIP");
        datosNuevos.setPrecio(10000f);
        datosNuevos.setCantidad(1);
        datosNuevos.setHorario("20:00");
 
        when(entradaRepository.findById(idInexistente)).thenReturn(Optional.empty());
 
        RuntimeException excepcion = assertThrows(RuntimeException.class,
                () -> entradaService.editarEntrada(idInexistente, datosNuevos));
        assertEquals("Entrada no encontrada", excepcion.getMessage());
    }
 
 
    @Test
    void testCancelarEntrada_Exitoso() {

        Integer id = 1;
        Entrada entradaExistente = buildEntradaFalsa(id, "General", 5000f, 1, "18:00", 1);
 
        when(entradaRepository.findById(id)).thenReturn(Optional.of(entradaExistente));

        String resultado = entradaService.cancelarEntrada(id);
 
        assertEquals("Entrada cancelada exitosamente", resultado);
        verify(entradaRepository, times(1)).delete(entradaExistente);
    }
 
    @Test
    void testCancelarEntrada_NoExiste_RetornaMensajeError() {
     
        Integer idInexistente = 99;
        when(entradaRepository.findById(idInexistente)).thenReturn(Optional.empty());
 
        String resultado = entradaService.cancelarEntrada(idInexistente);
 
 
        assertTrue(resultado.contains("No se pudo cancelar"),
                "El mensaje debe indicar que no se pudo cancelar");
    }
  
    @Test
    void testConvertirADTO_PeliculaNoDisponible_CuandoServicioFalla() {

        Entrada entradaFalsa = buildEntradaFalsa(1, "General", 5000f, 1, "19:00", 999);
        when(entradaRepository.findById(1)).thenReturn(Optional.of(entradaFalsa));
 
        EntradasDTO resultado = entradaService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals("Pelicula no disponible", resultado.getPelicula(),
                "Cuando el servicio de películas no responde, debe indicar 'Pelicula no disponible'");
    }
}