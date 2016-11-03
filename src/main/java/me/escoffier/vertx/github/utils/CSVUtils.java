package me.escoffier.vertx.github.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class CSVUtils {

  public static void write(File output, List<String> headers, List<List<String>> records) throws IOException {
    CSVFormat format = CSVFormat.EXCEL.withHeader(headers.toArray(new String[headers.size()]));

    FileWriter writer = new FileWriter(output);
    CSVPrinter printer = new CSVPrinter(writer, format);

    for (List<String> record : records) {
      printer.printRecord(record);
    }

    writer.close();
  }

  public static <X> void write(File output, Table<X> table) throws
      IOException {
    CSVFormat format = CSVFormat.EXCEL.withHeader(table.getColumns().toArray(new String[table.getColumns().size()]));

    FileWriter writer = new FileWriter(output);
    CSVPrinter printer = new CSVPrinter(writer, format);

    Map<String, Map<String, X>> records = table.getTable();
    for (String key : records.keySet()) {
      List<String> record = new ArrayList<>();
      record.add(key);
      Map<String, X> map = records.get(key);
      for (X x : map.values()) {
        record.add(x.toString());
      }
      printer.printRecord(record);
    }

    writer.close();
  }

}
