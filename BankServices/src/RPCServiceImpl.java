import java.sql.*;

public class RPCServiceImpl implements  RPCService {

    Connection con = null;
    Statement stat = null;
    String url = "jdbc:mysql://localhost:3306/bank";
    String user = "root";
    String passwd = "19970303";
    //static
    //static

    public RPCServiceImpl() {
    }

    public void database() {

    }

    public boolean login(String Username, String Password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, passwd);
            stat = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String dPassword = null;
        String dUsername = null;

        try {
            ResultSet rs = stat.executeQuery("select * from user where Username='" + Username + "'");

           // System.out.println(Username);


            while (rs.next()) {
                dPassword = rs.getString("Password");
                    //System.out.println(Password);
            }
            if (Password.equals(dPassword)) {
                //dUsername=Username;

                rs.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                con.close();
                stat.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean save(int Money, String Password,String dUsername,String dPassword) {
        if(Password.equals(dPassword)){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, user, passwd);
                stat = con.createStatement();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int dMoney=0;

        try {
            ResultSet rs = stat.executeQuery("select * from user WHERE Username='" + dUsername + "'");

            while (rs.next()) {
                dMoney=rs.getInt("Money");
            }

            dMoney=Money+dMoney;

            rs.close();

            int r=stat.executeUpdate("update user set Money = "+dMoney+" where Username='" + dUsername + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                con.close();
                stat.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public boolean draw(int Money, String Password,String dUsername,String dPassword) {
        if(Password.equals(dPassword)){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, user, passwd);
                stat = con.createStatement();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            return false;
        }

        int dMoney=0;

        try {
            ResultSet rs = stat.executeQuery("select * from user WHERE Username='" + dUsername + "'");

            while (rs.next()) {
                dMoney=rs.getInt("Money");
            }

            dMoney=dMoney-Money;

            rs.close();

            int r=stat.executeUpdate("update user set Money = "+dMoney+" where Username='" + dUsername + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                con.close();
                stat.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public boolean transfer(int Money, String Password,String toUsername,String dUsername,String dPassword) {
        Statement st=null;

        if(Password.equals(dPassword)){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, user, passwd);
                stat = con.createStatement();
                st=con.createStatement();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            return false;
        }

        int dMoney=0;
        int toMoney=0;

        try {
            ResultSet rs = stat.executeQuery("select * from user WHERE Username='" + dUsername + "'");
            ResultSet r = st.executeQuery("select * from user WHERE Username='" + toUsername + "'");

            while (rs.next()) {
                dMoney=rs.getInt("Money");
            }

            dMoney=dMoney-Money;

            while (r.next()){
                toMoney=r.getInt("Money");
            }

            rs.close();
            r.close();

            toMoney=toMoney+Money;

            int rr=stat.executeUpdate("update user set Money = "+dMoney+" where Username='" + dUsername + "'");
            rr=st.executeUpdate("update user set Money = "+toMoney+" where Username='" + toUsername + "'");

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                con.close();
                stat.close();
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public int check(String Password,String dUsername,String dPassword) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, user, passwd);
                stat = con.createStatement();
            } catch (Exception e) {
                e.printStackTrace();
            }


        int dMoney=0;

        try {
            ResultSet rs = stat.executeQuery("select * from user WHERE Username='" + dUsername + "'");

            while (rs.next()) {
                dMoney=rs.getInt("Money");
            }

            rs.close();

            System.out.println(dMoney);


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                con.close();
                stat.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dMoney;
    }

}
