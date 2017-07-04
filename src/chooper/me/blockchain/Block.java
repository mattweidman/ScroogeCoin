package chooper.me.blockchain;

import java.security.MessageDigest;

/**
 * Fundamental unit of a blockchain.
 */
public class Block extends Message {
	
	/** ID of block */
	private int blockID;
	
	/** pointer and hash to previous block */
	private HashPointer previous;
	
	/** contents of this block */
	private byte[] contents;
	
	/**
	 * Create a new block for a blockchain.
	 * @param id id of block
	 * @param prev has and pointer to previous block
	 * @param cont contents of block
	 * @param hashAlgo hashing algorithm, preferably SHA-256
	 */
	public Block(int id, HashPointer prev, byte[] cont, MessageDigest hashAlgo) {
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
		return Utilities.concatenateArrays(idBytes, refBytes, hashBytes, contents);
	}
	
	/** get ID of block */
	public int getID() { return blockID; }
	
	/** get pointer and hash to previous block */
	public HashPointer getPrevious() { return previous; }
	
	/** get contents of this block */
	public byte[] getContents() { return contents; }

}
