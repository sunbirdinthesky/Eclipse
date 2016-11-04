package ftpOperation;

import java.io.File;  
import java.io.IOException;  
import org.apache.commons.net.ftp.FTP;  
import org.apache.commons.net.ftp.FTPClient;  
import org.apache.commons.net.ftp.FTPFile;  
  
public class FtpOperations extends FTPCoreOperations {  
	String ip = vars.Vars.ip;
	int port = vars.Vars.port;
	String userName = vars.Vars.ftpName;  
    String password = vars.Vars.ftpPasswd; 
    /** 
     * 上传文件
     *  
     * @param ip 
     * @param port 
     * @param userName 
     * @param password 
     * @param file 
     * @param remotePathName 
     * @param remoteName 
     */  
    public boolean upload(File file, String remotePathName, 
            String remoteName) {  
        FTPClient ftpClient = new FTPClient();  
        try {  
            ftpClient.connect(ip, port);  
            ftpClient.login(userName, password);  
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);  
            ftpClient.enterLocalPassiveMode();  
            upload(ftpClient, file, remotePathName, remoteName);  
  
        } catch (Exception e) {  
            System.out.println("fail to upload files : " + e.getMessage()); 
            return false;
        } finally {  
            if (ftpClient != null && ftpClient.isConnected()) {  
                try {  
                    ftpClient.disconnect();  
                    System.out.println("file uploaded");
                    return true;
                } catch (IOException e) {  
                    System.out.println("ftp fails to disconnect : "  
                            + e.getMessage()); 
                    return false;
                }  
            }  
        }
		return false;  
    }  
	
	/** 
     * 删除文件
     *  
     * @param remotePathName 
     * @param remoteName 
     */  
    public boolean delete(String remotePathName, 
            String remoteName) {  
        FTPClient ftpClient = new FTPClient();  
        try {  
            ftpClient.connect(ip, port);  
            ftpClient.login(userName, password);  
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);  
            ftpClient.enterLocalPassiveMode();  
            changeDirectory(ftpClient, remotePathName);  
            FTPFile[] files = ftpClient.listFiles(remoteName);  
            if (files.length == 1) {
                if (!ftpClient.deleteFile(remoteName)) {  
                    throw new Exception("fail to delete remote file ["  
                            + remotePathName + "]");  
                }  
            }  
            backToRootDirectory(ftpClient);  
        } catch (Exception e) {  
            System.out.println("fail to delete : " + e.getMessage());  
            return false;
        } finally {  
            if (ftpClient != null && ftpClient.isConnected()) {  
                try {  
                    ftpClient.disconnect();  
                    System.out.println("file deleted");
                    return true;
                } catch (IOException e) { 
                    System.out.println("ftp fails to disconnect : "  
                            + e.getMessage());  
                    return false;
                }  
            }
        }
		return false;  
    }  
	
	/** 
     * 重命名文件
     *  
     * @param newName 
     * @param remotePathName 
     * @param remoteName 
     */  
	
    public boolean rename(String newName, String remotePathName, 
            String remoteName) {  
        FTPClient ftpClient = new FTPClient();  
        try {  
            ftpClient.connect(ip, port);  
            ftpClient.login(userName, password);  
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);  
            ftpClient.enterLocalPassiveMode();  
  
            rename(ftpClient, newName, remotePathName, remoteName);  
  
        } catch (Exception e) {  
            System.out.println("fail to rename files : " + e.getMessage());  
    		return false; 
        } finally {  
            if (ftpClient != null && ftpClient.isConnected()) {  
                try {  
                    ftpClient.disconnect();  
                    System.out.println("file renameed");
                    return true;
                } catch (IOException e) {  
                    System.out.println("ftp fails to disconnect : "  
                            + e.getMessage());  
                    return false;
                }  
            }  
        }
		return false;  
    } 
	
	/** 
     * 下载文件
     *  
     * @param localfile 
     * @param remotePathName 
     * @param remoteName 
     */  
    public boolean download(File file, String remotePathName, 
            String remoteName) {  
        FTPClient ftpClient = new FTPClient();  
        try {  
            ftpClient.connect(ip, port);  
            ftpClient.login(userName, password);  
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);  
            ftpClient.enterLocalPassiveMode();  
  
            download(ftpClient, file, remotePathName, remoteName);  
  
        } catch (Exception e) {  
            System.out.println("fail to download files : " + e.getMessage()); 
            return false;
        } finally {  
            if (ftpClient != null && ftpClient.isConnected()) {  
                try {  
                    ftpClient.disconnect();  
                    System.out.println("file downloaded");
                    return true;
                } catch (IOException e) {  
                    System.out.println("ftp fails to disconnect : "  
                            + e.getMessage());  
                    return false;
                }  
            }  
        }
		return false;  
    } 
  
    
}  
