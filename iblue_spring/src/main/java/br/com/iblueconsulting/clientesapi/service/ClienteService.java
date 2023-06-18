package br.com.iblueconsulting.clientesapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.iblueconsulting.clientesapi.dao.ClienteDao;
import br.com.iblueconsulting.clientesapi.dao.ViaCepDao;
import br.com.iblueconsulting.clientesapi.entity.Cliente;
import br.com.iblueconsulting.clientesapi.enums.ErrorMessageEnum;
import br.com.iblueconsulting.clientesapi.exception.BadRequestException;
import br.com.iblueconsulting.clientesapi.feign.ViaCepService;
import br.com.iblueconsulting.clientesapi.repository.ClienteRepository;
import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ViaCepService viaCepService;
	

	public List<Cliente> getAllClientes(){
		return clienteRepository.findAll(); 	
	}

	public Cliente getByEmail(String email) {
		Optional<Cliente> cliente = clienteRepository.findByEmail(email);
		
		if(cliente.isPresent()) {
			return cliente.get();
		}else {
			return new Cliente();
		}
	}

	public ClienteDao saveCliente(ClienteDao cliente) {
		
		//busca o cep na viacep
		ViaCepDao cep = getCep(cliente.getCep());
		
		/* valida email e cpf*/
		try {
			validaEmail(cliente.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			validaCpf(cliente.getCpf());
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* valida email e cpf*/
		
		
		//transforma o ClienteDao em Cliente
		Cliente c = new Cliente(cliente.getNome(), cliente.getCpf(), cliente.getEmail(), cliente.getCep(), null, 
				cep.getLogradouro(), null, cep.getComplemento(), cep.getBairro(), cep.getLocalidade(), cep.getUf());
		
		//Salva o cliente
		try {
			clienteRepository.save(c);
		}catch (Exception e) {
			log.error(ErrorMessageEnum.ERRO_AO_SALVAR);
		}
		
		return cliente;
	}

	public ClienteDao updateCliente(ClienteDao cliente) {
		/* busca o cliente*/
		Cliente c = clienteRepository.findByCpf(cliente.getCpf());
		//busca o cep na viacep
		ViaCepDao cep = getCep(cliente.getCep());
				
		c.setNome(cliente.getNome());
		//c.setCpf(cliente.getCpf());
		//c.setEmail(cliente.getEmail());
		c.setCep(cliente.getCep());
		c.setLogradouro(cep.getLogradouro());
		c.setComplemento( cep.getComplemento());
		c.setBairro(cep.getBairro());
		c.setCidade(cep.getLocalidade());
		c.setEstado(cep.getUf());
	
		//Atualiza o cliente
		try {
			clienteRepository.save(c);
		}catch (Exception e) {
			log.error(ErrorMessageEnum.ERRO_AO_ATUALIZAR);
		}
		
		return cliente;
	}

	public String deleteCliente(ClienteDao cliente) {
		
		try {
			clienteRepository.deleteByCpf(cliente.getCpf());
		}catch (Exception e) {
			log.error(ErrorMessageEnum.ERRO_DELETE);
		}
		return ErrorMessageEnum.OK_DELETE.toString();

	}


	public ViaCepDao getCep(String cep) {
		ViaCepDao c = null;
		try {
			 c = viaCepService.getCep(cep);
		}catch (BadRequestException e) {
			log.error(ErrorMessageEnum.CEP_NAO_ENCONTRADO);
		}
		
		return c;	
	}
	
	public void validaEmail(String email) throws Exception{
		Optional<Cliente> c = clienteRepository.findByEmail(email);
		
		if(c.isPresent()) {
			log.error(ErrorMessageEnum.EMAIL_EXISTENTE);
			throw new Exception();	
		}
	}
	
	public void validaCpf(String cpf) throws Exception{
		Optional<Cliente> c = clienteRepository.findByEmail(cpf);
		
		if(c.isPresent()) {
			log.error(ErrorMessageEnum.CPF_EXISTENTE);
			throw new Exception();	
		}
	}
	
	
	
}
