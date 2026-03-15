package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.naming.directory.SearchResult;

public class Model {
    private DataLoader dataLoader;
    private DataFrame dataFrame;
    private ArrayList<String> columnNames;
    private ArrayList<Map<String, String>> patientInfos;
    private ArrayList<Map<String, String>> sortedPatientInfos;

    public Model() {
        dataLoader = new DataLoader();
        dataFrame = null;
        columnNames = new ArrayList<String>();
        patientInfos = new ArrayList<Map<String, String>>();
        sortedPatientInfos = new ArrayList<Map<String, String>>();
    }

    public void readFile(String fileName) {
        dataLoader.readFile(fileName);
        dataFrame = dataLoader.getDataFrame();
        columnNames = dataFrame.getColumnNames();
        for (int i = 0; i < dataFrame.getRowCount(); i++) {
            patientInfos.add(getPatientInfoFromRowIndex(i));
            sortedPatientInfos.add(patientInfos.get(i));
        }
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    private Map<String, String> getPatientInfoFromRowIndex(int rowIndex) {
        HashMap<String, String> patientInfo = new HashMap<String, String>();
        for (int i = 0; i < dataFrame.getColumnCount(); i++) {
            String columnName = columnNames.get(i);
            patientInfo.put(columnName, dataFrame.getValue(columnName, rowIndex));
        }
        return patientInfo;
    }

    public Map<String, String> getPatientInfo(String patientID) {
        for (int i = 0; i < dataFrame.getRowCount(); i++) {
            if (dataFrame.getValue("ID", i).equals(patientID)) {
                return getPatientInfoFromRowIndex(i);
            }
        }
        return null;
    }

    private void sortStringMapList(List<Map<String, String>> list, String key, boolean isDesc) {
        if (isDesc) {
            list.sort(Comparator.<Map<String, String>, String>comparing(m -> m.get(key),
                    Comparator.nullsLast(String::compareTo)).reversed());
            return;
        }
        list.sort(Comparator.<Map<String, String>, String>comparing(m -> m.get(key),
                Comparator.nullsLast(String::compareTo)));
    }

    private void sortPatientList(List<Map<String, String>> patientList, String order) {
        switch (order.toLowerCase()) {
            case "default":
                return;

            case "first":
                sortStringMapList(patientList, "FIRST", false);
                return;

            case "firstdesc":
                sortStringMapList(patientList, "FIRST", true);
                return;

            case "last":
                sortStringMapList(patientList, "LAST", false);
                return;

            case "lastdesc":
                sortStringMapList(patientList, "LAST", true);
                return;

            case "birthdate":
                sortStringMapList(patientList, "BIRTHDATE", false);
                return;

            case "birthdatedesc":
                sortStringMapList(patientList, "BIRTHDATE", true);
                return;

            default:
                return;
        }
    }

    public List<Map<String, String>> getPatientInfos() {
        return patientInfos;
    }

    public List<Map<String, String>> getPatientInfos(String order) {
        if (order == null || order.toLowerCase().equals("default")) {
            return patientInfos;
        }
        sortPatientList(sortedPatientInfos, order);
        return sortedPatientInfos;
    }

    public List<Map<String, String>> searchFor(String keyword) {
        if (keyword.trim().length() == 0)
            return List.of(Map.of("ERROR", "Search keyword is empty. Please enter at least 1 character."));

        String[] words = keyword.split("\\s+");
        ArrayList<Map<String, String>> searchResult = new ArrayList<Map<String, String>>();
        for (int row = 0; row < dataFrame.getRowCount(); row++) {
            boolean keywordMatch = true; // True iff every word is found
            for (String word : words) {
                boolean wordFound = false;
                for (int col = 0; col < dataFrame.getColumnCount(); col++) {
                    String columnName = columnNames.get(col);
                    if (dataFrame.getValue(columnName, row).toLowerCase().contains(word.toLowerCase())) {
                        wordFound = true;
                        break;
                    }
                }
                if (!wordFound) {
                    keywordMatch = false;
                    break;
                }
            }
            if (keywordMatch) {
                searchResult.add(getPatientInfoFromRowIndex(row));
            }
        }
        return searchResult;
    }

    public List<Map<String, String>> searchFor(String keyword, String order) {
        if (keyword.trim().length() == 0)
            return List.of(Map.of("ERROR", "Search keyword is empty. Please enter at least 1 character."));

        List<Map<String, String>> searchResult = searchFor(keyword);

        if (order == null || order.toLowerCase().equals("default")) {
            return searchResult;
        }
        sortPatientList(searchResult, order);
        return searchResult;
    }
}
