package mateus.madeira.desafiopicpay.service;

import mateus.madeira.desafiopicpay.controller.dto.CreateWalletRequestDTO;
import mateus.madeira.desafiopicpay.controller.dto.WalletDepositDTO;
import mateus.madeira.desafiopicpay.controller.dto.WalletWithdrawDTO;
import mateus.madeira.desafiopicpay.entity.Wallet;
import mateus.madeira.desafiopicpay.exceptions.InsuficientBalanceException;
import mateus.madeira.desafiopicpay.exceptions.WalletDataAlreadyExistsException;
import mateus.madeira.desafiopicpay.exceptions.WalletNotFoundException;
import mateus.madeira.desafiopicpay.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
        var cpfCnpjLimpo = walletDTO.cpfCnpj().replaceAll("[^0-9]", "");
        var entity = walletDTO.toWallet();
        entity.setCpfCnpj(cpfCnpjLimpo);
        return walletRepository.save(entity);
    }

    public List<Wallet> getAll() {
        return this.walletRepository.findAll();
    }

    public Optional<Wallet> getWalletById(Long id) {
        return this.walletRepository.findById(id);
    }

    public Wallet deposit(WalletDepositDTO depositDto, Long walletId){

        var wallet = walletRepository.findById(walletId).orElseThrow(
                () -> new WalletNotFoundException(walletId)
        );

        wallet.credit(depositDto.amount());

        walletRepository.save(wallet);

        return wallet;
    }

    public Wallet withdraw(Long walletId, WalletWithdrawDTO withdrawDto){
        var wallet = walletRepository.findById(walletId).orElseThrow(
                () -> new WalletNotFoundException(walletId)
        );

       if(!wallet.isBalanceEqualOrGreaterThan(withdrawDto.amount())){
           throw new InsuficientBalanceException(withdrawDto.amount(), wallet.getBalance());
       }

       wallet.debit(withdrawDto.amount());
       walletRepository.save(wallet);

       return wallet;

    }
}
