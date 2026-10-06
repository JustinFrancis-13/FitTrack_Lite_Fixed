package com.fittrack.lite.model;
import com.fasterxml.jackson.annotation.JsonIgnore; import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="users")
public class User {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Column(nullable=false,unique=true) private String email;
 @Column(nullable=false) private String password;
 private LocalDateTime createdAt=LocalDateTime.now();
 public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;} @JsonIgnore public String getPassword(){return password;}
 public void setName(String v){name=v;} public void setEmail(String v){email=v;} public void setPassword(String v){password=v;}
}