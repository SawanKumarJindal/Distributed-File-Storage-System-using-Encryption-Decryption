import java.util.Map;
import java.util.HashMap;
class UserAuthentication
{
	static Map<String,String> hmap;
	public UserAuthentication()
	{
		 hmap=new HashMap<String,String>();
		 hmap.put("Abc","Pqr");
		 hmap.put("Tiger","123");
	}
	public Map<String,String> getHashMap()
	{
		return hmap;
	}
}