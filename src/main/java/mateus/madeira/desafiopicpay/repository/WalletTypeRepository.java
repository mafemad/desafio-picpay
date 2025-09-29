package mateus.madeira.desafiopicpay.repository;

import mateus.madeira.desafiopicpay.entity.WalletType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTypeRepository extends JpaRepository<WalletType, Long> {
}
