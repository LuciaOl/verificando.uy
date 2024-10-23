package controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import java.net.URI;
import java.net.URISyntaxException;


@RestController
@RequestMapping("/")
public class IduyController {

    private static final String AUTHORIZATION_URI = "https://auth-testing.iduruguay.gub.uy/oidc/v1/authorize";
    private static final String TOKEN_URI = "https://auth-testing.iduruguay.gub.uy/oidc/v1/token";
    private static final String USER_INFO_URI = "https://auth-testing.iduruguay.gub.uy/oidc/v1/userinfo";
    private static final String LOGOUT_URI = "https://auth-testing.iduruguay.gub.uy/oidc/v1/logout";

    private static final String CLIENT_ID = "890192";
    private static final String CLIENT_SECRET = "457d52f181bf11804a3365b49ae4d29a2e03bbabe74997a2f510b179";
    private static final String AUTHORIZATION_GRANT_TYPE = "authorization_code";
    private static final String REDIRECT_URI = "http://localhost:8080";
    private static final String SCOPE = "openid profile email";

    private final RestTemplate restTemplate = new RestTemplate();

 
    @GetMapping("/login")
    public RedirectView login() {
        return new RedirectView(AUTHORIZATION_URI + "?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&scope=" + SCOPE + "&response_type=code");
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/")
    public Map<String, Object> handleCallback(@RequestParam("code") String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", AUTHORIZATION_GRANT_TYPE);
        body.add("code", code);
        body.add("redirect_uri", REDIRECT_URI);
        body.add("client_id", CLIENT_ID);
        body.add("client_secret", CLIENT_SECRET);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(TOKEN_URI, HttpMethod.POST, request, Map.class);

        Map<String, Object> json_salida = response.getBody();
        
        

        String id_token = (String) json_salida.get("id_token");
        String access_token = (String) json_salida.get("access_token");
    

        Map<String, Object> user_info = fetchUserInfo(access_token);
        
        user_info.put("id_token", id_token);

        return user_info;
    
    }


    
    @SuppressWarnings("unchecked")
    public Map<String, Object> fetchUserInfo(String access_token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + access_token);
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String cleanedUrl = limpiarParametros(USER_INFO_URI);
        ResponseEntity<Map> response = restTemplate.exchange(cleanedUrl, HttpMethod.GET, entity, Map.class);
        
        return response.getBody();
    }
    
    private String limpiarParametros(String url) {
        try {
            URI uri = new URI(url);
            URI uriLimpia = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), null, null);
            return uriLimpia.toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return url;
        }
    }
    

    // Cerrar sesión
    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam("id_token") String idToken) {
        String logoutUrl = LOGOUT_URI + "?id_token_hint=" + idToken;
        ResponseEntity<String> response = restTemplate.getForEntity(logoutUrl, String.class);
        return response;
    }

}
