package de.quoss.example.lucene;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.SimpleCollector;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * Example on how to use Apache Lucene.
 *
 * @author Clemens Quoß
 */
public class LuceneExample {

    private static final String INDEX_DIRECTORY_PATH = "C:\\Users\\Cleme\\.m2\\maven-public-index";

    private IndexSearcher indexSearcher;

    private static final String SEARCH_TERM = "com.thoughtworks.xstream.converters.ErrorWriter";
    
    private List<String> hits = new ArrayList<>(); 
    
    private void run() throws Exception {
        long startMillis = System.currentTimeMillis();
        String methodName = "run()";
        Path path = Paths.get(INDEX_DIRECTORY_PATH);
        Directory directory = FSDirectory.open(path);
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        indexSearcher = new IndexSearcher(directoryReader);
        IndexReader indexReader = indexSearcher.getIndexReader();
        int numDocs = indexReader.numDocs();
        int allDocsCount = indexSearcher.count(new MatchAllDocsQuery());
        // System.out.format("%s [numDocs=%s,allDocsCount=%s]%n", methodName, 
        //         numDocs, allDocsCount);
        System.out.format("%s Search for %s ...%n", methodName, SEARCH_TERM);
        indexSearcher.search(new MatchAllDocsQuery(), new MyCollector());
        for (String hit : hits) {
            System.out.format("%s [hit=%s]%n", methodName, hit);
        }
        // String searchTerm = "/com/thoughtworks/xstream/converters/ErrorWriter";
        // String searchTerm = "*ErrorWriter*";
        // System.out.format("%s Search for %s:%n", methodName, searchTerm);
        // Term term = new Term(searchTerm);
        // WildcardQuery query = new WildcardQuery(term);
        // indexSearcher.search(query, new MyCollector());
        System.out.format("%s end [elapsed=%s]", methodName, 
                (System.currentTimeMillis() - startMillis));
    }

    class MyCollector extends SimpleCollector {
       
        @Override
        public void collect(final int unbasedDocumentNumber) {
            String methodName = "collect(int)";
            // System.out.format("%s [unbasedDocumentNumber=%s]%n", methodName, 
            //         unbasedDocumentNumber);
            try {
                Document document = indexSearcher.doc(unbasedDocumentNumber);
                String value = document.get("u");
                String classes = document.get("c");
                if (classes != null && classes.contains(SEARCH_TERM.replace('.', '/'))) {
                    if (!hits.contains(value)) {
                        hits.add(value);
                    }
                }
                // System.out.format("%s c [classes=%s]%n", methodName, classes);
                for (IndexableField field : document.getFields()) {
                    System.out.format("%s field [name=%s,stringValue=%s]%n", 
                            methodName, field.name(), field.stringValue());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        @Override
        public boolean needsScores() {
            return false;
        }
        
    }
    
    /**
     * Main method.
     *
     * @param args Command line arguments.
     * @throws Exception Thrown in case of error.
     */
    public static void main(final String[] args) throws Exception {
        new LuceneExample().run();
    }

}
