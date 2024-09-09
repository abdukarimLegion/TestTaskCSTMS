package uz.mango.apps.testtaskgbw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.mango.apps.testtaskgbw.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}