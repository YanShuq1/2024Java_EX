package hust.cs.javacourse.search.query.impl;

/**
*@ name:	SimpleSorter.java
*@ usage:	对命中得分进行排序
*@ author:	YanShuq1 for Github
*@ time:	2024/04/17 16:05
*/


import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.Sort;

import java.util.Comparator;
import java.util.List;

public class SimpleSorter implements Sort {
    @Override
    public void sort(List<AbstractHit> hits) {
        for (AbstractHit hit : hits) {
            hit.setScore(score(hit));
        }
        hits.sort(new Comparator<AbstractHit>() {
            @Override
            public int compare(AbstractHit o1, AbstractHit o2) {
                return o2.compareTo(o1);
            }
        });
    }

    @Override
    public double score(AbstractHit hit) {
        double score = 0;
        for(AbstractPosting posting : hit.getTermPostingMapping().values())
            score += posting.getFreq();
        return score;
    }
}
