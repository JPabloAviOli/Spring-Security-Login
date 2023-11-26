package com.pavila.TaskProyect.service.auth;

import com.pavila.TaskProyect.exception.ObjectNotFoundException;
import com.pavila.TaskProyect.model.dto.RegisteredUserRequest;
import com.pavila.TaskProyect.model.dto.RegisteredUserResponse;
import com.pavila.TaskProyect.model.dto.TaskResponse;
import com.pavila.TaskProyect.model.dto.UserProfileResponse;
import com.pavila.TaskProyect.model.dto.auth.AuthenticationRequest;
import com.pavila.TaskProyect.model.dto.auth.AuthenticationResponse;
import com.pavila.TaskProyect.model.entity.Task;
import com.pavila.TaskProyect.model.entity.security.User;
import com.pavila.TaskProyect.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;


    public RegisteredUserResponse registerOneUserAuth(RegisteredUserRequest userDtoRequest){

        User user = userService.registerOneUser(userDtoRequest);

        RegisteredUserResponse registeredUser = RegisteredUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        registeredUser.setJwt(jwt);

        return registeredUser;
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("authorities", user.getAuthorities());

        return extraClaims;
    }

    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        //creamos el objeto para el login
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()
        );
        //hacemos el proceso de login
        authenticationManager.authenticate(authentication);
        //obtenemos los detalles del usuario que se acaba de login
        User userDetails = userService.findOneByUsername(authRequest.getUsername()).get();
        //creamos y devolvemos el token en nuestro dto.
        String jwt = jwtService.generateToken(userDetails,generateExtraClaims(userDetails));

        return AuthenticationResponse.builder()
                .jwt(jwt)
                .build();
    }

    public boolean validateToken(String jwt) {
        try {
            //sí logra extraer el username exitosamente
            jwtService.extractUsername(jwt);
            return true;
        }catch (Exception e){ //si lanza un error
            System.out.println(e.getMessage());//para saber qué error nos da
            return false;
        }

    }

    public UserProfileResponse findLoggedInUser() {

        UsernamePasswordAuthenticationToken auth = (
                UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String username = (String) auth.getPrincipal();
        User user = userService.findOneByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User not found. Username: " + username));

        List<String> taskNames = user.getTask().stream()
                .map(Task::getTitle)
                .toList();

        return UserProfileResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .role(user.getRole())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .taskName(taskNames)
                .authorities(user.getRole().getPermissions())
                .build();
    }
}
