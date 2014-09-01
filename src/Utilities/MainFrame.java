package Utilities;

import Application.Node;
import Chord.Chord;
import DHash.Hashing;
import DHash.IdKey;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.DefaultCaret;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Main Frame of ChordFPG
 * @author ChordFPG team
 * @version  1.0
 */
public class MainFrame extends javax.swing.JFrame
{

    private final Command com;
    private Node node;
    private UpdateTaskLogs taskLogs;

    /**
     * Updates periodically the JTextAreas
     */
    private class UpdateTaskLogs extends TimerTask
    {

        public void run()
        {

            Log log = Log.getLogger();

            switch (log.getType())
            {
                case Log.ERROR:
                    MainFrame.jTextAreaDownR.append(log.getMessage() + "\n");
                    break;
                case Log.WARNING:
                    MainFrame.jTextAreaUpR.append(log.getMessage() + "\n");
                    break;
                case Log.INFORMATION:
                    MainFrame.jTextAreaUpL.append(log.getMessage() + "\n");
                    break;
                case Log.TABLES:
                    MainFrame.jTextAreaDownL.append(log.getMessage() + "\n");
                    break;
                default:
                    MainFrame.jTextAreaDownR.append("Error in Log\n");
            }

        }
    }

    /** Creates new form MainFrame */
    public MainFrame(final Node node, int timeIntervalGui, int timeIntervalLogs) throws RemoteException
    {

        initComponents();

        DefaultCaret caretUpL = (DefaultCaret) jTextAreaUpL.getCaret();
        DefaultCaret caretUpR = (DefaultCaret) jTextAreaUpR.getCaret();
        DefaultCaret caretDownL = (DefaultCaret) jTextAreaDownL.getCaret();
        DefaultCaret caretDownR = (DefaultCaret) jTextAreaDownR.getCaret();
        DefaultCaret caretCLI = (DefaultCaret) jTextAreaCLI.getCaret();

        caretUpL.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        caretUpR.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        caretDownL.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        caretDownR.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        caretCLI.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        jTextAreaUpL.setEditable(false);
        jTextAreaUpR.setEditable(false);
        jTextAreaCLI.setEditable(false);
        jTextAreaDownL.setEditable(false);
        jTextAreaDownR.setEditable(false);
        jTextPaneSouth.setEditable(false);

        jTextPaneSouth.setContentType("text/html");

        jTextAreaUpL.setToolTipText("Information");
        jTextAreaUpR.setToolTipText("Warnings");
        jTextAreaDownL.setToolTipText("Tables");
        jTextAreaDownR.setToolTipText("Errors");
        jTextAreaCLI.setToolTipText("CLI");


        this.setTitle("ChordFPG 1.0 - " + node.getLocalID().toString());
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/Utilities/resources/logo2.gif")));

        this.node = node;

        Timer timer1 = new Timer();
        taskLogs = new UpdateTaskLogs();
        timer1.scheduleAtFixedRate(taskLogs, 0, timeIntervalLogs);

        com = new Command();

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width / 2) - (this.getWidth() / 2);
        int y = (screen.height / 2) - (this.getHeight() / 2);
        this.setLocation(x, y);
    }

    public void sTitle(boolean isRegistry)
    {
        if (isRegistry)
        {
            String s = this.getTitle();
            this.setTitle(s + " - RMI Registry");
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelStats = new javax.swing.JPanel();
        jScrollPaneUpL = new javax.swing.JScrollPane();
        jTextAreaUpL = new javax.swing.JTextArea();
        jScrollPaneUpR = new javax.swing.JScrollPane();
        jTextAreaUpR = new javax.swing.JTextArea();
        jScrollPaneDownL = new javax.swing.JScrollPane();
        jTextAreaDownL = new javax.swing.JTextArea();
        jScrollPaneDownR = new javax.swing.JScrollPane();
        jTextAreaDownR = new javax.swing.JTextArea();
        jScrollPaneSouth = new javax.swing.JScrollPane();
        jTextPaneSouth = new javax.swing.JTextPane();
        jPanelCLI = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jScrollPaneCLI = new javax.swing.JScrollPane();
        jTextAreaCLI = new javax.swing.JTextArea();
        jTextField = new javax.swing.JTextField();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        saveAsItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        nodeViewerItem = new javax.swing.JMenuItem();
        chordViewerItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitItem = new javax.swing.JMenuItem();
        jMenuHelp = new javax.swing.JMenu();
        helpContentsItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        aboutItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTabbedPane.setPreferredSize(new java.awt.Dimension(650, 450));

        jTextAreaUpL.setColumns(20);
        jTextAreaUpL.setRows(5);
        jScrollPaneUpL.setViewportView(jTextAreaUpL);

        jTextAreaUpR.setColumns(20);
        jTextAreaUpR.setRows(5);
        jScrollPaneUpR.setViewportView(jTextAreaUpR);

        jTextAreaDownL.setColumns(20);
        jTextAreaDownL.setRows(5);
        jScrollPaneDownL.setViewportView(jTextAreaDownL);

        jTextAreaDownR.setColumns(20);
        jTextAreaDownR.setRows(5);
        jScrollPaneDownR.setViewportView(jTextAreaDownR);

        jScrollPaneSouth.setViewportView(jTextPaneSouth);

        javax.swing.GroupLayout jPanelStatsLayout = new javax.swing.GroupLayout(jPanelStats);
        jPanelStats.setLayout(jPanelStatsLayout);
        jPanelStatsLayout.setHorizontalGroup(
            jPanelStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPaneSouth, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelStatsLayout.createSequentialGroup()
                        .addGroup(jPanelStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPaneDownL)
                            .addComponent(jScrollPaneUpL, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPaneUpR, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                            .addComponent(jScrollPaneDownR, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanelStatsLayout.setVerticalGroup(
            jPanelStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneUpR, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                    .addComponent(jScrollPaneUpL, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPaneDownR, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(jScrollPaneDownL, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPaneSouth, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane.addTab("Stats", jPanelStats);

        jTextAreaCLI.setColumns(5);
        jTextAreaCLI.setEditable(false);
        jTextAreaCLI.setRows(5);
        jTextAreaCLI.setText("root@chordFPG:~$ ");
        jScrollPaneCLI.setViewportView(jTextAreaCLI);

        jTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCLILayout = new javax.swing.GroupLayout(jPanelCLI);
        jPanelCLI.setLayout(jPanelCLILayout);
        jPanelCLILayout.setHorizontalGroup(
            jPanelCLILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCLILayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCLILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneCLI, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
                    .addComponent(jTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 2, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelCLILayout.setVerticalGroup(
            jPanelCLILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCLILayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneCLI, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelCLILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane.addTab("CLI", jPanelCLI);

        jMenuFile.setText("File");

        saveAsItem.setText("Save As...");
        saveAsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsItemActionPerformed(evt);
            }
        });
        jMenuFile.add(saveAsItem);
        jMenuFile.add(jSeparator3);

        nodeViewerItem.setText("NodeViewer");
        nodeViewerItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodeViewerItemActionPerformed(evt);
            }
        });
        jMenuFile.add(nodeViewerItem);

        chordViewerItem.setText("Chord Viewer");
        chordViewerItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chordViewerItemActionPerformed(evt);
            }
        });
        jMenuFile.add(chordViewerItem);
        jMenuFile.add(jSeparator1);

        exitItem.setText("Exit");
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        jMenuFile.add(exitItem);

        jMenuBar.add(jMenuFile);

        jMenuHelp.setText("Help");

        helpContentsItem.setText("Help Contents");
        helpContentsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpContentsItemActionPerformed(evt);
            }
        });
        jMenuHelp.add(helpContentsItem);
        jMenuHelp.add(jSeparator2);

        aboutItem.setText("About");
        aboutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutItemActionPerformed(evt);
            }
        });
        jMenuHelp.add(aboutItem);

        jMenuBar.add(jMenuHelp);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldActionPerformed

        if (jTextField.getText().equals(""))
        {
            jTextField.setText("");
        }
        else if (jTextField.getText().equals(Command.WHOAMI))
        {

            try
            {
                String key = node.getLocalID().hashKeytoHexString();
                String id = node.getLocalID().toString();
                String pid = String.valueOf(node.getLocalID().getPID());

                String text = "\n" + id + "@" + pid + ":" + key;

                updateCLI(Command.WHOAMI, text);

            }
            catch (RemoteException ex)
            {
                jTextAreaCLI.setText(jTextAreaCLI.getText() + "\nAn error occurred.Please try again");
            }
        }
        else if (jTextField.getText().equals(Command.KILL))
        {
            updateCLI(Command.KILL, "");
            System.exit(0);
        }
        else if (jTextField.getText().equals(Command.VERSION))
        {
            updateCLI(Command.VERSION, "\nchordFPG 1.0\n\nCopyright (C) 2010 Fokas-Pomonis-Gasparis");
        }
        else if (jTextField.getText().equals(Command.IFCONFIG))
        {
            try
            {
                String ip = node.getLocalID().getIP();
                String[] macInter = node.getLocalID().getMacAndInterface();
                String text = "\n" + macInter[1] + "\t" + "Link encap:Ethernet  HWaddr " + macInter[0] + "\n\tinet addr:" + ip.substring(1);
                updateCLI(Command.IFCONFIG, text);
            }
            catch (RemoteException ex)
            {
                jTextAreaCLI.setText(jTextAreaCLI.getText() + "\nAn error occurred.Please try again");
            }
        }
        else if (jTextField.getText().startsWith(Command.GET + " "))
        {

            try
            {
                String s = jTextField.getText();
                String t = jTextField.getText().substring(4).trim();

                updateCLI(s, "");
                System.out.println("------------------------------------------------" + t);
                com.fetchFile(t, node);
                updateCLI("", "\nDownload Complete");

            }
            catch (IOException ex)
            {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (jTextField.getText().equals(Command.CHORDVIEWER))
        {
            updateCLI(Command.CHORDVIEWER, "");
            ChordViewer viewer;
            try
            {
                viewer = new ChordViewer(node);
                viewer.create();
            }
            catch (RemoteException ex)
            {
                JOptionPane.showMessageDialog(rootPane, "A problem occurred while attempting to load ChordViewer.\nPlease try again.", "ChordFPG said:", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (jTextField.getText().equals(Command.NODEVIEWER))
        {
            updateCLI(Command.NODEVIEWER, "");
            try
            {
                // TODO add your handling code here:
                NodeViewer viewer = new NodeViewer(node);
            }
            catch (RemoteException ex)
            {
                JOptionPane.showMessageDialog(rootPane, "A problem occurred while attempting to load NodeViewer.\nPlease try again.", "ChordFPG said:", JOptionPane.ERROR_MESSAGE);

            }
        }
        else if (jTextField.getText().startsWith(Command.SAVEASLOG))
        {
            String c = jTextField.getText();
            updateCLI(c, "");
            String seperator = "-------------------------------------\n";
            String[] log = MainFrame.getWholeLog();

            String message = seperator + "INFORMATION\n" + seperator + log[0] + "\n" + seperator + "TABLES\n" + seperator + log[1] + "\n" + seperator + "WARNINGS\n" + seperator + log[2] + "\n" + seperator + "ERRORS\n" + seperator + log[3];

            writeFile(c.substring((c.indexOf('>') + 2)), message);
        }
        else if (jTextField.getText().startsWith(Command.SAVEASCLI))
        {
            String message = jTextAreaCLI.getText();
            String c = jTextField.getText();
            updateCLI(c, "");
            writeFile(c.substring((c.indexOf('>') + 2)), message);
        }
        else if (jTextField.getText().equals(Command.STATS))
        {
            try
            {
                double[] nodeStats = node.getNodeStatistics();
                double[] ringStats = node.getRingStatistics();

                String text;
                text = "\nNode\n" + "Number of Files: " + nodeStats[0] + "\nNumber of Entries: " + nodeStats[1] + "\nAverage Time for Searches: " + String.format("%.3f", nodeStats[2]) + "\nAverage Hops for Searches: " + String.format("%.3f", nodeStats[3]) + "\nNumber of Searches " + nodeStats[4] + "\n\nRing" + "\nTotal Number of Nodes: " + ringStats[0] + "\nTotal Number of Files: " + ringStats[1] + "\nAverage Total Time for Searches: " + String.format("%.3f", ringStats[2]) + "\nAverage Total Hops for Searches: " + String.format("%.3f", ringStats[3]) + "\nTotal Number of Searches: " + ringStats[4];

                updateCLI(Command.STATS, text);

            }
            catch (RemoteException ex)
            {
                jTextAreaCLI.setText(jTextAreaCLI.getText() + "\nAn error occurred.Please try again");
            }
        }
        else if (jTextField.getText().equals(Command.ERRORS))
        {
            updateCLI(Command.ERRORS, "\n" + MainFrame.jTextAreaDownR.getText());
        }
        else if (jTextField.getText().equals(Command.WARNINGS))
        {
            updateCLI(Command.WARNINGS, "\n" + MainFrame.jTextAreaUpR.getText());
        }
        else if (jTextField.getText().equals(Command.SHOWSUCLIST))
        {
            try
            {
                String text = "";
                List<IdKey> listSuc = node.getWholeSucList();
                if (listSuc != null && listSuc.size() > 0)
                {
                    text += "\nSuccessor List\n";

                    for (int i = 0; i < listSuc.size(); i++)
                    {
                        text +=
                                "\nSuccessor[" + i + "]" + "\n\tID: " + listSuc.get(i).getHashKey().toHexString() + "\n\tIP " + listSuc.get(i).getIP().substring(1) + "\n\tPID: " + listSuc.get(i).getPID() + "\n\tMAC: " + listSuc.get(i).getMacAndInterface()[0] + "\n\tNetInterface: " + listSuc.get(i).getMacAndInterface()[1] + "\n";
                    }

                }
                else
                {
                    text = "\nSuccessor List : NULL";
                }
                updateCLI(Command.SHOWSUCLIST, text);
            }
            catch (RemoteException ex)
            {
                jTextAreaCLI.setText(jTextAreaCLI.getText() + "\nAn error occurred.Please try again");
            }
        }
        else if (jTextField.getText().equals(Command.SHOWFINLIST))
        {
            try
            {
                String text = "";
                List<IdKey> listFin = node.getFingerKeys();

                if (listFin != null && listFin.size() > 0)
                {
                    text += "\nFinger List\n";

                    for (int i = 0; i < listFin.size(); i++)
                    {
                        text +=
                                "\nFinger[" + i + "]" + "\n\tID: " + listFin.get(i).getHashKey().toHexString() + "\n\tIP " + listFin.get(i).getIP().substring(1) + "\n\tPID: " + listFin.get(i).getPID() + "\n\tMAC: " + listFin.get(i).getMacAndInterface()[0] + "\n\tNetInterface: " + listFin.get(i).getMacAndInterface()[1] + "\n";
                    }

                }
                else
                {
                    text = "\nFinger List : NULL";
                }
                updateCLI(Command.SHOWFINLIST, text);
            }
            catch (RemoteException ex)
            {
                jTextAreaCLI.setText(jTextAreaCLI.getText() + "\nAn error occurred.Please try again");
            }
        }
        else if (jTextField.getText().equals(Command.SHOWPRED))
        {
            try
            {
                IdKey pred = node.getPredecessor();
                String text;
                if (pred != null)
                {
                    text = "\nPredecessor" + "\n\tID: " + pred.getHashKey().toHexString() + "\n\tIP: " + pred.getIP().substring(1) + "\n\tPID: " + pred.getPID() + "\n\tMAC: " + pred.getMacAndInterface()[0] + "\n\tNetInterface: " + pred.getMacAndInterface()[1];
                }
                else
                {
                    text = "\nPredecessor : NULL";
                }
                updateCLI(Command.SHOWPRED, text);
            }
            catch (RemoteException ex)
            {
                jTextAreaCLI.setText(jTextAreaCLI.getText() + "\nAn error occurred.Please try again");
            }
        }
        else if (jTextField.getText().equals(Command.SHOWLOCALFILES))
        {
            try
            {
                String[] localFiles = node.getFileNames();
                String text = "";
                if (localFiles != null && localFiles.length > 0)
                {
                    text += "\nMy Files\n";
                    for (int i = 0; i < localFiles.length; i++)
                    {

                        text += "\nFile[" + i + "]\n" + "Name: " + localFiles[i] + "\nKey: " + Hashing.hash(localFiles[i].getBytes()).toHexString() + "\n";

                    }
                }
                else
                {
                    text = "\nLocal Files : NULL";
                }
                updateCLI(Command.SHOWLOCALFILES, text);
            }
            catch (RemoteException ex)
            {
                jTextAreaCLI.setText(jTextAreaCLI.getText() + "\nAn error occurred.Please try again");
            }
        }
        else if (jTextField.getText().equals(Command.HELP))
        {
            updateCLI(Command.HELP, "");
            Help help = new Help();
        }
        else if (jTextField.getText().equals(Command.SHOWREMOTEFILES))
        {
        }
        else if (jTextField.getText().equals(Command.QUIT))
        {
            try
            {
                Chord.disconnect(node);
            }
            catch (RemoteException ex)
            {
                jTextAreaCLI.setText(jTextAreaCLI.getText() + "\nAn error occurred.Please try again");
            }
        }
        else if (jTextField.getText().equals(Command.HELP))
        {
            Help help = new Help();
        }
        else
        {
            updateCLI(jTextField.getText(), "\n" + jTextField.getText() + ": command not found");
        }


}//GEN-LAST:event_jTextFieldActionPerformed
    public static void

setTextInCLI(String s) {
        String CLI = jTextAreaCLI.getText();

        jTextAreaCLI.setText

(CLI + "\n" + s);
    }

private void updateCLI(String command, String text) {
        jTextField.setText("");

        String user

= "root@chordFPG:~$ ";


        jTextAreaCLI.setText

(jTextAreaCLI.getText() + command);


        String CLI

= jTextAreaCLI.getText();

        jTextAreaCLI.setText

(CLI + text + "\n" + user);
    }

public static void

setTextPane(String s) {
        MainFrame.jTextPaneSouth.setText(s);
    }

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
    }//GEN-LAST:event_exitItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    }//GEN-LAST:event_formWindowClosing

    private void chordViewerItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chordViewerItemActionPerformed

        ChordViewer viewer;
        try

{
            viewer = new ChordViewer(node);
            viewer.create

();
        }

catch (RemoteException ex) {
            JOptionPane.showMessageDialog(rootPane, "A problem occurred while attempting to load ChordViewer.\nPlease try again.", "ChordFPG said:", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_chordViewerItemActionPerformed

    private void saveAsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsItemActionPerformed

        String message = "";
        String fileName;

JFileChooser fileChooser

= new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed

(false);
        int

option;

        if

(jTabbedPane.getSelectedIndex() == 0) {
            String seperator = "-------------------------------------\n";
            String[]

log = MainFrame.getWholeLog();

            message =

seperator + "INFORMATION\n" + seperator + log[0]
                    + "\n" + seperator + "TABLES\n" + seperator + log[1]
                    + "\n" + seperator + "WARNINGS\n" + seperator + log[2]
                    + "\n" + seperator + "ERRORS\n" + seperator + log[3];

        }

else if (jTabbedPane.getSelectedIndex() == 1) {

            message = jTextAreaCLI.getText();
        }

else {
            JOptionPane.showMessageDialog(MainFrame.this.getRootPane(), "An uknown problem occurred.\nPlease try again later.", "ChordFPG said: ", JOptionPane.ERROR_MESSAGE);
        }

option = fileChooser.showSaveDialog(MainFrame.this.rootPane);

        if

(option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if

(!file.getAbsolutePath().endsWith(".txt")) {
                fileName = file.getAbsolutePath() + ".txt";
            }

else {
                fileName = file.getAbsolutePath();
            }

writeFile(fileName, message);

            JOptionPane.showMessageDialog

(MainFrame.this.getRootPane(), "The file has been saved to:\n" + fileName, "ChordFPG said:", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_saveAsItemActionPerformed

    private void writeFile(String fileName, String message) {
        Log.addMessage(fileName, Log.WARNING);
        try

{
            File file = new File(fileName);
            FileOutputStream f

= new FileOutputStream(file);
            PrintStream p

= new PrintStream(f);
            p.print

(message);
        }

catch (IOException ex) {
            JOptionPane.showMessageDialog(MainFrame.this.getRootPane(), "A problem occurred while atempting to write the file.\nPlease try again later.", "ChordFPG said:", JOptionPane.ERROR_MESSAGE);
        }

}

    private void helpContentsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpContentsItemActionPerformed
        Help helpBox = new Help();
    }//GEN-LAST:event_helpContentsItemActionPerformed

public static String[]

getWholeLog() {

        String[] log = new String[4];
        log[0

] = jTextAreaUpL.getText();
        log[1

] = jTextAreaDownL.getText();
        log[2

] = jTextAreaUpR.getText();
        log[3

] = jTextAreaDownR.getText();

        return

log;
    }

    private void aboutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutItemActionPerformed
        aboutItemBox aboutBox = new aboutItemBox();
    }//GEN-LAST:event_aboutItemActionPerformed

    private void nodeViewerItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodeViewerItemActionPerformed
        try {

            NodeViewer viewer = new NodeViewer(node);
        }

catch (RemoteException ex) {
            JOptionPane.showMessageDialog(rootPane, "A problem occurred while attempting to load NodeViewer.\nPlease try again.", "ChordFPG said:", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_nodeViewerItemActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutItem;
    private javax.swing.JMenuItem chordViewerItem;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenuItem helpContentsItem;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JPanel jPanelCLI;
    private javax.swing.JPanel jPanelStats;
    private javax.swing.JScrollPane jScrollPaneCLI;
    private javax.swing.JScrollPane jScrollPaneDownL;
    private javax.swing.JScrollPane jScrollPaneDownR;
    private javax.swing.JScrollPane jScrollPaneSouth;
    private javax.swing.JScrollPane jScrollPaneUpL;
    private javax.swing.JScrollPane jScrollPaneUpR;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane;
    private static javax.swing.JTextArea jTextAreaCLI;
    private static javax.swing.JTextArea jTextAreaDownL;
    private static javax.swing.JTextArea jTextAreaDownR;
    private static javax.swing.JTextArea jTextAreaUpL;
    private static javax.swing.JTextArea jTextAreaUpR;
    private javax.swing.JTextField jTextField;
    private static javax.swing.JTextPane jTextPaneSouth;
    private javax.swing.JMenuItem nodeViewerItem;
    private javax.swing.JMenuItem saveAsItem;
    // End of variables declaration//GEN-END:variables
}