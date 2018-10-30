package br.com.gguuimaraes.springboot.models;

import lombok.*;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Car extends DefaultModel {
    protected String manufacturer;
    protected String model;
    protected String motor;

    @Builder
    public Car(String manufacturer, String model, String motor, Long code) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.motor = motor;
        this.code = code;
    }
}
