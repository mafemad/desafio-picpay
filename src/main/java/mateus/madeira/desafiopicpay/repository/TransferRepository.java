package mateus.madeira.desafiopicpay.repository;

import mateus.madeira.desafiopicpay.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransferRepository extends JpaRepository<Transfer, UUID> {
}
