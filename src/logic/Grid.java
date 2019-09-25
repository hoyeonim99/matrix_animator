package logic;

import org.apache.commons.math3.linear.RealVector;

public class Grid {
    private RealVector vector1;
    private RealVector vector2;
    private boolean highlight;

    Grid(RealVector vector1, RealVector vector2){
        this.vector1 = vector1;
        this.vector2 = vector2;
        this.highlight = false;
    }

    public RealVector getVector1(){
        return vector1;
    }

    public RealVector getVector2(){
        return vector2;
    }

    void setHighlight(){
        highlight = true;
    }

    public boolean getHighlight(){
        return highlight;
    }
}