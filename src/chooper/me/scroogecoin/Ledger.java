package chooper.me.scroogecoin;

import java.util.HashSet;

import chooper.me.blockchain.BlockChain;
import chooper.me.entities.User;

/**
 * Keeps a history of all transactions.
 */
public class Ledger {
	
	/** Data structure that holds all the transactions */
	private BlockChain blockchain;
	
	/** Record of coins previously consumed */
	private HashSet<CoinConsume> coinsConsumed;
	
	/** User who can verify the creation of money */
	private User admin;
	
	/**
	 * @param admin User who can verify the creation of money
	 */
	public Ledger(User admin) {
		blockchain = new BlockChain();
		coinsConsumed = new HashSet<>();
		this.admin = admin;
	}
	
	/**
	 * Checks a transaction for the following:
	 * (1) Consumed coins were created in a previous transaction.
	 * (2) Consumed coins were not already consumed previously.
	 * (3) Amount consumed equals amount created.
	 * (4) Consumed coins are signed.
	 * @param t transaction
	 * @return true if transaction is valid
	 */
	public boolean isValid(Transaction t) {
		// 1. Consumed coins were created in a previous transaction.
		for (CoinConsume coin : t.getConsumedCoins()) {
			try {
				Transaction prevT = (Transaction) blockchain.getMessage(coin.blockId);
				CoinCreate prevCoin = prevT.getCoin(coin.index);
				if (prevCoin.recipient != coin.user) return false;
			} catch (IndexOutOfBoundsException e) {
				return false;
			}
		}
		
		// 2. Consumed coins were not already consumed previously.
		for (CoinConsume coin : t.getConsumedCoins()) {
			if (coinsConsumed.contains(coin)) return false;
		}
		
		// 3. Amount consumed equals amount created.
		double valueConsumed = 0;
		for (CoinConsume coin : t.getConsumedCoins()) {
			Transaction prevT = (Transaction) blockchain.getMessage(coin.blockId);
			CoinCreate prevCoin = prevT.getCoin(coin.index);
			double value = prevCoin.value;
			valueConsumed += value;
		}
		
		double valueCreated = 0;
		for (CoinCreate coin : t.getCreatedCoins())
			valueCreated += coin.value;
		
		if (valueConsumed != valueCreated) return false;
		
		// 4. Consumed coins are signed.
		HashSet<User> users = new HashSet<>();
		for (CoinConsume coin : t.getConsumedCoins()) 
			users.add(coin.user);
		for (User user : users)
			if (!t.verifySigned(user)) return false;
		
		return true;
	}
	
	/**
	 * Adds transaction to the blockchain history.
	 * @param t transaction
	 * @return true if transaction went through; false if it was invalid
	 */
	public boolean submitTransaction(Transaction t) {
		if (isValid(t)) {
			blockchain.append(t);
			coinsConsumed.addAll(t.getConsumedCoins());
			return true;
		}
		return false;
	}
	
	/**
	 * Creates money. Transaction must contain the admin's signature.
	 * @param t transaction without any consumed coins
	 * @return true if attempt was valid, false otherwise
	 */
	public boolean createMoney(Transaction t) {
		if (!t.getSignatures().isEmpty() && t.verifySigned(admin)) {
			blockchain.append(t);
			return true;
		}
		return false;
	}

}
