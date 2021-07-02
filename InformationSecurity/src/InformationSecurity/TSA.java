package InformationSecurity;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;

public class TSA {
    //data string од корисникот
    private String text;
    //hash-иран string
    private String hashedData;
    //timestamp
    private LocalDateTime timestamp;

    public TSA(String hashedData) {
        text = "["+hashedData+"]";
        this.hashedData = Hashing.getCryptoHash(hashedData, "SHA-256");
        timestamp = generateTimestamp();
    }
    //генерирање на timestamp со помош на LocalDateTime(ја зема сегашната година, месец, час, мин..)
    private LocalDateTime generateTimestamp() {
        return LocalDateTime.now();
    }

    //имплементација на слика1
    public String digitalTSATimestamp() throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException {
        String cryptoHash = Hashing.getCryptoHash(hashedData + timestamp, "SHA-256");
        String encryptedString = Base64.getEncoder().encodeToString(RSAUtil.encrypt(cryptoHash, RSAUtil.getPublicKey()));
        return text + " " + encryptedString + " " + timestamp;
    }
    //имплементација на слика2
    public boolean validateTrustedTimestamp() throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException {
        String[] pom = digitalTSATimestamp().split(" ");
        String encryptedString = pom[pom.length-2];
        LocalDateTime timestamp = LocalDateTime.parse(pom[pom.length-1]);
        String thisHashed = Hashing.getCryptoHash(hashedData + timestamp, "SHA-256");
        return thisHashed.equals(RSAUtil.decrypt(encryptedString, RSAUtil.getPrivateKey()));
    }
}