package com.upc.idscm.tools;

import com.google.common.hash.Hashing;
import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
import com.sun.org.apache.xml.internal.security.encryption.XMLEncryptionException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Encryption {
    public static String hash(String value) {
        String result = Hashing.sha256()
                        .hashString(value, StandardCharsets.UTF_8)
                        .toString();
        return result;
    }
    
    public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException, UnsupportedEncodingException, Exception{
        File file = new File("");  
        //an instance of factory that gives a document builder  
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
        //an instance of builder to parse the specified xml file  
        DocumentBuilder db = dbf.newDocumentBuilder();  
        Document doc = db.parse(file); 
        String encrypted_xml = encrypt_xml_file(doc);
//        String decrypted_xml = decrypt_xml_file(encrypted_xml);
        
    }
    
   public static String encrypt_xml_file(Document doc) throws XMLEncryptionException, ParserConfigurationException, UnsupportedEncodingException, SAXException, IOException, Exception {
        String key = "pavel";

//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//
//        ByteArrayInputStream input = new ByteArrayInputStream(xml_file.getBytes("UTF-8"));
//        Document doc = builder.parse(input);

        XMLCipher keyCipher = XMLCipher.getInstance(XMLCipher.AES_128);
        SecretKey symmetricKey = new SecretKeySpec(key.getBytes(), "AES");
        keyCipher.init(XMLCipher.ENCRYPT_MODE, symmetricKey);
        boolean encryptContentsOnly = false;
        Document encrypted = keyCipher.doFinal(doc.getOwnerDocument(), (Element)doc, encryptContentsOnly);

        return encrypted.toString();
    }
   
   public static String decrypt_xml_file(Document doc) throws XMLEncryptionException, ParserConfigurationException, UnsupportedEncodingException, SAXException, IOException, Exception {
        String key = "pavel";

//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//
//        ByteArrayInputStream input = new ByteArrayInputStream(encrypted_xml_file.getBytes("UTF-8"));
//        Document doc = builder.parse(input);
//       
        XMLCipher keyCipher = XMLCipher.getInstance(XMLCipher.AES_128);
        SecretKey symmetricKey = new SecretKeySpec(key.getBytes(), "AES");
        keyCipher.init(XMLCipher.DECRYPT_MODE, symmetricKey);
        Document decrypted = keyCipher.doFinal(doc.getOwnerDocument(), (Element)doc);
        
        return decrypted.toString();
   }
   
   
}