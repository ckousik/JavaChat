package models;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private InetAddress senderAddress;
	private InetAddress recieverAddress;
	private String message;
	
	public Message(InetAddress sender, InetAddress reciever, String m){
		senderAddress = sender;
		recieverAddress = reciever;
		message = m;
	}
	
	public Message(String senderIP, String recieverIP, String m) {
		try{
			senderAddress=InetAddress.getByName(senderIP);
			recieverAddress= InetAddress.getByName(recieverIP);
			message=m;
		}catch(UnknownHostException e){
			e.printStackTrace();
		}
		
	}
	
	public Message(byte[] senderIP, byte[] recieverIP, String m) throws UnknownHostException{
		try{
			senderAddress=InetAddress.getByAddress(senderIP);
			recieverAddress=InetAddress.getByAddress(recieverIP);
			message = m;
		
		}catch(UnknownHostException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public InetAddress getSenderAddress(){
		return senderAddress;
	}
	
	public InetAddress getRecieverAddress(){
		return recieverAddress;
	}
	
	public String getText(){
		return message;
	}
	
	@Override
	public String toString() {
		return new String(senderAddress.toString()+">>>"
				+recieverAddress.toString()+">>>"+message);
	}
	
	public static byte[] serialize(Message M) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream objStream = new ObjectOutputStream(out);
		objStream.writeObject(M);
		objStream.close();
		return out.toString().getBytes();
		
	}
	
	public static Message deserialize(byte[] data) throws IOException,ClassNotFoundException{
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream objIn = new ObjectInputStream(in);
		return (Message)objIn.readObject();
		
	}
	
}
