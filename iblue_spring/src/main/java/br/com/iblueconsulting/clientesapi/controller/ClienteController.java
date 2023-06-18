package br.com.iblueconsulting.clientesapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.iblueconsulting.clientesapi.dao.ClienteDao;
import br.com.iblueconsulting.clientesapi.entity.Cliente;
import br.com.iblueconsulting.clientesapi.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/clientes")
@Tag(name="Clientes", description="API de gerenciamento de Clientes.")

public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
    @Operation(summary = "Retorna a lista de clientes", description = "Metodo sem parametros que retorna a lista de todos os clientes", tags = { "todos_clientes" })
	@GetMapping("/")
	public List<Cliente> getAllClientes(){
		return clienteService.getAllClientes();
	}
	
    @Operation(summary = "Retorna a busca de um cliente pelo email.", description = "Metodo que recebe o email e retorna o cliente, caso exista", tags = { "busca_por_email" })
	@GetMapping("/email/{email}")
	public Cliente getByEmail(@PathVariable("email") String email){
		return clienteService.getByEmail(email);	
	}
	
    @Operation(summary = "Salva o cliente.", description = "Metodo que recebe o ClienteDao e retorna o cliente salvo ou erro. ", tags = { "salva_cliente" })
	@PostMapping
	public ResponseEntity<ClienteDao> saveCliente(@RequestBody ClienteDao cliente){	
    	try {
    		ClienteDao c = clienteService.saveCliente(cliente);
    		return ResponseEntity.ok(c);
    	} catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }		
	}
	
    @Operation(summary = "Atualiza o cliente.", description = "Metodo que recebe o ClienteDao e retorna o cliente atualizado ou erro. ", tags = { "atualiza_cliente" })
	@PutMapping
	public ResponseEntity<ClienteDao> updateCliente(@RequestBody ClienteDao cliente){
    	try {
    		ClienteDao c = clienteService.updateCliente(cliente);
    		return ResponseEntity.ok(c);
    	} catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	}
	
    @Operation(summary = "Deleta o cliente.", description = "Metodo que recebe o ClienteDao e deleta o cliente caso ele existe. ", tags = { "exclui_cliente" })
	@DeleteMapping
	public String deleteCliente(@RequestBody ClienteDao cliente){
		return clienteService.deleteCliente(cliente);	
	}

}
