
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement; // Add this import
import java.sql.ResultSet; // Add this import
import java.sql.ResultSetMetaData;

public class SQLonRDS {

    private Connection con;
    private String url
            = "database-1.cpcaseuemk0k.us-east-1.rds.amazonaws.com"; // RDS endpoint
    private String uid = "admin"; // Your RDS username
    private String pw = "1518101001Ak"; // Your RDS password

    public static void main(String[] args) {
        SQLonRDS q = new SQLonRDS();
        try {
            q.connect(); // Establish connection to the RDS
            System.out.println("\nCreating Table: ");
            q.create(); // Create the tables in the database
            System.out.println("\nInserting records: ");
            q.insert(); // Insert data into the tables
            System.out.println("\nQuery 1 Results:");
            q.queryOne(); // Query the company table
            System.out.println("\nQuery 2 Results:");
            q.querytwo(); // Querying and performing the Join
            System.out.println("\nQuery 3 Results:");
            q.queryThree(); // Querying and performing the Join
            q.delete(); // Delete data from the tables
            q.close(); // Close the connection
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
// Method to connect to the database

    public void connect() throws SQLException, ClassNotFoundException {
        // Load MySQL JDBC Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        String jdbcUrl = "jdbc:mysql://" + url + "/database-1?user=" + uid
                + "&password=" + pw; // Ensure 'database-1'
        // is the database you
        // want to use
        System.out.println("Connecting to database...");
        con = DriverManager.getConnection(jdbcUrl);
        System.out.println("Connection Successful!");
    }
// Method to close the connection

    public void close() throws SQLException {
        if (con != null) {
            con.close();
            System.out.println("Connection Closed!");
        }

    }
// Method to create the tables

    public void create() throws SQLException {
        // Create the company table
        String createCompanyTable = "CREATE TABLE IF NOT EXISTS company ("
                + "id INT PRIMARY KEY, "
                + "name VARCHAR(50), "
                + "ticker CHAR(10), "
                + "annualRevenue DECIMAL(15,2), "
                + // up to
                999,
        999,999,999.99
    "numEmployees INT)";
    // Create the stockprice table with foreign key reference to
    company String createStockPriceTable = "CREATE TABLE IF NOT EXISTS
    stockprice(" + "
        companyId INT, " +
"priceDate DATE, " +
"openPrice DECIMAL(10,2), " + // up to 99,999,999.99
"highPrice DECIMAL(10,2), " + // up to 99,999,999.99
"lowPrice DECIMAL(10,2), " + // up to 99,999,999.99
"closePrice DECIMAL(10,2), " + // up to 99,999,999.99
"volume INT, " +
"PRIMARY KEY (companyId, priceDate), " +
"FOREIGN KEY (companyId) REFERENCES company(id))"; //
Foreign key to company table // Execute the create statements

        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(createCompanyTable);
            System.out.println("Company table created successfully!");
            stmt.executeUpdate(createStockPriceTable);
            System.out.println("Stockprice table created successfully!");
        }
    }
// Method to insert data into the tables

    public void insert() throws SQLException {
        // Insert data for the 'company' table
        String insertCompany = "INSERT IGNORE INTO company (id, name,ticker, annualRevenue, numEmployees) VALUES "
                + "(1, 'Apple', 'AAPL', 387540000000.00, 154000), "
                + "(2, 'GameStop', 'GME', 611000000.00, 12000), "
                + "(3, 'Handy Repair', null, 2000000, 50), "
                + "(4, 'Microsoft', 'MSFT', 198270000000.00, 221000), "
                + "(5, 'StartUp', null, 50000, 3)";
// Insert data for the 'stockprice' table
        String insertStockPrice = "INSERT IGNORE INTO stockprice (companyId, priceDate, openPrice, highPrice, lowPrice, closePrice, volume) VALUES "
                + "(1, '2022-08-15', 171.52, 173.39, 171.35, 173.19,54091700), "
                + "(1, '2022-08-16', 172.78, 173.71, 171.66, 173.03,56377100), "
                + "(1, '2022-08-17', 172.77, 176.15, 172.57, 174.55,79542000), "
                + "(1, '2022-08-18', 173.75, 174.90, 173.12, 174.15,62290100), "
                + "(1, '2022-08-19', 173.03, 173.74, 171.31, 171.52,70211500), "
                + "(1, '2022-08-22', 169.69, 169.86, 167.14, 167.57,69026800), "
                + "(1, '2022-08-23', 167.08, 168.71, 166.65, 167.23,54147100), "
                + "(1, '2022-08-24', 167.32, 168.11, 166.25, 167.53,53841500), "
                + "(1, '2022-08-25', 168.78, 170.14, 168.35, 170.03,51218200), "
                + "(1, '2022-08-26', 170.57, 171.05, 163.56, 163.62,78823500), "
                + "(1, '2022-08-29', 161.15, 162.90, 159.82, 161.38,73314000), "
                + "(1, '2022-08-30', 162.13, 162.56, 157.72, 158.91,77906200), "
                + "(2, '2022-08-15', 39.75, 40.39, 38.81, 39.68, 5243100),"
                + "(2, '2022-08-16', 39.17, 45.53, 38.60, 42.19,23602800), "
                + "(2, '2022-08-17', 42.18, 44.36, 40.41, 40.52, 9766400),"
                + "(2, '2022-08-18', 39.27, 40.07, 37.34, 37.93, 8145400),"
                + "(2, '2022-08-19', 35.18, 37.19, 34.67, 36.49, 9525600),"
                + "(2, '2022-08-22', 34.31, 36.20, 34.20, 34.50, 5798600),"
                + "(2, '2022-08-23', 34.70, 34.99, 33.45, 33.53, 4836300),"
                + "(2, '2022-08-24', 34.00, 34.94, 32.44, 32.50, 5620300)," + "(2, '2022-08-25', 32.84, 32.89, 31.50, 31.96, 4726300),"
                + "(2, '2022-08-26', 31.50, 32.38, 30.63, 30.94, 4289500),"
                + "(2, '2022-08-29', 30.48, 32.75, 30.38, 31.55, 4292700),"
                + "(2, '2022-08-30', 31.62, 31.87, 29.42, 29.84, 5060200),"
                + "(4, '2022-08-15', 291.00, 294.18, 290.11, 293.47,18085700), "
                + "(4, '2022-08-16', 291.99, 294.04, 290.42, 292.71,18102900), "
                + "(4, '2022-08-17', 289.74, 293.35, 289.47, 291.32,18253400), "
                + "(4, '2022-08-18', 290.19, 291.91, 289.08, 290.17,17186200), "
                + "(4, '2022-08-19', 288.90, 289.25, 285.56, 286.15,20557200), "
                + "(4, '2022-08-22', 282.08, 282.46, 277.22, 277.75,25061100), "
                + "(4, '2022-08-23', 276.44, 278.86, 275.40, 276.44,17527400), "
                + "(4, '2022-08-24', 275.41, 277.23, 275.11, 275.79,18137000), "
                + "(4, '2022-08-25', 277.33, 279.02, 274.52, 278.85,16583400), "
                + "(4, '2022-08-26', 279.08, 280.34, 267.98, 268.0927532500), "
                + "(4, '2022-08-29', 265.85, 267.40, 263.85, 265.23,20338500), "
                + "(4, '2022-08-30', 266.67, 267.05, 260.66, 262.97,22767100)";
// Create a Statement object to execute the queries
        try (Statement stmt = con.createStatement()) {
            // Execute the insert query for company
            stmt.executeUpdate(insertCompany);
            System.out.println(
                    "Data inserted into company table successfully !");
            // Execute the insert query for stockprice
            stmt.executeUpdate(insertStockPrice);
            System.out.println(
                    "Data inserted into stockprice table  successfully!");
        }
    }

    // Method to query 1
    public void queryOne() throws SQLException {
        String query = "SELECT name, annualRevenue, numEmployees "
                + "FROM company "
                + "WHERE numEmployees > 10000 OR annualRevenue < 1000000 "
                + "ORDER BY name ASC"; // Replace 'name' with the column youd like to sort by
        try (Statement stmt = con.createStatement(); ResultSet rst = stmt.executeQuery(query)) {
            System.out.println(resultSetToString(rst, 2)); // Display the first 10 rows of company data
        }
    }
// Method to query 2

    public void querytwo() throws SQLException {
// Fixed SQL query string
        String query = "SELECT c.name, c.ticker, "
                + "MIN(s.lowPrice) AS Lowest_Price, "
                + "MAX(s.highPrice) AS Highest_Price, "
                + "AVG(s.closePrice) AS Avg_Closing_Price, "
                + "AVG(s.volume) AS Avg_Volume "
                + "FROM company c "
                + "JOIN stockprice s ON c.id = s.companyId "
                + "WHERE s.priceDate BETWEEN '2022-08-22' AND '2022-08-26' "
                + "GROUP BY c.name, c.ticker "
                + "ORDER BY Avg_Volume DESC";
        try (Statement stmt = con.createStatement(); ResultSet rst = stmt.executeQuery(query)) {
            System.out.println(resultSetToString(rst, 10)); // Display thefirst 10 rows of company data
        }
    }
    // Method to query 3

    public void queryThree() throws SQLException {
        String query = "SELECT c.name, c.ticker, s.closePrice "
                + "FROM company c "
                + "JOIN stockprice s ON c.id = s.companyId "
                + "WHERE s.priceDate = '2022-08-30' "
                + "AND (c.ticker IS NULL OR s.closePrice <= "
                + "(SELECT AVG(s1.closePrice) * 1.10 "
                + "FROM stockprice s1 "
                + "WHERE s1.companyId = c.id "
                + "AND s1.priceDate BETWEEN '2022-08-15' AND '2022-08-19'))"
                + "ORDER BY c.name ASC";
        try (Statement stmt = con.createStatement(); ResultSet rst = stmt.executeQuery(query)) {
            System.out.println(resultSetToString(rst, 10)); // Display thefirst 10 rows of company data
        }

    }
