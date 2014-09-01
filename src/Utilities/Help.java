package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author ChordFPG team
 * @version  1.0
 */
public class Help extends JFrame {

    /** Creates new form HelpContents */
    public Help() {

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("ChordFPG 1.0 - Help");
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/Utilities/resources/logo2.gif")));


        String text;

        text = "<html>"
                + "<h3> Stats(Tab)</h3>"
                + "<ul>"
                + "<li>Information about the node of ChordFPG(successor, predecessor, IdKey)</li>"
                + "<li>Warnings</li><li>Information about fingers and successor lists of the node</li>"
                + "<li>Errors that occurred while ChordFPG is running</li>"
                + "</ul>"
                + "<h3> CLI(Tab)</h3>"
                + "<ul>"
                + "Console that the user can use in order to interact with ChordFPG"
                + "<ul>"
                + "<li>kill: Kills ChordFPG process. It does not disconnect the node from the ring.</li>"
                + "<li>cat /proc/chordFPG/successors: Prints the successor list, in the terminal window.</li>"
                + "<li>man chordFPG: Creates this window.</li>"
                + "<li>cat /proc/chordFPG/fingers: Prints the finger list of the node, in the terminal window. If the node has no fingers, it prints NULL.</li>"
                + "<li>exit: Disconnects the node from the chord ring, and terminates ChordFPG.</li>"
                + "<li>cat /proc/chordFPG/predecessor: Prints the predecessor of the node, in the terminal window.</li>"
                + "<li>cat /proc/chordFPG/localfiles: Prints the names of the files in the shared folder of the node.</li>"
                + "<li>cat /proc/chordFPG/remotefiles: Prints the Entries of the node.</li>"
                + "<li>GET (filename): Downloads the file(if it exists) from the ChordFPG shared folders.</li>"
                + "<li>whoami: Prints the IP, PID and the HashKey of the node, in the terminal window.</li>"
                + "<li>cat /var/log/errors: Prints the Errors that occurred during the execution of ChordFPG, in the terminal window.</li>"
                + "<li>cat /var/log/warnings: Prints the Warnings that were produced during the execution of ChordFPG, in the terminal window.</li>"
                + "<li>chordFPG --version: Prints the current version of the ChordFPG.</li>"
                + "<li>chordViewer: Creates a new window with a graphical design of the chord ring at that time.</li>"
                + "<li>nodeViewer: Creates a new window with a graphical design of the nodes.</li>"
                + "<li>cat /var/log/log >: Saves the information projected in a .txt file.</li>"
                + "<li>cat /var/chordFPG/cli >: Saves the terminal in a .txt file.</li>"
                + "<li>ifconfig: Prints the Connection Interface, the MacAddress and the IP address of the node, in the terminal window.</li>"
                + "<li>cat /proc/chordFPG/stats: Prints the Stats of the Node and of the Ring, in the terminal window.</li>"
                + "</ul>"
                + "</ul>"
                + "<h3> SaveAs</h3>"
                + "<ul>"
                + "<li>If in the Stats tab, this command saves the information projected.</li>"
                + "<li>If in CLI tab, this command saves the terminal.</li>"
                + "(Both are saved in .txt files)"
                + "</ul>"
                + "<h3> NodeViewer</h3>"
                + "<ul>"
                + "<li>Creates a new window with a graphical design of the nodes"
                + "<br>the current node is associated with(successor, predecessor, fingers)"
                + "</ul>"
                + "<h3> ChordViewer </h3>"
                + "<ul>"
                + "<li> Creates a new window with a graphical design of the chord ring at that time.</li>"
                + "</ul>"
                + "<h3> Exit </h3>"
                + "<ul>"
                + "<li>Disconnects the node from the chord ring and then terminates ChordFPG</li>"
                + "</ul>"
                + "<h3> Help</h3>"
                + "<ul>"
                + "<li>Creates this window.</li>"
                + "</ul>"
                + "<h3> About </h3>"
                + "<ul>"
                + "<li>Creates a new window with the data of the developers and the purpose of ChordFPG</li>"
                + "</ul>"
                + "</html>";


        JPanel panel = new JPanel();
        JLabel label = new JLabel(text);

        panel.setSize(600, 550);
        panel.add(label);
        JScrollPane scrollPane = new JScrollPane(panel);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
        setSize(620, 600);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width / 2) - (this.getWidth() / 2);
        int y = (screen.height / 2) - (this.getHeight() / 2);
        this.setLocation(x, y);
        this.setVisible(true);
    }
}