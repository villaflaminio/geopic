
package com.flaminiovilla.geopic.component.geocode.kdtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KDTree<T extends KDNodeComparator<T>> {
    private KDNode<T> root;

    /**
     * prendo come radice il primo elemento della lista di nodi, e lo uso come root
     * @param items
     */
    public KDTree( List<T> items ) {
        root = createKDTree(items, 0);
    }

    public T findNearest( T search ) {
        return findNearest(root, search, 0).location;
    }

    /**
     * Creo l'albero a partire dalla lista di places
     * @param items
     * @param depth
     * @return
     */
    private KDNode<T> createKDTree( List<T> items, int depth ) {
        if ( items.isEmpty() ) {
            return null;
        }
        Collections.sort(items, items.get(0).getComparator(depth % 3));
        int currentIndex = items.size()/2;
        return new KDNode<T>(createKDTree(new ArrayList<T>(items.subList(0, currentIndex)), depth+1), createKDTree(new ArrayList<T>(items.subList(currentIndex + 1, items.size())), depth+1), items.get(currentIndex));
    }

    /**
     * dato un albero binario eseguo una visita in profondita' per individuare
     * il nodo che ha la distanza minima rispetto al mio nodo di ricerca
     * Ho provato a tipizzare escludendo i generics ma stranamente ottengo performance peggiori
     * quindi salvo il nodo con la distanza minore e lo restituisco
     * @param search<T>
     * @param depth
     */
    private KDNode<T> findNearest(KDNode<T> currentNode, T search, int depth) {
        int direction = search.getComparator(depth % 3).compare( search, currentNode.location );
        KDNode<T> next = (direction < 0) ? currentNode.left : currentNode.right;
        KDNode<T> other = (direction < 0) ? currentNode.right : currentNode.left;
        KDNode<T> best = (next == null) ? currentNode : findNearest(next, search, depth + 1); // Go to a leaf
        if ( currentNode.location.squaredDistance(search) < best.location.squaredDistance(search) ) {
            best = currentNode; // Set best as required
        } 
        if ( other != null ) {
            if ( currentNode.location.axisSquaredDistance(search, depth % 3) < best.location.squaredDistance(search) ) {
                KDNode<T> possibleBest = findNearest( other, search, depth + 1 );
                if (  possibleBest.location.squaredDistance(search) < best.location.squaredDistance(search) ) {
                    best = possibleBest;
                }
            }
        }
        return best; // Work back up
    }
}
