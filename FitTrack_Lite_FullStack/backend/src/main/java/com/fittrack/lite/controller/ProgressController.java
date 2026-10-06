package com.fittrack.lite.controller;
import com.fittrack.lite.model.*; import com.fittrack.lite.repository.*; import com.fittrack.lite.security.SessionAuthFilter; import jakarta.servlet.http.*; import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/progress")
public class ProgressController {
 private final BodyMeasurementRepository measurements; private final UserRepository users;
 public ProgressController(BodyMeasurementRepository m,UserRepository u){measurements=m;users=u;}
 public record MeasurementReq(@PositiveOrZero Double weightKg,@PositiveOrZero Double chestCm,@PositiveOrZero Double armsCm,@PositiveOrZero Double waistCm){}
 @GetMapping public List<BodyMeasurement> progress(HttpSession s){return measurements.findByUserOrderByRecordedOnAscIdAsc(users.findById((Long)s.getAttribute(SessionAuthFilter.USER_ID)).orElseThrow());}
 @PostMapping("/measurements") public BodyMeasurement add(@Valid @RequestBody MeasurementReq r,HttpSession s){User u=users.findById((Long)s.getAttribute(SessionAuthFilter.USER_ID)).orElseThrow();BodyMeasurement x=new BodyMeasurement();x.setUser(u);x.setWeightKg(r.weightKg());x.setChestCm(r.chestCm());x.setArmsCm(r.armsCm());x.setWaistCm(r.waistCm());return measurements.save(x);}
}