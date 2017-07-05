package chooper.me.blockchain;

import static org.junit.Assert.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class Tests {

	@Test
	public void testIntToBytes() {
		int x = 0;
		for (byte b : Utilities.intToBytes(x))
			assertEquals(0, b);
		
		int y = 565656;
		byte[] ybytes = Utilities.intToBytes(y);
		byte[] yexp = {0x00, 0x08, (byte)0xa1, (byte)0x98};
		for (int i=0; i<4; i++) assertEquals(yexp[i], ybytes[i]);
	}
	
	@Test
	public void testConcatenate() {
		byte[] b1 = {0, 1, 2, 3};
		byte[] b2 = {4, 5, 6};
		byte[] concat = Utilities.concatenateArrays(b1, b2);
		byte[] exp = {0, 1, 2, 3, 4, 5, 6};
		for (int i=0; i<exp.length; i++) assertEquals(exp[i], concat[i]);
		
		byte[] b3 = {};
		byte[] b4 = {};
		byte[] b5 = {7, 8, 9};
		byte[] concat2 = Utilities.concatenateArrays(b1, b2, b3, b4, b5);
		byte[] exp2 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		for (int i=0; i<exp.length; i++) assertEquals(exp2[i], concat2[i]);
	}
	
	@Test
	public void testHashing() throws NoSuchAlgorithmException {
		// make sure two equivalent byte arrays have the same hash
		MessageDigest hashAlgo = MessageDigest.getInstance("SHA-256");
		byte[] b1 = {};
		byte[] b2 = {};
		assertArrayEquals(hashAlgo.digest(b1), hashAlgo.digest(b2));
		
		// make sure two base messages have the same hash
		MessageBase bm1 = new MessageBase(hashAlgo);
		MessageBase bm2 = new MessageBase(hashAlgo);
		assertArrayEquals(bm1.getHash(), bm2.getHash());
	}
	
	@Test
	public void testBlockChain() {
		BlockChain chain = new BlockChain();
		
		// nothing has been added to blockchain
		assert chain.getBinding().getPointer() instanceof MessageBase;
		byte[] emptyHash = new MessageBase(chain.getHashingAlgorithm()).getHash();
		assertArrayEquals(emptyHash, chain.getBinding().getHash());
		
		// one element in blockchain
		chain.append(new MessageString("Hello "));
		MessageBlock block0 = assertBlock(chain.getBinding().getPointer(), "Hello ", 0, 
				chain.getBinding().getHash());
		HashPointer prev0 = block0.getPrevious();
		assert prev0.getPointer() instanceof MessageBase;
		assertArrayEquals(emptyHash, prev0.getHash());
		
		// two elements in blockchain
		chain.append(new MessageString("world"));
		MessageBlock block1 = assertBlock(chain.getBinding().getPointer(), "world", 1,
				chain.getBinding().getHash());
		HashPointer prev1 = block1.getPrevious();
		assertBlock(prev1.getPointer(), "Hello ", 0, prev1.getHash());
	}
	
	private MessageBlock assertBlock(MessageHashable m, String expName, int expID, byte[] expHash) {
		assert m instanceof MessageBlock;
		MessageBlock block = (MessageBlock) m;
		assertArrayEquals(expName.getBytes(), block.getContents().serialize());
		assertEquals(expID, block.getID());
		assertArrayEquals(expHash, block.getHash());
		return block;
	}

}
