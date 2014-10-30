
package rsaclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Adel
 */
public class ConnectionManager extends Thread{
    private static final int SERVERPORT = 3000;
    private static final String SERVERIP = "127.0.0.1";
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private KDCManager kdc;
    private GUI gui;
    private RSAUtilities rsaUtilities;
    private String[] privateKey;
    private String ID;
    
    public ConnectionManager(GUI gui) throws Exception {
        kdc = new KDCManager();
        this.gui = gui;
        privateKey = kdc.generateAndGetPrivateKey();
    }
    
    @Override
    public void run(){
        try {
            gui.addLine("ESTABLISHING CONNECTION ...", "INFO");
            socket = new Socket(SERVERIP, SERVERPORT);
            gui.addLine("SOCKET CONNECTED", "INFO");
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            ID = dis.readUTF();
            //gui.addLine("READ ID:" + ID, "INFO");
            dos.writeUTF(RSAClient.myID);
            gui.addLine("SENT & READ ID:" + ID, "INFO");
            String[] publicKey = kdc.getPublicKey(ID);
            gui.addLine("GOT PUBLIC KEY", "INFO");
            rsaUtilities = new RSAUtilities(publicKey, privateKey);
            gui.setConMgr(this);
            gui.addLine("CONNECTION ESTABLISHED SUCCESSFULLY WITH ID:" + ID, "INFO");
        } catch (Exception ex) {
            gui.addLine("Couldn't connect to the server!", "INFO");
            System.err.println(ex.getMessage());
        }
        if(socket != null){
            try{
                while(true){
                    gui.addLine(rsaUtilities.decrypt(dis.readUTF()), ID);
                }
            }catch(IOException ex){
                gui.addLine("CONNECTION CLOSED!", "INFO");
                System.err.println(ex.getMessage());
            }
        }
    }
    
    public synchronized void send(String message){
        try{
            dos.writeUTF(rsaUtilities.encrypt(message));
        }catch (Exception ex){
            gui.addLine("COULDN'T SEND MESSAGE [" + message + "]", "INFO");
        }
    }
    
    
    
    
}
