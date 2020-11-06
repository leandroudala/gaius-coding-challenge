package app.udala.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.udala.pos.model.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, String>{

}
