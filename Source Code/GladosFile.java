import java.util.*;
import java.io.*;
import javax.crypto.*;
import java.security.*;
/*
* This is the main class where the execution begins.
*/
public class GladosFile
{
	 RandomAccessFile fin,fout;
	int j=0;
	int readInt;
	long e=0L;
	byte[] b=new byte[2048];
	static List<String> listOfServers = new ListOfServers().getServersList();
	static Map<String,String> hmapFileAndServers=new HashMap<String,String>();
	static Map<String,String> hmapUserAuthentication=new UserAuthentication().getHashMap();
	static Map<String,KeyPair> hmapKeyPair=new HashMap<String,KeyPair>();
	public static void displayOptions()
	{
		System.out.println("Enter the choice:");
		System.out.println("a: Upload a file.");
		System.out.println("b: Download a file.");
	}
	public static String randomServerName(String filename)
	{
		Random r=new Random();
		int randomNumber =r.nextInt(listOfServers.size());
		return listOfServers.get(randomNumber);
	}
		// Look at the video "https://www.youtube.com/watch?v=DXCPvQG19XQ"
	public static List<String> chooseServers(String parentServer)
	{
		List<String> list=new ArrayList<String>();
		list.add(parentServer);
		Random r=new Random();
		int randomNumber; 
		while(list.size() != listOfServers.size())
		{
			randomNumber =r.nextInt(listOfServers.size());
			if(!(list.contains(listOfServers.get(randomNumber))))
			{
				list.add(listOfServers.get(randomNumber));
			}
			
		}
		return list;
	}
	public static void addHeader(String filename,String server)
	{
		/*try {
			RandomAccessFile rf = new RandomAccessFile(filename, "rw");
			rf.seek(0);
			byte[] b = ("header:"+server+"\n").getBytes();
			
			rf.write(b);
						
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		try{
		StringBuilder sb=new StringBuilder();
		sb.append("Header:");
		sb.append(server);
		sb.append("\n");
		BufferedReader b=new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		String line=b.readLine();
		
			while(line!= null)
			{
			sb.append(line);
			sb.append("\n");
			line=b.readLine();
			}
    	
            FileWriter fwriter = new FileWriter(filename);
            BufferedWriter bwriter = new BufferedWriter(fwriter);
            bwriter.write(sb.toString());
            bwriter.close();
         }
        catch (Exception e){
              e.printStackTrace();
         }
    	
    	 
	}
	public static String extractFirstServer(String filename)
	{
		String[] arr=new String[2];;
		try{
		String firstServer="";
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String firstLine=br.readLine();
		arr=firstLine.split("\\:");
		
		removeFirstLine(filename);
		
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return arr[1];
	}
	public static void removeFirstLine(String fileName) throws IOException {  
    RandomAccessFile raf = new RandomAccessFile(fileName, "rw");          
     //Initial write position                                             
    long writePosition = raf.getFilePointer();                            
    raf.readLine();                                                       
    // Shift the next lines upwards.                                      
    long readPosition = raf.getFilePointer();                             

    byte[] buff = new byte[1024];                                         
    int n;                                                                
    while (-1 != (n = raf.read(buff))) {                                  
        raf.seek(writePosition);                                          
        raf.write(buff, 0, n);                                            
        readPosition += n;                                                
        writePosition += n;                                               
        raf.seek(readPosition);                                           
    }                                                                     
    raf.setLength(writePosition);                                         
    raf.close();                                                          
}        
	// Main method where the client starts all the servers and sends them the message to start fighting for Critical Section. 
	public static void main(String[] args)
	{
		try
		{
		RsaTest rr=new RsaTest();
		Scanner sc=new Scanner(System.in);
		String defaultPath="/home/stu14/s4/sj6390/Desktop/";
		char choice='x';
		do
		{
			displayOptions();
			String selectedOption = sc.next();
			switch(selectedOption)
			{
				// Case to uplaod the file.
				case "a":
					System.out.println("Enter the filename:");
					String filename=sc.next();
					String selectRandomServer;
					if(!(hmapFileAndServers.containsKey(filename)))
					{
						selectRandomServer=randomServerName(filename);
						hmapFileAndServers.put(filename,selectRandomServer);
					}
					else
					{
						System.out.println("File Already Present");
						System.out.println();
						break;
					}
					//  encrypt it, add the header as the next server, store it.
					try
					{
						//System.out.println("A");
						hmapKeyPair.put(filename,rr.getKeyPair());
						//System.out.println("BA");
						rr.textEncrypt(filename );
						//System.out.println("B");
						TestFile tf=new TestFile(filename);
						//System.out.println("C");
						//System.out.println(j);
						List<String> listOfServersToBeAssigned=chooseServers(selectRandomServer);
						System.out.println(listOfServersToBeAssigned);
						
						
						for(int i=0;i<listOfServers.size()-1;i++)
						{
							System.out.println(i+" "+"/home/stu14/s4/sj6390/Desktop/"+"Part"+ (i+1)+filename);
							// Encrypt the every part and write the header.
							
							addHeader("/home/stu14/s4/sj6390/Desktop/"+"Part"+ (i+1)+filename ,listOfServersToBeAssigned.get(i+1));
						}
						// Last part will have the header null and the file encrypted.
						
						//rr.textEncrypt("/home/stu14/s4/sj6390/Desktop/"+filename + "Part"+ listOfServers.size());
						addHeader("/home/stu14/s4/sj6390/Desktop/"+"Part"+ listOfServers.size()+filename ,null);
						//File ff=new File("/home/stu14/s4/sj6390/Desktop/"+filename);
						//System.out.println("haha:"+((int)ff.length()/listOfServers.size()));
						for(int i=0;i<listOfServers.size();i++)
						{
							System.out.println(i);
							Client c=new Client(listOfServersToBeAssigned.get(i),"Send the encrypted file",filename , (i+1));
						}
						/*for(int i=1;i<=listOfServers.size();i++)
						{
							File ff=new File("/home/stu14/s4/sj6390/Desktop/"+filename + "Part"+i);
							ff.delete();
						}*/
						for(int i=1;i<=listOfServers.size();i++)
						{
							File ff=new File("/home/stu14/s4/sj6390/Desktop/"+filename + "Part"+i+"_E");
							ff.delete();
						}
						
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
					
					
					
					
					
					break;
				// Case to download the file
				case "b":
					System.out.println("Enter Username:");
					String username=sc.next();
					System.out.println("Enter Password");
					String password=sc.next();
					if( hmapUserAuthentication.containsKey(username) && hmapUserAuthentication.get(username).equals(password))
					{
						System.out.println("User Authenticated");
						System.out.println("Enter the file to download");
						String file=sc.next();
						if(!(hmapFileAndServers.containsKey(file)))
						{
							System.out.println("File not present");
						}
						else
						{
							Thread t=new Thread();
							String firstServer=hmapFileAndServers.get(file);
							System.out.println(firstServer);
							// Go to the server, copy the file, decrypt it, and go to the next server.
							int k=0;
							while(!(firstServer.equals("null")))
							{
								try{
								System.out.println("K"+k);
								t.sleep(1000);
								//Thread.sleep(200000);
								Client cl=new Client(firstServer,"Request to download the file",file,++k);
								//System.out.println("gggg");
								//Thread.sleep(2000);
								firstServer=extractFirstServer("/home/stu14/s4/sj6390/Desktop/"+k+"_"+file);
								//Thread.sleep(2000);
								//rr.textDecrypt("/home/stu14/s4/sj6390/Desktop/"+k+"_"+file);
								}
								catch(Exception e)
								{
									e.printStackTrace();
								}
							}
							// Merge the subfiles and decrypt it.
							TestFile1 tf1=new TestFile1(file);
							// Decrypt the file
							//rr.textDecrypt("A1.txt");		
						}
					}
					else
					{
						System.out.println("User Not Authenticated");
					}
				
				
				
				
					break;
			}
			System.out.println("Do you wish to continue:");
			choice=sc.next().charAt(0);
		}while(choice=='Y' || choice=='y');	
		/*Thread t=new Thread();
		Random r=new Random();
		try{
		//	t.sleep(1000);
		String choosenServer=listOfServers.get(0);
		// After selecting the process, it will contact the server to start the election.
		Client c=new Client(choosenServer,"Start the Election");
		
		t.sleep(9000);
		for(int i=0;i<listOfServers.size();i++)
		{
				Client cl=new Client(listOfServers.get(i),"Still Working");
				t.sleep(3000);
		}
		
		t.sleep(1000);
		t.sleep(1000);
		Client cl=new Client("Declare Result");
		}catch(Exception e)
			
				System.out.println(e.getMessage());
			}*/
			
			
	}
	catch(Exception e)
			{
				e.printStackTrace();
			}
	}
}
