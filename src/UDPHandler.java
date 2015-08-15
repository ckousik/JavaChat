import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import datagram.UDPBroadcaster;
import models.Message;

public class UDPHandler {
	private static Message msg;
	private static Thread broadcast;
	public static void main(String[] args) {
		BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
		try {
			while(true){
				String m = b.readLine();
				msg = new Message(" ", " ", m);
				byte[] sendBytes = Message.serialize(msg);
				broadcast = new Thread(new UDPBroadcaster(sendBytes),"broadcaster");
				broadcast.start();
				broadcast.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			broadcast.destroy();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
