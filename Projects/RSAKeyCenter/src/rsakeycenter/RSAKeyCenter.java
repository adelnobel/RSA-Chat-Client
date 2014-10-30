package rsakeycenter;

import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class RSAKeyCenter {
    private static final int PORT = 2014;
    public static void main(String[] args) {
        try{
            DBManager db = new DBManager("keys.db");
            if(!db.doesExist("record")){
                db.execute("CREATE TABLE record("
                        + "ID TEXT PRIMARY KEY NOT NULL,"
                        + "EValue TEXT NOT NULL,"
                        + "NValue TEXT NOT NULL)");
            }
            ServerSocket serverSocket = new ServerSocket(PORT);
            JFrame frame = new JFrame("RSA KDC");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(200, 100);
            frame.getContentPane().add(new JLabel("KDC is running!"));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            while(true){
                Socket socket = serverSocket.accept();
                ClientThread thread = new ClientThread(socket, db);
                //thread.start();
                thread.run();
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
    }
    
}
