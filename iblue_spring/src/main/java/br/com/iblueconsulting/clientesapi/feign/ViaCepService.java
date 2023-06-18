package br.com.iblueconsulting.clientesapi.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.iblueconsulting.clientesapi.dao.ViaCepDao;

@FeignClient(name = "ViaCepService", url = "http://viacep.com.br")
public interface ViaCepService {

	 @RequestMapping("/ws/{cep}/json/")
	 ViaCepDao getCep(@PathVariable("cep") String cep);
}
