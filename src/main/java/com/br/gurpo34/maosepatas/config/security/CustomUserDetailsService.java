package com.br.gurpo34.maosepatas.config.security;

import com.br.gurpo34.maosepatas.entity.Administrador;
import com.br.gurpo34.maosepatas.repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdministradorRepository administradorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Administrador admin = administradorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Administrador não encontrado com o e-mail: " + email));

        return new User(
                admin.getEmail(),
                admin.getSenha(),
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
    }

}
