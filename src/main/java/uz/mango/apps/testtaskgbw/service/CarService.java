package uz.mango.apps.testtaskgbw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.mango.apps.testtaskgbw.entity.Car;
import uz.mango.apps.testtaskgbw.repository.CarRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public Car createCarWithImage(Car car, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = fileStorageService.saveFile(file);
            car.setImage(fileName);
        }
        return carRepository.save(car);
    }

    public Car updateCar(Long id, Car carDetails) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        car.setModel(carDetails.getModel());
        car.setBrand(carDetails.getBrand());

        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }
}
