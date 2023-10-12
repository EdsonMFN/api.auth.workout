package api.auth.workout.service;


import api.auth.workout.domains.entitys.Usuario;
import api.auth.workout.domains.repositorys.RepositoryUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutorizationService implements UserDetailsService {

    @Autowired
    RepositoryUsuario repositoryUsuario;

    @Override
    public Usuario loadUserByUsername(String nomeUsuario) throws UsernameNotFoundException {
        return repositoryUsuario.findByNomeUsuario(nomeUsuario);
    }
}
