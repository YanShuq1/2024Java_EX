package hust.cs.javacourse.search.index.impl;

/**
*@ name:	Posting.java
*@ usage:	存储Term在每个Doc中的信息
*@ author:	YanShuq1 for Github
*@ time:	2024/04/09 9:56
*/


import hust.cs.javacourse.search.index.AbstractPosting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Posting extends AbstractPosting {
    public Posting() {
        super();
    }

    public Posting(int docId, int curPos) {
        this.docId = docId;
        this.freq = 1;
        this.positions.add(curPos);
    }

    public Posting(int docId, int freq, List<Integer> positions) {
        super(docId, freq, positions);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Posting) {
            Posting posting = (Posting) obj;
            return docId == posting.docId && freq == posting.freq && positions.containsAll(posting.positions) && posting.positions.containsAll(positions);
        }
        return false;
    }

    @Override
    public String toString() {
        return "{docId: " + docId + ", freq: " + freq + ", positions: " + positions + "}";
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
    public int getFreq() {
        return freq;
    }

    @Override
    public void setFreq(int freq) {
        if (freq > 0)
            this.freq = freq;
    }

    @Override
    public List<Integer> getPositions() {
        return positions;
    }

    @Override
    public void setPositions(List<Integer> positions) {
        if (positions != null && !positions.isEmpty())
            this.positions = positions;
    }

    @Override
    public int compareTo(AbstractPosting o) {
        return this.docId - o.getDocId();
    }

    @Override
    public void sort() {
        positions.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            // 将this对象的成员依次序列化
            out.writeObject(this.docId);
            out.writeObject(this.freq);
            Integer[] obj = new Integer[positions.size()];
            positions.toArray(obj);
            out.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        // 将this对象的成员依次反序列化，注意和序列化次序一致
        try {
            this.docId = (int) (in.readObject());
            this.freq = (int) (in.readObject());
            Integer[] obj = (Integer[]) in.readObject();
            this.positions = Arrays.asList(obj);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
