package uk.ac.ucl.model;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class DataLoader {
    private DataFrame dataFrame;
    private ArrayList<String> columnNames;

    public DataLoader() {
        dataFrame = new DataFrame();
        columnNames = new ArrayList<String>();
    }

    public DataFrame getDataFrame() {
        return dataFrame;
    }

    public void readFile(String fileName) {
        try (Reader reader = new FileReader(fileName);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            boolean isHeaderRow = true;
            for (CSVRecord csvRecord : csvParser) {
                if (isHeaderRow) {
                    readHeaderRow(csvRecord);
                    isHeaderRow = false;
                } else {
                    readBodyRow(csvRecord);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read file \"" + fileName + "\": " + e.getMessage());
        }
    }

    private void readHeaderRow(CSVRecord csvRecord) {
        for (String columnName : csvRecord) {
            dataFrame.addColumn(new Column(columnName));
            columnNames.add(columnName);
        }
    }

    private void readBodyRow(CSVRecord csvRecord) {
        for (int i = 0; i < dataFrame.getColumnCount(); i++) {
            dataFrame.addValue(columnNames.get(i), csvRecord.get(i));
        }
    }
}
