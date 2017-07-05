package chooper.me.blockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Linked list data structure with hash pointers.
 */
public class BlockChain {
	
	/** HashPointer to original block. */
	private HashPointer binding;
	
	/** Counter for providing different ids for each block */
	private int idCounter = 0;
	
	/** Hashing algorithm for this blockchain */
	private MessageDigest hashAlgo;
	
	public BlockChain() {
		try {
			hashAlgo = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException nsae) {
			hashAlgo = null;
			System.err.println("SHA-256 algorithm could not be found.");
			nsae.printStackTrace();
			System.exit(1);
		}
		binding = new HashPointer(new MessageBase(hashAlgo));
	}
	
	/**
	 * Get the final hash pointer that binds the rest of the chain.
	 */
	public HashPointer getBinding() {
		return binding;
	}
	
	/**
	 * Append a new block to this blockchain.
	 * @param contents contents of new block
	 */
	public void append(Message contents) {
		MessageBlock block = new MessageBlock(idCounter, binding, contents, hashAlgo);
		idCounter++;
		binding = new HashPointer(block);
	}
	
	/**
	 * @return the generated SHA-256 instance
	 */
	public MessageDigest getHashingAlgorithm() {
		return hashAlgo;
	}

}
