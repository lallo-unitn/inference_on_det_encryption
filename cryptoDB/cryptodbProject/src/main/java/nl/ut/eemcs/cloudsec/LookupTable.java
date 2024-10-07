package nl.ut.eemcs.cloudsec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LookupTable {
    HashMap<String, String> lookup;

    public LookupTable(HashMap<String, Integer> hashFrequenciesOrdered, ArrayList<String> fileOrderedFrequency) {
        HashMap<String, String> lookup = new HashMap<>();
        int i = fileOrderedFrequency.size() - 1;
        //System.out.println("====================START===============");
        for (Map.Entry<String, Integer> entry : hashFrequenciesOrdered.entrySet()) {
            lookup.put(entry.getKey(), fileOrderedFrequency.get(i));
            //System.out.println(entry.getKey() + " | " + fileOrderedFrequency.get(i) + ": " + entry.getValue());
            i--;
        }
        System.out.println("==================END=================");
        this.lookup = lookup;
    }
}
