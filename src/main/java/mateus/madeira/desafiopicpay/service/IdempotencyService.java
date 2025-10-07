package mateus.madeira.desafiopicpay.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@EnableScheduling
public class IdempotencyService {

    private final Map<String, Long> recentTransfers = new ConcurrentHashMap<>();

    private static final long TIME_WINDOW_MS = 60_000;

    public boolean tryRegisterTransfer(Long senderId, Long receiverId, BigDecimal value) {
        String key = buildKey(senderId, receiverId,value);
        long now =  System.currentTimeMillis();

        if(recentTransfers.containsKey(key)){
            long lastTime = recentTransfers.get(key);
            if((now - lastTime) < TIME_WINDOW_MS){
                return false;
            }
        }

        recentTransfers.put(key,now);
        return true;
    }

    private String buildKey(Long senderId, Long receiverId, BigDecimal value) {
        return senderId + "-" + receiverId + "-" + value.stripTrailingZeros().toPlainString();
    }

    @Scheduled(fixedRate = 300_000)
    public void cleanupOldTransfers() {
        long now = System.currentTimeMillis();
        recentTransfers.entrySet().removeIf(entry -> (now - entry.getValue()) > TIME_WINDOW_MS);
    }
}
