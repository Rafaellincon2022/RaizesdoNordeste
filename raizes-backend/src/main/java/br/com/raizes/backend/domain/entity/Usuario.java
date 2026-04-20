package br.com.raizes.backend.domain.entity;

import br.com.raizes.backend.domain.enums.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @Column(name = "consentimento_lgpd", nullable = false)
    private boolean consentimentoLgpd;

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public boolean isConsentimentoLgpd() { return consentimentoLgpd; }
    public void setConsentimentoLgpd(boolean consentimentoLgpd) { this.consentimentoLgpd = consentimentoLgpd; }
}
