import java.io.*;
import java.security.*;
import java.util.*;
import javax.crypto.*;
public class RsaTest {
	
	
	public RsaTest() throws IOException {
		try {
			 initKeyPair();
			//textEncrypt("/Users/vijaykumar/Desktop/test.txt");
			//textDecrypt(("/Users/vijaykumar/Desktop/encrypted.txt"));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	public static void textEncrypt(String Filename) throws Exception
	{
		final Cipher cipher = Cipher.getInstance("RSA");
		//System.out.println("AAA");
		BufferedReader br = new BufferedReader(new FileReader("/home/stu14/s4/sj6390/Desktop/"+Filename));
		//System.out.println("BBB"+Filename);
		//File encryptedfile = new File(Filename);
//		FileOutputStream fos = new FileOutputStream("/Users/vijaykumar/Desktop/encrypted.txt");
		File encryptedfile = new File("/home/stu14/s4/sj6390/Desktop/"+"E_"+Filename);
		//	System.out.println("BBB");
//FileOutputStream fos = new FileOutputStream("/Users/vijaykumar/Desktop/outFile.txt");
		PrintWriter out = new PrintWriter(encryptedfile);
		try {
			StringBuilder sb = new StringBuilder();

			 
			String plaintext = br.readLine();
			
			while (plaintext != null) {
			//	System.out.println(plaintext);
				cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
	            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
	          //  System.out.println(encryptedBytes);
	            String chipertext = new String(Base64.getEncoder().encode(encryptedBytes));
	           // System.out.println("encrypted (chipertext) = " + chipertext);
//			    sb.append(plaintext);
	            

				 out.write(chipertext);
				 out.write("\n");
//				 out.flush();
   				plaintext = br.readLine();
				}

			out.close();

		} finally {
			br.close();
		}
	}
	/*public static void textEncrypt(String filename) throws Exception
	{
		final Cipher cipher = Cipher.getInstance("RSA");
		BufferedReader br = new BufferedReader(new FileReader(filename));
//		File encryptedfile = new File(Filename);
//		FileOutputStream fos = new FileOutputStream("/Users/vijaykumar/Desktop/encrypted.txt");
		File encryptedfile = new File(filename+"_E");
		System.out.println("File: "+filename);
//		FileOutputStream fos = new FileOutputStream("/Users/vijaykumar/Desktop/outFile.txt");
		PrintWriter out = new PrintWriter(encryptedfile);
		try {
			StringBuilder sb = new StringBuilder();

			 
			String plaintext = br.readLine();
			System.out.println(plaintext);
			while (plaintext != null) {
			 cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
	            byte[] encryptedBytes = new byte[1024];
	            System.out.println(encryptedBytes);
	            String chipertext = new String(Base64.getEncoder().encode(encryptedBytes));
	            System.out.println("encrypted (chipertext) = " + chipertext);
//			    sb.append(plaintext);
	            

				 out.write(chipertext);
				 out.write("\n");
			 out.flush();
   				plaintext = br.readLine();
				}

			out.close();

		} finally {
			br.close();
		}
	}*/
	
	public static void textDecrypt(String Filename) throws Exception
	{
		 final Cipher cipher = Cipher.getInstance("RSA");
		BufferedReader br = new BufferedReader(new FileReader("/home/stu14/s4/sj6390/Desktop/"+Filename));
		File decryptedfile = new File("/home/stu14/s4/sj6390/Desktop/"+"D_"+Filename);
		PrintWriter out = new PrintWriter(decryptedfile);
		try {
			StringBuilder sb = new StringBuilder();
			String chipertext = "";
//			System.out.println(line.getBytes());
//			File decryptedfile = new File("/Users/vijaykumar/Desktop/outFile.txt");
//			FileOutputStream fos = new FileOutputStream("/Users/vijaykumar/Desktop/outFile.txt");
//			PrintWriter out = new PrintWriter(decryptedfile);
			String line = br.readLine();
			while ((line) != null) {
				chipertext=chipertext + line;
				line = br.readLine();
			}		//				sb.append(line);
		//	chipertext = ciphertext +
//				byte[] decryptedText = Rsanew.decrypt(line.getBytes());
//				System.out.println(decryptedText);
				cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
				//System.out.println(chipertext);
	            byte[] ciphertextBytes = Base64.getDecoder().decode(chipertext.getBytes());
				System.out.println("CipherTextArray Len::"+ciphertextBytes.length);
				if(ciphertextBytes.length < 128)
				{
					byte[] b=new byte[128];
					int i=0;
					for(;i<ciphertextBytes.length;i++)
						b[i]=ciphertextBytes[i];
					for(;i<128;i++)
						b[i]=0;
					ciphertextBytes=b;
				}
				System.out.println("CipherTextArray Len::"+ciphertextBytes.length);
	            byte[] decryptedBytes = cipher.doFinal(ciphertextBytes);
	            String decryptedString = new String(decryptedBytes);
	           System.out.println("decrypted (plaintext) = " + decryptedString);
				 out.write(decryptedString);
				// out.write("\n");
				 out.flush();
//   				 sb.append(System.lineSeparator());
				 //chipertext = br.readLine();
				//}
//			String everything = sb.toString();
			// System.out.println(everything);
			out.close();

		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			br.close();
		}
	}
	public KeyPair getKeyPair()
	{
		return keyPair;
	}
	 private static KeyPair keyPair;
	 
	    private static KeyPair initKeyPair() {
	        try {
	            keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
	        } catch (NoSuchAlgorithmException e) {
	            System.err.println("Algorithm not supported! " + e.getMessage() + "!");
	        }
	 
	        return keyPair;
	    }
	
}
