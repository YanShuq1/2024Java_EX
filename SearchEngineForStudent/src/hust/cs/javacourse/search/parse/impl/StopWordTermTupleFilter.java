package hust.cs.javacourse.search.parse.impl;

/**
*@ name:	StopWordTermTupleFilter.java
*@ usage:	对Term停用词的过滤器
*@ author:	YanShuq1 for Github
*@ time:	2024/04/11 09:57
*/


import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.StopWords;

import java.util.Arrays;

public class StopWordTermTupleFilter extends AbstractTermTupleFilter {
    /**
     * 构造函数
     *
     * @param input ：Filter的输入，类型为AbstractTermTupleStream
     */
    public StopWordTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple tuple;
        while((tuple = input.next()) != null)
            if(Arrays.binarySearch(StopWords.STOP_WORDS, tuple.term.getContent()) < 0)
                return tuple;
        return null;
    }
}
