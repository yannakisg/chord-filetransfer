package Utilities;

import Application.Node;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.rmi.RemoteException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Frame showing some statistics for local node and Chord Ring
 * @author ChordFPG team
 * @version  1.0
 */
public class StatisticsGui extends JFrame {

    private Node node;

    public StatisticsGui(Node n) {
        setTitle("ChordFPG 1.0 - Statistics");

        this.node = n;

        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/Utilities/resources/logo2.gif")));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            double[] nodeStats = node.getNodeStatistics();
            double[] ringStats = node.getRingStatistics();

            String text;
            /*gets local and ring statistics*/
            text = "<html>"
                    + "<h3>Node</h3>"
                    + "<ul>"
                    + "<li><font color=\"red\">Number of Files:</font> " + "<font color=\"blue\">" + nodeStats[0] + "</font>" + "</li>"
                    + "<li><font color=\"red\">Number of Entries:</font> " + "<font color=\"blue\">" + nodeStats[1] + "</font>" + "</li>"
                    + "<li><font color=\"red\">Average Time for Searches:</font> " + "<font color=\"blue\">" + String.format("%.3f", nodeStats[2]) + " ms</font>" + "</li>"
                    + "<li><font color=\"red\">Average Hops for Searches:</font> " + "<font color=\"blue\">" + String.format("%.3f", nodeStats[3]) + " ms</font>" + "</li>"
                    + "<li><font color=\"red\">Number of Searches:</font> " + "<font color=\"blue\">" + nodeStats[4] + "</font>" + "</li>"
                    + "</ul>"
                    + "<h3>Ring</h3>"
                    + "<ul>"
                    + "<li><font color=\"red\">Total Number of Nodes:</font> " + "<font color=\"blue\">" + ringStats[0] + "</font>" + "</li>"
                    + "<li><font color=\"red\">Total Number of Files:</font> " + "<font color=\"blue\">" + ringStats[1] + "</font>" + "</li>"
                    + "<li><font color=\"red\">Average Total Time for Searches:</font> " + "<font color=\"blue\">" + String.format("%.3f", ringStats[2]) + " ms</font>" + "</li>"
                    + "<li><font color=\"red\">Average Total Hops for Searches:</font> " + "<font color=\"blue\">" + String.format("%.3f", ringStats[3]) + " ms</font>" + "</li>"
                    + "<li><font color=\"red\">Total Number of Searches:</font> " + "<font color=\"blue\">" + ringStats[4] + "</font>" + "</li>"
                    + "</ul>"
                    + "</html>";


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


        } catch (RemoteException rex) {
            JOptionPane.showMessageDialog(this, "A problem occurred while collecting statistics for the node. Please try again.", "ChordFPG said:", JOptionPane.ERROR_MESSAGE);
        }
    }
}