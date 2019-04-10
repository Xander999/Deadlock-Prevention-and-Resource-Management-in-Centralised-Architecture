// Java implementation for a client 
// Save file as Client.java 

import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
import java.util.Timer;
import java.util.TimerTask;

// Client class 
public class Client 
{ 
		int tim=0;

		Timer myt=new Timer();
		TimerTask ob=new TimerTask(){
			public void run(){
				tim++;
					}
				};

		public void start()throws IOException{
		try
		{ 
			myt.schedule(ob,1000,60000);
			Scanner scn = new Scanner(System.in); 
						
			// getting localhost ip 
			InetAddress ip = InetAddress.getByName("localhost"); 
	
			// establish the connection with server port 5056 
			Socket s = new Socket(ip, 5056); 
			// obtaining input and out streams 
			DataInputStream dis = new DataInputStream(s.getInputStream()); 
			DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
	
			// the following loop performs the exchange of 
			// information between client and client handler 
			while (true) 
			{ 
				System.out.println(dis.readUTF()); 
				String tosend;

				if(tim%7==0){
				tosend="";
				dos.writeUTF(tosend);
				tim=0;
				}
				else{
				System.out.println("tim= "+tim);
				tosend = scn.nextLine(); 
				dos.writeUTF(tosend); 
				}
				
				// If client sends exit,close this connection 
				// and then break from the while loop 
				if(tosend.equals("Exit")) 
				{ 
					System.out.println("Closing this connection : " + s); 
					s.close(); 
					System.out.println("Connection closed"); 
					break; 
				} 
				
				// printing date or time as requested by client 
				String received = dis.readUTF(); 
				System.out.println(received); 
			} 
			
			// closing resources 
			scn.close(); 
			dis.close(); 
			dos.close(); 
		}catch(Exception e){ 
			e.printStackTrace(); 
		} 



				   }
	public static void main(String[] args) throws IOException 
	{ 
			Client ob=new Client();
			ob.start();
	}



 
} 

