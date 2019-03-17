package cn.letsky.wechat.dao;

import java.security.SecureRandom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FunctionTest {

	
	@Test
	public void random() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] byte1 = new byte[20];
		secureRandom.nextBytes(byte1);
		System.out.println(secureRandom.nextInt(32));
	}
}
