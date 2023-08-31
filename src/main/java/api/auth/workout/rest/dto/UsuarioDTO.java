package api.auth.workout.rest.dto;


import api.auth.workout.entitys.acesso.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTO {

    private Long idUsuario;

    private String nomeUsuario;

    private String senha;

    private UserRole role;

}

