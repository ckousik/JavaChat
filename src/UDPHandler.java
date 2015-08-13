import java.io.IOException;

import datagram.UDPBroadcaster;
import models.Message;

public class UDPHandler {
	private static Message msg= new Message("192.168.56.1", "0.0.0.0", "Test Message") ;
	private static Thread broadcast;
	public static void main(String[] args) {
		try {
			byte[] sendBytes = Message.serialize(msg);
			broadcast = new Thread(new UDPBroadcaster(sendBytes),"broadcaster");
			broadcast.start();
			broadcast.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
