package br.com.iblueconsulting.clientesapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.iblueconsulting.clientesapi.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

	Optional<Cliente> findByEmail(String email);

	Cliente findByCpf(String cpf);

	Cliente deleteByCpf(String cpf);

}
