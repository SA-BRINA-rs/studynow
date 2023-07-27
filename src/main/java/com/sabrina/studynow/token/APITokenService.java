package com.sabrina.studynow.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class APITokenService {

    private final APITokenRepository APITokenRepository;

    @Autowired
    public APITokenService(APITokenRepository APITokenRepository) {
        this.APITokenRepository = APITokenRepository;
    }

    public APIToken getById(String id) {
    	return APITokenRepository.findById(id).orElse(null);
    }

    public ResponseEntity<String> add(APIToken APIToken) {
        APITokenRepository.save(APIToken);
        return ResponseEntity.ok("Device token added successfully");
    }

    public void delete(String id) {
    	APITokenRepository.deleteById(id);
    }

    public ResponseEntity<String> update(APIToken APIToken) {
    	APITokenRepository.save(APIToken);
    	return ResponseEntity.ok("Device token updated successfully");
    }

    public List<APIToken> getAll() {
    	return APITokenRepository.findAll();
    }

    public APIToken getAPIToken(Long id) {
        return APITokenRepository.findByUserId(id).orElse(new APITokenNullObject());
    }
}
