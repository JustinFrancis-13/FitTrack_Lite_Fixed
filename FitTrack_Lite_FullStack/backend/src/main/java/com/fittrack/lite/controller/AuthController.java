package com.fittrack.lite.controller;
import com.fittrack.lite.model.User; import com.fittrack.lite.repository.UserRepository; import com.fittrack.lite.security.SessionAuthFilter; import jakarta.servlet.http.*; import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.http.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository users; private final PasswordEncoder encoder;
 public AuthController(UserRepository u,PasswordEncoder e){users=u;encoder=e;}
 public record RegisterReq(@NotBlank String name,@Email @NotBlank String email,@NotBlank @Size(min=6) String password){}
 public record LoginReq(@Email @NotBlank String email,@NotBlank String password){}
 @PostMapping("/register") public ResponseEntity<?> register(@Valid @RequestBody RegisterReq r,HttpSession s){
  if(users.findByEmail(r.email().toLowerCase()).isPresent()) return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message","Email already registered"));
  User u=new User();u.setName(r.name());u.setEmail(r.email().toLowerCase());u.setPassword(encoder.encode(r.password()));u=users.save(u);s.setAttribute(SessionAuthFilter.USER_ID,u.getId());
  return ResponseEntity.ok(Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail()));
 }
 @PostMapping("/login") public ResponseEntity<?> login(@Valid @RequestBody LoginReq r,HttpSession s){
  var u=users.findByEmail(r.email().toLowerCase()).orElse(null);
  if(u==null||!encoder.matches(r.password(),u.getPassword())) return ResponseEntity.status(401).body(Map.of("message","Invalid email or password"));
  s.setAttribute(SessionAuthFilter.USER_ID,u.getId()); return ResponseEntity.ok(Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail()));
 }
 @GetMapping("/me") public ResponseEntity<?> me(HttpServletRequest req){
  HttpSession s=req.getSession(false);
  Object id=s==null?null:s.getAttribute(SessionAuthFilter.USER_ID);
  var u=id==null?null:users.findById((Long)id).orElse(null);
  if(u==null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message","Not logged in"));
  return ResponseEntity.ok(Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail()));
 }
 @PostMapping("/logout") public ResponseEntity<?> logout(HttpSession s){s.invalidate();return ResponseEntity.ok(Map.of("message","Logged out"));}
}