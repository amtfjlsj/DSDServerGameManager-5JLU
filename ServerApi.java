import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

public class ServerApi {
	private static int PORT = 9898;
	private static ServerApi serverApi = new ServerApi();
	private ServerSocket server;
	private Map<String, Socket> map;
	private ServerGameManager sgm;
	private GameBuilder gb;

	private ServerApi() {
		map = new HashMap<String, Socket>();
	}

	public void setServerGameManager(ServerGameManager s) {
		sgm = s;
	}

	public void setGameBuilder(GameBuilder g) {
		gb = g;
	}

	public static ServerApi getInstance() {
		return serverApi;
	}

	public void sendMsg(String ip, String msg) {
		// Send msg to client of ip.
	}

	public void sendMsg(Info info) {
		sendMsg(info.getIP(), info.getMsg());
	}

	public void sendAll(String msg) {
		//Send msg to all clients.
	}

	private class HandOut implements Runnable {
		private String ip;
		private String msg;
		
		public HandOut(String i, String m) {
			ip = i;
			msg = m;
		}

		@Override
		public void run() {
			gb.play(ip, msg);
			sgm.play(ip, msg);
		}
	}

	private class IORunnable implements Runnable {
		@Override
		public void run() {
			boolean running = true;
			System.out.println("ServerApi is waiting client connection(s)...");
			while(running) {
				for(String ip: map.keySet()) {
					try {
						ObjectInputStream input = 
							new ObjectInputStream(map.get(ip).getInputStream());
						if(input.available() > 0) {
							String message = input.readUTF();
							(new HandOut(ip, message)).start();
						}
					}catch(IOException ex) {
						System.out.println("Error: Unable to read from "+ip);
					}
				}
			}
		}
	}

}

class ServerGameManager {
	void play(String ip, String msg) {
		try {
			System.out.println("ServerGameManager is playing...");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class GameBuilder {
	void play(String ip, String msg) {
		try {
			System.out.println("GameBuilder is playing...");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
