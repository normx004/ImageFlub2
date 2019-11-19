package imageflubber;
import java.rmi.*;

public interface RemoteChooser extends java.rmi.Remote {
		public String getNextFilePath ( String lastFileName)
			throws java.rmi.RemoteException;
		}

