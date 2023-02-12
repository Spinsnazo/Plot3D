package com.plot3d.plot3d;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class ListOfPoints<T> {
    final private List<T> list = new ArrayList<>();
    
    public void addPoint(T P) throws Exception{
        Class<?> clazz = P.getClass();
        Constructor<?> constructor = clazz.getConstructor(clazz);
        T np = (T) constructor.newInstance(P);
        list.add(np);
    }
    public void removePoint(T P) throws Exception{
        list.remove(P);
    }
    public boolean isPointInList(T P) throws Exception{
        return list.contains(P);
    }
    public T getPoint(int i) throws IndexOutOfBoundsException{
        return list.get(i);
    }
    public int calculateSize(){
        return list.size();
    }
}
