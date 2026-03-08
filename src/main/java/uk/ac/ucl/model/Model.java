package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private DataLoader dataLoader;
    private DataFrame dataFrame;
    private ArrayList<String> patientSummaries;

    public Model() {
        dataLoader = new DataLoader();
        dataFrame = null;
        patientSummaries = new ArrayList<String>();
    }

    public List<String> getPatientSummaries() {
        return patientSummaries;
    }

    public void readFile(String fileName) {
        dataLoader.readFile(fileName);
        dataFrame = dataLoader.getDataFrame();
        for (int i = 0; i < dataFrame.getRowCount(); i++) {
            patientSummaries.add(getPatientSummaryFromRowIndex(i));
        }
    }

    private String getPatientSummaryFromRowIndex(int rowIndex) {
        String id = dataFrame.getValue("ID", rowIndex);
        String first = dataFrame.getValue("FIRST", rowIndex);
        String last = dataFrame.getValue("LAST", rowIndex);
        String birthday = dataFrame.getValue("BIRTHDATE", rowIndex);
        String gender = dataFrame.getValue("GENDER", rowIndex);
        return id + " " + first + " " + last + " " + birthday + " " + gender;
    }

    public List<String> searchFor(String keyword) {
        if (keyword.length() == 0)
            return List.of("Search keyword is empty. Please enter at least 1 character.");

        String[] words = keyword.split("\\s");
        ArrayList<String> searchResult = new ArrayList<String>();
        for (int row = 0; row < dataFrame.getRowCount(); row++) {
            boolean keywordMatch = true;
            for (String word : words) {
                boolean wordFound = false;
                for (int col = 0; col < dataFrame.getColumnCount(); col++) {
                    String columnName = dataFrame.getColumnNames().get(col);
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
                searchResult.add(getPatientSummaryFromRowIndex(row));
            }
        }
        return searchResult;
    }
}
