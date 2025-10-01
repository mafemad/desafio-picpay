package mateus.madeira.desafiopicpay.service;

import mateus.madeira.desafiopicpay.client.NotificationClient;
import mateus.madeira.desafiopicpay.entity.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationClient notificationClient;

    public NotificationService(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    public void sendNotification(Transfer transfer){
        try{
            var response = notificationClient.sendNotification(transfer);

            if(response.getStatusCode().isError()){
                log.error("Error while sending notification");
            }
        } catch (Exception e) {
            log.error("Error while sending notification", e);
        }
    }
}
