import datagram.UDPBroadcastReciever;

public class UDPRecieverHandler {
	private static Thread receive = new Thread(new UDPBroadcastReciever(8888),"broadcast_reciever");
	
	public static void main(String[] args) {
		try{
			receive.start();
			receive.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}

} 
