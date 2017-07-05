package chooper.me.entities;

import org.junit.Test;

public class Tests {

	@Test
	public void testUser() {
		User user = new User();
		byte[] message = "Hello world".getBytes();
		byte[] signature = user.sign(message);
		assert user.verify(message, signature);
		
		byte[] fakeMessage = "Hello world!".getBytes();
		assert !user.verify(fakeMessage, signature);
	}

}
