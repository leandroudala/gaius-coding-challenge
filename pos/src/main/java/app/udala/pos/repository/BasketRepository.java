package app.udala.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.udala.pos.model.Basket;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}
