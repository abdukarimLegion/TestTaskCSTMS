package uz.mango.apps.testtaskgbw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.mango.apps.testtaskgbw.entity.Car;
import uz.mango.apps.testtaskgbw.entity.Person;
import uz.mango.apps.testtaskgbw.repository.PersonRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CarService carService;

    public Person createPersonWithCarAndImage(Person person, MultipartFile carImage) throws IOException {
        for (Car car : person.getCars()) {
            car.setOwner(person);
            if (carImage != null && !carImage.isEmpty()) {
                carService.createCarWithImage(car, carImage);
            } else {
                carService.createCarWithImage(car, null);
            }
        }
        return personRepository.save(person);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public Person updatePerson(Long id, Person personDetails) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        person.setName(personDetails.getName());
        person.setEmail(personDetails.getEmail());

        return personRepository.save(person);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}
