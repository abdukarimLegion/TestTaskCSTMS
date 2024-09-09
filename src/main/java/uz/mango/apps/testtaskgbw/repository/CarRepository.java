package uz.mango.apps.testtaskgbw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mango.apps.testtaskgbw.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}