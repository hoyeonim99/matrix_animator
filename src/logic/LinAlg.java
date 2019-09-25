package logic;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinAlg {
    private RealMatrix matrix; //transformation matrix that user wants to see in the end
    private RealMatrix intermMatrix; //an intermediate matrix for animation purposes

    private Map<ColoredVector, ColoredVector> vectors;

    //these four doubles are placeholders to help increment the intermMatrix during
    // animation
    private double aIncrement;
    private double bIncrement;
    private double cIncrement;
    private double dIncrement;


    private List<Grid> grid;//each grid holds 2 vectors whose ends will be
    // connected in DrawingBoard to represent the background grid

    private List<Grid> intermGrid; //to help animate the grid going through a
    // transformation

    private boolean restart; //to tell DrawingBoard whether to reset the grid/vector or not
    private boolean going; //to tell DrawingBoard whether to animate or not;
    private int counter; //for animation frames, used in DrawingBoard's timer
    private int colorCounter; //for determining color of the vectors

    public LinAlg(){
        restart = false;
        going = false;
        counter = 0;
        colorCounter = 1;
        double defaultX = 1;
        double defaultY = 1;

        //setting up the LinAlg object. Default matrix is the identity matrix. Default
        // vector is (1, 1)
        double[][] matrixData = {{1, 0}, {0, 1}};
        double[] vectorData = {defaultX * 100, defaultY * 100};

        matrix = MatrixUtils.createRealMatrix(matrixData);
        // transformation wanted
        intermMatrix = MatrixUtils.createRealMatrix(matrixData);

        RealVector vector = MatrixUtils.createRealVector(vectorData);
        RealVector intermVector = MatrixUtils.createRealVector(vectorData);

        vectors = new HashMap<>();
        ColoredVector vector1 = new ColoredVector(vector, VectorColor.YELLOW);
        ColoredVector vector2 = new ColoredVector(intermVector, VectorColor.YELLOW);
        vectors.put(vector1, vector2);

        grid = new ArrayList<>();
        intermGrid = new ArrayList<>();

        //setting up the vertical grid-lines
        for (int i = -500; i < 600; i += 100){
            double[] grid1Data1 = {i, 500};
            RealVector grid1vec1 = MatrixUtils.createRealVector(grid1Data1);
            double[] grid1Data2 = {i, -500};
            RealVector grid1vec2 = MatrixUtils.createRealVector(grid1Data2);
            Grid grid1 = new Grid(grid1vec1, grid1vec2);
            if (i == 0) {
                grid1.setHighlight();
            }
            grid.add(grid1);
        }

        //setting up the horizontal grid-lines
        for (int i = -500; i < 600; i += 100){
            double[] grid2Data1 = {500, i};
            RealVector grid2vec1 = MatrixUtils.createRealVector(grid2Data1);
            double[] grid2Data2 = {-500, i};
            RealVector grid2vec2 = MatrixUtils.createRealVector(grid2Data2);
            Grid grid2 = new Grid(grid2vec1, grid2vec2);
            if (i == 0){
                grid2.setHighlight();
            }
            grid.add(grid2);
        }

        copyOriginalGrid();
    }

    //this is to reset the grid-lines in case the user wants to reset the
    // transformations done
    private void copyOriginalGrid(){
        intermGrid.clear();
        for (Grid vecGrid : grid){
            double[] toAdd1 = {vecGrid.getVector1().getEntry(0),
                    vecGrid.getVector1().getEntry(1)};
            RealVector toAddVec1 = MatrixUtils.createRealVector(toAdd1);

            double[] toAdd2 = {vecGrid.getVector2().getEntry(0),
                    vecGrid.getVector2().getEntry(1)};
            RealVector toAddVec2 = MatrixUtils.createRealVector(toAdd2);
            Grid toAddGrid = new Grid(toAddVec1, toAddVec2);
            if (vecGrid.getHighlight()){
                toAddGrid.setHighlight();
            }

            intermGrid.add(toAddGrid);
        }
    }

    //for changing the transformation matrix, but it's only used once in Main to
    // initiate the program
    public void setMatrix(double a, double b, double c, double d){
        matrix.setEntry(0, 0, a);
        matrix.setEntry(0, 1, b);
        matrix.setEntry(1, 0, c);
        matrix.setEntry(1, 1, d);
    }


    public Map<ColoredVector, ColoredVector> getVectors(){
        return vectors;
    }

    //for transforming the vectors and the grid by a little bit
    public void transform2(){
        if (counter == 0){
            setMatrixIncrements();
        }
        incrementMatrix();

        for (ColoredVector vec : vectors.keySet()){
            vectors.get(vec).matrixMultiply(intermMatrix, vec.getVector()); /*using a
            method of the ColoredVector class to transform the vector with
            intermMatrix. Each of these transformation will represent a frame of
            animation that Swing will draw*/
        }

        List<Grid> helper = new ArrayList<>();

        copyOriginalGrid();

        for (Grid grid : intermGrid){
            RealVector vecModified1 = intermMatrix.operate(grid.getVector1());
            RealVector vecModified2 = intermMatrix.operate(grid.getVector2());

            Grid gridModified = new Grid(vecModified1, vecModified2);
            if (grid.getHighlight()){
                gridModified.setHighlight();
            }
            helper.add(gridModified);
        }
        intermGrid.clear();
        intermGrid.addAll(helper);
        ++counter;
        if (counter >= 120){
            going = false;
        }
    }

    //for the users to reset all the transformations done, and to return all of the
    // vectors and the grid-lines back to their default configurations
    public void reset2(){
        //resetting the matrices
        double[][] matrixData = {{1, 0}, {0, 1}};
        intermMatrix = MatrixUtils.createRealMatrix(matrixData);
        matrix = MatrixUtils.createRealMatrix(matrixData);

        copyOriginalGrid(); //resetting the grid

        //resetting the vectors
        for (ColoredVector vec : vectors.keySet()){
            double[] data = {vec.getVector().getEntry(0), vec.getVector().getEntry(1)};
            RealVector resetVector = MatrixUtils.createRealVector(data);
            VectorColor resetColor = vec.getColor();
            ColoredVector resetColoredVector = new ColoredVector(resetVector, resetColor);
            vectors.put(vec, resetColoredVector);
        }
    }

    public void clearVectors(){
        vectors.clear();
    }

    /*for changing the animated transformations. Will be used with buttons and text
    fields for the users to input transformation matrices that they want to see
    animated, as many times they like*/
    public void setNextMatrix(double a, double b, double c, double d){
        double[][] anotherMatrixData = {{a, b}, {c, d}};
        RealMatrix anotherMatrix = MatrixUtils.createRealMatrix(anotherMatrixData);
        matrix = anotherMatrix.multiply(matrix);
    }

    //this is for users who want to see the inverse transformation of a matrix
    public void invertNextMatrix(double a, double b, double c, double d){
        double[][] inverseMatrixData = {{a, b}, {c, d}};
        RealMatrix inverseMatrix = MatrixUtils.createRealMatrix(inverseMatrixData);
        inverseMatrix = MatrixUtils.inverse(inverseMatrix);
        matrix = inverseMatrix.multiply(matrix);
    }

    //private method to get ready to animate the matrix transformation in increments b
    private void setMatrixIncrements(){
        aIncrement = (matrix.getEntry(0, 0) - intermMatrix.getEntry(0, 0))/120;
        bIncrement = (matrix.getEntry(0, 1) - intermMatrix.getEntry(0, 1))/120;
        cIncrement = (matrix.getEntry(1, 0) - intermMatrix.getEntry(1, 0))/120;
        dIncrement = (matrix.getEntry(1, 1) - intermMatrix.getEntry(1, 1))/120;
    }

    //private method to help animate the transformation in increments
    private void incrementMatrix(){
        intermMatrix.setEntry(0, 0, intermMatrix.getEntry(0, 0) + aIncrement);
        intermMatrix.setEntry(0, 1, intermMatrix.getEntry(0, 1) + bIncrement);
        intermMatrix.setEntry(1, 0, intermMatrix.getEntry(1, 0) + cIncrement);
        intermMatrix.setEntry(1, 1, intermMatrix.getEntry(1, 1) + dIncrement);
    }

    /*actual transformation animation. The counter is used with the timer in DrawingBoard
    for animation purposes.*/

    public List<Grid> getIntermGrid(){
        return intermGrid;
    }

    public boolean getRestart(){
        return restart;
    }

    public void changeRestart(boolean bool){
        restart = bool;
    }

    public boolean getGoing(){
        return going;
    }

    //this method is for setting vectors' colors. The program supports three colors.
    public void setColorCounter(){
        if (colorCounter >= 2){
            colorCounter = 0;
        }
        else {
            ++colorCounter;
        }
    }

    public void resetColorCounter(){
        colorCounter = 0;
    }

    public int getColorCounter(){
        return colorCounter;
    }

    /*to set the "going" variable. Will be used in DrawingBoard to tell it to either
    animate or not animate. Will be set to off once the transformation animation is
    over and re-initiated once the user clicks the apply transformation button.*/
    public void setGoing(boolean bool){
        going = bool;
        counter = 0;
    }

    public RealMatrix getMatrix(){
        return matrix;
    }
}