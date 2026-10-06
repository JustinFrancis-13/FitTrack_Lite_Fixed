package com.fittrack.lite.model;
import com.fasterxml.jackson.annotation.JsonIgnore; import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="workout_logs")
public class WorkoutLog {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @JsonIgnore @ManyToOne(optional=false) private User user;
 private String planName; private String exerciseName; private int sets; private int reps; private double weightKg; private boolean completed;
 private LocalDateTime completedAt=LocalDateTime.now();
 public Long getId(){return id;} @JsonIgnore public User getUser(){return user;} public void setUser(User v){user=v;}
 public String getPlanName(){return planName;} public void setPlanName(String v){planName=v;}
 public String getExerciseName(){return exerciseName;} public void setExerciseName(String v){exerciseName=v;}
 public int getSets(){return sets;} public void setSets(int v){sets=v;} public int getReps(){return reps;} public void setReps(int v){reps=v;}
 public double getWeightKg(){return weightKg;} public void setWeightKg(double v){weightKg=v;} public boolean isCompleted(){return completed;} public void setCompleted(boolean v){completed=v;}
 public LocalDateTime getCompletedAt(){return completedAt;}
}