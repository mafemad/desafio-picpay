package mateus.madeira.desafiopicpay.service;

import mateus.madeira.desafiopicpay.controller.dto.CreateWalletRequestDTO;
import mateus.madeira.desafiopicpay.entity.Wallet;
import mateus.madeira.desafiopicpay.exceptions.WalletDataAlreadyExistsException;
import mateus.madeira.desafiopicpay.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletRequestDTO walletDTO) {

        Optional<Wallet> walletDb = walletRepository.findByCpfCnpjOrEmail(walletDTO.cpfCnpj(), walletDTO.email());

        if(walletDb.isPresent()){
            throw new WalletDataAlreadyExistsException("Cpf/Cnpj or Email already exists");
        }

        return walletRepository.save(walletDTO.toWallet());
    }
}
