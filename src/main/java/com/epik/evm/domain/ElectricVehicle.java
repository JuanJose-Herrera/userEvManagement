package com.epik.evm.domain;

import com.epik.evm.catalogs.Brands;
import com.epik.evm.catalogs.ElectricVehicleType;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(indexes = {@Index(columnList = "year"),@Index(columnList = "brand")} )
public class ElectricVehicle {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Enumerated
    @NotNull
    private Brands brand;

    @NotNull
    private String model;

    @NotNull
    private int year;

    @NotNull
    private int batteryCapacity;

    @Enumerated
    @NotNull
    private ElectricVehicleType electricVehicleType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Brands getBrand() {
        return brand;
    }

    public void setBrand(Brands brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public ElectricVehicleType getElectricVehicleType() {
        return electricVehicleType;
    }

    public void setElectricVehicleType(ElectricVehicleType electricVehicleType) {
        this.electricVehicleType = electricVehicleType;
    }




}
