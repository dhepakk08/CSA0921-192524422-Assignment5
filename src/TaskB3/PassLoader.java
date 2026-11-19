package TaskB3;
import java.sql.*;import java.util.List;
public final class PassLoader{
 public static int loadAll(Connection c,List<String[]> rows)throws SQLException{
  String q="INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES(?,?,?,?,?)";boolean old=c.getAutoCommit();c.setAutoCommit(false);
  try(PreparedStatement p=c.prepareStatement(q)){int n=0;for(String[] x:rows){if(x.length!=5)throw new IllegalArgumentException("five fields required");p.setString(1,x[0]);p.setString(2,x[1]);p.setString(3,x[2]);p.setInt(4,Integer.parseInt(x[3]));p.setDouble(5,Double.parseDouble(x[4]));p.addBatch();n++;if(n%1000==0)p.executeBatch();}p.executeBatch();c.commit();return n;
  }catch(SQLException|RuntimeException e){try{c.rollback();}catch(SQLException x){e.addSuppressed(x);}throw e;}finally{c.setAutoCommit(old);}
 }
}
