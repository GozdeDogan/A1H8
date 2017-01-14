/**
 * Created and implemented by GozdeDogan on 4.01.2017.
 *
 */
public class Cell {
    private int type; //Hucrenin rengi; 0 -> Beyaz, 1 -> Siyah , 2 -> Boþ
    private int row; // hucrenin satir koordinati
    private int col; // hucrenin sutun koordinati

    /**
     * Bir Cell nesnesi olsuturuldugunda; koordinatlarý (-1, -1) olan ve type=2 yani bos olan
     * bir nesne olsuturulur.
     * (-1, -1) koordinatlari gercek bir deger belirtmedigi icin tercih edildi.
     */
    Cell(){
        this.row = -1;
        this.col = -1;
        this.type = 2;
    }

    /**
     * Bir Cell hucresi olsuturuldugunda; koordinatlari (x,y) ve type=type olan bir nesne olsuturulur.
     * Gelen x, y, type parametreleri gecerli araliktaysa, degilse yine (-1, -1) koordinatli 2 typeli bir nesne
     * olusturulur.
     * @param x     -> hucrenin alinan satir koordinati
     * @param y     -> hucrenin alinan sutun koordinati
     * @param type  -> hucrenin alinan rengi
     */
    Cell(int x, int y, int type){
        if(x>=0 && x<8)
            this.row = x;
        else
            this.row = -1;

        if(y>=0 && y<8)
            this.col = y;
        else
            this.col = -1;

        if(type>=0 && type<3)
            this.type = type;
        else
            this.type = 2;
    }

    /**
     * Bir Cell nensnesi olusuturuldugunda alinan parametrenin sahip oldugu attributeler nesnenin attributeleri olur
     * @param cell -> hucre icin alinan hucre parametresi
     */
    Cell(Cell cell){
        this.row = cell.getRow();
        this.col = cell.getCol();
        this.type = cell.getType();
    }

    /**
     *
     * @return
     */
    public int getRow(){
        return this.row;
    }

    /**
     *
     * @return
     */
    public int getCol(){
        return this.col;
    }

    /**
     *
     * @return
     */
    public int getType(){
        return this.type;
    }

    /**
     *
     * @param x
     */
    public void setRow(int x){
        if(x>=0 && x<8)
            this.row = x;
    }

    /**
     *
     * @param y
     */
    public void setCol(int y){
        if(y>=0 && y<8)
            this.col = y;
    }

    /**
     *
     * @param type
     */
    public void setType(int type){
        if(type>=0 && type<3)
            this.type = type;
    }

    /**
     *
     * @param x
     * @param y
     * @param type
     */
    public void setCell(int x, int y, int type){
        if(x>=0 && x<8)
            this.row = x;
        else
            this.row = -1;

        if(y>=0 && y<8)
            this.col = y;
        else
            this.col = -1;

        if(type>=0 && type<3)
            this.type = type;
        else
            this.type = 2;
    }

    /**
     * Pararmetrenin ozellikleri nesnemizin ozellikleri olur.
     * @param A1
     */
    public void setCell(Cell A1){
        setCell(A1.getRow(), A1.getCol(), A1.getType());
    }

    /**
     *
     * @return
     */
    public Cell getCell(){
        return this;
    }

    /**
     *
     * @return
     */
    public String toString(){
        return "x:" + getRow() + " y:" + getCol() + " type:" + getType();
    }
}

