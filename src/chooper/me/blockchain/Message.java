package chooper.me.blockchain;

/**
 * An object that can be marshalled into an array of bytes.
 */
public abstract class Message {
	
	/**
	 * @return serialized representation of everything inside this message
	 */
	public abstract byte[] serialize();
	
}
