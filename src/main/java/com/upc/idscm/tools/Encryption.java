package com.upc.idscm.tools;

import com.google.common.hash.Hashing;
import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;

public class Encryption {
    public static String hash(String value) {
        String result = Hashing.sha256()
                        .hashString(value, StandardCharsets.UTF_8)
                        .toString();
        return result;
    }
    
    public static String encrypt_xml_file(XML xml_file) {
        XMLCipher keyCipher = XMLCipher.getInstance(XMLCipher.AES_128);
        SecretKey symmetricKey = new SecretKeySpec(key.getBytes(), "AES");
        xmlCipher.init(XMLCipher.ENCRYPT_MODE, symmetricKey);
        boolean encryptContentsOnly = false;
        xmlCipher.doFinal(node.getOwnerDocument(), (Element)node,
        encryptContentsOnly);
    }
}
