package com.pavila.TaskProyect.config.security.filter;

import com.pavila.TaskProyect.exception.ObjectNotFoundException;
import com.pavila.TaskProyect.model.entity.security.User;
import com.pavila.TaskProyect.service.IUserService;
import com.pavila.TaskProyect.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Entro en el filtro JWT Authentication Filter");
        //obtener el http request llamado Authorization
        String authorizationHeader = request.getHeader("Authorization");
        //si no tiene texto o si no empieza con Bearer y un espacio, mandamos el FilterChain
        if(!StringUtils.hasText(authorizationHeader)|| !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        //2. Obtener token JWT desde el encabezado
        String jwt = authorizationHeader.split(" ")[1];

        //3. Obtener el subject/username desde el token
        // esta accion a su vez valida el formato del token, firma y fecha de expiración
        String username = jwtService.extractUsername(jwt);

        //4. Setear el objeto authentication dentro de security context holder
        UserDetails user = userService.findOneByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User not found. Username: " + username));
        //crear el authentication y pasarle el principal, credenciales y los authorities
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, user.getAuthorities()
        );
        //detalles del usuario logueado
        authToken.setDetails(new WebAuthenticationDetails(request));

        //setear el contextHolder
        SecurityContextHolder.getContext().setAuthentication(authToken);


        //5. Ejecutar el registro de filtros
        filterChain.doFilter(request, response);

    }
}
