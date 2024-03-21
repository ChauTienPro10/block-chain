import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class App1 {
    public static void main(String[] args) {
        try {
            String password = "nguoidung123";

            // Tạo cặp khóa RSA từ mật khẩu
            KeyPair keyPair = generateKeyPairFromPassword(password);

            // Lấy khóa riêng
            PrivateKey privateKey = keyPair.getPrivate();
            System.out.println("Private Key: " + privateKeyToString(privateKey));

            // Lấy khóa công khai
            PublicKey publicKey = keyPair.getPublic();
            System.out.println("Public Key: " + publicKeyToString(publicKey));
            
            // Tạo chữ ký số
            byte[] signature = generateDigitalSignature("chau duong phat tien", privateKey);
            System.out.println("Digital Signature: " + bytesToHex(signature));
            
         // Xác thực chữ ký số
            boolean isVerified = verifyDigitalSignature("chau duong phat tien ", signature, publicKey);
            System.out.println("Signature Verification: " + isVerified);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm tạo cặp khóa RSA từ mật khẩu
    private static KeyPair generateKeyPairFromPassword(String password) throws Exception {
        // Tạo khóa bí mật (Secret Key) từ mật khẩu
        SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(), "AES");

        // Tạo cặp khóa RSA bằng cách mã hóa khóa bí mật
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secretKeySpec.getEncoded());
        keyPairGenerator.initialize(2048, secureRandom);

        return keyPairGenerator.generateKeyPair();
    }

    // Hàm chuyển đổi khóa riêng sang chuỗi Base64
    private static String privateKeyToString(PrivateKey privateKey) {
        byte[] privateKeyBytes = privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(privateKeyBytes);
    }

    // Hàm chuyển đổi khóa công khai sang chuỗi Base64
    private static String publicKeyToString(PublicKey publicKey) {
        byte[] publicKeyBytes = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(publicKeyBytes);
    }
    
    private static byte[] generateDigitalSignature(String data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes());

        return signature.sign();
    }
    
    
 // Hàm chuyển đổi mảng byte sang chuỗi hex
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
    
    
    // Hàm xác thực chữ ký số
    private static boolean verifyDigitalSignature(String data, byte[] signature, PublicKey publicKey) throws Exception {
        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(publicKey);
        verifier.update(data.getBytes());

        return verifier.verify(signature);
    }
    
    // Hàm chuyển đổi chuỗi hex thành mảng byte
    private static byte[] hexToBytes(String hexString) {
        int len = hexString.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                                 + Character.digit(hexString.charAt(i+1), 16));
        }
        return bytes;
    }
}