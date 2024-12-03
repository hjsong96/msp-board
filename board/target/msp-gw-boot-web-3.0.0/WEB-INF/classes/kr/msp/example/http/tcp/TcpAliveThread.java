package kr.msp.example.http.tcp;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcpAliveThread extends Thread{

	@Getter
	@Setter
	private boolean isRunning = true;
	private final TcpAliveManager tcpAliveManager;

	public TcpAliveThread(TcpAliveManager tcpAliveManager){
		this.tcpAliveManager = tcpAliveManager;
	}

	@Override
	public void run(){
		log.info("### TCP Alive Check Thread START ###");

		while(isRunning){
			for(Map.Entry<String, SocketAddress> mapEntry : tcpAliveManager.getAllHostMap().entrySet()){
				Socket socket = new Socket();
				String hostName = mapEntry.getKey();
				SocketAddress socketAddress = mapEntry.getValue();

				int timeout = 2000;
				try {
					socket.connect(socketAddress, timeout);
					if(socket.isConnected()) {
						socket.close();
					}
					if(!tcpAliveManager.getAliveHostNameSet().contains(hostName)){
						tcpAliveManager.successConnectSocketAddress(hostName,socketAddress);
					}
				} catch (IOException e) {
					if(tcpAliveManager.getAliveHostNameSet().contains(hostName)) {
						tcpAliveManager.failConnectSocketAddress(hostName, socketAddress);
					}
					log.error("IOException - Unable to connect to [" + hostName + "] : " + socketAddress.toString() + ". " + e.toString());
				}

			}

			try {
				Thread.sleep(tcpAliveManager.getConnectionRetryTime());
			} catch (InterruptedException e) {
				log.error("Exception occurred.", e);
			}
		}
	}

}
