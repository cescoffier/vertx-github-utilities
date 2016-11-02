package me.escoffier.vertx.github.commands;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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

}
