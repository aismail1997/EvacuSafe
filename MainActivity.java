public static void main(String[] args) {
    Connection conn1 = null;

    try {
        // connect way #1
        String url1 = "jdbc:mysql://localhost:3306/workerdb";
        String user = "myuser";
        String password = "xxxx";

        conn1 = DriverManager.getConnection(url1, user, password);
        if (conn1 != null) {
            System.out.println("Connected to the database test1");
        }
    } catch (SQLException ex) {
        System.out.println("An error occurred. Maybe user/password is invalid");
        ex.printStackTrace();
    }
}

