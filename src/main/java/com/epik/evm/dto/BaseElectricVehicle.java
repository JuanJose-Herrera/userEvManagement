package com.epik.evm.dto;

import com.epik.evm.catalogs.Brands;
import com.epik.evm.catalogs.ElectricVehicleType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class BaseElectricVehicle {

    @NotNull
    private Brands brand;

    @NotNull
    @NotBlank
    private String model;

    @NotNull
    private int year;

    @NotNull
    private int batteryCapacity;

    @NotNull
    private ElectricVehicleType electricVehicleType;

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
