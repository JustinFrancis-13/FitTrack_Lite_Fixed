package com.fittrack.lite.repository;
import com.fittrack.lite.model.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface BodyMeasurementRepository extends JpaRepository<BodyMeasurement,Long>{List<BodyMeasurement> findByUserOrderByRecordedOnAscIdAsc(User user); Optional<BodyMeasurement> findTopByUserOrderByRecordedOnDescIdDesc(User user); }