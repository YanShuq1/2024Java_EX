package hust.cs.javacourse.search.index.impl;

/**
*@ name:	DocumentBuilder.java
*@ usage:	Document构造器
*@ author:	YanShuq1 for Github
*@ time:	2024/04/11 10:03
*/


import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.parse.impl.LengthTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.PatternTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.StopWordTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.TermTupleScanner;

import java.io.*;

public class DocumentBuilder extends AbstractDocumentBuilder {
    @Override
    public AbstractDocument build(int docId, String docPath, AbstractTermTupleStream termTupleStream) {
        AbstractDocument doc = new Document(docId, docPath);
        AbstractTermTuple tuple;
        while((tuple = termTupleStream.next()) != null)
            doc.addTuple(tuple);
        System.out.println(docPath);
        return doc;
    }

    @Override
    public AbstractDocument build(int docId, String docPath, File file) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file)));
            AbstractTermTupleStream ts = new PatternTermTupleFilter(new LengthTermTupleFilter(
                            new StopWordTermTupleFilter(new TermTupleScanner(reader))));
            AbstractDocument doc = new Document(docId, docPath);
            AbstractTermTuple tuple;
            while ((tuple = ts.next()) != null)
                doc.addTuple(tuple);
            return doc;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
