package br.com.iblueconsulting.clientesapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import br.com.iblueconsulting.clientesapi.dao.ClienteDao;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClientesControllerIntegrationTests  {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
    private WebApplicationContext webApplicationContext;

	
	private Gson gson = new Gson();
	ClienteDao clienteTest = null;


	@Before
	public void init() {
		this.clienteTest = new ClienteDao("NOME CLIENTE","10489212808","email_teste@gmail.com","01310100","Av Paulista");
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}
	
	/* Valid Tests */
	
	@Test
	public void getAllClientes_ValidTest() throws Exception {
		
		mvc.perform(get("/clientes") //
				.contentType(MediaType.APPLICATION_JSON)) //
				.andExpect(status().isOk()) //
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void saveCliente_ValidTest() throws Exception {
		
		 this.mvc.perform(post("/clientes")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(gson.toJson(clienteTest.toString())))
		            .andDo(print())
		            .andExpect(status().isOk());
	}
	
	@Test
	public void updateCliente_ValidTest() throws Exception {
		
		this.clienteTest.setNome("NOME CLIENTE 1");
		
		 this.mvc.perform(put("/clientes")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(gson.toJson(clienteTest.toString())))
		            .andExpect(status().isOk());
	}
	
	@Test
	public void deleteCliente_ValidTest() throws Exception {
		
		this.clienteTest.setNome("NOME CLIENTE 1");
		
		 this.mvc.perform(put("/clientes")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(gson.toJson(clienteTest.toString())))
		            .andExpect(status().isOk());
	}

	/* Invalid Tests */
	
	
}
