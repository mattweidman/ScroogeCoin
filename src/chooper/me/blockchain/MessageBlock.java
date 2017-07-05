package chooper.me.blockchain;

import java.security.MessageDigest;

/**
 * Fundamental unit of a blockchain.
 */
public class MessageBlock extends MessageHashable {
	
	/** ID of block */
	private int blockID;
	
	/** pointer and hash to previous block */
	private HashPointer previous;
	
	/** contents of this block */
	private Message contents;
	
	/**
	 * Create a new block for a blockchain.
	 * @param id id of block
	 * @param prev has and pointer to previous block
	 * @param cont message holding contents of block
	 * @param hashAlgo hashing algorithm, preferably SHA-256
	 */
	public MessageBlock(int id, HashPointer prev, Message cont, MessageDigest hashAlgo) {
		super(hashAlgo);
		blockID = id;
		contents = cont;
		previous = prev;
	}
	
	@Override
	public byte[] serialize() {
		byte[] idBytes = Utilities.intToBytes(blockID);
		byte[] refBytes = Utilities.intToBytes(previous.getPointer().hashCode());
		byte[] hashBytes = previous.getHash();
		byte[] contentBytes = contents.serialize();
		return Utilities.concatenateArrays(idBytes, refBytes, hashBytes, contentBytes);
	}
	
	/** get ID of block */
	public int getID() { return blockID; }
	
	/** get pointer and hash to previous block */
	public HashPointer getPrevious() { return previous; }
	
	/** get contents of this block */
	public Message getContents() { return contents; }

}
