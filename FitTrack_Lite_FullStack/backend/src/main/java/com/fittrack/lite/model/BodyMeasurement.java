package com.fittrack.lite.model;
import com.fasterxml.jackson.annotation.JsonIgnore; import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="body_measurements")
public class BodyMeasurement {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @JsonIgnore @ManyToOne(optional=false) private User user; private LocalDate recordedOn=LocalDate.now();
 private Double weightKg, chestCm, armsCm, waistCm;
 public Long getId(){return id;} @JsonIgnore public User getUser(){return user;} public void setUser(User v){user=v;} public LocalDate getRecordedOn(){return recordedOn;}
 public Double getWeightKg(){return weightKg;} public void setWeightKg(Double v){weightKg=v;} public Double getChestCm(){return chestCm;} public void setChestCm(Double v){chestCm=v;}
 public Double getArmsCm(){return armsCm;} public void setArmsCm(Double v){armsCm=v;} public Double getWaistCm(){return waistCm;} public void setWaistCm(Double v){waistCm=v;}
}