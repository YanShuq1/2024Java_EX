package hust.cs.javacourse.search.parse.impl;

/**
*@ name:	PatternTermTupleFilter.java
*@ usage:	根据正则表达式对Term格式进行过滤
*@ author:	YanShuq1 for Github
*@ time:	2024/04/11 09:48
*/


import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

public class PatternTermTupleFilter extends AbstractTermTupleFilter {
    /**
     * 构造函数
     *
     * @param input ：Filter的输入，类型为AbstractTermTupleStream
     */
    public PatternTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple tuple;
        while((tuple = input.next()) != null)
            if(tuple.term.getContent().matches(Config.TERM_FILTER_PATTERN))
                return tuple;
        return null;
    }
}
