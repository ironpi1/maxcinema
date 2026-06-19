package com.cliente.clientes.Service;

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

import com.cliente.clientes.DTO.ClienteDTO;
import com.cliente.clientes.repository.ClienteRepository;
import com.cliente.clientes.model.Cliente;
import com.cliente.clientes.model.TipoCliente;
import com.cliente.clientes.service.ClienteService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
class ClienteApplicationTests {

	@Mock
	private ClienteRepository clienteRepository; 
	
	@InjectMocks
	private ClienteService clienteService; 
	private Faker faker = new Faker(); // Nuestro generador de datos
	@BeforeEach
	void setUp() {
		
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testBuscarPorId_Exitoso() {

		Integer idSimulado = 1;
		String nombreAleatorio = faker.book().title(); // Genera títulos de películas aleatorios de prueba
		Cliente clientefalso = new Cliente();
		clientefalso.setId(idSimulado);
		clientefalso.setNombre(nombreAleatorio);
		clientefalso.setRut(faker.lorem().characters(9));
		clientefalso.setEmail(faker.lorem().characters(20, 100));
		clientefalso.setTelefono(faker.lorem().characters(15));
		
		TipoCliente tipoFalso = new TipoCliente();
		tipoFalso.setId(1);
		tipoFalso.setTipo("NORMAL");
		clientefalso.setTipoCliente(tipoFalso);

		when(clienteRepository.findById(idSimulado)).thenReturn(Optional.of(clientefalso));

		ClienteDTO resultado = clienteService.buscarPorId(idSimulado);
        
		assertNotNull(resultado, "El DTO resultante no debería ser nulo");
		assertEquals(nombreAleatorio, resultado.getNombre(), "El nombre transformado al DTO debe coincidir con el de la DB");

		verify(clienteRepository, times(1)).findById(idSimulado);
	}
}