package com.sylvie.usuarios.controller;

import com.sylvie.usuarios.dto.LoginRequestDTO;
import com.sylvie.usuarios.dto.LoginResponseDTO;
import com.sylvie.usuarios.dto.RestriccionDTO;
import com.sylvie.usuarios.dto.UsuarioRegistroDTO;
import com.sylvie.usuarios.entity.Restriccion;
import com.sylvie.usuarios.entity.Usuario;
import com.sylvie.usuarios.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST /api/usuarios/registro
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody UsuarioRegistroDTO registroDTO) {
        try {
            Usuario usuario = usuarioService.registrar(registroDTO);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // POST /api/usuarios/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginDTO) {
        try {
            LoginResponseDTO response = usuarioService.login(loginDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    // GET /api/usuarios/perfil/{id}
    @GetMapping("/perfil/{id}")
    public ResponseEntity<?> obtenerPerfil(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/usuarios/perfil/email/{email}
    @GetMapping("/perfil/email/{email}")
    public ResponseEntity<?> obtenerPerfilPorEmail(@PathVariable String email) {
        try {
            Usuario usuario = usuarioService.buscarPorEmail(email);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/usuarios/restricciones/{usuarioId}
    @PostMapping("/restricciones/{usuarioId}")
    public ResponseEntity<?> agregarRestriccion(@PathVariable Long usuarioId,
                                                @RequestBody RestriccionDTO restriccionDTO) {
        try {
            Restriccion restriccion = usuarioService.agregarRestriccion(usuarioId, restriccionDTO);
            return ResponseEntity.ok(restriccion);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/usuarios/restricciones/{usuarioId}
    @GetMapping("/restricciones/{usuarioId}")
    public ResponseEntity<?> listarRestricciones(@PathVariable Long usuarioId) {
        try {
            List<RestriccionDTO> restricciones = usuarioService.listarRestricciones(usuarioId);
            return ResponseEntity.ok(restricciones);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/usuarios/restricciones/{usuarioId}/{ingrediente}
    @DeleteMapping("/restricciones/{usuarioId}/{ingrediente}")
    public ResponseEntity<?> eliminarRestriccion(@PathVariable Long usuarioId,
                                                 @PathVariable String ingrediente) {
        try {
            usuarioService.eliminarRestriccion(usuarioId, ingrediente);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}