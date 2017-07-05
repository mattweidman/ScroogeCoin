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
	
	@Test
	public void testLedger() {
		User admin = new User();
		Ledger ledger = new Ledger(admin);
		
		// create $100
		Transaction t = new Transaction();
		t.createCoin(100, admin);
		assert !ledger.createMoney(t);
		t.sign(admin);
		assert t.verifySigned(admin);
		assert ledger.createMoney(t);
		
		// give $75 to Alice
		User alice = new User();
		t = new Transaction();
		t.createCoin(75, alice); // $75
		t.consumeCoin(0, 0, admin); // $100
		t.sign(admin);
		t.sign(alice);
		assert !ledger.submitTransaction(t);
		t.createCoin(25, admin); // need $75 + $25 = $100
		assert !ledger.submitTransaction(t);
		t.sign(admin); // need to sign again because message chanaged
		assert ledger.submitTransaction(t);
		
		// Alice tries to give to both Bob and Chuck
		User bob = new User();
		User chuck = new User();
		t = new Transaction();
		t.createCoin(75, bob);
		t.createCoin(75, chuck);
		t.consumeCoin(1, 0, alice);
		t.sign(alice);
		assert !ledger.submitTransaction(t);
		
		// using a bitcoin that isn't yours
		t = new Transaction();
		t.consumeCoin(1, 0, bob);
		t.createCoin(75, chuck);
		t.sign(bob);
		assert !ledger.submitTransaction(t);
		
		// using a coin that is spent already
		t = new Transaction();
		t.consumeCoin(1, 0, alice);
		t.createCoin(75, bob);
		t.sign(alice);
		assert ledger.submitTransaction(t);
		t = new Transaction();
		t.consumeCoin(1, 0, alice);
		t.createCoin(75, chuck);
		t.sign(alice);
		assert !ledger.submitTransaction(t);
		
		// using coins that were never created
		t = new Transaction();
		t.consumeCoin(4, 8, alice);
		t.createCoin(75, chuck);
		t.sign(alice);
		assert !ledger.submitTransaction(t);
	}

}
