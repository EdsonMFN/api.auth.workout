package api.auth.workout.rest.response;


import api.auth.workout.domains.model.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ResponseUsuario {

    private UsuarioDTO usuarioDTO;
    private String token;
    private String tokenRefresh;

    public ResponseUsuario(UsuarioDTO usuarioDTO,String token){

        this.usuarioDTO = usuarioDTO;
        this.token = token;
    }

    public ResponseUsuario(UsuarioDTO usuarioDTO) {
        this.usuarioDTO = usuarioDTO;
    }
}
