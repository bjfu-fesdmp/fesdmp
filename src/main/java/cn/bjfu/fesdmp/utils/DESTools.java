  
package cn.bjfu.fesdmp.utils;  

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/** 
 * ClassName:DESTools <br/> 
 * Function: Des 鍔犲瘑瑙ｅ瘑绠楁硶鐨勫疄鐜扮殑宸ュ叿绫� <br/> 
 * Reason:   Des 鍔犲瘑瑙ｅ瘑绠楁硶鐨勫疄鐜扮殑宸ュ叿绫� <br/> 
 * Date:     2013骞�1鏈�鏃�涓嬪崍4:05:52 <br/> 
 * @author   zhangzhaoyu 
 * @version   
 * @since    JDK 1.6 
 * @see       
 */
public class DESTools {
	
	/**
	 * 绯荤粺鐢ㄦ埛鐨勯噸缃箣鍚庣殑瀵嗙爜
	 */
	public static final String DEFAULT_PASSWARD = "12345678";
	
	/**
	 * DES 鍔犲瘑绠楁硶鐨刱ey
	 */
	public static final String DES_KEY = "Yqoi$MaI";
	/**
	 * DES 鍔犲瘑绠楁硶鐨処V
	 */
	public static final String DES_IV = "Yqoi$MaI";
	
	public static String decrypt(String data, String key, String iv) throws Exception {
		
		if (data == null) {
			return null;
		}
		
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] buf = decoder.decodeBuffer(data);
		byte[] bt = desDecrypt(buf, key, iv);
		
		return new String(bt, "UTF-8");
	}
	
	public static byte[] desDecrypt(byte[] data, String key, String vi) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(vi.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        return cipher.doFinal(data);
	}
	
	public static String encrypt(String data, String key, String iv) throws Exception {
		
		byte[] bt = desEncrypt(data, key, iv);
		String strs =  new BASE64Encoder().encode(bt);
		return strs;
	}
	
	public static byte[] desEncrypt(String message, String key, String vi) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(vi.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        return cipher.doFinal(message.getBytes("UTF-8"));
	}
}
 