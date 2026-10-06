package com.fittrack.lite.model;
import jakarta.persistence.*;
@Entity @Table(name="workout_plans")
public class WorkoutPlan {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name; private String focus; private int exerciseCount; private String tag;
 public WorkoutPlan(){} public WorkoutPlan(String n,String f,int c,String t){name=n;focus=f;exerciseCount=c;tag=t;}
 public Long getId(){return id;} public String getName(){return name;} public String getFocus(){return focus;} public int getExerciseCount(){return exerciseCount;} public String getTag(){return tag;}
}