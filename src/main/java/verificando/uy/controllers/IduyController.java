//package verificando.uy.controllers;
//
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.core.oidc.user.OidcUser;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.view.RedirectView;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/iduy")
//public class IduyController {
//
//    private final ClientRegistrationRepository clientRegistrationRepository;
//
//    public IduyController(ClientRegistrationRepository clientRegistrationRepository) {
//        this.clientRegistrationRepository = clientRegistrationRepository;
//    }
//
//    // Iniciar el flujo de autenticación
//    @GetMapping("/login")
//    public RedirectView login() {
//        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("iduruguay");
//        String redirectUri = "/oauth2/authorization/" + clientRegistration.getRegistrationId();
//        return new RedirectView(redirectUri);
//    }
//
//    @GetMapping("/user-info")
//    public Map<String, Object> handleOAuth2Callback(@AuthenticationPrincipal OidcUser oidcUser) {
//        Map<String, Object> userInfo = new HashMap<>();
//
//        if (oidcUser != null) {
//            userInfo.put("name", oidcUser.getFullName());
//            userInfo.put("email", oidcUser.getEmail());
//
//            userInfo.put("attributes", oidcUser.getAttributes());
//        }
//
//        return userInfo; // Devolver la información del usuario como JSON
//    }
//}
