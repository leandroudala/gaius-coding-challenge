package app.udala.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.udala.pos.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
