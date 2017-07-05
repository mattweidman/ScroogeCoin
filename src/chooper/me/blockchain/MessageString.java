package chooper.me.blockchain;

/**
 * Message object that only holds a string.
 */
public class MessageString extends Message {
	
	private String str;
	
	public MessageString(String str) {
		this.str = str;
	}
	
	@Override
	public byte[] serialize() {
		return str.getBytes();
	}

}
