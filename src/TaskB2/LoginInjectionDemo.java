package TaskB2;
import common.Db;import java.sql.*;
/** Unsafe example is only for a disposable local test database. */
public final class LoginInjectionDemo{
 static String payload="x' OR '1'='1' -- ";
 static boolean unsafe(Connection c,String id,String pwd)throws SQLException{String q="SELECT user_id FROM login_test WHERE user_id='"+id+"' AND password_value='"+pwd+"'";try(Statement s=c.createStatement();ResultSet r=s.executeQuery(q)){return r.next();}}
 static boolean safe(Connection c,String id,String pwd)throws SQLException{try(PreparedStatement p=c.prepareStatement("SELECT user_id FROM login_test WHERE user_id=? AND password_value=?")){p.setString(1,id);p.setString(2,pwd);try(ResultSet r=p.executeQuery()){return r.next();}}}
 public static void main(String[] a)throws SQLException{try(Connection c=Db.connect();Statement s=c.createStatement()){s.executeUpdate("DELETE FROM login_test");s.executeUpdate("INSERT INTO login_test VALUES('demo','correct-only-for-local-test')");System.out.println("unsafe="+unsafe(c,payload,"wrong"));System.out.println("prepared="+safe(c,payload,"wrong"));}}
}
