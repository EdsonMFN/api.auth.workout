package api.auth.workout.domains.repositorys;

import api.auth.workout.domains.entitys.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryUsuario extends JpaRepository<Usuario,Long>{

    Usuario findByNomeUsuario(String nomeUsuario);

}
