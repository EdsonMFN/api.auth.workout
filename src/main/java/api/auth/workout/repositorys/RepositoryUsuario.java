package api.auth.workout.repositorys;

import api.auth.workout.entitys.acesso.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryUsuario extends JpaRepository<Usuario,Long>{

    Usuario findByNomeUsuario(String nomeUsuario);

    Usuario getReferenceByNomeUsuarioAndSenha(String nomeUsuario, String senha);


    Usuario getReferenceByNomeUsuario(String nomeUsuario);

    List<Usuario> findAllByNomeUsuario(String nomeUsuario);
}
