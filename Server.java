// Java implementation of Server side 
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 

import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 


// Server class 
public class Server 
{ 
	public static void main(String[] args) throws IOException 
	{ 
		// server is listening on port 5056 
		ServerSocket ss = new ServerSocket(5056); 
		System.out.println("Server Process is loaded....");
		//System.out.println(LocalDateTime.now());
		System.out.println("There are four resources '..a1...a2...a3...a4..'");
		
		// running infinite loop for getting 
		// client request 
		while (true) 
		{ 
			Socket s = null; 
			
			try
			{ 
				// socket object to receive incoming client requests 
				s = ss.accept(); 
				
				System.out.println("A new client-process is connected : " + s); 
				System.out.println("Local Port :"+s.getLocalPort());
				System.out.println("Remote Port :"+s.getPort());
				System.out.println("Inet Address :"+s.getInetAddress());
				System.out.println("Local Address :"+s.getLocalAddress());


				
				// obtaining input and out streams 
				DataInputStream dis = new DataInputStream(s.getInputStream()); 
				DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
				
				System.out.println("Assigning new thread for this client"); 

				// create a new thread object 
				Thread t = new ClientHandler(s, dis, dos); 

				// Invoking the start() method 
				t.start(); 
				
			} 
			catch (Exception e){ 
				s.close(); 
				e.printStackTrace(); 
			} 
		} 
	} 
} 

// ClientHandler class 
class ClientHandler extends Thread 
{ 
	final DataInputStream dis; 
	final DataOutputStream dos; 
	final Socket s; 
	String status="WAIT";
	static int max[]={4,4,4,4};
	static int num[]={4,4,4,4};
	int need[]={0,0,0,0};


	// Constructor 
	public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) 
	{ 
		this.s = s; 
		this.dis = dis; 
		this.dos = dos; 
	} 

	@Override
	public void run() 
	{ 
		String received; 
		String toreturn; 
		while (true) 
		{ 
			try { 

				// Ask user what he wants 
				dos.writeUTF("Enter 'New' to demand resources\nEnter 'Release' to release instances of resources \nEnter 'Show' to see the result of demand after demanding is done\nEnter 'Exit' to end connection"); 
				
				// receive the answer from client 
				received = dis.readUTF(); 
				System.out.println("Received Signal from Client "+s);
				if(received.equals("Exit")) 
				{ 
					System.out.println("Client " + this.s + " sends exit..."); 
					System.out.println("Closing this connection."); 
					this.s.close(); 
					System.out.println("Connection closed"); 
					break; 
				} 
				else if(received.equals("Show")){
					System.out.println(s+" Client demands :SHOW");
					System.out.println("\nInstance left :");
					for(int i=0;i<4;i++){
						System.out.print(num[i]+" ");
							}					
					System.out.println("\nAssigned to Client :");
					for(int i=0;i<4;i++){
						System.out.print(need[i]+" ");
							}
					dos.writeUTF(status);
					//System.out.println("status is changed from "+status+" ");
					//status="ACCEPT";
					//System.out.print("to "+status);
					//System.out.println("----Value of a = "+a);


									}
				else if(received.equals("New")){
					System.out.println(s+" Client demands :NEW");
					System.out.println("\nInstance left :");
					for(int i=0;i<4;i++){
						System.out.print(num[i]+" ");
							}					
					System.out.println("\nAssigned to Client :");
					for(int i=0;i<4;i++){
						System.out.print(need[i]+" ");
							}
					dos.writeUTF(status);
					//System.out.println("status is changed from "+status+" ");
					status="WAIT";
					System.out.print("to "+status);
					//System.out.println("----Value of a is changed from "+a);
					String xx=dis.readUTF();
					String[] tempArray;
					tempArray = xx.split(" ");
					System.out.println("Client has demanded for "+xx);
					for(int i = 0; i < tempArray.length; i++){
            			            need[i]=Integer.parseInt(tempArray[i]);
    										}
					System.out.println("Need Vector Assigned for Client"+s);
							int fg=0;				
					for(int i=0;i<4;i++){
						if(num[i]<need[i]){
							status="Wait";
							System.out.println("num ="+num[i]+" need ="+need[i]+" for i.no="+i);
							fg=1;
							}
								}
					if(fg==0){
						status="Assigned";
						for(int i=0;i<4;i++){
							num[i]=num[i]-need[i];
								    }
						}
					else{   status="Cannot Assign";}

					dos.writeUTF(status);
				
					}
				else if(received.equals("Release")){
					dos.writeUTF("Enter the release vector :");
					String xx=dis.readUTF();
					String[] tempArray;
					tempArray = xx.split(" ");
					int suc=0;
					for(int i = 0; i < tempArray.length; i++){
        			            	int z=Integer.parseInt(tempArray[i]);
						if(z<=need[i]){
							need[i]=need[i]-z;
							num[i]=num[i]+z;
							       }
						else{ suc=1; break;   }
    										}
				if(suc==0){  dos.writeUTF("Resource Instances Released");  }
				else{	     dos.writeUTF("Resource Instances not Correct");  }
					

									}				
				// write on output stream based on the 
				// answer from the client 

			} catch (IOException e) { 
				e.printStackTrace(); 
			} 
		} 
		
		try
		{ 
			// closing resources 
			this.dis.close(); 
			this.dos.close(); 
			
		}catch(IOException e){ 
			e.printStackTrace(); 
		} 
	} 
} 

