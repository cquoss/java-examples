package de.quoss.example.jrecordexample;

import net.sf.JRecord.Common.Constants;
import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.Details.Line;
import net.sf.JRecord.External.CobolCopybookLoader;
import net.sf.JRecord.External.CopybookLoader;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.External.ToLayoutDetail;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.IO.CobolIoProvider;
import net.sf.JRecord.Numeric.Convert;

import java.math.BigDecimal;

public class JRecordExample {

    public void runNestedOccurs() throws Exception {
        System.out.println("Running Nested Occurs Example:");
        CobolIoProvider cobolIoProvider = CobolIoProvider.getInstance();
        AbstractLineReader abstractLineReader = cobolIoProvider.getLineReader(Constants.IO_FIXED_LENGTH,
                Convert.FMT_INTEL, CopybookLoader.SPLIT_NONE,
                "src/main/resources/nestedOccursRecord.cbl",
                "src/main/resources/nestedOccursContent.txt");
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

    public void runBigDecimalExample() throws Exception {
        System.out.println("Running Big Decimal Example:");
        CopybookLoader copybookLoader = new CobolCopybookLoader();
        ExternalRecord externalRecord = copybookLoader.loadCopyBook("src/main/resources/bigDecimalRecord.cbl", 0,
                0, "", 0, 0, null);
        LayoutDetail layoutDetail = ToLayoutDetail.getInstance().getLayout(externalRecord);
        AbstractLine abstractLine = new Line(layoutDetail);
        BigDecimal bigDecimal = new BigDecimal("0.00001");
        abstractLine.setField("FIELD-1", bigDecimal);
        System.out.println(String.format("Abstract Line - FIELD-1 (String): %s",
                abstractLine.getFieldValue("FIELD-1").asString()));
    }

    public static void main(String[] args) throws Exception {
        JRecordExample jRecordExample = new JRecordExample();
        jRecordExample.runNestedOccurs();
        jRecordExample.runBigDecimalExample();
    }

}
