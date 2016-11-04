package vars;

import java.util.Vector;

import backgroundThreads.keepOnline.Online;
import backgroundThreads.update.GetFile;

/**
 * Created by SunBird on 2016/2/11.
 */
public class Vars {
    public static String imei;
    public static int nItem = 0;
    public static String ip = "192.168.1.40";//"119.29.68.158";//"192.168.1.44";
    public static int port = 21;
    public static String ftpName = "sunbird";
    public static String ftpPasswd = "a5018335686";
    public static String sqlName = "root";
    public static String sqlPasswd = "a5018335686";
    public static String localPathRoot = "/home/pi";
    public static Vector<String> administrator = new Vector<>();
    public static boolean isAdminListUsed = false;


    public static Thread online = new Online();
    public static Thread getfile = new GetFile();
    public static boolean isSet = false;

    public static int adID = -1;
    public static int repeatID = -1;

    public static String version;
    public static boolean versionCorrect = true;
    
}
