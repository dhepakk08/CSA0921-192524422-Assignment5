package TaskB3;
import common.Db;import java.sql.*;import java.util.*;
public final class JdbcInsertBenchmark{
 static List<String[]> rows(int n){List<String[]>a=new ArrayList<>(n);for(int i=0;i<n;i++)a.add(new String[]{"B"+i,"R"+i,"Student"+i,""+(i%50+1),"4500.00"});return a;}
 static long run(Connection c,List<String[]> a,int m)throws SQLException{try(Statement s=c.createStatement()){s.executeUpdate("DELETE FROM bus_pass_benchmark");}c.setAutoCommit(false);long t=System.nanoTime();try{
  if(m==0){try(Statement s=c.createStatement()){for(String[]x:a)s.executeUpdate("INSERT INTO bus_pass_benchmark VALUES('"+x[0]+"','"+x[1]+"','"+x[2]+"',"+x[3]+","+x[4]+")");}}
  else try(PreparedStatement p=c.prepareStatement("INSERT INTO bus_pass_benchmark VALUES(?,?,?,?,?)")){int i=0;for(String[]x:a){p.setString(1,x[0]);p.setString(2,x[1]);p.setString(3,x[2]);p.setInt(4,Integer.parseInt(x[3]));p.setBigDecimal(5,new java.math.BigDecimal(x[4]));if(m==1)p.executeUpdate();else{p.addBatch();if(++i%1000==0)p.executeBatch();}}if(m==2)p.executeBatch();}c.commit();
 }catch(SQLException|RuntimeException e){c.rollback();throw e;}finally{c.setAutoCommit(true);}return System.nanoTime()-t;}
 static long count(Connection c)throws SQLException{try(Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT COUNT(*) FROM bus_pass_benchmark")){r.next();return r.getLong(1);}}
 public static void main(String[]z)throws Exception{try(Connection c=Db.connect()){for(int n:new int[]{10,1000,10000,100000})for(int m=0;m<3;m++){long ns=run(c,rows(n),m),count=count(c);System.out.printf("rows=%d method=%s elapsedNs=%d count=%d%n",n,new String[]{"Statement","PreparedStatement","Batch"}[m],ns,count);if(count!=n)throw new SQLException("count mismatch");}}}
}
