package com.endava.internship.collections;

import java.util.*;

public class StudentMap implements Map<Integer, Student> {
    LinkedList<Student>[] studentTable;
    Integer size;
    Integer listLength;

    static final Integer DEFAULT_LIST_LENGTH = 12;


    public StudentMap() {
        listLength = DEFAULT_LIST_LENGTH;
        studentTable = new LinkedList[listLength];
        size = 0;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object o) {
        return getInnerList(o) != null;
    }

    @Override
    public boolean containsValue(Object o) {
        LinkedList<Student> list = getInnerList(o);
        return list != null && list.contains(o);
    }

    @Override
    public Student get(Object o) {
        LinkedList<Student> list = getInnerList(o);
        Student firstStudent = list.getFirst();

        if (firstStudent.equals(o)) {
            return firstStudent;
        } else {
            return list.get(list.indexOf(o));
        }
    }

    private LinkedList<Student> getInnerList(Object o) {
        int key = o.hashCode() % listLength;
        LinkedList<Student> list = studentTable[key];
        return list;
    }

    @Override
    public Student put(Integer hash, Student student) {
        if (hash == null)
            hash = student.hashCode();

        int key = hash % listLength;

        if (studentTable[key] == null) {
            studentTable[key] = new LinkedList<Student>();
            if (studentTable[key].add(student)) {
                ++size;
            }
        } else {
            int studentLinkedListIndex = studentTable[key].indexOf(student);
            if (studentLinkedListIndex > 0) {
                ++size;
                return studentTable[key].set(studentLinkedListIndex, student);
            } else {
                if (studentTable[key].add(student)) {
                    ++size;
                }
            }
        }

        return null;
    }

    @Override
    public Student remove(Object o) {
        LinkedList<Student> list = getInnerList(o);
        if (list == null)
            return null;

        int index = list.indexOf(o);
        if (index > 0) {
            Student oldValue = list.get(index);
            list.remove(o);
            return oldValue;
        }

        return null;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Student> map) {
        map.forEach((hash, student) -> put(hash, student));
    }

    @Override
    public void clear() {
        size = 0;
        studentTable = new LinkedList[listLength];
    }

    @Override
    public Set<Integer> keySet() {

        return new KeySet();
    }

    @Override
    public Collection<Student> values() {
        List<Student> resultList = new ArrayList<>();

        for (LinkedList studentsList : studentTable) {
            if (studentsList != null)
                Collections.addAll(resultList, (Student[]) studentsList.toArray());
        }

        return resultList;
    }

    @Override
    public Set<Entry<Integer, Student>> entrySet() {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }
}

