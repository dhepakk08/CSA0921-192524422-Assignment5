package TaskB3;

import common.Db;
import java.sql.*;

/** Demonstrates partial persistence vs rollback on a disposable local database. */
public final class TransactionFailureDemo {
    private TransactionFailureDemo() {}

    private static long count(Connection c) throws SQLException {
        try (Statement s=c.createStatement(); ResultSet r=s.executeQuery("SELECT COUNT(*) FROM bus_pass_benchmark")) {
            r.next(); return r.getLong(1);
        }
    }

    private static void run(Connection c, boolean autoCommit) throws SQLException {
        try (Statement s=c.createStatement()) { s.executeUpdate("DELETE FROM bus_pass_benchmark"); }
        c.setAutoCommit(autoCommit);
        try (PreparedStatement p=c.prepareStatement("INSERT INTO bus_pass_benchmark VALUES(?,?,?,?,?)")) {
            for (int i=0;i<10;i++) {
                p.setString(1,"F"+i); p.setString(2,"R"+i); p.setString(3,"Fixture"+i);
                p.setInt(4,1); p.setBigDecimal(5,i==5?new java.math.BigDecimal("-1.00"):new java.math.BigDecimal("1.00"));
                p.executeUpdate();
            }
            if (!autoCommit) c.commit();
        } catch (SQLException e) {
            if (!autoCommit) c.rollback();
            System.out.println("mode="+(autoCommit?"auto-commit":"transaction")+" failure="+e.getSQLState());
        } finally { c.setAutoCommit(true); }
        System.out.println("mode="+(autoCommit?"auto-commit":"transaction")+" rowsRemaining="+count(c));
    }

    public static void main(String[] args) throws SQLException {
        try (Connection c=Db.connect()) { run(c,true); run(c,false); }
    }
}
