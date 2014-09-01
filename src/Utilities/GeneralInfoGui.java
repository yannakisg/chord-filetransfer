package Utilities;

import Application.Node;
import DHash.Hashing;
import DHash.IdKey;
import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.Toolkit;
import java.rmi.RemoteException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Frame showing much info about the local node, its successor lists , finger lists ,files etc...
 * @author ChordFPG team
 * @version  1.0
 */
public class GeneralInfoGui extends JFrame {

    private Node node;

    public GeneralInfoGui(Node n) {

        setTitle("ChordFPG 1.0 - General Info");
        this.node = n;
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/Utilities/resources/logo2.gif")));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            List<IdKey> listSuc = node.getWholeSucList();
            List<IdKey> listFin = node.getFingerKeys();
            IdKey pred = node.getPredecessor();
            IdKey localID = node.getLocalID();
            String[] localFiles = node.getFileNames();
            String[] files = node.entriesToStringArray();

            String text;
            /*gets info for the local node*/
            text = "<html>"
                    + "<h3>Node</h3>"
                    + "<ul>"
                    + "<li><font color=\"red\">ID:</font> " + "<font color=\"blue\">" + localID.getHashKey().toHexString() + "</font>" + "</li>"
                    + "<li><font color=\"red\">IP:</font> " + "<font color=\"blue\">" + localID.getIP().substring(1) + "</font>" + "</li>"
                    + "<li><font color=\"red\">PID:</font> " + "<font color=\"blue\">" + localID.getPID() + "</font>" + "</li>"
                    + "<li><font color=\"red\">MAC:</font> " + "<font color=\"blue\">" + localID.getMacAndInterface()[0] + "</font>" + "</li>"
                    + "<li><font color=\"red\">NetInterface:</font> " + "<font color=\"blue\">" + localID.getMacAndInterface()[1] + "</font>" + "</li>"
                    + "</ul>";
             /*gets info for the predecessor*/
            if (pred != null) {
                text += "<h3>Predecessor</h3>"
                        + "<ul>"
                        + "<li><font color=\"red\">ID:</font> " + "<font color=\"blue\">" + pred.getHashKey().toHexString() + "</font>" + "</li>"
                        + "<li><font color=\"red\">IP:</font> " + "<font color=\"blue\">" + pred.getIP().substring(1) + "</font>" + "</li>"
                        + "<li><font color=\"red\">PID:</font> " + "<font color=\"blue\">" + pred.getPID() + "</font>" + "</li>"
                        + "<li><font color=\"red\">MAC:</font> " + "<font color=\"blue\">" + pred.getMacAndInterface()[0] + "</font>" + "</li>"
                        + "<li><font color=\"red\">NetInterface:</font> " + "<font color=\"blue\">" + pred.getMacAndInterface()[1] + "</font>" + "</li>"
                        + "</ul>";
            }
             /*gets info for the successor list*/
            if (listSuc != null && listSuc.size() > 0) {
                text += "<h3> Successor List </h3>"
                        + "<ul>";

                for (int i = 0; i < listSuc.size(); i++) {
                    text +=
                            "<li> Successor[" + i + "] </li>"
                            + "<ul>"
                            + "<li><font color=\"red\">ID:</font> " + "<font color=\"blue\">" + listSuc.get(i).getHashKey().toHexString() + "</font>" + "</li>"
                            + "<li><font color=\"red\">IP:</font> " + "<font color=\"blue\">" + listSuc.get(i).getIP().substring(1) + "</font>" + "</li>"
                            + "<li><font color=\"red\">PID:</font> " + "<font color=\"blue\">" + listSuc.get(i).getPID() + "</font>" + "</li>"
                            + "<li><font color=\"red\">MAC:</font> " + "<font color=\"blue\">" + listSuc.get(i).getMacAndInterface()[0] + "</font>" + "</li>"
                            + "<li><font color=\"red\">NetInterface:</font> " + "<font color=\"blue\">" + listSuc.get(i).getMacAndInterface()[1] + "</font>" + "</li>"
                            + "</ul>";
                }
                text += "</ul>";
            }
             /*gets info for the the finger list*/
            if (listFin != null && listFin.size() > 0) {
                text += "<h3> Finger List </h3>"
                        + "<ul>";

                for (int i = 0; i < listFin.size(); i++) {
                    text +=
                            "<li> Finger[" + i + "] </li>"
                            + "<ul>"
                            + "<li><font color=\"red\">ID:</font> " + "<font color=\"blue\">" + listFin.get(i).getHashKey().toHexString() + "</font>" + "</li>"
                            + "<li><font color=\"red\">IP:</font> " + "<font color=\"blue\">" + listFin.get(i).getIP().substring(1) + "</font>" + "</li>"
                            + "<li><font color=\"red\">PID:</font> " + "<font color=\"blue\">" + listFin.get(i).getPID() + "</font>" + "</li>"
                            + "<li><font color=\"red\">MAC:</font> " + "<font color=\"blue\">" + listFin.get(i).getMacAndInterface()[0] + "</font>" + "</li>"
                            + "<li><font color=\"red\">NetInterface:</font> " + "<font color=\"blue\">" + listFin.get(i).getMacAndInterface()[1] + "</font>" + "</li>"
                            + "</ul>";
                }
                text += "</ul>";
            }
             /*gets info for the local files*/
            if (localFiles != null && localFiles.length > 0) {
                text += "<h3>My Files</h3>"
                        + "<ul>";
                for (int i = 0; i < localFiles.length; i++) {
                    String hashFile = localFiles[i];
                    if (hashFile.lastIndexOf(".") > 0) {
                        hashFile = hashFile.substring(0, hashFile.lastIndexOf("."));
                    }
                    text += "<li>File[" + i + "]</li>"
                            + "<ul>"
                            + "<li><font color=\"red\">Name: </font>" + "<font color=\"blue\">" + localFiles[i] + "</font>" + "</li>"
                            + "<li><font color=\"red\">Key: </font>" + "<font color=\"blue\">" + Hashing.hash(hashFile.getBytes()).toHexString() + "</font>" + "</li>"
                            + "</ul>";

                }
                text += "</ul>";
            }

             /*gets info for the remote files*/
            if (files != null && files.length > 0) {

                text += "<h3>Remote Files</h3>"
                        + "<ul>";
                for (int i = 0; i < files.length; i++) {
                    if (files[i] != null) {
                        text += "<ul>" + files[i] + "</ul>";
                    }
                }
            }

            text += "</html>";

            label.setText(text);
            panel.setSize(500, 500);
            panel.add(label);


            JScrollPane scrollPane = new JScrollPane(panel);
            this.getContentPane().add(scrollPane, BorderLayout.CENTER);
            setSize(550, 550);

            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screen.width / 2) - (this.getWidth() / 2);
            int y = (screen.height / 2) - (this.getHeight() / 2);
            this.setLocation(x, y);

            setVisible(true);

        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(this, "A problem occurred while collecting info for the node. Please try again.", "ChordFPG said:", JOptionPane.ERROR_MESSAGE);
        }

    }
}