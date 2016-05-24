import java.util.*;
import java.io.*;
class TestFile1
{
	int a;
	int j=1;
	RandomAccessFile fin,fout;
	byte[] b=new byte[2048];
	public TestFile1(String file) throws Exception
	{
		File f=new File("/home/stu14/s4/sj6390/Desktop/"+j+"_"+file);
		//System.out.println("C");
		// Here the name of the output file to be given that we want to put on the file. 
		fout=new RandomAccessFile("A1.txt","rw");
		//System.out.println("D");
		//doJoin(f);
		//System.out.println("E");
		merge(file);
	}
	
	public static void merge(String file){
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("A1.txt", true));
			String res = "";
			for(int i = 1; i < 5; i++){
				BufferedReader br = new BufferedReader(new FileReader(new File("/home/stu14/s4/sj6390/Desktop/"+i+"_"+file)));
				int c = 0;
				while((c = br.read()) != -1){
					res += (char) c;
				}
				br.close();
				bw.write(res);
				bw.flush();
				res = "";
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	void doJoin(File f) throws Exception
	{
		System.out.println("A::"+f);
		System.out.println("AA" +f.length());
		if(f.exists())
		{
			fin=new RandomAccessFile(f,"r");
			while((a=fin.read(b))!=-1)
			{
				fout.write(b,0,a);
				
			}
			//fout.writeBytes("\n");
			fin.close();
			doJoin(new File(f.getPath().replace(j+"_",(++j)+"_")));
		}
	}
	/*public static void main(String[] args)
	{
		try
		{
			//System.out.println("A");
			new TestFile1(new File("/home/stu14/s4/sj6390/Desktop/A.txt"));
			//System.out.println("B");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}*/
}