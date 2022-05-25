package it.unibs.ing.ingsw.io;

import java.util.Collection;

public interface CollectionRepository<T> {

    Collection<T> all();

    void add(T element);

    void addAll(Collection<T> collection);

    boolean isEmpty();

}
