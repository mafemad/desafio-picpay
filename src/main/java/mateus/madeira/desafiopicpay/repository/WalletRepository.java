package mateus.madeira.desafiopicpay.repository;

import mateus.madeira.desafiopicpay.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
