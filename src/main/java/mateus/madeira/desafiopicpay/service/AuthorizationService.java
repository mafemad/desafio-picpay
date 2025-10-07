package mateus.madeira.desafiopicpay.service;

import mateus.madeira.desafiopicpay.client.AuthorizationClient;
import mateus.madeira.desafiopicpay.client.dto.AuthorizationResponse;
import mateus.madeira.desafiopicpay.dto.transfer.TransferDTO;
import mateus.madeira.desafiopicpay.exceptions.PicPayException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationService {

    private final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public Boolean isAuthorized(TransferDTO transfer) {

        ResponseEntity<List<AuthorizationResponse>> response = authorizationClient.isAuthorized();

        if(response.getStatusCode().isError()){
            throw new PicPayException();
        }
        List<AuthorizationResponse> authList = response.getBody();

        if (authList != null && !authList.isEmpty()) {
            return authList.getFirst().authorized();
        }

        return false;
    }

}
