package mateus.madeira.desafiopicpay.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mateus.madeira.desafiopicpay.controller.dto.TransferDTO;
import mateus.madeira.desafiopicpay.entity.Transfer;
import mateus.madeira.desafiopicpay.entity.Wallet;
import mateus.madeira.desafiopicpay.exceptions.*;
import mateus.madeira.desafiopicpay.repository.TransferRepository;
import mateus.madeira.desafiopicpay.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TransferService {


    private final TransferRepository transferRepository;
    private final NotificationService notificationService;
    private final AuthorizationService authorizationService;
    private final WalletRepository walletRepository;
    private final IdempotencyService idempotencyService;


    public TransferService(TransferRepository transferRepository, NotificationService notificationService, AuthorizationService authorizationService, WalletRepository walletRepository, IdempotencyService idempotencyService) {
        this.transferRepository = transferRepository;
        this.notificationService = notificationService;
        this.authorizationService = authorizationService;
        this.walletRepository = walletRepository;
        this.idempotencyService = idempotencyService;
    }

    @Transactional
    public Transfer transfer(TransferDTO transferDto) {
        var sender = walletRepository.findById(transferDto.payer())
                .orElseThrow(() -> new WalletNotFoundException(transferDto.payer()));

        var receiver = walletRepository.findById(transferDto.payee())
                .orElseThrow(() -> new WalletNotFoundException(transferDto.payee()));

        boolean canProceed = idempotencyService.tryRegisterTransfer(
                sender.getId(),
                receiver.getId(),
                transferDto.value()
        );

        if (!canProceed) {
            throw new DuplicateTransferException("Identical transfer detected in the last 60 seconds.");
        }

        validateTransfer(transferDto, sender);

        sender.debit(transferDto.value());
        receiver.credit(transferDto.value());

        Transfer transfer = new Transfer(sender, receiver, transferDto.value());

        walletRepository.save(sender);
        walletRepository.save(receiver);
        Transfer transferResult = transferRepository.save(transfer);

        CompletableFuture.runAsync(() -> notificationService.sendNotification(transferResult));

        return transferResult;
    }

    private void validateTransfer(TransferDTO transferDto, Wallet sender) {
        if(!sender.isTransferAllowedForWalletType()){
            throw new TransferNotAllowedForWalletTypeException();
        }

        if(!sender.isBalanceEqualOrGreaterThan(transferDto.value())){
            throw new InsuficientBalanceException(transferDto.value(), sender.getBalance());
        }

        if(!authorizationService.isAuthorized(transferDto)){
            throw new TransferNotAuthorizedException();
        }
    }


}
