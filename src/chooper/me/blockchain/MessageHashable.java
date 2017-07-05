package chooper.me.blockchain;

import java.security.MessageDigest;

/**
 * Message that holds contents in a block chain or transaction.
 */
public abstract class MessageHashable extends Message {
	
	MessageDigest hashAlgo;
	
	/**
	 * @param hashAlgo hashing algorithm, preferably SHA-256
	 */
	public MessageHashable(MessageDigest hashAlgo) {
		this.hashAlgo = hashAlgo;
	}
	
	/**
	 * @return hash of this message
	 */
	public byte[] getHash() {
		return hashAlgo.digest(serialize());
	}

}
