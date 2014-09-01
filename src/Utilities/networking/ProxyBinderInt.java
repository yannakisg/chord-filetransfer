package Utilities.networking;

import java.rmi.*;

/**
 * @author ChordFPG Team
 * @version 1.0
 */
public interface ProxyBinderInt extends Remote {

    public void remoteBind(String prefix, Remote omg) throws RemoteException, AlreadyBoundException;

    public void remoteUnbind(String prefix) throws RemoteException, NotBoundException;
}