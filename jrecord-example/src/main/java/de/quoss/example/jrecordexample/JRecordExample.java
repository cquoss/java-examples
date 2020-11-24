package de.quoss.example.jrecordexample;

import net.sf.JRecord.Details.AbstractLine;
import net.sf.JRecord.IO.AbstractLineReader;
import net.sf.JRecord.JRecordInterface1;
import net.sf.JRecord.Numeric.ICopybookDialects;
import net.sf.JRecord.Option.ICobolSplitOptions;
import net.sf.JRecord.def.IO.builders.ICobolIOBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JRecordExample {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    public void run(String[] args) throws Exception {
        String methodName = "run(String[])";
        LOGGER.debug("{} start [args={}]", methodName, args);
        ICobolIOBuilder ioBuilder = JRecordInterface1.COBOL
                .newIOBuilder("CobolCopybook.cbl")
                .setSplitCopybook(ICobolSplitOptions.SPLIT_01_LEVEL)
                .setDialect(ICopybookDialects.FMT_MAINFRAME_COMMA_DECIMAL);
        AbstractLineReader reader = ioBuilder.newReader("inputFile");
        AbstractLine line;
        while ((line = reader.read()) != null) {
            String field1Value = line.getFieldValue("FIELD-01").asString();
            String field2Value = line.getFieldValue("FIELD-02").asString();
            String field3Value = line.getFieldValue("FIELD-03").asString();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("{} [field1Value={},field2Value={},field3Value={}]",
                        methodName, field1Value, field2Value, field3Value);
            }
        }
        reader.close();
        LOGGER.debug("{} end", methodName);
    }

    public static void main(String[] args) throws Exception {
        new JRecordExample().run(args);
    }
}
