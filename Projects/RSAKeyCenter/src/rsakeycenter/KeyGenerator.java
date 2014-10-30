

package rsakeycenter;

import java.math.BigInteger;
import java.util.Random;

public class KeyGenerator {
    public static final int keySize = 1024;
    private static final int minimum = 256;
    private BigInteger p, q, n, phi, d, e;
    public KeyGenerator(int keyLength) throws Exception{
        if(keyLength < minimum){
            throw new Exception("Key size must be " + minimum + " at least");
        }
        p = BigInteger.probablePrime(keyLength / 2, new Random());
        q = BigInteger.probablePrime(keyLength / 2, new Random());
        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        do{
            d = new BigInteger(keyLength - 2, new Random());
        }while(d.gcd(phi).compareTo(BigInteger.ONE) != 0);
        e = d.modInverse(phi);
    }

    /**
     * @return the n
     */
    public BigInteger getN() {
        return n;
    }

    /**
     * @return the d
     */
    public BigInteger getD() {
        return d;
    }

    /**
     * @return the e
     */
    public BigInteger getE() {
        return e;
    }
    
}
