package chooper.me.scroogecoin;

import chooper.me.entities.User;

/**
 * Coin being gifted.
 */
public class CoinCreate {
	
	/** Number of ScroogeCoins in this transaction */
	public final double value;
	
	/** Recipient of transaction */
	public final User recipient;
	
	/**
	 * @param value Number of ScroogeCoins in this transaction
	 * @param recipient Recipient of transaction
	 */
	public CoinCreate(double value, User recipient) {
		this.value = value;
		this.recipient = recipient;
	}

}
