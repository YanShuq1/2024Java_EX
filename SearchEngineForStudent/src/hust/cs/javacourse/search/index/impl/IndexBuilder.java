package hust.cs.javacourse.search.index.impl;

/**
*@ name:	IndexBuilder.java
*@ usage:	Index构造器
*@ author:	YanShuq1 for Github
*@ time:	2024/04/11 10:54
*/


import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.parse.impl.LengthTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.PatternTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.StopWordTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.TermTupleScanner;
import hust.cs.javacourse.search.util.FileUtil;

import java.io.*;
import java.util.List;

public class IndexBuilder extends AbstractIndexBuilder {

    public IndexBuilder(AbstractDocumentBuilder docBuilder) {
        super(docBuilder);
    }

    @Override
    public AbstractIndex buildIndex(String rootDirectory) {
        try {
            AbstractIndex index = new Index();
            List<String> fileNames = FileUtil.list(rootDirectory);
            for(String fileName : fileNames) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(new File(fileName))));
                AbstractTermTupleStream ts = new PatternTermTupleFilter(new LengthTermTupleFilter(
                        new StopWordTermTupleFilter(new TermTupleScanner(reader))));
                AbstractDocument doc = new DocumentBuilder().build(docId++, fileName, ts);
                index.addDocument(doc);
            }
            return index;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
