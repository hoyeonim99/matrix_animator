package logic;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class ColoredVector {
    private RealVector vector;
    private VectorColor color;

    //each ColoredVector will have a RealVector and an associated color for aesthetics
    public ColoredVector(RealVector vector, VectorColor color){
        this.vector = vector;
        this.color = color;
    }

    //this method will be used to help multiply the ColoredVector by some matrix in order
    // to animate the matrix transformation
    void matrixMultiply(RealMatrix matrix, RealVector vec){
        vector = matrix.operate(vec);
    }

    public RealVector getVector(){
        return vector;
    }

    public VectorColor getColor(){
        return color;
    }

    public boolean equals(Object object){
        if (object == null){
            return false;
        }
        if (object.getClass() != this.getClass()){
            return false;
        }
        ColoredVector compare = (ColoredVector) object;
        return (vector.getEntry(0) == compare.vector.getEntry(0) &&
        vector.getEntry(1) == compare.vector.getEntry(1));
    }

    public int hashCode(){
        return ("" + vector.getEntry(0) + vector.getEntry(1)).hashCode();
    }
}