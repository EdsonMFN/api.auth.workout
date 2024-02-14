package api.auth.workout.rest.controller;



import api.auth.workout.rest.request.RequestUsuario;
import api.auth.workout.rest.response.ResponseUsuario;
import api.auth.workout.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/acesso")
@CrossOrigin(origins = "*")
public class AcessoController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<ResponseUsuario> login(@RequestBody RequestUsuario requestUsuario){
        ResponseUsuario responseUsuario = usuarioService.acessarLogin(requestUsuario);
        return ResponseEntity.ok(responseUsuario);
    }
    @GetMapping("/authUsuario")
    public ResponseEntity<ResponseUsuario> autenticarUsuario(HttpServletRequest requestToken){
        ResponseUsuario responseUsuario = usuarioService.autenticarUsuario(requestToken);
        return ResponseEntity.ok(responseUsuario);
    }
}
