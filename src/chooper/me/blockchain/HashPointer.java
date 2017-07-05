package chooper.me.blockchain;

public class HashPointer {
	
	/** reference to some message */
	private MessageHashable pointer;
	
	/** hash of the message */
	private byte[] hash;
	
	/**
	 * Generate a hash pointer to a message.
	 * @param m Message to record and hash. 
	 */
	public HashPointer(MessageHashable m) {
		pointer = m;
		hash = m.getHash();
	}
	
	/** get hash of the message */
	public byte[] getHash() { return hash; }
	
	/** get reference to message */
	public MessageHashable getPointer() { return pointer; }
	
}
