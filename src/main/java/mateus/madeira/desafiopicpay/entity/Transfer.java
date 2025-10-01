package mateus.madeira.desafiopicpay.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_transfer")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "wallet_sender_id")
    private Wallet sender;

    @ManyToOne()
    @JoinColumn(name = "wallet_receiver_id")
    private Wallet receiver;

    @Column(name = "value")
    private BigDecimal value;
}
