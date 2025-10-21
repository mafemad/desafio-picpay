package mateus.madeira.desafiopicpay.dto.wallet;

import io.swagger.v3.oas.annotations.media.Schema;
import mateus.madeira.desafiopicpay.entity.Wallet;
import mateus.madeira.desafiopicpay.entity.WalletType;

import java.math.BigDecimal;

public record WalletResponseDTO(
        @Schema(description = "ID único da carteira", example = "1")
        Long id,

        @Schema(description = "Nome completo do usuário", example = "John Doe")
        String fullName,

        @Schema(description = "CPF ou CNPJ do usuário (sem formatação)", example = "12345678901")
        String cpfCnpj,

        @Schema(description = "Email do usuário", example = "john.doe@example.com")
        String email,

        @Schema(description = "Saldo atual da carteira", example = "150.75")
        BigDecimal balance,

        @Schema(description = "Tipo de carteira (USER ou MERCHANT)", example = "USER")
        WalletType walletType
) {
    public WalletResponseDTO(Wallet wallet) {
        this(wallet.getId(),
                wallet.getFullName(),
                wallet.getCpfCnpj(),
                wallet.getEmail(),
                wallet.getBalance(),
                wallet.getWalletType());
    }
}
