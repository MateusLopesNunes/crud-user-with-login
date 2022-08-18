package br.com.managementSystem.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.managementSystem.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query(value = "SELECT * FROM USER WHERE (:q IS NULL OR (UPPER(name) LIKE UPPER(CONCAT('%', :q, '%'))))", nativeQuery = true)
	Page<User> findByName(String q, Pageable page);
	
	@Query(value = "SELECT * FROM USER WHERE email = :email LIMIT 1", nativeQuery = true)
	Optional<User> findByEmail(String email);

	@Query(value = "SELECT * FROM USER WHERE cpf = :cpf LIMIT 1", nativeQuery = true)
	Optional<User> findByCpf(String cpf);
} 