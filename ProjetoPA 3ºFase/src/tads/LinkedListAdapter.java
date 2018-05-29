package tads;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Representa a estrutura de suporte á organização da tabela de estatisticas
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 *
 */
public class LinkedListAdapter<E> implements Ranking<E>, Serializable {

    private LinkedList<E> adaptee;
    private final Comparator<E> comparator;

    public LinkedListAdapter(Comparator comparator) {
        this.adaptee = new LinkedList<>();
        this.comparator = comparator;
    }

    /**
     * Permite saber o tamanho da lista
     *
     * @return tamanho
     */
    @Override
    public int size() {
        return adaptee.size();
    }

    /**
     * Permite adicinar um elmento a lista
     *
     * @param element elemento
     */
    @Override
    public void add(E element) throws OutOfBoundsException {
        boolean inserted = false;
        for (int i = 0; i < size(); i++) {
            if (comparator.compare(adaptee.get(i), element) < 0) {
                adaptee.add(i, element);
                inserted = true;
                break;

            }
        }

        if (!inserted || adaptee.isEmpty()) {
            adaptee.add(element);
        }
    }

   /**
     * Permite devolver um elemento da lista
     *
     * @param rank rank a aceder
     */
    @Override
    public E get(int rank) throws OutOfBoundsException {
        return adaptee.get(rankValid(rank, size() - 1));
    }

    //verifica se é valido
    private int rankValid(int rank, int limit) throws OutOfBoundsException {
        if (rank < 0 || rank > limit) {
            throw new OutOfBoundsException(rank);
        }
        return rank;
    }

    /**
     * Permite iterar a lista
     *
     */
    @Override
    public Iterator<E> iterator() {
        return adaptee.iterator();
    }

}
