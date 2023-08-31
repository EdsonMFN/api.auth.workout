package api.auth.workout.service;


import api.auth.workout.entitys.acesso.Usuario;
import api.auth.workout.repositorys.RepositoryUsuario;
import api.auth.workout.rest.dto.UsuarioDTO;
import api.auth.workout.rest.request.RequestUsuario;
import api.auth.workout.rest.response.ResponseUsuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private RepositoryUsuario repositoryUsuario;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AutorizationService autorizacaoService;

    public ResponseUsuario acessarLogin(RequestUsuario requestUsuario){

        Usuario usuario = autorizacaoService.loadUserByUsername(requestUsuario.getNomeUsuario());

        var usuarioAcesso = new UsernamePasswordAuthenticationToken(requestUsuario.getNomeUsuario(),requestUsuario.getSenha());
        var acesso = authenticationManager.authenticate(usuarioAcesso);
        var token = tokenService.gerarToken(usuario);
        var refresh = tokenService.gerarRefreshToken(usuario);
        SecurityContextHolder.getContext().setAuthentication(acesso);

        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .idUsuario(usuario.getId())
                .nomeUsuario(usuario.getNomeUsuario())
                .senha(usuario.getSenha())
                .role(usuario.getRole())
                .build();

        ResponseUsuario responseUsuario = new ResponseUsuario();
        responseUsuario.setUsuarioDTO(usuarioDTO);
        responseUsuario.setToken(token);
        responseUsuario.setTokenRefresh(refresh);

        return responseUsuario;
    }
    public ResponseUsuario autenticarUsuario(HttpServletRequest requestToken){

        UsuarioDTO usuarioDTO = null;

        if (requestToken != null){
            var login = tokenService.validationToken(clearTorken(requestToken));
            Usuario usuario = repositoryUsuario.findByNomeUsuario(login);

            var autorization = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(autorization);
            usuarioDTO = UsuarioDTO.builder().nomeUsuario(usuario.getNomeUsuario()).idUsuario(usuario.getId()).build();
        }
        return new ResponseUsuario(usuarioDTO);
    }
    private String clearTorken(HttpServletRequest request){

        var autorizationHeader = request.getHeader("Authorization");
        if (autorizationHeader == null){
            return null;
        }
        return autorizationHeader.replace("Bearer ", "");

    }
}
