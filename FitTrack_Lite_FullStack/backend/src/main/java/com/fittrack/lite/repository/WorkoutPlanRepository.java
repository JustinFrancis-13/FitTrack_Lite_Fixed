package com.fittrack.lite.repository;
import com.fittrack.lite.model.WorkoutPlan; import org.springframework.data.jpa.repository.JpaRepository;
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan,Long>{}