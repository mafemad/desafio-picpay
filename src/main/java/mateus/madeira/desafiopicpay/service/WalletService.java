package mateus.madeira.desafiopicpay.service;

import mateus.madeira.desafiopicpay.controller.dto.CreateWalletRequestDTO;
import mateus.madeira.desafiopicpay.controller.dto.WalletDepositDTO;
import mateus.madeira.desafiopicpay.controller.dto.WalletResponseDTO;
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

    public List<WalletResponseDTO> getAll() {
        var wallets =this.walletRepository.findAll();

        return wallets.stream().map(WalletResponseDTO::new).toList();
    }

    public WalletResponseDTO getWalletById(Long id) {

        var wallet = walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException(id));

        return  new WalletResponseDTO(wallet);
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
