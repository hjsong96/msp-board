package kr.msp.example.encode;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: mium2(Yoo Byung Hee)
 * Date: 2014-09-16
 * Time: 오전 10:11
 * To change this template use File | Settings | File Templates.
 */
public class CryptoDataSource extends BasicDataSource{

	public CryptoDataSource() {
		super();
	}

	public synchronized void setPassword(String encodedPassword){
		super.setPassword(decode(encodedPassword));
	}

	@Override
	public synchronized void setUrl(String url) {
		super.setUrl(decode(url));
	}

	@Override
	public void setUsername(String username) {
		super.setUsername(decode(username));
	}

	private String decode(String encodeStr) {
		AES128Cipher a256 = AES128Cipher.getInstance();
		String returnDecodedStr = null;
		try {
			returnDecodedStr = a256.AES_Decode(encodeStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnDecodedStr;
	}

	public String encode(String str){
		AES128Cipher a256 = AES128Cipher.getInstance();
		String returnEncodedStr = null;
		try {
			returnEncodedStr = a256.AES_Encode(str);
		}catch (Exception e){
			e.printStackTrace();
		}
		return returnEncodedStr;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}


}
