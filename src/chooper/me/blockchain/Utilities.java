package chooper.me.blockchain;

import java.nio.ByteBuffer;

public class Utilities {
	
	public static byte[] intToBytes(int x) {
		return ByteBuffer.allocate(Integer.SIZE / 8).putInt(x).array();
	}
	
	public static byte[] doubleToBytes(double x) {
		return ByteBuffer.allocate(Double.SIZE / 8).putDouble(x).array();
	}
	
	public static byte[] concatenateArrays(byte[]... arrs) {
		int numBytes = 0;
		for (byte[] arr : arrs) numBytes += arr.length;
		
		byte[] ans = new byte[numBytes];
		int ansIndex = 0;
		for (int i=0; i<arrs.length; i++) {
			byte[] arr = arrs[i];
			System.arraycopy(arr, 0, ans, ansIndex, arr.length);
			ansIndex += arr.length;
		}
		return ans;
	}

}
