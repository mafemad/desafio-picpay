package mateus.madeira.desafiopicpay.service;

import jakarta.transaction.Transactional;
import mateus.madeira.desafiopicpay.config.security.TokenConfig;
import mateus.madeira.desafiopicpay.dto.auth.CreateWalletRequestDTO;
import mateus.madeira.desafiopicpay.dto.auth.LoginRequest;
import mateus.madeira.desafiopicpay.dto.auth.LoginResponse;
import mateus.madeira.desafiopicpay.entity.Wallet;
import mateus.madeira.desafiopicpay.exceptions.WalletDataAlreadyExistsException;
import mateus.madeira.desafiopicpay.repository.WalletRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthService {


    private final WalletRepository walletRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;
    private final PasswordEncoder passwordEncoder;


    public AuthService(WalletRepository walletRepository, AuthenticationManager authenticationManager, TokenConfig tokenConfig, PasswordEncoder passwordEncoder) {
        this.walletRepository = walletRepository;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
        this.passwordEncoder = passwordEncoder;
    }

    public Wallet createWallet(CreateWalletRequestDTO walletDTO) {

        var cpfCnpjLimpo = walletDTO.cpfCnpj().replaceAll("[^0-9]", "");
        var entity = walletDTO.toWallet();

        walletRepository.findByCpfCnpj(cpfCnpjLimpo)
                .ifPresent(w -> {
                    throw new WalletDataAlreadyExistsException("Cpf/Cnpj already exists");
                });

        walletRepository.findByEmail(walletDTO.email())
                .ifPresent(w -> {
                    throw new WalletDataAlreadyExistsException("Email already exists");
                });

        entity.setCpfCnpj(cpfCnpjLimpo);
        entity.setPassword(passwordEncoder.encode(walletDTO.password()));

        return walletRepository.save(entity);
    }

    public LoginResponse login(LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken userAndPass = new
                UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());

        Authentication authentication = authenticationManager.authenticate(userAndPass);

        Wallet wallet = (Wallet) authentication.getPrincipal();
        String token = tokenConfig.generateToken(wallet);

        return new LoginResponse(token);
    }
}
