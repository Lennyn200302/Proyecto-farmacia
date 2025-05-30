package com.mycompany.controller;

import com.mycompany.modelo.Rol;
import com.mycompany.modelo.Usuario;
import com.mycompany.repository.UsuarioRepository;
import com.mycompany.security.JwtTokenUtil;
import com.mycompany.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepo;

    @PostMapping("/registro")
    public Usuario register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        Rol rolAsignado = username.toUpperCase().endsWith("A") ? Rol.ADMIN : Rol.USER;
        return usuarioService.registrar(username,password,Set.of(rolAsignado)
        );
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> body) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.get("username"), body.get("password"))
        );

        UserDetails user = (UserDetails) auth.getPrincipal();
        return jwtTokenUtil.generateToken(user);
    }

}