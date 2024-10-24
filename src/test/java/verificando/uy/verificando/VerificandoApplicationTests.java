package verificando.uy.verificando;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import verificando.uy.verificando.services.PeripheralNodeService;

@SpringBootTest
class VerificandoApplicationTests {

	@Test
	void contextLoads() {
	}
	@MockBean
	private PeripheralNodeService peripheralNodeService;
}
