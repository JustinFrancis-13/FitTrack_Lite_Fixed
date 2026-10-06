package com.fittrack.lite.repository;
import com.fittrack.lite.model.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog,Long>{List<WorkoutLog> findByUserOrderByCompletedAtDesc(User user); long countByUser(User user); }