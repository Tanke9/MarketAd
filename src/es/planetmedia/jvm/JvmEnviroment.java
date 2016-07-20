package es.planetmedia.jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;


/**
 * Environment constants.
 * @author Ajosete
 *
 */
public class JvmEnviroment {

	private static final String EQUAL = "=";
	private static JvmEnviroment INSTANCE = null;
	private static List<String> argumentList = null;
	
	private JvmEnviroment(){
		RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
		argumentList = bean.getInputArguments();
	}
	
	private synchronized static void createInstance(){
		if (INSTANCE == null){
			INSTANCE = new JvmEnviroment();
		}
	}
	
	public static JvmEnviroment getInstance(){
		if (INSTANCE == null ) createInstance();
		return INSTANCE;
	}
	
	public String getBucketNameJVM(){
		return getParam(Constants.BUCKET_NAME_JVM);
	}
	
	public String getBucketAccessKeyJVM(){
		return getParam(Constants.BUCKET_ACCESS_KEY_JVM);
	}
	
	public String getBucketSecretKeyJVM(){
		return getParam(Constants.BUCKET_SECRET_KEY_JVM);
	}
	
	public String getSoapUrlParam(){
		return getParam(Constants.SOAP_URL_PARAM_JVM);
	}
	
	public int getMaxActiveJVM(){
		return Integer.parseInt(getParam(Constants.DB_MAX_ACTIVE_JVM));
	}
	
	public int getMaxIdleJVM(){
		return Integer.parseInt(getParam(Constants.DB_MAX_IDLE_JVM));
	}
	
	public long getMaxWaitJVM(){
		return Long.parseLong(getParam(Constants.DB_MAX_WAIT_JVM));
	}
	
	public String getPasswordJVM(){
		return getParam(Constants.DB_PASSWORD_JVM);
	}
	
	public String getUrlJVM(){
		return getParam(Constants.DB_URL_JVM);
	}
	
	public String getUsernameJVM(){
		return getParam(Constants.DB_USERNAME_JVM);
	}
	
	public String getLogPathJVM(){
		return getParam(Constants.LOG_FILE_PATH_JVM);
	}
	
	public String getParam(String argument){
		
		  for (int i = 0; i < argumentList.size(); i++) {
			  if (argumentList.get(i).contains(argument)){
				  return argumentList.get(i).substring(argumentList.get(i).indexOf(EQUAL)+1);
			  }
		  }
		  return null;
	}
}
