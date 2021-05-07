package com.upc.idscm.tools;

import com.google.common.hash.Hashing;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.io.FileUtils;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Encryption {
    private static final String KEY = "UjXn2r5u8x/A%D*G";
    private static boolean isInitialized = false;
    private static SecretKey secretKey = null;
    
    public static String hash(String value) {
        String result = Hashing.sha256()
                        .hashString(value, StandardCharsets.UTF_8)
                        .toString();
        return result;
    }
    
    private static void initEncryption() {
        if (!isInitialized) {
            org.apache.xml.security.Init.init();
            isInitialized = true;
        }
    }
    
    private static SecretKey getSecretKey() {
        if (secretKey == null) 
            secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
        return secretKey;
    }
    
    public static Document readDocument(String path) throws ParserConfigurationException, SAXException, IOException {
        File file = new File(path);  
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();   
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();  
        Document doc = db.parse(file);
        
        return doc;
    }
    
    public static String encryptXML(Document doc) throws XMLEncryptionException, ParserConfigurationException, UnsupportedEncodingException, SAXException, IOException, Exception {
        initEncryption();
       
        XMLCipher keyCipher = XMLCipher.getInstance(XMLCipher.AES_128);
        keyCipher.init(XMLCipher.ENCRYPT_MODE, getSecretKey());
        
        boolean encryptContentsOnly = false;
        Document encrypted = keyCipher.doFinal(doc, doc.getDocumentElement(), encryptContentsOnly);

        return toXML(encrypted);
    }
   
    public static String decryptXML(Document doc) throws XMLEncryptionException, ParserConfigurationException, UnsupportedEncodingException, SAXException, IOException, Exception {
        try {
            initEncryption(); 

            XMLCipher keyCipher = XMLCipher.getInstance(XMLCipher.AES_128);
            keyCipher.init(XMLCipher.DECRYPT_MODE, getSecretKey());

            Document decrypted = keyCipher.doFinal(doc, doc.getDocumentElement());

            return toXML(decrypted);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public static void encryptDocument(String path) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        SecretKey key = getSecretKey();
        Cipher cipher = Cipher.getInstance(XMLCipher.AES_128);

        File original = new File(path);
        byte[] bytes = FileUtils.readFileToByteArray(original);
        cipher.init(Cipher.ENCRYPT_MODE, key); 

        byte[] encryptedData = cipher.doFinal(bytes);
        FileUtils.writeByteArrayToFile(new File(path+"_encrypted"), encryptedData);
    }
    
    public static void decryptDocument(String path) throws NoSuchAlgorithmException, IOException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKey key = getSecretKey();
        Cipher cipher = Cipher.getInstance(XMLCipher.AES_128);

        byte[] bytes = FileUtils.readFileToByteArray(new File(path));
        cipher.init(Cipher.DECRYPT_MODE, key);
        
        byte[] encryptedData = cipher.doFinal(bytes);
        FileUtils.writeByteArrayToFile(new File(path+"_decrypted"), encryptedData);
    }
   
    public static String toXML(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (TransformerException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public static boolean store(String data, String path) {
        try {
            java.io.FileWriter fw = new java.io.FileWriter(path);
            fw.write(data);
            fw.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }   
}