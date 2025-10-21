package mateus.madeira.desafiopicpay.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autenticação", description = "Endpoints para login e registro de usuários")
public class AuthController {

    private final AuthService authService;



    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @Operation(summary = "Autenticar um usuário",
            description = "Realiza o login com email e senha, retornando um token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (email ou senha em branco)",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas (email ou senha incorretos)",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);

        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Registrar uma nova carteira (usuário)",
            description = "Cria uma nova carteira/usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carteira criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WalletResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de registro inválidos (ex: email, senha fraca, campos em branco)",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflito de dados (Email ou CPF/CNPJ já existente)",
                    content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<WalletResponseDTO> registerWallet(@RequestBody @Valid CreateWalletRequestDTO dto){
        Wallet newWallet = authService.createWallet(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new WalletResponseDTO(newWallet));
    }
}
