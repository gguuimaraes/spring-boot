package br.com.gguuimaraes.springboot.repositories;

import br.com.gguuimaraes.springboot.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
