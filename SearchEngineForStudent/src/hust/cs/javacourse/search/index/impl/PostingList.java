package hust.cs.javacourse.search.index.impl;

/**
*@ name:	PostingList.java
*@ usage:	Posting的List集合包装
*@ author:	YanShuq1 for Github
*@ time:	2024/04/09 10:47
*/


import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PostingList extends AbstractPostingList {
    @Override
    public void add(AbstractPosting posting) {
        if (!list.contains(posting))
            list.add(posting);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (AbstractPosting posting : list)
            str.append(posting).append(" ");
        return str.toString();
    }

    @Override
    public void add(List<AbstractPosting> postings) {
        for (AbstractPosting posting : postings) {
            if (!list.contains(posting))
                list.add(posting);
        }
    }

    @Override
    public AbstractPosting get(int index) {
        if (index >= list.size() || index < 0)
            return null;
        return list.get(index);
    }

    @Override
    public int indexOf(AbstractPosting posting) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(posting))
                return i;
        }
        return -1;
    }

    @Override
    public int indexOf(int docId) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDocId() == docId)
                return i;
        }
        return -1;
    }

    @Override
    public boolean contains(AbstractPosting posting) {
        return list.contains(posting);
    }

    @Override
    public void remove(int index) {
        list.remove(index);
    }

    @Override
    public void remove(AbstractPosting posting) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(posting)) {
                list.remove(i);
                break;
            }
        }
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void sort() {
        list.sort(new Comparator<AbstractPosting>() {
            @Override
            public int compare(AbstractPosting o1, AbstractPosting o2) {
                return o1.getDocId() - o2.getDocId();
            }
        });
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            // 将this对象的成员依次序列化
            AbstractPosting[] postings = new AbstractPosting[list.size()];
            list.toArray(postings);
            out.writeObject(postings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try {
            // 将this对象的成员依次反序列化，注意和序列化次序一致
            AbstractPosting[] postings = (AbstractPosting[]) (in.readObject());
            this.list = Arrays.asList(postings);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
