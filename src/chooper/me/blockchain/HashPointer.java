package chooper.me.blockchain;

public class HashPointer {
	
	/** reference to some message */
	private Message pointer;
	
	/** hash of the message */
	private byte[] hash;
	
	/**
	 * Generate a hash pointer to a message.
	 * @param m Message to record and hash. 
	 */
	public HashPointer(Message m) {
		pointer = m;
		hash = m.getHash();
	}
	
	/** get hash of the message */
	public byte[] getHash() { return hash; }
	
	/** get reference to message */
	public Message getPointer() { return pointer; }
	
}
