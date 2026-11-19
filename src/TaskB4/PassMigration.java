package TaskB4;
import common.Db;import java.io.*;import java.nio.file.*;import java.sql.*;
public final class PassMigration{
 public static void main(String[]a)throws Exception{if(a.length!=2){System.err.println("Usage: PassMigration input.csv rejected.csv");return;}int ok=0,bad=0;boolean old;
  try(Connection c=Db.connect();BufferedReader r=Files.newBufferedReader(Path.of(a[0]));BufferedWriter out=Files.newBufferedWriter(Path.of(a[1]),StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING)){
   old=c.getAutoCommit();c.setAutoCommit(false);try(PreparedStatement p=c.prepareStatement("INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES(?,?,?,?,?)")){
    String line;int no=0,batch=0;while((line=r.readLine())!=null){no++;String[]f=line.split(",",-1);try{if(f.length!=5||f[0].isBlank()||f[1].isBlank()||f[2].isBlank())throw new IllegalArgumentException("field count/empty");int route=Integer.parseInt(f[3]);java.math.BigDecimal amount=new java.math.BigDecimal(f[4]);if(route<1||amount.signum()<0)throw new IllegalArgumentException("range");p.setString(1,f[0]);p.setString(2,f[1]);p.setString(3,f[2]);p.setInt(4,route);p.setBigDecimal(5,amount);p.addBatch();ok++;if(++batch==1000){p.executeBatch();batch=0;}}catch(IllegalArgumentException e){out.write(no+","+e.getMessage()+","+line);out.newLine();bad++;}}
    if(batch>0)p.executeBatch();c.commit();out.flush();
   }catch(SQLException|RuntimeException e){c.rollback();throw e;}finally{c.setAutoCommit(old);}
  }System.out.println("loaded="+ok+" rejected="+bad);}
}
