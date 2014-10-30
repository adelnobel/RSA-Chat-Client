

package rsakeycenter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Adel
 */
public class ClientThread /*extends Thread*/{
    Socket socket;
    DBManager db;
    

    public ClientThread(Socket s, DBManager d) {
        socket = s;
        db = d;
    }
    
    private String[] generateKey(String clientID) throws Exception{
        KeyGenerator key = new KeyGenerator(KeyGenerator.keySize);
        String res[] = new String[2];
        res[0] = key.getD().toString();
        res[1] = key.getN().toString();
        db.executeUpdate("INSERT OR REPLACE INTO record VALUES('"+clientID+"', "
                + "'"+key.getE()+"', '"+res[1]+"')");
        return res;
    }
    
    private String[] getPublicKey(String ID) throws Exception{
        String res[] = new String[2];
        String sql = "SELECT EValue, NValue FROM record WHERE ID = '"+ID+"'";
        boolean found = false;
        for(int tries = 0; tries < 5 && !found; tries++){
            ResultSet rs = db.executeQuery(sql);
            if(rs.next()){
                found = true;
                res[0] = rs.getString("EValue");
                res[1] = rs.getString("NValue");
            }
        }
        if(!found){
            throw new Exception("Didn't find ID: " + ID);
        }
        return res;
    }
    
    //@Override
    public void run(){
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            String clientID = dis.readUTF();
            String service = dis.readUTF();
            if(service.compareTo("GENERATE") == 0){
                String[] privateKey = generateKey(clientID);
                dos.writeUTF(privateKey[0]);
                dos.writeUTF(privateKey[1]);
            }else{
                String ID = dis.readUTF();
                String[] key = getPublicKey(ID);
                dos.writeUTF(key[0]); //write e
                dos.writeUTF(key[1]); //write n
            }
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.err.println(ex.getMessage());
        }
    }
    
}
