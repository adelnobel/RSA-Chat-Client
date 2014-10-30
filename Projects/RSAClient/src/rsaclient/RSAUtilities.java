package rsaclient;

import java.math.BigInteger;


public class RSAUtilities {
    private BigInteger[] publicKey; //Other end public key
    private BigInteger[] privateKey;
    
    public RSAUtilities(String[] pubKey, String[] priKey){
        publicKey = new BigInteger[2];
        privateKey = new BigInteger[2];
        for(int i = 0; i < 2; i++){
            publicKey[i] = new BigInteger(pubKey[i]);
            privateKey[i] = new BigInteger(priKey[i]);
        }
    }
    
    public String decrypt(String msg){
        BigInteger c = new BigInteger(msg);
        BigInteger d = privateKey[0];
        BigInteger n = privateKey[1];
        BigInteger m = c.modPow(d, n);
        return new String(m.toByteArray());
    }
    
    /**
     * Assuming the message length is always less than the key size! No message splitting is done!
     * @param msg
     * @return 
     */
    public String encrypt(String msg){
        BigInteger m = new BigInteger(msg.getBytes());
        BigInteger e = publicKey[0];
        BigInteger n = publicKey[1];
        BigInteger c = m.modPow(e, n);
        return c.toString();
    }
    
}
