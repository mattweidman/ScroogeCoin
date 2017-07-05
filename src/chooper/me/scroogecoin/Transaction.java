package chooper.me.scroogecoin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import chooper.me.blockchain.Message;
import chooper.me.blockchain.Utilities;
import chooper.me.entities.User;

public class Transaction extends Message {
	
	/** List of users receiving currency and the amount they get */
	private List<CoinCreate> createdCoins;
	
	/** Ids of coins consumed */
	private List<CoinConsume> consumedCoins;
	
	/** Signatures of payers */
	private HashMap<User, byte[]> signatures;
	
	public Transaction() {
		createdCoins = new ArrayList<>();
		consumedCoins = new LinkedList<>();
		signatures = new HashMap<>();
	}

	@Override
	public byte[] serialize() {
		
		// initialize array
		int numCreateBytes = (Double.SIZE + Integer.SIZE) * createdCoins.size() / 8;
		int numConsumeBytes = (Integer.SIZE * 3) * consumedCoins.size() / 8;
		byte[] serial = new byte[numCreateBytes + numConsumeBytes];
		int j = 0; // index in serial
		
		// serialize createCoins
		for (CoinCreate cc : createdCoins) {
			
			byte[] valueBytes = Utilities.doubleToBytes(cc.value);
			System.arraycopy(valueBytes, 0, serial, j, valueBytes.length);
			j += valueBytes.length;
			
			byte[] userBytes = Utilities.intToBytes(cc.recipient.hashCode());
			System.arraycopy(userBytes, 0, serial, j, userBytes.length);
			j += userBytes.length;
		}
		
		// serialize consumedCoins
		for (CoinConsume cc : consumedCoins) {
			
			byte[] idBytes = Utilities.intToBytes(cc.blockId);
			System.arraycopy(idBytes, 0, serial, j, idBytes.length);
			j += idBytes.length;
			
			byte[] indexBytes = Utilities.intToBytes(cc.index);
			System.arraycopy(indexBytes, 0, serial, j, indexBytes.length);
			j += indexBytes.length;
			
			byte[] userBytes = Utilities.intToBytes(cc.user.hashCode());
			System.arraycopy(userBytes, 0, serial, j, userBytes.length);
			j += userBytes.length;
		}
		
		return serial;
	}
	
	/**
	 * Create a new coin and give it to someone.
	 * @param value value of new coin
	 * @param recipient user to give coin to
	 */
	public void createCoin(double value, User recipient) {
		CoinCreate coin = new CoinCreate(value, recipient);
		createdCoins.add(coin);
	}
	
	/**
	 * Consume a coin during transaction.
	 * @param blockId id of block where coin was made
	 * @param index index inside that block
	 * @param user user whose block it was (for signing)
	 */
	public void consumeCoin(int blockId, int index, User user) {
		CoinConsume coin = new CoinConsume(blockId, index, user);
		consumedCoins.add(coin);
	}
	
	/**
	 * Signs this transaction. All signing should happen AFTER
	 * all payments/recipients have been registered.
	 * @param user user signing this transaction
	 */
	public void sign(User user) {
		byte[] signature = user.sign(this.serialize());
		signatures.put(user, signature);
	}
	
	/**
	 * Verify whether a user signed this transaction.
	 * @param user signer in question
	 * @return true if user signed this transaction
	 */
	public boolean verifySigned(User user) {
		return signatures.containsKey(user) &&
			user.verify(this.serialize(), signatures.get(user));
	}
	
	/**
	 * Get all the signatures on this transaction.
	 * @return user -> signature mapping
	 */
	public Map<User, byte[]> getSignatures() {
		return signatures;
	}
	
	/**
	 * Get the record of a coin created in this transaction.
	 * @param index where in the list coin was gotten
	 * @return value and recipient
	 * @throws IndexOutOfBoundsException - if the index is out of range
	 */
	public CoinCreate getCoin(int index) throws IndexOutOfBoundsException {
		return createdCoins.get(index);
	}
	
	/**
	 * @return list of coins created in transaction
	 */
	public List<CoinCreate> getCreatedCoins() {
		return createdCoins;
	}
	
	/**
	 * @return list of coins consumed in transaction
	 */
	public List<CoinConsume> getConsumedCoins() {
		return consumedCoins;
	}

}
