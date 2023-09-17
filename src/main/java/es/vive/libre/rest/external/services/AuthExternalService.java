package es.vive.libre.rest.external.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.vive.libre.rest.models.AuthExternalResponse;
import es.vive.libre.rest.models.AuthRequest;
import es.vive.libre.rest.models.AuthResponse;

@Service
public class AuthExternalService {

	@Value( "${vivelibre.rest.endpoint}")
	private String authEndpoint;

	@Value( "${vivelibre.user}")
	private String authUser;
	
	@Value( "${vivelibre.password}")
	private String authPass;
	
	private final RestTemplate rest = new RestTemplate();
	
	@Autowired
    private ObjectMapper mapper;
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
	
	public AuthExternalService() {
	
	}
	
	/**
	 * Calls the external service to authenticate
	 * @return the response obtained from the auth service
	 * @throws Exception if any error ocurred
	 */
	public AuthResponse callAuthService() throws Exception {
		
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(getRequestBody(), headers);
        ResponseEntity<AuthExternalResponse> responseEntity = rest.postForEntity(authEndpoint, requestEntity, AuthExternalResponse.class);
		return getResponse(responseEntity.getBody());
	}
	
	/**
	 * Creates the JSON body to send in the request
	 * the authorization user and password are defined
	 * in the properties of the service
	 * 
	 * @return
	 * @throws JsonProcessingException
	 */
	private String getRequestBody() throws JsonProcessingException {
		
		AuthRequest request = new AuthRequest();
		request.setPassword(authPass);
		request.setUsername(authUser);
		
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
	}
	
	/**
	 * Convert the response of the external service
	 * to the required model
	 * 
	 * @param externalResponse
	 * @return
	 */
	private AuthResponse getResponse(AuthExternalResponse externalResponse) {
		AuthResponse response = new AuthResponse();
		response.setToken(externalResponse.getToken());
		response.setDate(getCurrentDateRepresentation());
		return response;
	}
	
	/**
	 * Get the current date with the format MMMM dd, yyyy
	 * 
	 * @return
	 */
	private String getCurrentDateRepresentation() {
		return formatter.format(LocalDate.now());
	}
}
