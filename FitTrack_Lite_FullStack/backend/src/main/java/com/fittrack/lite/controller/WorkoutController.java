package com.fittrack.lite.controller;
import com.fittrack.lite.model.*; import com.fittrack.lite.repository.*; import com.fittrack.lite.security.SessionAuthFilter; import jakarta.servlet.http.*; import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/workouts")
public class WorkoutController {
 private final WorkoutPlanRepository plans; private final WorkoutLogRepository logs; private final UserRepository users; private final PersonalRecordRepository records;
 public WorkoutController(WorkoutPlanRepository p,WorkoutLogRepository l,UserRepository u,PersonalRecordRepository r){plans=p;logs=l;users=u;records=r;}
 @GetMapping("/plans") public List<WorkoutPlan> plans(){return plans.findAll();}
 public record LogReq(@NotBlank String planName,@NotBlank String exerciseName,@Min(1) int sets,@Min(1) int reps,@PositiveOrZero double weightKg,boolean completed){}
 @PostMapping public WorkoutLog log(@Valid @RequestBody LogReq r,HttpSession s){User u=users.findById((Long)s.getAttribute(SessionAuthFilter.USER_ID)).orElseThrow();WorkoutLog x=new WorkoutLog();x.setUser(u);x.setPlanName(r.planName());x.setExerciseName(r.exerciseName());x.setSets(r.sets());x.setReps(r.reps());x.setWeightKg(r.weightKg());x.setCompleted(r.completed());WorkoutLog saved=logs.save(x);
  // Keep Personal Records up to date automatically (best weight per exercise)
  if(r.completed()&&r.weightKg()>0){var pr=records.findByUserAndExerciseName(u,r.exerciseName()).orElseGet(PersonalRecord::new);if(pr.getId()==null||r.weightKg()>pr.getWeightKg()){pr.setUser(u);pr.setExerciseName(r.exerciseName());pr.setWeightKg(r.weightKg());records.save(pr);}}
  return saved;}
 @GetMapping("/history") public List<WorkoutLog> history(HttpSession s){return logs.findByUserOrderByCompletedAtDesc(users.findById((Long)s.getAttribute(SessionAuthFilter.USER_ID)).orElseThrow());}
}