package mateus.madeira.desafiopicpay.config;

import mateus.madeira.desafiopicpay.entity.WalletType;
import mateus.madeira.desafiopicpay.repository.WalletTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final WalletTypeRepository walletTypeRepository;

    public DataLoader(WalletTypeRepository walletTypeRepository) {
        this.walletTypeRepository = walletTypeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Itera sobre os valores do enum
        Arrays.stream(WalletType.WalletTypeEnum.values()).forEach(enumValue -> {
            Long typeId = enumValue.getWalletType().getId();

            if (!walletTypeRepository.existsById(typeId)) {
                walletTypeRepository.save(enumValue.getWalletType());
                System.out.println("Populando WalletType: " + enumValue.name());
            }
        });
    }
}
