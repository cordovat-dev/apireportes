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
                byte[] bytes = java.util.Base64.getMimeEncoder().encode(enc);
                return new String(bytes);
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
                byte[] dec = java.util.Base64.getMimeDecoder().decode(str);
                
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
			return o;
		}

    }
