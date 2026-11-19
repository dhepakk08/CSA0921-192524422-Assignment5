package TaskA5;
import java.sql.*;import java.math.BigDecimal;
public final class WalletTransfer{
 public static void transfer(Connection c,String from,String to,BigDecimal a)throws SQLException{
  if(a==null||a.signum()<=0)throw new IllegalArgumentException("amount must be positive");boolean old=c.getAutoCommit();c.setAutoCommit(false);
  try{
   String first=from.compareTo(to)<=0?from:to,second=from.compareTo(to)<=0?to:from;lock(c,first);if(!first.equals(second))lock(c,second);
   try(PreparedStatement p=c.prepareStatement("UPDATE wallet SET balance=balance-? WHERE wallet_id=? AND balance>=?")){p.setBigDecimal(1,a);p.setString(2,from);p.setBigDecimal(3,a);if(p.executeUpdate()!=1)throw new SQLException("Source missing or insufficient funds");}
   try(PreparedStatement p=c.prepareStatement("UPDATE wallet SET balance=balance+? WHERE wallet_id=?")){p.setBigDecimal(1,a);p.setString(2,to);if(p.executeUpdate()!=1)throw new SQLException("Destination missing");}c.commit();
  }catch(SQLException|RuntimeException e){try{c.rollback();}catch(SQLException x){e.addSuppressed(x);}throw e;}finally{c.setAutoCommit(old);}
 }
 private static void lock(Connection c,String id)throws SQLException{try(PreparedStatement p=c.prepareStatement("SELECT wallet_id FROM wallet WHERE wallet_id=? FOR UPDATE")){p.setString(1,id);try(ResultSet r=p.executeQuery()){if(!r.next())throw new SQLException("Missing wallet");}}}
}
