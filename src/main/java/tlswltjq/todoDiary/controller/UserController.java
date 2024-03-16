package tlswltjq.todoDiary.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tlswltjq.todoDiary.dto.ResponseDTO;
import tlswltjq.todoDiary.dto.UserDTO;
import tlswltjq.todoDiary.entity.UserEntity;
import tlswltjq.todoDiary.security.TokenProvider;
import tlswltjq.todoDiary.service.UserService;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO){
        log.info("가입 요청");
        try{
            if(userDTO == null || userDTO.getPassword() == null){
                throw new RuntimeException("Invalid Password value.");
            }
            UserEntity user = UserEntity.builder()
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .build();
            UserEntity registeredUser = userService.create(user);
            UserDTO responseUserDTO = UserDTO.builder()
                    .id(registeredUser.getId())
                    .username(registeredUser.getUsername())
                    .build();
            return ResponseEntity.ok(responseUserDTO);
        }catch (Exception e){
            ResponseDTO<?> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.ok(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){
        log.info("로그인 요청");
        UserEntity user = userService.getByCredentials(userDTO.getUsername(), userDTO.getPassword(), passwordEncoder);
        if(user!= null){
            final  String token = tokenProvider.create(user);
            final UserDTO responseUserDTO = UserDTO.builder()
                    .username(user.getUsername())
                    .id(user.getId())
                    .token(token)
                    .build();
            return ResponseEntity.ok(responseUserDTO);
        }else{
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login failed")
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
