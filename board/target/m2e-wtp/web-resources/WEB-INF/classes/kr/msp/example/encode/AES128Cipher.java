package kr.msp.example.encode;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by IntelliJ IDEA.
 * User: mium2(Yoo Byung Hee)
 * Date: 2014-09-16
 * Time: 오전 9:22
 * To change this template use File | Settings | File Templates.
 */
public class AES128Cipher {
	private static volatile AES128Cipher INSTANCE;

	private final static String secretKey   = "u2r456a89c12l4e6"; //16bit

	public static AES128Cipher getInstance(){
		if(INSTANCE==null){
			synchronized(AES128Cipher.class){
				if(INSTANCE==null)
					INSTANCE=new AES128Cipher();
			}
		}
		return INSTANCE;
	}

	private AES128Cipher(){
	}

	//암호화
	public static String AES_Encode(String str) throws Exception{

		byte[] keyData = secretKey.getBytes();
		SecretKeySpec secureKey = new SecretKeySpec(keyData, "AES");
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.ENCRYPT_MODE, secureKey);

		byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
		String enStr = new String(Base64.encodeBase64(encrypted));

		return enStr;
	}

	//복호화
	public static String AES_Decode(String str) throws Exception{
		byte[] keyData = secretKey.getBytes();
		SecretKey secureKey = new SecretKeySpec(keyData, "AES");
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.DECRYPT_MODE, secureKey);

		byte[] byteStr = Base64.decodeBase64(str.getBytes());

		return new String(c.doFinal(byteStr),"UTF-8");
	}
}
