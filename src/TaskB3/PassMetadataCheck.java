package TaskB3;

import common.Db;
import java.sql.*;

/** Verifies small-data insert results and reports ResultSet column metadata. */
public final class PassMetadataCheck {
    public static void main(String[] args) throws SQLException {
        try (Connection c=Db.connect(); Statement s=c.createStatement()) {
            try (ResultSet r=s.executeQuery("SELECT * FROM bus_pass ORDER BY pass_id LIMIT 10")) {
                ResultSetMetaData m=r.getMetaData();
                for(int i=1;i<=m.getColumnCount();i++)
                    System.out.println(m.getColumnLabel(i)+","+m.getColumnTypeName(i));
                int rows=0; while(r.next()) rows++;
                System.out.println("verifiedRows="+rows);
            }
        }
    }
}
