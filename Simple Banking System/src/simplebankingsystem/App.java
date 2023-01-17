package simplebankingsystem;

import java.sql.*;
import java.util.Scanner;
import simplebankingsystem.User;

public class App {
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:/Users/damianmichalec/database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public void createNewTable() {

        // SQL statement for creating a new table
        String sql = """
                CREATE TABLE IF NOT EXISTS card (
                	id integer PRIMARY KEY,
                	number text NOT NULL,
                 pin text NOT NULL,
                	balance integer NOT NULL
                );""";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertData(String cardNumber, String pinNumber, int balance){
        String sql = "INSERT or IGNORE into card(number, pin, balance) VALUES(?,?,?)";
        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, cardNumber);
            pstmt.setString(2, pinNumber);
            pstmt.setInt(3, balance);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validation(User user, String cardNumber, String pin) {
        boolean match = false;
        String sql = "SELECT * FROM card";
        try(Connection conn = this.connect();
            Statement statement = conn.createStatement();) {

            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                if(resultSet.getString("number").equals(cardNumber)
                        && resultSet.getString("pin").equals(pin)) {
                    user.setCardNumber(resultSet.getString("number"));
                    user.setPin(resultSet.getString("pin"));
                    user.setBalance(resultSet.getInt("balance"));
                    match = true;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return match;
    }
    public void addIncome(User user) {
        System.out.println("Enter income: ");
        Scanner sc = new Scanner(System.in);
        user.setBalance(user.getBalance() + sc.nextInt());
        String query = "update card set balance = ? where number = ?";

        try(Connection conn = this.connect();
            PreparedStatement preparedStmt = conn.prepareStatement(query);)
        {
            preparedStmt.setInt   (1, user.getBalance());
            preparedStmt.setString(2, user.getCardNumber());
            preparedStmt.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        System.out.println("Income was added!");
    }
    public int checkCardBalance(String cardNumber) {
        String sql = "SELECT * FROM card";
        try(Connection conn = this.connect()) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                if (resultSet.getString("number").equals(cardNumber))
                    return resultSet.getInt("balance");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void handleTransfer(User user, String cardNumber) {
        Scanner sc = new Scanner(System.in);
        int balanceOfRetriever;
        int transferAmount = sc.nextInt();
        if(!(transferAmount > user.getBalance())) {

            try(Connection conn = this.connect()) {
                conn.setAutoCommit(false);
                balanceOfRetriever = checkCardBalance(cardNumber);

                String query = "update card set balance = ? where number = ?";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setInt(1, user.getBalance() - transferAmount);
                preparedStmt.setString(2, user.getCardNumber());
                preparedStmt.executeUpdate();
                user.setBalance(user.getBalance() - transferAmount);

                query = "update card set balance = ? where number = ?";
                preparedStmt = conn.prepareStatement(query);
                preparedStmt.setInt(1, balanceOfRetriever + transferAmount);
                preparedStmt.setString(2, cardNumber);
                preparedStmt.executeUpdate();

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Not enough money!");
        }
    }
    public void closeAccount(User user) {
        String sql = "DELETE from card where number =" + user.getCardNumber();
        try(Connection conn = this.connect()){
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
