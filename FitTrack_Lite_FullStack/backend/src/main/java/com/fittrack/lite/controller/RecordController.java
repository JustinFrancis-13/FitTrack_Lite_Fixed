package com.fittrack.lite.controller;
import com.fittrack.lite.model.*; import com.fittrack.lite.repository.*; import com.fittrack.lite.security.SessionAuthFilter; import jakarta.servlet.http.*; import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/records")
public class RecordController {
 private final PersonalRecordRepository records; private final UserRepository users;
 public RecordController(PersonalRecordRepository r,UserRepository u){records=r;users=u;}
 @GetMapping public List<PersonalRecord> all(HttpSession s){return records.findByUser(users.findById((Long)s.getAttribute(SessionAuthFilter.USER_ID)).orElseThrow());}
 public record RecordReq(@NotBlank String exerciseName,@Positive double weightKg){}
 @PostMapping public PersonalRecord save(@Valid @RequestBody RecordReq r,HttpSession s){User u=users.findById((Long)s.getAttribute(SessionAuthFilter.USER_ID)).orElseThrow();var x=records.findByUserAndExerciseName(u,r.exerciseName()).orElseGet(PersonalRecord::new);x.setUser(u);x.setExerciseName(r.exerciseName());if(r.weightKg()>x.getWeightKg())x.setWeightKg(r.weightKg());return records.save(x);}
}