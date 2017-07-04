package chooper.me.blockchain;

import java.security.MessageDigest;

/**
 * Block in a blockchain that doesn't point to anything.
 */
public class BaseMessage extends Message {
	
	public BaseMessage(MessageDigest hashAlgo) {
		super(hashAlgo);
	}

	@Override
	public byte[] serialize() {
		return new byte[] {};
	}

}
