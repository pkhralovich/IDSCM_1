package com.upc.idscm.tools;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

public class Encryption {
    public static String hash(String value) {
        String result = Hashing.sha256()
                        .hashString(value, StandardCharsets.UTF_8)
                        .toString();
        return result;
    }
}
