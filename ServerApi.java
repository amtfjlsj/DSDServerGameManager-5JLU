import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;

import Info;

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
		sendMsg(info.getIP(), info.gtMsg());
	}

	public void sendAll(String msg) {
		//Send msg to all clients.
	}

	private void handout(String ip, String msg) {
		gb.play(ip, msg);
		sgm.play(ip, msg);
	}

	private class IORunnable implements Runnable {
		@Override
		public void run() {
			boolean running = true;
			System.out.println("ServerApi is waiting client connection(s)...");
			while(running) {
				for(String ip: map.keySet()) {
					if(!get(ip)) {
						try {
							ObjectInputStream input = 
								new ObjectInputStream(get(ip).getInputStream());
							if(input.available() > 0) {
								String message = inputStream.readUTF();
								handout(ip, message);
							}
						}catch(IOException ex) {
							System.out.println("Error: Unable to read from "+ip);
						}
					}
				}
			}
		}
	}

	private class AcceptRunnable implements Runnable {
		@Override
		public void run() {
			try {
				server = new ServerSocket(PORT);
				boolean running = true;
				while(running) {
					Socket socket = server.accept();
					map.put(socket.getRemoteSocketAddress().toString(), 
							socket);
					System.out.println("Client "+socket.getRemoteSocketAddress().toString()
							+" connected to "+socket.getLocalSocketAddress());
				} catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}

class ServerGameManager {
	void play() {
		System.out.println("ServerGameManager is playing...");
	}
}

class GameBuilder {
	void play() {
		System.out.println("GameBuilder is playing...");
	}
}
