package uz.mango.apps.testtaskgbw.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mango.apps.testtaskgbw.entity.Car;
import uz.mango.apps.testtaskgbw.service.PersonService;
import uz.mango.apps.testtaskgbw.service.CarService;

import java.util.List;@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private PersonService personService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // Get all cars
    @Operation(summary = "Retrieve all cars", description = "Fetches all available cars from the database.")
    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    // Get car by ID
    @Operation(summary = "Retrieve a car by ID", description = "Fetches a specific car based on the provided car ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carService.getCarById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create new car with image
    @Operation(summary = "Create a new car", description = "Creates a new car along with an optional image. If the owner is provided, it is also associated with the car.")
    @PostMapping
    public ResponseEntity<Car> createCar(
            @RequestPart("car") Car car,
            @RequestPart(value = "carImage", required = false) MultipartFile carImage
    ) {
        try {
            // Optionally, verify if the owner exists
            if (car.getOwner() != null && car.getOwner().getId() != null) {
                personService.getPersonById(car.getOwner().getId())
                        .ifPresent(car::setOwner);
            }
            Car savedCar = carService.createCarWithImage(car, carImage);
            return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update car
    @Operation(summary = "Update a car", description = "Updates the details of an existing car based on the provided car ID.")
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car carDetails) {
        try {
            Car updatedCar = carService.updateCar(id, carDetails);
            return ResponseEntity.ok(updatedCar);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
