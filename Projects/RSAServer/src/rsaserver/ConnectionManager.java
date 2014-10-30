
package rsaserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adel
 */
public class ConnectionManager extends Thread{
    private static final int SERVERPORT = 3000;
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
            gui.addLine("WAITING FOR CONNECTION ...", "INFO");
            ServerSocket sSocket = new ServerSocket(SERVERPORT);
            gui.addLine("OPENED SERVER SOCKET", "INFO");
            socket = sSocket.accept();
            gui.addLine("ACCEPTED", "INFO");
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(RSAServer.myID);
            //gui.addLine("SENT ID", "INFO");
            ID = dis.readUTF();
            gui.addLine("SENT & READ ID:" + ID, "INFO");
            String[] publicKey = kdc.getPublicKey(ID);
            gui.addLine("GOT PUBLIC KEY", "INFO");
            rsaUtilities = new RSAUtilities(publicKey, privateKey);
            gui.setConMgr(this);
            gui.addLine("CONNECTION ESTABLISHED SUCCESSFULLY WITH ID:" + ID, "INFO");
        } catch (Exception ex) {
            gui.addLine("CONNECTION COULDN'T BE ESTABLISHED", "INFO");
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
        }catch (IOException ex){
            gui.addLine("COULDN'T SEND MESSAGE [" + message + "]", "INFO");
        }
    }
    
    
    
    
}
