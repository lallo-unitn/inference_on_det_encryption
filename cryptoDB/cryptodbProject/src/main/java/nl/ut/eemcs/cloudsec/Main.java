package nl.ut.eemcs.cloudsec;

import java.util.*;

public class Main {
    private static final int ALPHA = 4;

    public static void main(String[] args) {
        LineReader firstNameReader = new LineReader();
        ArrayList<String> namesOrderedFrequency =
                firstNameReader.readLinesFromFile("./src/main/resources/firstnames.txt", 100);
        //firstNameReader.displayLinesAndPos(namesOrderedFrequency);

        LineReader lastNameReader = new LineReader();
        ArrayList<String> lastnamesOrderedFrequency =
                lastNameReader.readLinesFromFile("./src/main/resources/lastnames.txt", 80);
        //lastNameReader.displayLinesAndPos(lastnamesOrderedFrequency);


        Database db = new Database();
        System.out.println("Number of distinct first names: " + db.getFirstNameCount());

        List<Student> students = db.getStudents();

        // order list by surname
        students.sort(Comparator.comparing(s -> s.enc_surname));

        HashMap<String, Integer> lastnameFrequenciesOrdered = db.getLastnameFrequenciesOrdered();

        // print lastnameFrequenciesOrdered
        for (Map.Entry<String, Integer> entry : lastnameFrequenciesOrdered.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        LookupTable lookupNames = new LookupTable(db.getFirstnameFrequenciesOrdered(), namesOrderedFrequency);

        LookupTable lookupLastNames = new LookupTable(lastnameFrequenciesOrdered, lastnamesOrderedFrequency);

        // print lastnameFrequenciesOrdered
        for (Map.Entry<String, Integer> entry : lastnameFrequenciesOrdered.entrySet()) {
            System.out.println(entry.getKey() + " | " + lookupLastNames.lookup.get(entry.getKey()) + ": " + entry.getValue());
        }

        List<Student> secondToTopStudents = db.getSecondToTopStudents();

        // print number of students with the second most common last name
        System.out.println("Number of students with the second most common last name: " + secondToTopStudents.size());

        // iterate on the list of last names
        for (Student student : secondToTopStudents) {
            System.out.println(
                    lookupNames.lookup.get(student.enc_name) + " " +
                            lookupLastNames.lookup.get(student.enc_surname)
            );
        }

        // order names alphabetically
        ArrayList<String> lastNamesAlphaOrder = new ArrayList<>(lastnamesOrderedFrequency);
        lastNamesAlphaOrder.sort(String::compareTo);

        IndexMatrix indexMatrix = new IndexMatrix(students, lastNamesAlphaOrder, lookupLastNames.lookup);

        HashMap<String, Integer> lastnameFrequencies = new HashMap<>();

        // iterate on the list of last names
        for (String lastName : lastNamesAlphaOrder) {
            lastnameFrequencies.put(lastName, indexMatrix.getSecStudentsFromSurname(lastName, ALPHA).size());
        }

        // Sort the map by value using a list
        List<Map.Entry<String, Integer>> list = new LinkedList<>(lastnameFrequencies.entrySet());
        list.sort(Map.Entry.comparingByValue());

        // print chart Lastname_Frequency_Distribution.png
        ChartBuilder.printChartToFile(
                ChartBuilder.buildChart(lookupLastNames.lookup, lastnameFrequenciesOrdered, false),
                "Lastname_Frequency_Distribution.png"
        );

        // print chart Lastname_Frequency_Distribution_Plain.png
        ChartBuilder.printChartToFile(
                ChartBuilder.buildChart(lookupLastNames.lookup, lastnameFrequenciesOrdered, true),
                "Lastname_Frequency_Distribution_Plain.png"
        );

        // print chart Secure_Lastname_Frequency_Distribution.png
        ChartBuilder.printChartToFile(
                ChartBuilder.buildSecChart(lookupLastNames.lookup, list, false),
                "Secure_Lastname_Frequency_Distribution.png"
        );

        // print chart Secure_Lastname_Frequency_Distribution.png
        ChartBuilder.printChartToFile(
                ChartBuilder.buildSecChart(lookupLastNames.lookup, list, true),
                "Secure_Lastname_Frequency_Distribution_Plain.png"
        );

        indexMatrix.getSecStudentsFromSurname("Wang", ALPHA).forEach(System.out::println);
    }
}