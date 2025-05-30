package com.mycompany.service;

import com.mycompany.modelo.Rol;
import com.mycompany.modelo.Usuario;
import com.mycompany.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private PasswordEncoder encoder;

    public Usuario registrar(String username, String password, Set<Rol> roles) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(encoder.encode(password));
        usuario.setRoles(roles);
        return usuarioRepo.save(usuario);
    }

}
