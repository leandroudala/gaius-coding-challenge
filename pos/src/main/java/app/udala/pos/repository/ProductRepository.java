package app.udala.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.udala.pos.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
	
}
