
package cs.pkg460.java.cryptography.extension;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.security.*;
import java.util.Scanner;
import javax.crypto.*;


public class CS460JavaCryptographyExtension {

    public static void main(String[] args) throws Exception {
        
    
        
    FileWriter fw = new FileWriter("src/Text Files/decryptedText.txt");
    PrintWriter pw = new PrintWriter(fw);
   
    // Open file
    String input = readFile("src/Text Files/plaintext.txt");   
    byte[] plainText = input.getBytes("UTF8");
    //
    
    // get a DES private key
    System.out.println( "\nStart generating AES key" );
    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    keyGen.init(128);
    Key key = keyGen.generateKey();
    System.out.println( "Finish generating AES key" );
    //
    // get a DES cipher object and print the provider
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    System.out.println( "\n" + cipher.getProvider().getInfo() );
    //
    // encrypt using the key and the plaintext
    System.out.println( "\nStart encryption" );
    cipher.init(Cipher.ENCRYPT_MODE, key);
    byte[] cipherText = cipher.doFinal(plainText);
    System.out.println( "Finish encryption: " );
    System.out.println( new String(cipherText, "ASCII"));
    
    //put encryption to cipherText.txt
    
    // read from the cipherText
//    String encryptedText = readFile("src/Text Files/ciphertext.txt");   
//    byte[] encryptedTextByte = encryptedText.getBytes("UTF8");
    File cFile = new File("src/Text Files/ciphertext.txt");
    cFile.createNewFile();
    FileOutputStream cipherOutFile = new FileOutputStream(cFile);
    cipherOutFile.write(cipherText);
    cipherOutFile.flush();

    FileInputStream cipertextInput = new FileInputStream(cFile);
    byte[] encryptedTextByte = new byte[(int)cFile.length()];
    cipertextInput.read(encryptedTextByte);


    //
    // decrypt the ciphertext using the same key
    System.out.println( "\nStart decryption" );
    cipher.init(Cipher.DECRYPT_MODE, key);
    byte[] newPlainText = cipher.doFinal(encryptedTextByte);
    System.out.println( "Finish decryption: " );
    System.out.println( new String(newPlainText, "ASCII") );
    
    // place decrypted input to output file
    pw.write(new String(newPlainText, "ASCII"));
    pw.close();
    }
    
    // read file input
    public static String readFile(String filename){
        String inputText = "";
        File file = new File(filename);
        try{
            Scanner s = new Scanner(file);
            while (s.hasNext()){
                inputText = inputText + s.next() + " ";
            }
        }catch(FileNotFoundException e){
            System.out.printf("File not found");
        }
        return inputText;
    }
    
}
