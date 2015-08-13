package datagram;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class UDPBroadcaster implements Runnable {
	DatagramSocket bSocket;
	DatagramPacket bPacket;
	InetAddress serverAddress;
	byte[] sendData;

	public UDPBroadcaster(byte[] dataToSend) {
		super();
		try {
			sendData = dataToSend;
			bSocket = new DatagramSocket();// Socket is bound to this port
			bSocket.setBroadcast(true);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			bPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8888);
			bSocket.send(bPacket);
			System.out.println(getClass().getName() + ">>>sent packet to 255.255.255.255(DEFAULT)");

			bPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("192.168.1.255"), 8888);
			System.out.println(getClass().getName() + ">>>sent packet to 192.168.1.255(DEFAULT)");
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();
				if (networkInterface.isLoopback() || !networkInterface.isUp())
					continue;

				System.out.println(getClass().getName() + ">>>Interface: " + networkInterface.getDisplayName());

				for (InterfaceAddress interfaceAdress : networkInterface.getInterfaceAddresses()) {
					InetAddress broadcast = interfaceAdress.getBroadcast();
					if (broadcast == null)
						continue;
					bPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
					// System.out.println("TEST: Check Data: "+new
					// String(bPacket.getData()));
					System.out.println(getClass().getName() + ">>>sent packet to " + broadcast.getHostAddress());
				}
			}
			System.out.println(getClass().getName() + ">>>Finished looping over interfaces");
			bSocket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
