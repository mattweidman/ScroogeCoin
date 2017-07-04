package chooper.me.blockchain;

import java.security.MessageDigest;

/**
 * Message that holds contents in a block chain or transaction.
 */
public abstract class Message {
	
	MessageDigest hashAlgo;
	
	/**
	 * @param hashAlgo hashing algorithm, preferably SHA-256
	 */
	public Message(MessageDigest hashAlgo) {
		this.hashAlgo = hashAlgo;
	}
	
	/**
	 * @return serialized representation of everything inside this message
	 */
	public abstract byte[] serialize();
	
	/**
	 * @return hash of this message
	 */
	public byte[] getHash() {
		return hashAlgo.digest(serialize());
	}

}
