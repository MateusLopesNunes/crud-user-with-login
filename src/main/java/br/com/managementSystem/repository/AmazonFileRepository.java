package br.com.managementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.managementSystem.model.AmazonFile;

public interface AmazonFileRepository extends JpaRepository<AmazonFile, Long>{

}
