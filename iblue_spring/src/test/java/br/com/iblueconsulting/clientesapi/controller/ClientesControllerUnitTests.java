package br.com.iblueconsulting.clientesapi.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import br.com.iblueconsulting.clientesapi.dao.ClienteDao;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ClientesControllerUnitTests  {

	@LocalServerPort
    Integer ServerPort;
	
	@Test
	public void testGetAllClienteListSuccess() throws URISyntaxException {
	    RestTemplate restTemplate = new RestTemplate();
	     
	    final String baseUrl = "http://localhost:" + ServerPort + "/clientes";
	    URI uri = new URI(baseUrl);
	 
	    ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
	  
	    Assert.assertEquals(200, result.getStatusCodeValue());
	}    
	
	@Test
	public void testAddCliente() throws URISyntaxException {
	    RestTemplate restTemplate = new RestTemplate();
	     
	    final String baseUrl = "http://localhost:" + ServerPort + "/clientes";
	    URI uri = new URI(baseUrl);
	    ClienteDao c = new ClienteDao("NOME CLIENTE","10489212808","email_teste@gmail.com","01310100","Av Paulista");
	    
	    HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");      
	 
        HttpEntity<ClienteDao> request = new HttpEntity<ClienteDao>(c, headers);
        
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);        
	  
	    Assert.assertEquals(200, result.getStatusCodeValue());
	}   

	
}
