package chooper.me.blockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Linked list data structure with hash pointers.
 */
public class BlockChain {
	
	/** HashPointer to original block. */
	private HashPointer binding;
	
	/** Hashing algorithm for this blockchain */
	private MessageDigest hashAlgo;
	
	/** Way of finding message given its block id */
	private ArrayList<Message> idToMessages;
	
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
		idToMessages = new ArrayList<>();
	}
	
	/**
	 * Get the final hash pointer that binds the rest of the chain.
	 */
	public HashPointer getBinding() {
		return binding;
	}
	
	/**
	 * @return the generated SHA-256 instance
	 */
	public MessageDigest getHashingAlgorithm() {
		return hashAlgo;
	}
	
	/**
	 * Append a new block to this blockchain.
	 * @param contents contents of new block
	 */
	public void append(Message contents) {
		MessageBlock block = new MessageBlock(idToMessages.size(), binding, 
				contents, hashAlgo);
		binding = new HashPointer(block);
		idToMessages.add(contents);
	}
	
	/**
	 * Get a previous message.
	 * @param blockId id of block/message
	 * @throws IndexOutOfBoundsException id out of range
	 */
	public Message getMessage(int blockId) throws IndexOutOfBoundsException {
		return idToMessages.get(blockId);
	}

}
