package udpSocketTrading;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPsocketTrader implements Runnable {

	private static final Logger lgr = LoggerFactory.getLogger(UDPsocketTrader.class);
	int portNumber;
	String socketName = "";

	public UDPsocketTrader(int portNumber, String socketName ) {
		this.portNumber = portNumber;
		this.socketName = socketName;
	}

	@SuppressWarnings("resource")
	public void run() {
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(portNumber);
		} catch (SocketException e) {
			lgr.error("[socket = new DatagramSocket(portNumber)] failed with [{}]", socketName);
			e.printStackTrace();
		}
		DatagramPacket inPacket;
		DatagramPacket outPacket;

		byte[] inMsg = new byte[300];

		while (true) {
			// create packet to store the received data
			inPacket = new DatagramPacket(inMsg, inMsg.length);

			lgr.info("[{}] is waitng for packet...", socketName);
			try {
				// store the data in the packet
				socket.receive(inPacket);
				lgr.info("socket [{}] recieved data.", socketName);
			} catch (IOException e) {
				lgr.error("[socket.receive(inPacket)] failed with [{}]", socketName);
				e.printStackTrace();
			}

			String msg =  new String(inPacket.getData(), 0, inPacket.getLength());
			lgr.info("received data : " + new String(inPacket.getData(), 0, inPacket.getLength()));

			// create packet to store the data to be sent
			outPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, inPacket.getAddress(), inPacket.getPort());
			try {
				// send the data to the client
				socket.send(outPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}