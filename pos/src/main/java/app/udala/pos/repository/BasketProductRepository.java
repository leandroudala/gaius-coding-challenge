package app.udala.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.udala.pos.model.BasketProduct;

public interface BasketProductRepository extends JpaRepository<BasketProduct, Long>{

}
