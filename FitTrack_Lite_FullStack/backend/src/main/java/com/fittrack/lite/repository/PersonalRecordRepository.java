package com.fittrack.lite.repository;
import com.fittrack.lite.model.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface PersonalRecordRepository extends JpaRepository<PersonalRecord,Long>{List<PersonalRecord> findByUser(User user); Optional<PersonalRecord> findByUserAndExerciseName(User user,String exerciseName); }