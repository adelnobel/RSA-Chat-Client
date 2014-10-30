package rsaserver;

import java.io.IOException;

/**
 *
 * @author Adel
 */
public class RSAServer {


 public static int keyLength = 1024;
    public static String myID = "SERVER";
    public static void main(String[] args) {
        GUI gui = new GUI();
        ConnectionManager conMgr = null;
        try{
            conMgr = new ConnectionManager(gui);
        }catch(Exception ex){
            gui.addLine("Couldn't connect to KDC!!", "INFO");
            System.err.println(ex.getMessage());
        }
        if(conMgr != null) {
            conMgr.start();
        }
    }
}
