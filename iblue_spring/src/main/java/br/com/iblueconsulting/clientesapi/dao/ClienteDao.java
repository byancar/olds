package br.com.iblueconsulting.clientesapi.dao;

import javax.validation.constraints.NotNull;

public class ClienteDao {
	
    private String nome;
    @NotNull(message = "O campo CPF não pode ser vazio.")
    private String cpf;
    @NotNull(message = "O campo Email não pode ser vazio.")
    private String email;
    private String cep;
    private String lougradouro;
    
	public ClienteDao() {}

	public ClienteDao(String nome, @NotNull(message = "O campo CPF não pode ser vazio.") String cpf,
			@NotNull(message = "O campo Email não pode ser vazio.") String email, String cep, String lougradouro) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.cep = cep;
		this.lougradouro = lougradouro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLougradouro() {
		return lougradouro;
	}

	public void setLougradouro(String lougradouro) {
		this.lougradouro = lougradouro;
	}

	@Override
	public String toString() {
		return "ClienteDao [nome=" + nome + ", cpf=" + cpf + ", email=" + email + ", cep=" + cep + ", lougradouro="
				+ lougradouro + "]";
	}


}