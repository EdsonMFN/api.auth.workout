package api.auth.workout.rest.request;


import api.auth.workout.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUsuario {

    private Long idUsuario;
    private String nomeUsuario;
    private String senha;
    private UserRole role;

}
