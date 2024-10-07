package nl.ut.eemcs.cloudsec;

import java.util.Objects;

public class Student {
    public String enc_name;
    public String enc_grade;
    public String enc_surname;

    public Student(String enc_name, String enc_grade, String enc_surname) {
        this.enc_name = enc_name;
        this.enc_grade = enc_grade;
        this.enc_surname = enc_surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(enc_name, student.enc_name) && Objects.equals(enc_grade, student.enc_grade) && Objects.equals(enc_surname, student.enc_surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enc_name, enc_grade, enc_surname);
    }

    @Override
    public String toString() {
        return "Student{" +
                "enc_name='" + enc_name + '\'' +
                ", enc_surname='" + enc_surname + '\'' +
                ", enc_grade='" + enc_grade + '\'' +
                '}';
    }
}
