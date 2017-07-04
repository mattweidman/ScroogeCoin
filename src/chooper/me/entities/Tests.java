package chooper.me.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class Tests {

	@Test
	public void testUser() {
		User user = new User();
		byte[] message = "Hello world".getBytes();
		byte[] signature = user.sign(message);
		assert user.verify(message, signature);
		
		byte[] fakeMessage = "fake signature".getBytes();
		assert !user.verify(fakeMessage, signature);
	}

}
