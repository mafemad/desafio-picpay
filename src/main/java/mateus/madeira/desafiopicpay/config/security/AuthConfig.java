package mateus.madeira.desafiopicpay.config.security;

import mateus.madeira.desafiopicpay.repository.WalletRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthConfig implements UserDetailsService {


    private final WalletRepository walletRepository;

    public AuthConfig(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return walletRepository.findWalletByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
