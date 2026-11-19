package TaskA3;
import java.sql.SQLException;import java.util.*;
public interface Dao<T,K>{void insert(T x)throws SQLException;Optional<T> findById(K id)throws SQLException;List<T> findAll()throws SQLException;boolean update(T x)throws SQLException;boolean delete(K id)throws SQLException;}
