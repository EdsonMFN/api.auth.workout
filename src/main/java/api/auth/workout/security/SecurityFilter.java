package api.auth.workout.security;

import api.auth.workout.domains.entitys.Usuario;
import api.auth.workout.domains.repositorys.RepositoryUsuario;
import api.auth.workout.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private RepositoryUsuario repositoryUsuario;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var token = clearTorken(request);

        if (token != null){
            var login = tokenService.validationToken(token);
            Usuario usuario = repositoryUsuario.findByNomeUsuario(login);

            var autorization = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(autorization);
        }
        filterChain.doFilter(request,response);
    }
    private String clearTorken(HttpServletRequest request){

        var autorizationHeader = request.getHeader("Authorization");
        if (autorizationHeader == null){
            return null;
        }
        return autorizationHeader.replace("Bearer ", "");

    }
}
