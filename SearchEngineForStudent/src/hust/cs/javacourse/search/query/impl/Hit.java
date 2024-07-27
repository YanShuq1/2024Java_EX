package hust.cs.javacourse.search.query.impl;

/**
*@ name:	Hit.java
*@ usage:	对Doc查询命中的记录
*@ author:	YanShuq1 for Github
*@ time:	2024/04/14 14:13
*/


import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;

import java.util.Map;

public class Hit extends AbstractHit {
    public Hit() {
        super();
    }

    public Hit(int docId, String docPath) {
        super(docId, docPath);
    }

    public Hit(int docId, String docPath, Map<AbstractTerm, AbstractPosting> termPostingMapping) {
        super(docId, docPath, termPostingMapping);
    }
    public Hit(int docId, String docPath, Map<AbstractTerm, AbstractPosting> termPostingMapping, double score) {
        super(docId, docPath, termPostingMapping);
        this.score = score;
    }
    @Override
    public int getDocId() {
        return docId;
    }

    @Override
    public String getDocPath() {
        return docPath;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        if (content != null && !content.isEmpty())
            this.content = content;
    }

    @Override
    public double getScore() {
        return score;
    }

    @Override
    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public Map<AbstractTerm, AbstractPosting> getTermPostingMapping() {
        return termPostingMapping;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("docId: ").append(docId).append(", docPath: ").append(docPath).append(", score: ").append(score).append("\n");
        str.append("content: \n").append(content).append("\nTerm----posting mapping: \n");
        for (Map.Entry<AbstractTerm, AbstractPosting> entry : termPostingMapping.entrySet()) {
            str.append(entry.getKey()).append("  ---->  ").append(entry.getValue()).append("\n");
        }
        return str.toString();
    }

    @Override
    public int compareTo(AbstractHit o) {
        return (int) (score - o.getScore());
    }
}
