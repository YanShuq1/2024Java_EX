package hust.cs.javacourse.search.query.impl;

/**
*@ name:	IndexSearcher.java
*@ usage:	根据构建好的索引搜索
*@ author:	YanShuq1 for Github
*@ time:	2024/04/17 15:04
*/


import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.impl.Posting;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class IndexSearcher extends AbstractIndexSearcher {
    @Override
    public void open(String indexFile) {
        index.load(new File(indexFile));
    }

    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        List<AbstractHit> hits = new ArrayList<>();
        if(index.termToPostingListMapping.containsKey(queryTerm)) {
            AbstractPostingList list = index.termToPostingListMapping.get(queryTerm);
            for(int i = 0; i < list.size(); i++) {
                AbstractPosting posting = list.get(i);
                Map<AbstractTerm, AbstractPosting>tempMap = new TreeMap<AbstractTerm, AbstractPosting>();
                tempMap.put(queryTerm, posting);
                AbstractHit hit = new Hit(posting.getDocId(), index.docIdToDocPathMapping.get(posting.getDocId()), tempMap);
                hit.setScore(posting.getFreq());
                hits.add(hit);
            }
        }
        sorter.sort(hits);
        return hits.toArray(new AbstractHit[0]);
    }

    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        List<AbstractHit> hits = new ArrayList<>();
        if (combine == LogicalCombination.AND) {
            if(index.termToPostingListMapping.containsKey(queryTerm1) && index.termToPostingListMapping.containsKey(queryTerm2)) {
                AbstractPostingList list1 = index.termToPostingListMapping.get(queryTerm1);
                AbstractPostingList list2 = index.termToPostingListMapping.get(queryTerm2);
                int i = 0, j = 0;
                while(i < list1.size() && j < list2.size()) {
                    AbstractPosting posting1 = list1.get(i);
                    AbstractPosting posting2 = list2.get(j);
                    if (posting1.getDocId() == posting2.getDocId()) {
                        Map<AbstractTerm, AbstractPosting> tempMap = new TreeMap<AbstractTerm, AbstractPosting>();
                        tempMap.put(queryTerm1, posting1);
                        tempMap.put(queryTerm2, posting2);
                        AbstractHit hit = new Hit(posting1.getDocId(), index.docIdToDocPathMapping.get(posting1.getDocId()), tempMap);
                        hit.setScore(sorter.score(hit));
                        hits.add(hit);
                        i++;
                        j++;
                    } else if(posting1.getDocId() < posting2.getDocId()) {
                        i++;
                    } else {
                        j++;
                    }
                }
            }
        } else if (combine == LogicalCombination.OR) {
            if(index.termToPostingListMapping.containsKey(queryTerm1) && !index.termToPostingListMapping.containsKey(queryTerm2))
                return search(queryTerm1, sorter);
            else if(!index.termToPostingListMapping.containsKey(queryTerm1) && index.termToPostingListMapping.containsKey(queryTerm2))
                return search(queryTerm2, sorter);
            else if(index.termToPostingListMapping.containsKey(queryTerm1) && index.termToPostingListMapping.containsKey(queryTerm2)) {
                AbstractPostingList list1 = index.termToPostingListMapping.get(queryTerm1);
                AbstractPostingList list2 = index.termToPostingListMapping.get(queryTerm2);
                int i = 0, j = 0;
                while(true) {
                    if(i >= list1.size() && j >= list2.size()) {
                        break;
                    }
                    if (j >= list2.size() || (i < list1.size() && list1.get(i).getDocId() < list2.get(j).getDocId())) {
                        AbstractPosting posting = list1.get(i);
                        Map<AbstractTerm, AbstractPosting> tempMap = new TreeMap<AbstractTerm, AbstractPosting>();
                        tempMap.put(queryTerm1, posting);
                        AbstractHit hit = new Hit(posting.getDocId(), index.docIdToDocPathMapping.get(posting.getDocId()), tempMap);
                        hit.setScore(posting.getFreq());
                        hits.add(hit);
                        i++;
                    } else if (i >= list1.size() || (j < list2.size() && list2.get(j).getDocId() < list1.get(i).getDocId())) {
                        AbstractPosting posting = list2.get(j);
                        Map<AbstractTerm, AbstractPosting> tempMap = new TreeMap<AbstractTerm, AbstractPosting>();
                        tempMap.put(queryTerm2, posting);
                        AbstractHit hit = new Hit(posting.getDocId(), index.docIdToDocPathMapping.get(posting.getDocId()), tempMap);
                        hit.setScore(posting.getFreq());
                        hits.add(hit);
                        j++;
                    } else if (i < list1.size() && j < list2.size() && list2.get(j).getDocId() == list1.get(i).getDocId()){
                        AbstractPosting posting1 = list1.get(i);
                        AbstractPosting posting2 = list2.get(j);
                        Map<AbstractTerm, AbstractPosting> tempMap = new TreeMap<AbstractTerm, AbstractPosting>();
                        tempMap.put(queryTerm1, posting1);
                        tempMap.put(queryTerm2, posting2);
                        AbstractHit hit = new Hit(posting1.getDocId(), index.docIdToDocPathMapping.get(posting1.getDocId()), tempMap);
                        hit.setScore(sorter.score(hit));
                        hits.add(hit);
                        i++;
                        j++;
                    }
                }
            }
        } else if (combine == LogicalCombination.AS_PHRASE) {
            if (index.termToPostingListMapping.containsKey(queryTerm1) && index.termToPostingListMapping.containsKey(queryTerm2)) {
                AbstractPostingList list1 = index.termToPostingListMapping.get(queryTerm1);
                AbstractPostingList list2 = index.termToPostingListMapping.get(queryTerm2);
                int i = 0, j = 0;
                while (i < list1.size() && j < list2.size()) {
                    AbstractPosting posting1 = list1.get(i);
                    AbstractPosting posting2 = list2.get(j);
                    if (posting1.getDocId() == posting2.getDocId()) {
                        List<Integer> pos = checkPhrase(posting1.getPositions(), posting2.getPositions());
                        if(!pos.isEmpty()) {
                            Map<AbstractTerm, AbstractPosting> tempMap = new TreeMap<AbstractTerm, AbstractPosting>();
                            AbstractTerm queryTerm = new Term(queryTerm1.getContent() + " " + queryTerm2.getContent());
                            AbstractPosting posting = new Posting(posting1.getDocId(), pos.size(), pos);
                            tempMap.put(queryTerm, posting);
                            AbstractHit hit = new Hit(posting1.getDocId(), index.docIdToDocPathMapping.get(posting1.getDocId()), tempMap);
                            hit.setScore(sorter.score(hit));
                            hits.add(hit);
                            i++;
                            j++;
                        }
                    } else if (posting1.getDocId() < posting2.getDocId()) {
                        i++;
                    } else {
                        j++;
                    }
                }
            }
        }
        sorter.sort(hits);
        return hits.toArray(new AbstractHit[0]);
    }

    private List<Integer> checkPhrase(List<Integer> a, List<Integer> b) {
        int i = 0, j = 0;
        List<Integer> pos = new ArrayList<>();
        while(i < a.size() && j < b.size()) {
            if(a.get(i) + 1 == b.get(j)) {
                pos.add(a.get(i));
                i++;
                j++;
            } else if (a.get(i) >= b.get(j)) {
                j++;
            } else if (a.get(i) + 1 < a.get(j)) {
                i++;
            }
        }
        return pos;
    }
}
