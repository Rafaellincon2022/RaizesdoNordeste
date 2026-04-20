package br.com.raizes.backend.infrastructure.security;

import br.com.raizes.backend.domain.entity.Estoque;
import br.com.raizes.backend.domain.entity.Produto;
import br.com.raizes.backend.domain.entity.Unidade;
import br.com.raizes.backend.domain.entity.Usuario;
import br.com.raizes.backend.domain.enums.Role;
import br.com.raizes.backend.infrastructure.repository.EstoqueRepository;
import br.com.raizes.backend.infrastructure.repository.ProdutoRepository;
import br.com.raizes.backend.infrastructure.repository.UnidadeRepository;
import br.com.raizes.backend.infrastructure.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(
            UsuarioRepository usuarioRepository,
            UnidadeRepository unidadeRepository,
            ProdutoRepository produtoRepository,
            EstoqueRepository estoqueRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario();
                admin.setNome("Admin");
                admin.setEmail("admin@raizes.com");
                admin.setSenhaHash(passwordEncoder.encode("Senha@123"));
                admin.setRole(Role.ADMIN);
                admin.setConsentimentoLgpd(true);
                usuarioRepository.save(admin);

                Usuario cliente = new Usuario();
                cliente.setNome("Cliente Teste");
                cliente.setEmail("cliente@raizes.com");
                cliente.setSenhaHash(passwordEncoder.encode("Senha@123"));
                cliente.setRole(Role.CLIENTE);
                cliente.setConsentimentoLgpd(true);
                usuarioRepository.save(cliente);
            }

            if (unidadeRepository.count() == 0) {
                Unidade unidade = new Unidade();
                unidade.setNome("Unidade Recife Centro");
                unidade.setCidade("Recife");
                unidade.setAtiva(true);
                unidadeRepository.save(unidade);
            }

            if (produtoRepository.count() == 0) {
                Produto cuscuz = new Produto();
                cuscuz.setNome("Cuscuz Recheado");
                cuscuz.setPreco(java.math.BigDecimal.valueOf(18.90));
                cuscuz.setAtivo(true);
                produtoRepository.save(cuscuz);

                Produto tapioca = new Produto();
                tapioca.setNome("Tapioca de Frango");
                tapioca.setPreco(java.math.BigDecimal.valueOf(16.50));
                tapioca.setAtivo(true);
                produtoRepository.save(tapioca);
            }

            if (estoqueRepository.count() == 0) {
                Unidade unidade = unidadeRepository.findAll().get(0);
                for (Produto produto : produtoRepository.findAll()) {
                    Estoque estoque = new Estoque();
                    estoque.setUnidade(unidade);
                    estoque.setProduto(produto);
                    estoque.setQuantidade(30);
                    estoqueRepository.save(estoque);
                }
            }
        };
    }
}
