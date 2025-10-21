package mateus.madeira.desafiopicpay.service;

import jakarta.transaction.Transactional;
import mateus.madeira.desafiopicpay.dto.auth.CreateWalletRequestDTO;
import mateus.madeira.desafiopicpay.dto.wallet.*;
import mateus.madeira.desafiopicpay.entity.Wallet;
import mateus.madeira.desafiopicpay.exceptions.InsuficientBalanceException;
import mateus.madeira.desafiopicpay.exceptions.WalletDataAlreadyExistsException;
import mateus.madeira.desafiopicpay.exceptions.WalletHasBalanceException;
import mateus.madeira.desafiopicpay.exceptions.WalletNotFoundException;
import mateus.madeira.desafiopicpay.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public List<WalletResponseDTO> getAll() {
        var wallets =this.walletRepository.findAll();

        return wallets.stream().map(WalletResponseDTO::new).toList();
    }

    public WalletResponseDTO getWalletById(Long id) {

        var wallet = walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException(id));

        return  new WalletResponseDTO(wallet);
    }

    public WalletResponseDTO deposit(WalletDepositDTO depositDto, Long walletId){

        var wallet = walletRepository.findById(walletId).orElseThrow(
                () -> new WalletNotFoundException(walletId)
        );

        wallet.credit(depositDto.amount());

        walletRepository.save(wallet);

        return new WalletResponseDTO(wallet);
    }

    public WalletResponseDTO withdraw(Long walletId, WalletWithdrawDTO withdrawDto){
        var wallet = walletRepository.findById(walletId).orElseThrow(
                () -> new WalletNotFoundException(walletId)
        );

       if(!wallet.isBalanceEqualOrGreaterThan(withdrawDto.amount())){
           throw new InsuficientBalanceException(withdrawDto.amount(), wallet.getBalance());
       }

       wallet.debit(withdrawDto.amount());
       walletRepository.save(wallet);

       return new WalletResponseDTO(wallet);

    }

    @Transactional
    public WalletResponseDTO updateWallet(Long id, UpdateWalletRequestDTO updateDto) {
        var wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));


        if(updateDto.cpfCnpj() != null) {
            var cpfCnpjLimpo = updateDto.cpfCnpj().replaceAll("[^0-9]", "");
            walletRepository.findByCpfCnpj(cpfCnpjLimpo)
                    .filter(w -> !w.getId().equals(id))
                    .ifPresent(w -> {
                        throw new WalletDataAlreadyExistsException("Cpf/Cnpj already exists");
                    });
        }

        if(updateDto.email() != null) {
            walletRepository.findByEmail(updateDto.email())
                    .filter(w -> !w.getId().equals(id))
                    .ifPresent(w -> {
                        throw new WalletDataAlreadyExistsException("Email already exists");
                    });
        }

        if (updateDto.fullName() != null && !updateDto.fullName().isBlank()) {
            wallet.setFullName(updateDto.fullName());
        }

        if (updateDto.email() != null && !updateDto.email().isBlank()) {
            wallet.setEmail(updateDto.email());
        }

        if (updateDto.cpfCnpj() != null && !updateDto.cpfCnpj().isBlank()) {
            wallet.setCpfCnpj(updateDto.cpfCnpj());
        }

        if (updateDto.password() != null && !updateDto.password().isBlank()) {
            wallet.setPassword(updateDto.password());
        }

        var updatedWallet = walletRepository.save(wallet);

        return new WalletResponseDTO(updatedWallet);
    }

    @Transactional
    public void deleteWallet(Long id) {
        var wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));

        if (wallet.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new WalletHasBalanceException(id);
        }

        walletRepository.delete(wallet);
    }

}
