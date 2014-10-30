
package rsaserver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Adel
 */
public class GUI {
    private final JTextArea msgBox, chatWindow;
    private ConnectionManager conMgr;
    
    private JScrollPane makeScrollableTextArea(JTextArea tArea){
        tArea.setLineWrap(true);
        tArea.setWrapStyleWord(false);
        DefaultCaret caret = (DefaultCaret)tArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane scrollPane = new JScrollPane(tArea);
        return scrollPane;
    }
    
    public void setConMgr(ConnectionManager cmg){
        conMgr = cmg;
    }
    
    public void addLine(String line, String sender){
        line = line.replace("\n", "");
        chatWindow.append("[" + sender + "]  " + line + "\n");
    }
    
    GUI(){
        JFrame frame = new JFrame("RSA Chat Server");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        BorderLayout bl = new BorderLayout();
        frame.getContentPane().setLayout(bl);
        chatWindow = new JTextArea();
        chatWindow.setEnabled(false);
        chatWindow.setDisabledTextColor(Color.BLACK);
        msgBox = new JTextArea();
        msgBox.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    System.out.println(msgBox.getText());
                    if(conMgr == null){
                        addLine("NOT CONNECTED YET!", "INFO");
                    }else{
                        addLine(msgBox.getText(), RSAServer.myID);
                        conMgr.send(msgBox.getText());
                    }
                    msgBox.setText(null);
                }
            }
        });
        frame.getContentPane().add(makeScrollableTextArea(chatWindow), BorderLayout.CENTER);
        frame.getContentPane().add(makeScrollableTextArea(msgBox), BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    
    
    
    
}
