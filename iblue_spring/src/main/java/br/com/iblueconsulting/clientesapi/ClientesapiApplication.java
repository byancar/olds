package br.com.iblueconsulting.clientesapi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ClientesapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientesapiApplication.class, args);
	}

}
