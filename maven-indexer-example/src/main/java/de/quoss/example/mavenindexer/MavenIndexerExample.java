package de.quoss.example.mavenindexer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.maven.index.ArtifactInfo;
import org.apache.maven.index.DefaultIndexer;
import org.apache.maven.index.DefaultIndexerEngine;
import org.apache.maven.index.DefaultQueryCreator;
import org.apache.maven.index.DefaultSearchEngine;
import org.apache.maven.index.Indexer;
import org.apache.maven.index.IteratorResultSet;
import org.apache.maven.index.IteratorSearchRequest;
import org.apache.maven.index.IteratorSearchResponse;
import org.apache.maven.index.SearchEngine;
import org.apache.maven.index.context.DefaultIndexingContext;
import org.apache.maven.index.context.IndexCreator;
import org.apache.maven.index.context.IndexingContext;
import org.apache.maven.index.creator.JarFileContentsIndexCreator;
import org.apache.maven.index.creator.MavenArchetypeArtifactInfoIndexCreator;
import org.apache.maven.index.creator.MinimalArtifactInfoIndexCreator;

/**
 * Maven Indexer Example.
 *
 * @author Clemens Quoß
 */
public class MavenIndexerExample {

    /**
     * Run main logic.
     *
     * @throws Exception Thrown in case of error.
     */
    private void run() throws Exception {
        DefaultSearchEngine searchEngine = new DefaultSearchEngine();
        DefaultIndexerEngine indexerEngine = new DefaultIndexerEngine();
        DefaultQueryCreator queryCreator = new DefaultQueryCreator();
        DefaultIndexer indexer = new DefaultIndexer(searchEngine,
                indexerEngine, queryCreator);
        String id = "maven-public";
        String repositoryId = id;
        File repository = null;
        File indexDirectory
                = new File("C:\\Users\\Cleme\\.m2\\maven-public-index");
        String repositoryUrl 
                = "http://cooltek:8083/repository/maven-public/";
        String indexUpdateUrl = null;
        List<IndexCreator> indexers = new ArrayList<>();
        indexers.add(new MinimalArtifactInfoIndexCreator());
        indexers.add(new MavenArchetypeArtifactInfoIndexCreator());
        indexers.add(new JarFileContentsIndexCreator());
        IndexingContext indexingContext = indexer.createIndexingContext(id,
                repositoryId, repository, indexDirectory, repositoryUrl,
                indexUpdateUrl, true, true, indexers);
        IteratorSearchRequest searchRequest = new IteratorSearchRequest(
                new MatchAllDocsQuery(), indexingContext);
        IteratorSearchResponse searchResponse = indexer.searchIterator(
                searchRequest);
        IteratorResultSet resultSet = searchResponse.getResults();
        for (ArtifactInfo artifactInfo : resultSet) {
            String groupId = artifactInfo.getGroupId();
            String artifactId = artifactInfo.getArtifactId();
            String version = artifactInfo.getVersion();
            String classNames = artifactInfo.getClassNames();
            System.out.format(
                    "ArtifactInfo [groupId=%s,artifactId=%s,version=%s,classNames=%s]%n",
                    groupId, artifactId, version, classNames);
        }
    }

    /**
     * Main method.
     *
     * @param args Command line arguments.
     * @throws Exception Thrown in case of error.
     */
    static public void main(final String[] args) throws Exception {
        new MavenIndexerExample().run();
    }

}
