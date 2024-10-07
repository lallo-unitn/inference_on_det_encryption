package nl.ut.eemcs.cloudsec;

import java.sql.*;
import java.util.*;

public class Database {

    private static final String url = "jdbc:sqlite:/home/lallo/uni/cloud_security/assignment_2/cryptoDB/encdb.sqlite";

    public int getFirstNameCount() {
        String query = "SELECT COUNT(DISTINCT enc_firstname) FROM enc_students";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }

    public HashMap<String, Integer> getLastnameFrequenciesOrdered() {
        String query = "SELECT enc_lastname FROM enc_students";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            HashMap<String, Integer> lastnameFrequencies = new HashMap<>();
            while (rs.next()) {
                String lastname = rs.getString("enc_lastname");
                if (lastnameFrequencies.containsKey(lastname)) {
                    lastnameFrequencies.put(lastname, lastnameFrequencies.get(lastname) + 1);
                } else {
                    lastnameFrequencies.put(lastname, 1);
                }
            }

            return orderHashMapByValue(lastnameFrequencies);

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    // determine first name frequencies
    public HashMap<String, Integer> getFirstnameFrequenciesOrdered() {
        String query = "SELECT enc_firstname FROM enc_students";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            HashMap<String, Integer> firstnameFrequencies = new HashMap<>();
            while (rs.next()) {
                String firstname = rs.getString("enc_firstname");
                if (firstnameFrequencies.containsKey(firstname)) {
                    firstnameFrequencies.put(firstname, firstnameFrequencies.get(firstname) + 1);
                } else {
                    firstnameFrequencies.put(firstname, 1);
                }
            }

            return orderHashMapByValue(firstnameFrequencies);

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public String getSecondUniqueHighestValue() {
        String query = "SELECT enc_grade FROM ("
                + "    SELECT DISTINCT enc_grade"
                + "    FROM enc_students"
                + "    ORDER BY enc_grade DESC"
                + ") as eseg LIMIT 1 OFFSET 1;";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Process the result
            if (rs.next()) {
                return rs.getString("enc_grade");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Student> getSecondToTopStudents() {
        String secondValue = getSecondUniqueHighestValue();
        String query = "SELECT enc_firstname, enc_lastname FROM enc_students WHERE enc_grade = ?";
        List<Student> resultList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, secondValue);
            ResultSet rs = pstmt.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Student student = null;
                for (int i = 1; i <= columnCount; i++) {
                    student = new Student(
                            rs.getString("enc_firstname"),
                            secondValue,
                            rs.getString("enc_lastname")
                    );
                }
                resultList.add(student);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return resultList;
    }

    // get students
    public List<Student> getStudents() {
        String query = "SELECT enc_firstname, enc_grade, enc_lastname FROM enc_students";
        List<Student> resultList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Student student = null;
                for (int i = 1; i <= columnCount; i++) {
                    student = new Student(
                            rs.getString("enc_firstname"),
                            rs.getString("enc_grade"),
                            rs.getString("enc_lastname")
                    );
                }
                resultList.add(student);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return resultList;
    }

    private void displayLastnameFrequencies(HashMap<String, Integer> lastnameFrequencies) {
        System.out.println("Lastname Frequencies:");
        for (Map.Entry<String, Integer> entry : lastnameFrequencies.entrySet()) {
            System.out.println("Lastname: " + entry.getKey() + ", Frequency: " + entry.getValue());
        }
    }

    // display first name frequencies
    private void displayFirstnameFrequencies(HashMap<String, Integer> firstnameFrequencies) {
        System.out.println("Firstname Frequencies:");
        for (Map.Entry<String, Integer> entry : firstnameFrequencies.entrySet()) {
            System.out.println("Firstname: " + entry.getKey() + ", Frequency: " + entry.getValue());
        }
    }

    private HashMap<String, Integer> orderHashMapByValue(HashMap<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());
        // Sort the list in ascending order
        list.sort(Map.Entry.comparingByValue());

        HashMap<String, Integer> lastnameFrequenciesOrdered = new LinkedHashMap<>();

        // put data from sorted list to hashmap
        for (Map.Entry<String, Integer> aa : list) {
            lastnameFrequenciesOrdered.put(aa.getKey(), aa.getValue());
        }

        return lastnameFrequenciesOrdered;
    }

}
