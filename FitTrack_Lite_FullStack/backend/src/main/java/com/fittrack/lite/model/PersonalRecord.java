package com.fittrack.lite.model;
import com.fasterxml.jackson.annotation.JsonIgnore; import jakarta.persistence.*;
@Entity @Table(name="personal_records")
public class PersonalRecord {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @JsonIgnore @ManyToOne(optional=false) private User user; private String exerciseName; private double weightKg;
 public Long getId(){return id;} @JsonIgnore public User getUser(){return user;} public void setUser(User v){user=v;} public String getExerciseName(){return exerciseName;} public void setExerciseName(String v){exerciseName=v;} public double getWeightKg(){return weightKg;} public void setWeightKg(double v){weightKg=v;}
}