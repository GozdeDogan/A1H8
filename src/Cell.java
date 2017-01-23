/**
 * Created by GozdeDogan on 23.01.2017.
 */
public class Cell {
    int row;
    int col;

    public Cell(){}

    public Cell(Cell c){
        row=c.row;
        col=c.col;
    }
    public Cell(int r,int c){
        row=r;
        col=c;
    }

    @Override
    public String toString() {
        return "col : " +col + " row:" + row;
    }
}
