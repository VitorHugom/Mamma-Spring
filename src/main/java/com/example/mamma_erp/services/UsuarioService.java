package com.example.mamma_erp.services;

import com.example.mamma_erp.entities.usuario.Usuario;
import com.example.mamma_erp.entities.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getUsuariosBloqueados() {
        return usuarioRepository.findByStatus("bloqueado");
    }
}
