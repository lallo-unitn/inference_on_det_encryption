package nl.ut.eemcs.cloudsec;

import java.util.HashMap;
import java.util.List;

public class IndexMatrix {
    public boolean[][] matrix = null;
    public int rows = 0;
    public int cols = 0;
    public List<String> surnameList = null;
    public List<Student> studentList = null;
    public HashMap<String, String> lookupLastNames = null;

    public IndexMatrix(List<Student> studentList, List<String> surnameList, HashMap<String, String> lookupLastNames) {
        this.surnameList = surnameList;
        this.studentList = studentList;
        this.lookupLastNames = lookupLastNames;
        this.rows = surnameList.size();
        this.cols = studentList.size();
        matrix = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = surnameList.get(i).contains(lookupLastNames.get(studentList.get(j).enc_surname));
            }
        }
    }

    // get students from surname using matrix
    public List<Student> getStudentsFromSurname(String surname) {
        List<Student> students = new java.util.ArrayList<>();
        int i = surnameList.indexOf(surname);
        if (i != -1) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j]) {
                    students.add(studentList.get(j));
                }
            }
        }
        return students;
    }

    // get students from surname using matrix
    public List<Student> getSecStudentsFromSurname(String surname, int alpha) {
        List<Student> students = new java.util.ArrayList<>();
        int i = surnameList.indexOf(surname);

        //compute cluster boundaries
        int clusterUpperBound = i;
        for (int j = i; (j % alpha != 0 || j == i) && (j < rows); j++) {
            clusterUpperBound = j;
        }
        int clusterLowerBound = i;
        for (int j = i; (j % alpha != 0) && j > 0; j--) {
            clusterLowerBound = j - 1;
        }
        //System.out.println("i: " + i);
        //System.out.println("Cluster upper bound: " + clusterUpperBound);
        //System.out.println("Cluster lower bound: " + clusterLowerBound);

        if (i != -1) {
            for (int j = i; j <= clusterUpperBound && (j < rows); j++) {
                for (int k = 0; k < cols; k++) {
                    if (matrix[j][k]) {
                        students.add(studentList.get(k));
                    }
                }
            }
            i--;
            for (int j = i; j >= clusterLowerBound; j--) {
                for (int k = 0; k < cols; k++) {
                    if (matrix[j][k]) {
                        students.add(studentList.get(k));
                    }
                }
            }
        }

        return students;
    }

    //print matrix to file
    public void printMatrixToFile(String fileName) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(fileName)) {
            for (int i = 0; i < rows; i++) {
                writer.print(surnameList.get(i) + " ");
                //System.out.println(surnameList.get(i));
                writer.print("[ ");
                for (int j = 0; j < cols; j++) {
                    writer.print(lookupLastNames.get(studentList.get(j).enc_surname) + "{ ");
                    writer.print(matrix[i][j] + " }  ");
                }
                writer.print(" ]");
                writer.println();
                writer.println();
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.println("An error occurred while writing the file: " + e.getMessage());
        }
    }
}
