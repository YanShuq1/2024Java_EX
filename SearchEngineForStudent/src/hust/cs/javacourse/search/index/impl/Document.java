package hust.cs.javacourse.search.index.impl;

/**
*@ name:	Document.java
*@ usage:	Document结构类
*@ author:	YanShuq1 for Github
*@ time:	2024/04/07 17:15
*/


import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractTermTuple;

import java.util.ArrayList;
import java.util.List;

public class Document extends AbstractDocument {

    public Document() {
        this.docId = -1;
        this.docPath = "unknown";
        this.tuples = new ArrayList<>();
    }

    public Document(int docId, String docPath) {
        this.docId = docId;
        this.docPath = docPath;
        this.tuples = new ArrayList<>();
    }

    public Document(int docId, String docPath, List<AbstractTermTuple> tuples) {
        this.docId = docId;
        this.docPath = docPath;
        this.tuples = tuples;
    }

    @Override
    public int getDocId() {
        return docId;
    }

    @Override
    public void setDocId(int docId) {
        if (docId > 0)
            this.docId = docId;
    }

    @Override
    public String getDocPath() {
        return docPath;
    }

    @Override
    public void setDocPath(String docPath) {
        if(docPath != null && !docPath.isEmpty())
            this.docPath = docPath;
    }

    /**
     * 获得文档包含的三元组列表
     *
     * @return ：文档包含的三元组列表
     */
    @Override
    public List<AbstractTermTuple> getTuples() {
        return tuples;
    }

    /**
     * 向文档对象里添加三元组, 要求不能有内容重复的三元组
     *
     * @param tuple ：要添加的三元组
     */
    @Override
    public void addTuple(AbstractTermTuple tuple) {
        if(!tuples.contains(tuple))
            tuples.add(tuple);
    }

    /**
     * 判断是否包含指定的三元组
     *
     * @param tuple ： 指定的三元组
     * @return ： 如果包含指定的三元组，返回true;否则返回false
     */
    @Override
    public boolean contains(AbstractTermTuple tuple) {
        return tuples.contains(tuple);
    }

    /**
     * 获得指定下标位置的三元组
     *
     * @param index：指定下标位置
     * @return ： 三元组
     */
    @Override
    public AbstractTermTuple getTuple(int index) {
        return tuples.get(index);
    }

    /**
     * 返回文档对象包含的三元组的个数
     *
     * @return ：文档对象包含的三元组的个数
     */
    @Override
    public int getTupleSize() {
        return tuples.size();
    }

    /**
     * 获得Document的字符串表示
     *
     * @return ： Document的字符串表示
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("docId: " + docId + "\ndocPath: " + docPath + "\ntuples:\n");
        for (AbstractTermTuple tuple : tuples) {
            string.append("\t").append(tuple).append("\n");
        }
        return string.toString();
    }
}
