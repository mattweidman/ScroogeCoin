package chooper.me.scroogecoin;

import chooper.me.entities.User;

/**
 * Id of coin being consumed.
 */
public class CoinConsume {
	
	/** Id of transaction block */
	public final int blockId;
	
	/** Index inside the transaction block */
	public final int index;
	
	/** User giving up the coin */
	public final User user;
	
	/**
	 * @param blockId id of transaction block
	 * @param index index inside the transaction block
	 * @param user user giving up the coin
	 */
	public CoinConsume(int blockId, int index, User user) {
		this.blockId = blockId;
		this.index = index;
		this.user = user;
	}

}
