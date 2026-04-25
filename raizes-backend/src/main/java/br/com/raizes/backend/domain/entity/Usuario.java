package br.com.raizes.backend.domain.entity;

import br.com.raizes.backend.domain.enums.Role;
import jakarta.persistence.*;

// essa classe representa um usuário do sistema
@Entity

// nome da tabela no banco
@Table(name = "usuarios")
public class Usuario {

    // id do usuário (chave primária)
    @Id

    // o banco gera automaticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nome do usuário
    // não pode ser nulo
    @Column(nullable = false)
    private String nome;

    // email do usuário
    // não pode ser nulo e não pode repetir
    @Column(nullable = false, unique = true)
    private String email;

    // senha do usuário (armazenada como hash, não texto puro)
    // nome da coluna no banco é diferente
    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    // tipo do usuário (admin, cliente, etc)
    // salvo como texto no banco
    @Enumerated(EnumType.STRING)

    @Column(nullable = false)
    private Role role;

    // indica se o usuário aceitou os termos (LGPD)
    // não pode ser nulo
    @Column(name = "consentimento_lgpd", nullable = false)
    private boolean consentimentoLgpd;

    // getter do id
    public Long getId() { 
        return id; 
    }

    // pega o nome
    public String getNome() { 
        return nome; 
    }

    // define o nome
    public void setNome(String nome) { 
        this.nome = nome; 
    }

    // pega o email
    public String getEmail() { 
        return email; 
    }

    // define o email
    public void setEmail(String email) { 
        this.email = email; 
    }

    // pega a senha (hash)
    public String getSenhaHash() { 
        return senhaHash; 
    }

    // define a senha (já deve vir criptografada)
    public void setSenhaHash(String senhaHash) { 
        this.senhaHash = senhaHash; 
    }

    // pega o tipo do usuário
    public Role getRole() { 
        return role; 
    }

    // define o tipo do usuário
    public void setRole(Role role) { 
        this.role = role; 
    }

    // verifica se aceitou LGPD
    public boolean isConsentimentoLgpd() { 
        return consentimentoLgpd; 
    }

    // define consentimento LGPD
    public void setConsentimentoLgpd(boolean consentimentoLgpd) { 
        this.consentimentoLgpd = consentimentoLgpd; 
    }
}