package TaskB4;
import common.Db;import java.sql.*;
public final class DatabaseMetadataReport{public static void main(String[]a)throws SQLException{try(Connection c=Db.connect()){DatabaseMetaData m=c.getMetaData();System.out.println("database="+m.getDatabaseProductName()+" "+m.getDatabaseProductVersion());System.out.println("driver="+m.getDriverName()+" "+m.getDriverVersion());try(ResultSet r=m.getTables(c.getCatalog(),null,"%",new String[]{"TABLE"})){while(r.next())System.out.println("table="+r.getString("TABLE_NAME"));}}}}
