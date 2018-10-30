package br.com.gguuimaraes.springboot.controllers;

import br.com.gguuimaraes.springboot.exceptions.ResourceNotFoundException;
import br.com.gguuimaraes.springboot.interfaces.GenericOperations;
import br.com.gguuimaraes.springboot.models.Car;
import br.com.gguuimaraes.springboot.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping
public class CarController implements GenericOperations<Car> {
    @Autowired
    CarRepository carRepository;

    @Override
    @PostMapping(path = "/car", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public HttpEntity<Car> create(@RequestBody Car entity) {
        carRepository.save(entity);
        entity.add(linkTo(methodOn(CarController.class).read(entity.getCode())).withSelfRel());
        return new ResponseEntity<>(entity, entity.getCode() != null ? HttpStatus.CREATED : HttpStatus.NO_CONTENT);
    }

    @Override
    @PutMapping(path = "/car", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public HttpEntity<Car> update(@RequestBody Car entity) {
        Optional<Car> result = carRepository.findById(entity.getCode());
        result.ifPresent(car -> {
            car.setManufacturer(entity.getManufacturer());
            car.setModel(entity.getModel());
            car.setMotor(entity.getMotor());
            carRepository.save(car);
        });
        result.orElseThrow(() -> new ResourceNotFoundException(Car.class, entity.getCode()));
        entity.add(linkTo(methodOn(CarController.class).read(entity.getCode())).withSelfRel());
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @Override
    @GetMapping(path = "/car/{code}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public HttpEntity<Car> read(@PathVariable(value = "code") long code) {
        Optional<Car> result = carRepository.findById(code);
        if (result.isPresent()) {
            Car entity = result.get();
            entity.add(linkTo(methodOn(CarController.class).read(entity.getCode())).withSelfRel());
            entity.add(linkTo(methodOn(CarController.class).read()).withRel("all"));
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }
        throw new ResourceNotFoundException(Car.class, code);
    }

    @Override
    @DeleteMapping(path = "/car/{code}")
    public void delete(@PathVariable(value = "code") long code) {
        carRepository.deleteById(code);
    }

    @Override
    @PostMapping(path = "/cars", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public HttpEntity<List<Car>> create(@RequestBody List<Car> entities) {
        entities.forEach(c -> {
            carRepository.save(c);
            c.add(linkTo(methodOn(CarController.class).read(c.getCode())).withSelfRel());
        });
        return new ResponseEntity<>(entities, HttpStatus.CREATED);
    }

    @Override
    @PutMapping(path = "/cars", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public HttpEntity<List<Car>> update(@RequestBody List<Car> entities) {
        carRepository.saveAll(entities);
        entities.forEach(c -> {
            c.add(linkTo(methodOn(CarController.class).read(c.getCode())).withSelfRel());
        });
        return new ResponseEntity<>(entities, HttpStatus.CREATED);
    }

    @Override
    @GetMapping(path = "/cars", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public HttpEntity<List<Car>> read() {
        List<Car> cars = carRepository.findAll();
        cars.forEach(c -> {
            c.add(linkTo(methodOn(CarController.class).read(c.getCode())).withSelfRel());
        });
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @Override
    @DeleteMapping(path = "/cars", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void delete(@RequestBody List<Car> entities) {
        carRepository.deleteAll(entities);
    }
}

