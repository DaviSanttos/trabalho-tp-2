package com.sistema.coxinha.controller;

import com.sistema.coxinha.dto.LoginRequestDTO;
import com.sistema.coxinha.dto.LoginResponseDTO;
import com.sistema.coxinha.model.Cliente;
import com.sistema.coxinha.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final ClienteService clienteService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        Optional<Cliente> clienteOpt = clienteService.login(request.getEmail(), request.getSenha());

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            return ResponseEntity.ok(LoginResponseDTO.builder()
                    .clienteId(cliente.getId())
                    .nome(cliente.getNome())
                    .email(cliente.getEmail())
                    .sucesso(true)
                    .mensagem("Login realizado com sucesso")
                    .build());
        }

        return ResponseEntity.badRequest().body(LoginResponseDTO.builder()
                .sucesso(false)
                .mensagem("Email ou senha inválidos")
                .build());
    }
}
