import java.security.PublicKey;
import java.util.Base64;

public class Base64Encoder {

    public static String publicKeyToStringBase62(PublicKey publicKey){
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(publicKey.getEncoded());
    }
}
