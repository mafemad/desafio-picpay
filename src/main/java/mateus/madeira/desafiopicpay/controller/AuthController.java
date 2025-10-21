package mateus.madeira.desafiopicpay.controller;

import jakarta.validation.Valid;
import mateus.madeira.desafiopicpay.dto.auth.CreateWalletRequestDTO;
import mateus.madeira.desafiopicpay.dto.auth.LoginRequest;
import mateus.madeira.desafiopicpay.dto.auth.LoginResponse;
import mateus.madeira.desafiopicpay.dto.wallet.WalletResponseDTO;
import mateus.madeira.desafiopicpay.entity.Wallet;
import mateus.madeira.desafiopicpay.repository.WalletRepository;
import mateus.madeira.desafiopicpay.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;



    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<WalletResponseDTO> registerWallet(@RequestBody @Valid CreateWalletRequestDTO dto){
        Wallet newWallet = authService.createWallet(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new WalletResponseDTO(newWallet));
    }
}
