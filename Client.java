// Java implementation for a client 
// Save file as Client.java 

import java.io.*; 
import java.net.*; 
import java.util.Scanner; 

// Client class 
public class Client 
{ 
	public static void main(String[] args) throws IOException{ 
		try
		{ 
			Scanner scn = new Scanner(System.in); 
			
			// getting localhost ip  172.17.25.223
			InetAddress ip = InetAddress.getByName("192.168.137.1"); 
	
			// establish the connection with server port 5056 
			Socket s = new Socket(ip, 5056); 
	
			// obtaining input and out streams 
			DataInputStream dis = new DataInputStream(s.getInputStream()); 
			DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
	
			// the following loop performs the exchange of 
			// information between client and client handler 
			int p=0;
			while (true) 
			{ 
				System.out.println(dis.readUTF()); 
				String tosend = scn.nextLine(); 
				System.out.println("Sending ....."+tosend);
				
				// If client sends exit,close this connection 
				// and then break from the while loop 
				if(tosend.equals("Exit")) 
				{ 
					dos.writeUTF(tosend); 
				//System.out.println("Coming back to Client...");
					System.out.println("Closing this connection : " + s); 
					s.close(); 
					System.out.println("Connection closed"); 
					break; 
				}
				else if(tosend.equals("Show") && p==1){
						dos.writeUTF(tosend);
						System.out.println("Show division....");
 						String dd=dis.readUTF();
						System.out.println("Show division after reciving...");
						System.out.println(dd);

											
									}	
				else if(tosend.equals("Show") && p==0){ 
					System.out.println("First Submit Some demand "); 
								     }
				else if(tosend.equals("New") && p==0){
					dos.writeUTF(tosend);
					//System.out.println("New Division....");
					String msg=dis.readUTF();
					System.out.println("Initial Status :"+msg);
					//System.out.println("New Division after receiving...");
					System.out.println("Enter the demand vector ");
					String a=scn.nextLine();
					dos.writeUTF(a);
					p=1;
					msg=dis.readUTF();
					System.out.println("Final Status :"+msg);
					if(msg.equals("Cannot Assign")){ p=0;}
				
 
							}
				else if(tosend.equals("New") && p==1){ 
				System.out.println("A demand is already submitted");
									}
				else if(tosend.equals("Release") && p==1){
						dos.writeUTF(tosend);
						System.out.println(dis.readUTF());
						String re=scn.nextLine();
						dos.writeUTF(re);
						String msg=dis.readUTF();
						System.out.println(msg);
									} 
				else if(tosend.equals("Release") && p==0){	
					System.out.println("Resources not assigned");
									}				
			
			} 
			
			// closing resources 
			scn.close(); 
			dis.close(); 
			dos.close(); 
		}catch(Exception e){ 
			e.printStackTrace(); 
		} 
	} 
} 

