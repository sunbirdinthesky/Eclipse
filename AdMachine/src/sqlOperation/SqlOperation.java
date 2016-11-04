package sqlOperation;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * sql core, for MySQL
 */

public class SqlOperation {
	protected String driver = "com.mysql.jdbc.Driver";
	protected String url = "jdbc:mysql://" + vars.Vars.ip + ":3306/AdMachine";
	protected String user = "root";
	protected String password = "a5018335686";
    protected Connection conn;
    protected Statement statement;
    public ResultSet rSet;
    /*
     * return if sql is connected
     */
    public  boolean isConnected() {
        if (conn == null)
            return false;
        try {
            return !conn.isClosed();
        } catch (SQLException e) {
        }
        return false;
    }

    public synchronized boolean SqlInit() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);

            if(!conn.isClosed())
                System.out.println("SQL:sql connected!");

            statement = conn.createStatement();

            return true;
        } catch(Exception e){
        }
        return false;
    }
    
    /*
     * return if the phrase is successfully sent
     */
    
    public synchronized boolean SqlUpdate (String phrase) {
    	try {
    		statement.executeUpdate(phrase);
    		return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return false;
    }

    public synchronized boolean SqlQuery(String phrase) {
        try {
        	rSet =  statement.executeQuery(phrase);
    		return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    public synchronized void disconnect () {
        try {
			if(conn != null && !conn.isClosed())
			    conn.close();
	        if(rSet != null && !rSet.isClosed())
	            rSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
        disconnect();
    }
}
