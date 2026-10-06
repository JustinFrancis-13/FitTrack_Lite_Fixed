package com.fittrack.lite.config;
import com.fittrack.lite.model.WorkoutPlan; import com.fittrack.lite.repository.WorkoutPlanRepository; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration;
@Configuration public class DataSeeder {
 @Bean CommandLineRunner seed(WorkoutPlanRepository r){return args->{if(r.count()==0){r.save(new WorkoutPlan("Push Day","Chest • Shoulders • Triceps",6,"Strength"));r.save(new WorkoutPlan("Pull Day","Back • Biceps",5,"Strength"));r.save(new WorkoutPlan("Legs Day","Quads • Hamstrings • Glutes",6,"Lower Body"));r.save(new WorkoutPlan("Full Body","Total Body Workout",8,"Complete"));}};}
}