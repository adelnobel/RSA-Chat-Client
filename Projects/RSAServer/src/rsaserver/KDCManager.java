package rsaserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Adel
 */
public class KDCManager {
    private static final int KDCPORT = 2014;
    private static final String KDCIP = "127.0.0.1";
    
    public KDCManager() {
        
    }
    
    public String[] generateAndGetPrivateKey() throws Exception{
        String res[] = new String[2];
        Socket socket = new Socket(KDCIP, KDCPORT);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        dos.writeUTF(RSAServer.myID);
        dos.writeUTF("GENERATE");
        res[0] = dis.readUTF();
        res[1] = dis.readUTF();
        return res;
    }
    
    public String[] getPublicKey(String ID) throws Exception{
        String res[] = new String[2];
        Socket socket = new Socket(KDCIP, KDCPORT);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        dos.writeUTF(RSAServer.myID);
        dos.writeUTF("GET");
        dos.writeUTF(ID);
        res[0] = dis.readUTF();
        res[1] = dis.readUTF();
        return res;
    }
    
    
    
}
