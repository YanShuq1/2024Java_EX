package hust.cs.javacourse.search.parse.impl;

/**
*@ name:	LengthTermTupleFilter.java
*@ usage:	对Term长度的过滤器
*@ author:	YanShuq1 for Github
*@ time:	2024/04/11 09:12
*/


import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

public class LengthTermTupleFilter extends AbstractTermTupleFilter {
    /**
     * 构造函数
     *
     * @param input ：Filter的输入，类型为AbstractTermTupleStream
     */
    public LengthTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple tuple;
        while((tuple = input.next()) != null)
            if(tuple.term.getContent().length() >= Config.TERM_FILTER_MINLENGTH && tuple.term.getContent().length() <= Config.TERM_FILTER_MAXLENGTH)
                return tuple;
        return null;
    }
}
