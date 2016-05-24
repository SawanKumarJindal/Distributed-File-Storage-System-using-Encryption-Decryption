import java.net.*;
import java.io.*;
import java.util.*;
/*
* Client class which deals in both the transfering and receiving of files with the server.
*/
public class Client 
{
	Socket s=null;
	DataInputStream dataIStream;
	DataOutputStream dataOStream;
	// Client constructors dealing with multiple scenario's. Each constructor is created to handle specific case.
	public Client(String server,String msg)
	{
		try
		{
			int portNo = 4234;
			s = new Socket(server, portNo);
			dataIStream = new DataInputStream( s.getInputStream());
			dataOStream =new DataOutputStream( s.getOutputStream());
			dataOStream.writeUTF(msg);	
		}
		catch (UnknownHostException e)
		{
			System.out.println("Sock:"+e.getMessage());
		}
		catch (EOFException e)
		{
			System.out.println("EOF:"+e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println("IO:"+e.getMessage());
		}
		finally 
		{
			if(s!=null)
				try 
				{
					s.close();
				}
				catch (IOException e)
				{//close failed
				}
		}
	}
	public Client(String serverName,String msg,String file)
	{
		try
		{
			int portNo = 4234;
			s = new Socket(serverName, portNo);
			dataIStream = new DataInputStream( s.getInputStream());
			dataOStream =new DataOutputStream( s.getOutputStream());
			dataOStream.writeUTF(msg);	
			dataOStream.writeUTF(file);	
			dataOStream.writeUTF(serverName);			
			if(msg.equals("Request to download the file"))
			{
				//receiveFile(s,file,"");
				
				/*String dataReceived=dataIStream.readUTF();
				if(dataReceived.equals("Leader Elected"))
					leader=dataIStream.readUTF();
				else
				{
					int valueToBe=Integer.parseInt(dataIStream.readUTF());
					hmap.put(serverName,valueToBe);
				}
				activeServers.add(serverName);*/
			}
			else if(msg.equals("Send the encrypted file"))
			{
				transferFile(file);
			}
			//Thread t=new Thread();
			//t.sleep(1000);
		}
		catch(Exception e1)
		{
			System.out.println(e1.getMessage());
		}
		
		finally 
		{
			if(s!=null)
				try 
				{
					s.close();
				}
				catch (IOException e)
				{/*close failed*/}
		}
	}
	public Client(String serverName,String msg,String file,int k)
	{
		try
		{
			int portNo = 4234;
			s = new Socket(serverName, portNo);
			dataIStream = new DataInputStream( s.getInputStream());
			dataOStream =new DataOutputStream( s.getOutputStream());
			dataOStream.writeUTF(msg);
			//System.out.println("File:"+file);	
			dataOStream.writeUTF(file);	
			dataOStream.writeUTF(serverName);			
			if(msg.equals("Request to download the file"))
			{
				receiveFile(s,file,"",k);
				
				/*String dataReceived=dataIStream.readUTF();
				if(dataReceived.equals("Leader Elected"))
					leader=dataIStream.readUTF();
				else
				{
					int valueToBe=Integer.parseInt(dataIStream.readUTF());
					hmap.put(serverName,valueToBe);
				}
				activeServers.add(serverName);*/
			}
			else if(msg.equals("Send the encrypted file"))
			{
				transferFile("Part"+k+file);
			}
			//Thread t=new Thread();
			//t.sleep(1000);
		}
		catch(Exception e1)
		{
			System.out.println(e1.getMessage());
		}
		
		finally 
		{
			if(s!=null)
				try 
				{
					s.close();
				}
				catch (IOException e)
				{/*close failed*/}
		}
	}
	public void transferFile(String file)
	{
		String fileName="/home/stu14/s4/sj6390/Desktop/"+file;
		System.out.println(fileName);
		//System.out.println(fileName.length());
		File f=new File(fileName);
		try
		{
			byte[] byteArray=new byte[(int)f.length()];
			System.out.println(byteArray.length);
			BufferedInputStream br = new BufferedInputStream(new FileInputStream(fileName));
			br.read(byteArray, 0, byteArray.length);
			OutputStream o = s.getOutputStream();
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
	public static void receiveFile(Socket s,String file,String presentName,int k)
	{
		try
		{
			Thread t=new Thread();
			//System.out.println("A");
			byte[] byteArray = new byte[2048];
			//System.out.println("B");
			//t.sleep(1000);
			InputStream inp = s.getInputStream();
			
			
			//System.out.println("C");
			FileOutputStream fileOutputStream = new FileOutputStream(k+"_"+file);
			System.out.println("D");
			//t.sleep(1000);
			BufferedOutputStream br = new BufferedOutputStream(fileOutputStream);
			//System.out.println("E");
			//t.sleep(1000);
			
			int readBytes = inp.read(byteArray, 0, byteArray.length);
			//System.out.println("F");
			//t.sleep(1000);
			
			br.write(byteArray, 0, readBytes);
			//System.out.println("G");
			//t.sleep(1000);
			
			br.flush();
			//System.out.println("F");
			//t.sleep(1000);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}	
	}
	/*
	public Client(String serverName,String msg,String hostName)
	{
		try
		{
			int portNo = 4234;
			s = new Socket(serverName, portNo);
			dataIStream = new DataInputStream( s.getInputStream());
			dataOStream =new DataOutputStream( s.getOutputStream());
			dataOStream.writeUTF(msg);	
			dataOStream.writeUTF(hostName);	
			dataOStream.writeUTF(serverName);	
		}
		catch (UnknownHostException e)
		{
			System.out.println("Sock:"+e.getMessage());
		}
		catch (EOFException e)
		{
			System.out.println("EOF:"+e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println("IO:"+e.getMessage());
		}
		finally 
		{
			if(s!=null)
				try 
				{
					s.close();
				}
				catch (IOException e)
				{
					//close failed
					}
		}
	}
	public String calculateWinner()
	{
		if(!(leader.equals("") ))
		{
			if( activeServers.contains(leader))
				return "Leader Elected: "+leader;
			else
				return "Re-Election";
		}
		else
		{
			
			Collection<Integer> m;
			while(true)
			{
				String tempString="";
				int maxValue=Collections.max(hmap.values());
				// Find max value
				for(Map.Entry<String,Integer> hh:hmap.entrySet())
				{
					if(hh.getValue() == maxValue)
					{
						tempString=hh.getKey();
						break;
					}
				}
				if(activeServers.contains(tempString))
				{	
					return tempString;
				}
				else
					hmap.remove(tempString);
			}
		}
	}*/
}