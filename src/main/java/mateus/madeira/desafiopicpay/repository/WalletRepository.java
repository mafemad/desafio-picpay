package mateus.madeira.desafiopicpay.repository;

import mateus.madeira.desafiopicpay.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {


    Optional<Wallet> findByCpfCnpj(String cpfCnpj);

    Optional<Wallet> findByEmail(String email);

    Optional<UserDetails> findWalletByEmail(String username);
}
