package com.fittrack.lite.controller;

import com.fittrack.lite.model.BodyMeasurement;
import com.fittrack.lite.model.WorkoutLog;
import com.fittrack.lite.repository.BodyMeasurementRepository;
import com.fittrack.lite.repository.UserRepository;
import com.fittrack.lite.repository.WorkoutLogRepository;
import com.fittrack.lite.security.SessionAuthFilter;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final UserRepository users;
    private final WorkoutLogRepository logs;
    private final BodyMeasurementRepository measurements;

    public DashboardController(UserRepository u, WorkoutLogRepository l, BodyMeasurementRepository m) {
        users = u; logs = l; measurements = m;
    }

    @GetMapping
    public Map<String, Object> dashboard(HttpSession s) {
        var u = users.findById((Long) s.getAttribute(SessionAuthFilter.USER_ID)).orElseThrow();
        BodyMeasurement latest = measurements.findTopByUserOrderByRecordedOnDescIdDesc(u).orElse(null);
        List<WorkoutLog> history = logs.findByUserOrderByCompletedAtDesc(u);

        // Map.of() throws on null values, so use HashMap (measurements fields are optional)
        Map<String, Object> stats = new HashMap<>();
        stats.put("workouts", history.stream().map(x -> x.getCompletedAt().toLocalDate() + "|" + x.getPlanName()).distinct().count());
        stats.put("weight", latest == null || latest.getWeightKg() == null ? 0 : latest.getWeightKg());
        stats.put("streak", streak(history));
        stats.put("lastWorkout", history.isEmpty() ? "None" : history.get(0).getPlanName());

        Map<String, Object> m = new HashMap<>();
        if (latest != null) {
            m.put("weight", latest.getWeightKg());
            m.put("chest", latest.getChestCm());
            m.put("arms", latest.getArmsCm());
            m.put("waist", latest.getWaistCm());
        }

        Map<String, Object> out = new HashMap<>();
        out.put("user", Map.of("id", u.getId(), "name", u.getName(), "email", u.getEmail()));
        out.put("stats", stats);
        out.put("measurements", m);
        return out;
    }

    /** Consecutive days (ending today or yesterday) with at least one logged exercise. */
    private int streak(List<WorkoutLog> history) {
        Set<LocalDate> days = history.stream().map(x -> x.getCompletedAt().toLocalDate()).collect(Collectors.toSet());
        LocalDate d = LocalDate.now();
        if (!days.contains(d)) d = d.minusDays(1);
        int n = 0;
        while (days.contains(d)) { n++; d = d.minusDays(1); }
        return n;
    }
}
