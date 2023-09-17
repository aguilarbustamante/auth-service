package es.vive.libre.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import es.vive.libre.rest.external.services.AuthExternalService;
import es.vive.libre.rest.models.AuthResponse;

@RestController
public class AuthController {
	
	private final AuthExternalService externalService;
	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	public AuthController(AuthExternalService externalService) {
		this.externalService = externalService;
	}
	
	@GetMapping("/get-token")
	private ResponseEntity<AuthResponse> authenticate() {
		try {
			
			logger.debug("Request authenticate service ...");
			
			AuthResponse callAuthService = externalService.callAuthService();
			return new ResponseEntity<AuthResponse>(callAuthService, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error in request Auth service", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
