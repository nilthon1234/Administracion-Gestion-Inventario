package com.gestion.today.service.interfaces;
import com.gestion.today.service.http.request.SeparationRequest;
import com.gestion.today.service.http.response.ClientSeparationResponse;

public interface SeparationSlipperService {

      void save(SeparationRequest request)throws Exception;
      ClientSeparationResponse getClientSeparationById(String id);
}
