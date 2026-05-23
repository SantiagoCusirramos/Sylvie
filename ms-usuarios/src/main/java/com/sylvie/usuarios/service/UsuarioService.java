package com.sylvie.usuarios.service;

import com.sylvie.usuarios.dto.LoginRequestDTO;
import com.sylvie.usuarios.dto.LoginResponseDTO;
import com.sylvie.usuarios.dto.RestriccionDTO;
import com.sylvie.usuarios.dto.UsuarioRegistroDTO;
import com.sylvie.usuarios.entity.Restriccion;
import com.sylvie.usuarios.entity.Usuario;
import com.sylvie.usuarios.repository.RestriccionRepository;
import com.sylvie.usuarios.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RestriccionRepository restriccionRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository usuarioRepository, RestriccionRepository restriccionRepository) {
        this.usuarioRepository = usuarioRepository;
        this.restriccionRepository = restriccionRepository;
    }

    public Usuario registrar(UsuarioRegistroDTO registroDTO) {
        if (usuarioRepository.existsByEmail(registroDTO.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        return usuarioRepository.save(usuario);
    }

    public LoginResponseDTO login(LoginRequestDTO loginDTO) {
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Email o contraseña incorrectos"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Email o contraseña incorrectos");
        }

        String token = "token-temporal-" + System.currentTimeMillis();

        return new LoginResponseDTO(token, usuario.getEmail(), usuario.getNombre());
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Restriccion agregarRestriccion(Long usuarioId, RestriccionDTO restriccionDTO) {
        buscarPorId(usuarioId);

        Restriccion restriccion = new Restriccion();
        restriccion.setUsuarioId(usuarioId);
        restriccion.setIngrediente(restriccionDTO.getIngrediente());
        restriccion.setTipo(restriccionDTO.getTipo());

        return restriccionRepository.save(restriccion);
    }

    public List<RestriccionDTO> listarRestricciones(Long usuarioId) {
        buscarPorId(usuarioId);

        return restriccionRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(r -> {
                    RestriccionDTO dto = new RestriccionDTO();
                    dto.setIngrediente(r.getIngrediente());
                    dto.setTipo(r.getTipo());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void eliminarRestriccion(Long usuarioId, String ingrediente) {
        restriccionRepository.deleteByUsuarioIdAndIngrediente(usuarioId, ingrediente);
    }
}