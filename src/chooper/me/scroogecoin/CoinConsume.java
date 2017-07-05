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
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CoinConsume)) return false;
		CoinConsume otherCoin = (CoinConsume) obj;
		return otherCoin.blockId == blockId && otherCoin.index == index
				&& otherCoin.user == user;
	}
	
	@Override
	public String toString() {
		return blockId + "(" + index + ")" + user;
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

}
