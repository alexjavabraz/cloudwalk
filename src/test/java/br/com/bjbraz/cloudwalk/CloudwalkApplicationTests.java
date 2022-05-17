package br.com.bjbraz.cloudwalk;

import br.com.bjbraz.cloudwalk.util.FileProcessorUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class CloudwalkApplicationTests {

	@Test
	void lineTokenNameKilled() {
		String line = "  1:08 Kill: 3 2 6: Isgalamido killed Mocinha by MOD_ROCKET";
		String resultOk = "Mocinha";
		String result = FileProcessorUtil.getTokenAtPoint(7, line);
		assertEquals(resultOk, result);
	}

	@Test
	void lineTokenNamePlayer() {
		String line = "  1:08 Kill: 3 2 6: Isgalamido killed Mocinha by MOD_ROCKET";
		String resultOk = "Isgalamido";
		String result = FileProcessorUtil.getTokenAtPoint(5, line);
		assertEquals(resultOk, result);
	}

}
