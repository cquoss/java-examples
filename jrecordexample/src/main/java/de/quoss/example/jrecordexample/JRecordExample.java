package de.quoss.example.jrecordexample;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.CobolIoProvider;
import net.sf.JRecord.Numeric.Convert;

public class JRecordExample {

    public void run() throws Exception {
        CobolIoProvider cobolIoProvider = CobolIoProvider.getInstance();
        AbstractLineReader abstractLineReader = cobolIoProvider.getLineReader(Constants.IO_FIXED_LENGTH,
                Convert.FMT_INTEL, CopybookLoader.SPLIT_NONE, "src/main/resources/record.cbl",
                "src/main/resources/content.txt");
        AbstractLine abstractLine;
        while ((abstractLine = abstractLineReader.read()) != null) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    String fieldName = String.format("FIELD-1 (%s, %s)", i, j);
                    System.out.println(String.format("%s: %s", fieldName,
                            abstractLine.getFieldValue(fieldName).asString()));
                }
            }
        }

    }

    public static void main(String[] args) throws Exception {
        new JRecordExample().run();
    }

}
