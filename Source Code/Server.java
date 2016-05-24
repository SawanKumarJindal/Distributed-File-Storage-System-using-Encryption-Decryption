import java.net.*;
import java.io.*;
import java.util.*;
/*
* Server class which deals in both the transfering and receiving of files with the client.
*/
public class Server
{
	// main method whihc will start the server.
	public static void main (String args[]) 
	{
		try
		{
			int portNo = 4234;
			ServerSocket serverSocket = new ServerSocket(portNo);
			while(true)
			{
				Socket clientSocket = serverSocket.accept();
				Connection c = new Connection(clientSocket);
			}
		}
		catch(IOException e)
		{
			System.out.println("Listen :"+e.getMessage());
		}
	}
}
// This class will act as a thread for each client request.
class Connection extends Thread 
{
	DataInputStream inputStream;
	DataOutputStream outputStream;
	Socket clientSocket;
	//static boolean sendReq=false;
	public Connection (Socket clientSocket)
	{
		try 
		{
			this.clientSocket = clientSocket;
			inputStream = new DataInputStream( clientSocket.getInputStream());
			outputStream =new DataOutputStream( clientSocket.getOutputStream());
			this.start();
		}
		catch(IOException e)
		{
			System.out.println("Connection:"+e.getMessage());
		}
	}
	// This run method handles the dofferent cases thrown by the client.
	public void run()
	{
		try
		{ 
			String dataReceived = inputStream.readUTF();
			String yourName,hostName,fromServer;
			String file= inputStream.readUTF();
			yourName=inputStream.readUTF();
			if(dataReceived.equals("Request to download the file"))
			{
				
				
				transferFile(file,yourName);
				
				
				// In this case it wil send request to the processes that are higher numbered.
				
				//if(listOfServers.indexOf(hostName) == listOfServers.size() -1)
				//	leader=hostName;
				//else
				//	sendElectionRequests(hostName);
				
				// Check the random number and get the highest value and chcek if the entry is not in the store queue map
				// thne display it.	
				
			}
			else if(dataReceived.equals("Send the encrypted file"))
			{
				
				receiveFile(file,yourName);
			}
			
		}
		catch(EOFException e) 
		{
			System.out.println("EOF:"+e.getMessage());
		}
		catch(IOException e) 
		{
			System.out.println("IO:"+e.getMessage());
		}
		finally
		{ 
			try
			{
				clientSocket.close();
			}
			catch (IOException e)
			{/*close failed*/}
		}
	}	
	/*public void transferDataFile(String file,String server)
	{
		String fileLocation="/home/stu14/s4/sj6390/Desktop/"+server;
		String[] arr=list(fileLocation+file+"Part");
		String exactFile
		
	}*/
	public  void receiveFile(String file,String root)
	{
		try
		{
			byte[] byteArray = new byte[1000000];
			InputStream inp = clientSocket.getInputStream();
			FileOutputStream fileOutputStream = new FileOutputStream(root + "." +file);
			BufferedOutputStream br = new BufferedOutputStream(fileOutputStream);
			int readBytes = inp.read(byteArray, 0, byteArray.length);
			br.write(byteArray, 0, readBytes);
			br.flush();
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}	
	}
	// This method transfers the file to the client
	public void transferFile(String file,String server)
	{
		String fileName="/home/stu14/s4/sj6390/Desktop/"+server+"."+file;
		System.out.println(fileName);
		//System.out.println(fileName.length());
		File f=new File(fileName);
		try
		{
			byte[] byteArray=new byte[(int)f.length()];
			System.out.println(byteArray.length);
			BufferedInputStream br = new BufferedInputStream(new FileInputStream(fileName));
			br.read(byteArray, 0, byteArray.length);
			OutputStream o = clientSocket.getOutputStream();
			o.write(byteArray, 0, byteArray.length);
			o.flush();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
}