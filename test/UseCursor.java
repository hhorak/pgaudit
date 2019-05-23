/*
javac UseCursor.java
java -cp /pgaudit/test/postgresql-42.2.5.jar:. UseCursor
sudo tail -f /var/lib/pgsql/11/data/log/postgresql-Thu.log

sudo vi /var/lib/pgsql/10/data/postgresql.conf
shared_preload_libraries = 'pgaudit'
pgaudit.log = 'all'
sudo systemctl restart postgresql-10.service

sudo tail -f /var/lib/pgsql/10/data/log/postgresql-Thu.log
*/
public class UseCursor {
    public static void main(String[] args) throws ClassNotFoundException, java.sql.SQLException
    {
        Class.forName("org.postgresql.Driver");
        java.sql.Connection conn =
            java.sql.DriverManager.getConnection("jdbc:postgresql://localhost/postgres");
        conn.setAutoCommit(false);
        java.sql.PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM pg_class", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        stmt.setFetchSize(20);
        java.sql.ResultSet rs = stmt.executeQuery();
        while (rs.next())
            System.out.println(rs.getString(1));
        rs.close();
        stmt.close();
        conn.close();
    }
}

shared_preload_libraries = 'pgaudit'
pgaudit.log = 'all'

AUDIT: SESSION,1,1,MISC,SET,,,SET extra_float_digits = 3,<not logged>
AUDIT: SESSION,2,1,MISC,SET,,,SET application_name = 'PostgreSQL JDBC Driver',<not logged>
LOG:  AUDIT: SESSION,3,1,MISC,BEGIN,,,BEGIN,<not logged>
LOG:  AUDIT: SESSION,4,1,READ,SELECT,,,SELECT * FROM pg_class,<not logged>
