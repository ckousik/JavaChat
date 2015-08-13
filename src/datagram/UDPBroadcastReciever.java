package datagram;
import java.io.IOException;
import java.net.*;
import models.Message;


public class UDPBroadcastReciever implements Runnable {
	DatagramSocket rSocket;
	DatagramPacket rPacket;
	byte[] recvBuffer;

	public UDPBroadcastReciever(int SERVER_PORT) {
		super();
		try {
			recvBuffer = new byte[4136];
			rSocket = new DatagramSocket(SERVER_PORT, InetAddress.getByName("0.0.0.0"));
			rPacket = new DatagramPacket(recvBuffer, recvBuffer.length);
			rSocket.setBroadcast(true);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			recvBuffer = new byte[4132];
			rSocket.receive(rPacket);
			recvBuffer = rPacket.getData();
			System.out
					.println(getClass().getName() + ">>>Recieved packet from " + rPacket.getAddress().getHostAddress());
			Message m = Message.deserialize(recvBuffer);
			System.out
			.println("Data: "+m.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}

}
