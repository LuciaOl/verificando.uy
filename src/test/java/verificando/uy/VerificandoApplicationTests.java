package verificando.uy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import verificando.uy.services.PeripheralNodeService;

@SpringBootTest
class VerificandoApplicationTests {
	@MockBean
	private PeripheralNodeService peripheralNodeService;

	@Test
	void contextLoads() {
	}

}
