
package com.flaminiovilla.geopic.component.geocode.kdTree;

import java.util.Comparator;


public abstract class KDNodeComparator<T> { 
    // This should return a comparator for whatever axis is passed in
    protected abstract Comparator getComparator(int axis);
    
    // Return squared distance between current and other
    protected abstract double squaredDistance(T other);
    
    // Return squared distance between one axis only
    protected abstract double axisSquaredDistance(T other, int axis);
}
