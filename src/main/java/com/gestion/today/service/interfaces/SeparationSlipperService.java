package com.gestion.today.service.interfaces;
import com.gestion.today.service.http.request.SeparationRequest;

public interface SeparationSlipperService {

      void save(SeparationRequest request)throws Exception;
}
