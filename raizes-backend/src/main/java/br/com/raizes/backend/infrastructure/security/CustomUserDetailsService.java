package br.com.raizes.backend.infrastructure.security;

import br.com.raizes.backend.infrastructure.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Essa classe é usada pelo Spring Security para buscar o usuário no banco
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Repository que acessa os usuários no banco
    private final UsuarioRepository usuarioRepository;

    // Construtor para injetar o repository
    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Método chamado automaticamente pelo Spring quando alguém tenta fazer login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Busca o usuário pelo email (username aqui é o email)
        var usuario = usuarioRepository.findByEmail(username)
                // Se não encontrar, lança erro
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado."));

        // Converte o usuário do banco para um formato que o Spring entende
        return User.builder()
                // Define o username (email)
                .username(usuario.getEmail())

                // Define a senha (já criptografada no banco)
                .password(usuario.getSenhaHash())

                // Define a role/permissão do usuário
                // Ex: ROLE_ADMIN ou ROLE_USER
                .authorities(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name()))

                // Finaliza a construção do usuário
                .build();
    }
}