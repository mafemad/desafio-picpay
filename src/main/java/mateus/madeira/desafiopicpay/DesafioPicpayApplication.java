package mateus.madeira.desafiopicpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DesafioPicpayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesafioPicpayApplication.class, args);
    }

}
