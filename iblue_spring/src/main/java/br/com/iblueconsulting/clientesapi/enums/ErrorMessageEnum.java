package br.com.iblueconsulting.clientesapi.enums;

public enum ErrorMessageEnum {

	CEP_NAO_ENCONTRADO("O Cep não foi encontrado."),
	ERRO_AO_SALVAR ("Erro ao salvar o cliente."),
	ERRO_AO_ATUALIZAR ("Erro ao atualizar o cliente."),
	EMAIL_EXISTENTE( "O email já existe na base de dados."),
	ERRO_DELETE( "Erro ao deletar o cliente."),
	OK_DELETE( "Cliente deletado com sucesso."),
	CPF_EXISTENTE( "O CPF já existe na base de dados.");

	private String erro;
	
	ErrorMessageEnum(String erro) {
		this.erro = erro;
	}

	public String getErro() {
		return erro;
	}	
}
