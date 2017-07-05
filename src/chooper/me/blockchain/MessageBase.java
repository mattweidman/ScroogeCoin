package chooper.me.blockchain;

import java.security.MessageDigest;

/**
 * Block in a blockchain that doesn't point to anything.
 */
public class MessageBase extends MessageHashable {
	
	public MessageBase(MessageDigest hashAlgo) {
		super(hashAlgo);
	}

	@Override
	public byte[] serialize() {
		return new byte[] {};
	}

}
