package chooper.me.entities;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 * An entity with a digital signature.
 */
public class User {
	
	/** private key */
	private PrivateKey privateKey;
	
	/** public key */
	private PublicKey publicKey;
	
	/** Algorithm for key public/private generation */
	public static final String keyGenAlgo = "DSA";
	
	/** Algorithm for signatures */
	public static final String signAlgo = "SHA1withDSA";
	
	/** Signature-making object */
	private Signature signer;

	public User() {
		signer = null;
		KeyPairGenerator keyGen = null;
		try {
			signer = Signature.getInstance(signAlgo);
			keyGen = KeyPairGenerator.getInstance(keyGenAlgo);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(1);
		}
		KeyPair pair = keyGen.generateKeyPair();
		privateKey = pair.getPrivate();
		publicKey = pair.getPublic();
	}
	
	/** get public key */
	public byte[] getPublicKey() {
		return publicKey.getEncoded();
	}
	
	/**
	 * Create signature of message using private key.
	 * @param message message to sign
	 * @param signAlgo algorithm for signatures
	 * @return signature of message
	 */
	public byte[] sign(byte[] message) {
		try {
			signer.initSign(privateKey);
			signer.update(message);
			return signer.sign();
		} catch (SignatureException | InvalidKeyException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	/**
	 * Create signature of message using private key.
	 * @param message message to sign
	 * @param signature signature of message
	 * @param signAlgo algorithm for signatures
	 * @return whether signature is valid
	 */
	public boolean verify(byte[] message, byte[] signature) {
		try {
			signer.initVerify(publicKey);
			signer.update(message);
			return signer.verify(signature);
		} catch (SignatureException | InvalidKeyException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}

}
