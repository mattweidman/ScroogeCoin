package chooper.me.scroogecoin;

import static org.junit.Assert.*;

import org.junit.Test;

import chooper.me.blockchain.Utilities;
import chooper.me.entities.User;

public class Tests {

	@Test
	public void testTransaction() {
		Transaction tx = new Transaction();
		assertArrayEquals(new byte[] {}, tx.serialize());
		
		User matt = new User();
		tx.createCoin(3.2, matt);
		CoinCreate coin0 = tx.getCoin(0);
		assertEquals(matt, coin0.recipient);
		assertEquals(3.2, coin0.value, 0.001);
		
		User ihavenofriends = new User();
		tx.createCoin(4.9, ihavenofriends);
		CoinCreate coin1 = tx.getCoin(1);
		assertEquals(ihavenofriends, coin1.recipient);
		assertEquals(4.9, coin1.value, 0.001);
		
		User billyG = new User();
		tx.consumeCoin(0, 1, billyG);
		CoinConsume coin2 = tx.getConsumedCoins().get(0);
		assertEquals(0, coin2.blockId);
		assertEquals(1, coin2.index);
		assertEquals(billyG, coin2.user);
		
		byte[] expBytes = Utilities.concatenateArrays(
				Utilities.doubleToBytes(3.2),
				Utilities.intToBytes(matt.hashCode()),
				Utilities.doubleToBytes(4.9),
				Utilities.intToBytes(ihavenofriends.hashCode()),
				Utilities.intToBytes(0),
				Utilities.intToBytes(1),
				Utilities.intToBytes(billyG.hashCode()));
		assertArrayEquals(expBytes, tx.serialize());
	}

}
