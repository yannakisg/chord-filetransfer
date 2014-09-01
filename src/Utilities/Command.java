package Utilities;

import Application.FIDEntry;
import Application.FileSearch;
import Application.Node;
import FileServices.RequestFile;
import java.io.IOException;
import java.net.InetAddress;

/**
 * @author ChordFPG team
 * @version  1.0
 */
public class Command {

    private Node node;
    public static final String KILL = "kill"; //done
    public static final String SHOWSUCLIST = "cat /proc/chordFPG/successors"; //done
    public static final String HELP = "man chordFPG";
    public static final String SHOWFINLIST = "cat /proc/chordFPG/fingers"; //done
    public static final String QUIT = "exit"; //done
    public static final String SHOWPRED = "cat /proc/chordFPG/predecessor"; //done
    public static final String SHOWLOCALFILES = "cat /proc/chordFPG/localfiles"; //done
    public static final String SHOWREMOTEFILES = "cat /proc/chordFPG/remotefiles";
    public static final String GET = "wget"; //done
    public static final String WHOAMI = "whoami"; //done
    public static final String ERRORS = "cat /var/log/errors"; //done
    public static final String WARNINGS = "cat /var/log/warnings"; //done
    public static final String VERSION = "chordFPG --version"; //done
    public static final String CHORDVIEWER = "chordViewer"; //done
    public static final String NODEVIEWER = "nodeViewer"; //done
    public static final String SAVEASLOG = "cat /var/log/log >"; //done
    public static final String SAVEASCLI = "cat /var/chordFPG/cli >"; //done
    public static final String IFCONFIG = "ifconfig"; //done
    public static final String STATS = "cat /proc/chordFPG/stats"; //done

    public void fetchFile(String filename, Node node) throws IOException, java.net.UnknownHostException {
        FileSearch fs = new FileSearch();
        FIDEntry entry;
        entry = fs.searchFile(filename, node);
        RequestFile.getFile(InetAddress.getByName(entry.getIp().substring(1)), entry.getFSPort(), filename);
    }

    public String getManPage(String command) {
        return "Boring";
    }

    public String getHelp() {
        return null;
    }

    private void quit() {
        System.exit(0);
    }
}
