package com.apireportes.seg;

import javax.crypto.*;
import java.io.*;

    public class DesEncrypter {
        Cipher ecipher;
        Cipher dcipher;
        public DesEncrypter(SecretKey key) {
            try {
                ecipher = Cipher.getInstance("DES");
                dcipher = Cipher.getInstance("DES");
                ecipher.init(Cipher.ENCRYPT_MODE, key);
                dcipher.init(Cipher.DECRYPT_MODE, key);

            } catch (javax.crypto.NoSuchPaddingException e) {
            } catch (java.security.NoSuchAlgorithmException e) {
            } catch (java.security.InvalidKeyException e) {
            }
        }

        public String encrypt(String str) {
            try {
                // Encode the string into bytes using utf-8
                byte[] utf8 = str.getBytes("UTF8");

                // Encrypt
                byte[] enc = ecipher.doFinal(utf8);

                // Encode bytes to base64 to get a string
                return new sun.misc.BASE64Encoder().encode(enc);
            } catch (javax.crypto.BadPaddingException e) {
            } catch (IllegalBlockSizeException e) {
            } catch (UnsupportedEncodingException e) {
            } catch (java.io.IOException e) {
            }
            return null;
        }

        public String decrypt(String str) {
            try {
                // Decode base64 to get bytes
                byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

                // Decrypt
                byte[] utf8 = dcipher.doFinal(dec);

                // Decode using utf-8
                return new String(utf8, "UTF8");
            } catch (javax.crypto.BadPaddingException e) {
            } catch (IllegalBlockSizeException e) {
            } catch (UnsupportedEncodingException e) {
            } catch (java.io.IOException e) {
            }
            return null;
        }


		public static void guardarClave(SecretKey key_, String dbStr_, String dbUsr_, String dbPW_, String nomArch_) throws FileNotFoundException, IOException{
			FileOutputStream fos = new FileOutputStream(nomArch_);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(dbStr_);
			oos.writeObject(dbUsr_);
			oos.writeObject(dbPW_);
			oos.writeObject(key_);
		}

		public static Object[] recuperarClave(String nomArch_) throws FileNotFoundException, IOException, ClassNotFoundException{
			FileInputStream fis = new FileInputStream(nomArch_);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object o[] = new Object[4];
			o[0] = ois.readObject();
			o[1] = ois.readObject();
			o[2] = ois.readObject();
			o[3] = ois.readObject();
			//SecretKey key = (SecretKey)ois.readObject();
			return o;
		}

        public static void main (String a[]){

			// generar una clave y guardarla en disco

			try{
				// Generate a temporary key. In practice, you would save this key.
				// See also e464 Encrypting with DES Using a Pass Phrase.
				SecretKey key = KeyGenerator.getInstance("DES").generateKey();
				DesEncrypter encrypter = new DesEncrypter(key);
				String encryptedDBStr = encrypter.encrypt("jdbc:oracle:thin:@255.255.255.225:0000:ORIDZZ");
				String encryptedDBUsr = encrypter.encrypt("usuariox");
				String encryptedDBPW =  encrypter.encrypt("******");
				guardarClave(key,encryptedDBStr,encryptedDBUsr,encryptedDBPW,"/tmp/Desarrollo.key");
				
				encrypter = new DesEncrypter(key);
				encryptedDBStr = encrypter.encrypt("jdbc:postgresql://255.255.255.225:0000/xxxx");
				encryptedDBUsr = encrypter.encrypt("xxx");
				encryptedDBPW =  encrypter.encrypt("xxx");
				guardarClave(key,encryptedDBStr,encryptedDBUsr,encryptedDBPW,"/tmp/test01.key");		
				
				
				
			} catch (Exception e) {
			}





			try {
					{
						Object o[] = recuperarClave("/tmp/Desarrollo.key");

						//SecretKey key =
						String encryptedDBStr = (String)o[0];
						String encryptedDBUsr = (String)o[1];
						String encryptedDBPW = (String)o[2];
						SecretKey key = (SecretKey)o[3];

						DesEncrypter encrypter = new DesEncrypter(key);
						String decryptedDBStr = encrypter.decrypt(encryptedDBStr);
						String decryptedDBUsr = encrypter.decrypt(encryptedDBUsr);
						String decryptedDBPW = encrypter.decrypt(encryptedDBPW);

						System.out.println(decryptedDBStr + " " + decryptedDBUsr + " " + decryptedDBPW);
					}

					{

						Object o[] = recuperarClave("/tmp/01correcto.key");

						//SecretKey key =
						String encryptedDBStr = (String)o[0];
						String encryptedDBUsr = (String)o[1];
						String encryptedDBPW = (String)o[2];
						SecretKey key = (SecretKey)o[3];

						DesEncrypter encrypter = new DesEncrypter(key);
						String decryptedDBStr = encrypter.decrypt(encryptedDBStr);
						String decryptedDBUsr = encrypter.decrypt(encryptedDBUsr);
						String decryptedDBPW = encrypter.decrypt(encryptedDBPW);

						//System.out.println(encrypted);
						//System.out.println(encrypted);
						System.out.println(decryptedDBStr + " " + decryptedDBUsr + " " + decryptedDBPW);
					}

					{

						Object o[] = recuperarClave("/tmp/orip102.key");

						//SecretKey key =
						String encryptedDBStr = (String)o[0];
						String encryptedDBUsr = (String)o[1];
						String encryptedDBPW = (String)o[2];
						SecretKey key = (SecretKey)o[3];

						DesEncrypter encrypter = new DesEncrypter(key);
						String decryptedDBStr = encrypter.decrypt(encryptedDBStr);
						String decryptedDBUsr = encrypter.decrypt(encryptedDBUsr);
						String decryptedDBPW = encrypter.decrypt(encryptedDBPW);

						//System.out.println(encrypted);
						//System.out.println(encrypted);
						System.out.println(decryptedDBStr + " " + decryptedDBUsr + " " + decryptedDBPW);
					}

			} catch (Exception e) {
				e.printStackTrace();
			}




		}
    }
