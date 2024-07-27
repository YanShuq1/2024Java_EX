package hust.cs.javacourse.search.parse.impl;

/**
*@ name:	TermTupleScanner.java
*@ usage:	对TermTuple进行扫描读取
*@ author:	YanShuq1 for Github
*@ time:	2024/04/11 08:27
*/


import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StringSplitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TermTupleScanner extends AbstractTermTupleScanner {

    private List<String> tempParts = new ArrayList<>();

    private int curPos = 0;

    public TermTupleScanner() {
        super();
    }

    public TermTupleScanner(BufferedReader input) {
        super(input);
    }

    @Override
    public AbstractTermTuple next() {
        if(tempParts.isEmpty()){
            try {
                String line;
                while((line = input.readLine()) != null) {
                    StringSplitter splitter = new StringSplitter();
                    splitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
                    tempParts.addAll(splitter.splitByRegex(line));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(curPos < tempParts.size()) {
            if (Config.IGNORE_CASE)
                return new TermTuple(new Term(tempParts.get(curPos).toLowerCase()), curPos++);
            else
                return new TermTuple(new Term(tempParts.get(curPos)), curPos++);
        }
        else
            return null;
    }
}
