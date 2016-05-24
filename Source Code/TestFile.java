import java.util.*;
import java.io.*;
import java.math.*;

public class TestFile
{
	
	RandomAccessFile fin,fout;
	int j=0;
	int readInt;
	long e=0L;
	byte[] b=new byte[2048];
		
	public TestFile(String str) throws Exception
	{
		/*File f=new File("/home/stu14/s4/sj6390/Desktop/"+"E_"+str);
		fin=new RandomAccessFile(f,"r");
		doPart1(str);*/
		Partition(str);
	}
	public  void doPart1(String str) throws Exception
	{
		//System.out.println("ACC");
		fout=new RandomAccessFile("/home/stu14/s4/sj6390/Desktop/" +"Part" + ++j +str,"rw" );
		System.out.println("/home/stu14/s4/sj6390/Desktop/" +"Part" + j +str);
		while((readInt=fin.read(b)) != -1)
		{
			//System.out.println(readInt);
			fout.write(b,0,readInt);
			//System.out.println("ACC1");
			e+= readInt;
			//System.out.println("ACC2");
			if(e == 1024 * 2)
			{
				//System.out.println("ACC3");
				e=0L;
				//System.out.println("ACC4");
				fout.close();
				//System.out.println("ACC5");
				doPart1(str);
			}
			//System.out.println("ACC6");
		}
		//System.out.println("ACC7");
		//fout.close();
		//fin.close();
	}
	public void Partition(String str) throws Exception
	{
		
		
		String filename = "/home/stu14/s4/sj6390/Desktop/"+"E_"+str;		
		try {
			File file = new File(filename);
			BufferedReader br = new BufferedReader(new FileReader(file));
			long orgFileLen = file.length();
			long currLen = file.length() / 4;			
			boolean hasMoreFour = false;			
			if(currLen * 4 < file.length()) hasMoreFour = true;			
			long totalRead = 0;
			int c;
			String res = "";
			int i = 0;
			while((c = br.read()) != -1){
				res += (char)c;
				++totalRead;				
				--currLen;				
				if(currLen == 0){
					++i;					
					BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/home/stu14/s4/sj6390/Desktop/" +"Part" + i +str )));
					bw.write(res);
					bw.flush();
					bw.close();
					res = "";
					
					if(i == 3 && hasMoreFour) {
						currLen = (orgFileLen - totalRead);
					} else {
						if((orgFileLen - totalRead) > (orgFileLen / 4)){
							currLen = (orgFileLen / 4);
						} else {
							currLen = (orgFileLen - totalRead);
						}
					}					
				}
			}
			
			br.close();
			
		} catch (IOException e) {			
			e.printStackTrace();
		}		
		
		
	}
	/*
	public static void main(String args[])
	{
		System.out.println("A");
		try{
			System.out.println("B");
			//TestFile t=new TestFile();
		new TestFile(new File("/home/stu14/s4/sj6390/Desktop/A.txt"));
		System.out.println("C");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}*/
}