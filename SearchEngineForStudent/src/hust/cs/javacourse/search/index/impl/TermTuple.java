package hust.cs.javacourse.search.index.impl;

/**
*@ name:	TermTuple.java
*@ usage:	对单词出现情况的包装TermTuple类
*@ author:	YanShuq1 for Github
*@ time:	2024/04/07 16:46
*/

import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.AbstractTermTuple;

public class TermTuple extends AbstractTermTuple {
    public TermTuple() {
        term = new Term();
        curPos = -1;
    }

    public TermTuple(AbstractTerm term, int curPos) {
        this.term = term;
        this.curPos = curPos;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TermTuple) {
            TermTuple t = (TermTuple) obj;
            return term.equals(t.term) && curPos == t.curPos;
        }
        return false;
    }

    @Override
    public String toString() {
        return "<\"" + term.toString() + "\", " + freq + ", " + curPos + ">";
    }
}
