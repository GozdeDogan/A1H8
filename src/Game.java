import java.util.*;

/**
 * Created and implemented by GozdeDogan on 4.01.2017.
 */
public class Game {
    /**
     * OYUN TAHTASI
     */
    private ArrayList<ArrayList<Cell>> board;

    /**
     * siyah taslarin koordinatlarini tutar
     */
    private ArrayList<Cell> blackList;

    /**
     * beyaz taslarin koordinatlarini tutar
     */
    private ArrayList<Cell> whiteList;

    /**
     *
     */
    private ArrayList<Cell> moveableList;

    /**
     * A1 Cell'i, A1deki tas her hareket ettiginde hareketin koordinatlari buraya kaydedilir.
     */
    private Cell A1;

    /**
     * A1'in H8'e giderken yaptigi hamleler
     */
    private ArrayList<Cell> movesOfA1;

    /**
     * A1'in H8'e giderken karsilastigi siyah taslar
     */
    private ArrayList<Cell> blackStones;

    /**
     * //Oynayan tas, 0 -> Beyaz, 1 -> Siyah
     */
    private int player;

    /**
     * Bir Game nesnesi olsuturuldugunda Game'in sahip oldugu attributelar olusturulur.
     * board olusturulur.
     * random olarak 3-9 arasinda koyulacak maximum tas sayisi belirlenir.
     * movesOfA1 olusturulur.
     * blackStones olsuturulur.
     * A1 olusturulur.
     * board'da A1 hucresine beyaz tas yerlestirilir.
     * A1'in ilk bulundugu bu hucre movesOfA1 listesine eklenir.
     * Tahta doldurulur. (Yarisi beyaz, yarisi siyah)
     * player beyaz yapilir.(ilk beyaz baslar)
     */
    Game(){
        board = new ArrayList<ArrayList<Cell>>(8);
        for(int i =0; i < 8; ++i){
            board.add(new ArrayList<Cell>());
            for(int j =0; j < 8; ++j)
                board.get(i).add(new Cell(i, j, 2));
        }

        Random rand = new Random();
        int maxStone;
        do {
            maxStone = rand.nextInt(9);
        }while(maxStone<3);
        //System.out.println("maxStone:" + maxStone);
        movesOfA1 = new ArrayList<>(64);
        blackStones = new ArrayList<>(64);
        A1 = new Cell();
        board.get(0).get(0).setCell(0, 0, 0);
        A1.setCell(board.get(0).get(0));
        movesOfA1.add(A1);
        fillBoard(0, 0, 4, 1, maxStone);
        fillBoard(1, 4, 8, 0, maxStone);
        this.player = 0;
        blackList = new ArrayList<Cell>(9);
        whiteList = new ArrayList<Cell>(9);
        moveableList = new ArrayList<Cell>(5);
    }

    /**
     * @return
     */
    public ArrayList<ArrayList<Cell>> getBoard(){
        return this.board;
    }

    /**
     * @return
     */
    public int getPlayer(){
        return this.player;
    }

    /**
     * @return
     */
    public Cell getA1(){
        return this.A1;
    }

    /**
     * @param board -> nesnenin board attribute'unu doldurmak icin alinan ArrayList<ArrayList<Cell>> parametresi
     */
    public void setBoard(ArrayList<ArrayList<Cell>> board){
        int i, j;
        for(i=0; i<8; i++) //row
            for(j=0; j<8; j++) //col
                this.board.get(i).get(j).setCell(board.get(i).get(j).getCell());
    }

    /**
     * @param player
     */
    public void setPlayer(int player){
        if(player==0 || player==1)
            this.player = player;
    }

    /**
     * @param A1
     */
    public void setA1(Cell A1){
        this.A1.setCell(A1);
    }


    /**
     * Oyun tahtasi doldurulur.
     * Koyulacak tas sayisi random olarak belirlendi.
     * Taslarin koyulacagi yerler de random olarak belirlendi.
     * Tahtanin bir yarisi; random olarak beyaz taslarla yerlestirilir,
     * diger yarisi; random olarak siyah taslarla yerlestirilir.
     * @param type      -> dolurulacak tas rengi
     * @param xDown     -> Tahtanin hangi yarisinin dolduralacagini belirleyen alt sinir
     * @param xUp       -> Tahtanin hangi yarisinin dolduralacagini belirleyen ust sinir
     * @param stone     -> Beyaz tasin birinin yeri belli oldugu icin(A1 hucresi),
     *                     geriye maxStone-stone kadar beyaz tas yerlestirilir.
     *                     Siyah tas icin ise bu parametre zaten 0 olarak geliyor.
     * @param maxStone  -> 3 ile 9 arasinda belirlenmis maximum koyulacak tas sayisi
     */
    private void fillBoard(int type, int xDown, int xUp, int stone, int maxStone){
        int x, y, yUp=8;
        Random rand = new Random();

        int random;
        if(type == 0)
            random = 4;
        else
            random = 8;


        /**
         * H8'in bos olmasi lazim!! Cunku A1deki beyaz tas H8 hucresine ulasmaya calisiyor.
         * Siyah ise row<8 && column<8 olmali!!
         * Bu nedenle gelen xUp degeri type siyahsa 8 gelecegi icin bir azalttim.
         * Ayni sekilde yUp her zaman 8dir, ama type siyahsa bu degiskene 7 asign ettim
         */
        if(type == 1){
            xUp -= 1;
            yUp = 7;
        }

        do{
            x = rand.nextInt(random);
            y = rand.nextInt(random);

                if (xDown <= x && x < xUp) {
                    if (0 <= y && y < yUp) {
                        if (board.get(x).get(y).getType() == 2) {
                            board.get(x).get(y).setType(type);
                            //System.out.println("x:" + x + "     y:" + y + "     type:" + type);
                            stone = stone + 1;
                        }
                    }
                }

        }while(stone <= maxStone);
    }

    private void fillBlackAndWhiteList(){

        for(int i=0; i<8; i++){ //row
            for(int j=0; j<8; j++){ //col
                if(board.get(i).get(j).getType() == 1)
                    blackList.add(new Cell(i, j, 1));
                else if(board.get(i).get(j).getType() == 0)
                    whiteList.add(new Cell(i, j, 0));
            }
        }
    }
    /**
     * Oyuncunun hamlesi gerceklestirilir.
     * Oynayan oyuncu degistirilir.
     * A1 kontrol edilir. A1 H8e ulasmadigi muddetce de oyun devam eder.
     * Oyun bittikten sonra A1in H8e ulasirken yaptigi hamleler ekrana yazilir
     */
    public void playGame(){
        Scanner scan = new Scanner(System.in);
        System.out.println("MENU\n1.TWO PLAYER\n2.COMPUTER\nChoose 1 OR 2->>>");
        switch (scan.nextInt()){
            case 1:
                do {
                    printBoard();
                    playerMove();
                    if (getPlayer() == 0) //Beyazsa
                        setPlayer(1);
                    else //Siyahsa
                        setPlayer(0);
                }while(!checkA1());
                break;
            case 2:
                fillBlackAndWhiteList();
               /* System.out.println("BLACK");
                printList(blackList);
                System.out.println("WHITE");
                printList(whiteList);*/
                do {
                    printBoard();
                    //System.out.println("OKEY!");
                    fromA1ToH8(0, 0);
                    //System.out.println("OKEY!!!!!!");
                    printBoard();
                }while(!checkA1());
        }


        System.out.println("THE END OF GAME!");
        System.out.println("movesOfA1--->");
        printList(movesOfA1); //A1Ýn H8'e giderken yaptigi hamleler
        System.out.println("blackStones--->");
        printList(blackStones); //A1'in H8'e giderken karsilastigi siyah taslar
    }

    /**
     * !!!!!!!!!!!!!!1HATA VAR BUNU DUZELTMEYE CALIS!!
     *
     * Burada A1deki beyaz pulun H8e gitmesini sagliyorum.
     * Recursive olarak yaptim!
     * Cünkü oldugu cellden sonra her zaman etrafindaki cellleri inceleyip yoluna devam etmesi gerekiyor.
     * @param row   -> satir numarasi
     * @param col   -> sutun numarasi
     */
    private void fromA1ToH8(int row, int col){
        try {
            if (row <= 7 && row >= 0 && col <= 7 && col >= 0) {
                if (board.get(row).get(col).getType() == 0) {
                    System.out.println("if2 in fromA1ToH8");
                    if (board.get(row + 1).get(col + 1).getType() == 2) {
                        System.out.println("row+1, col+1 in fromA1ToH8");
                        movesOfA1.add(new Cell(col+1, row+1, 0));
                        fromA1ToH8(row + 1, col + 1);
                    } else if (board.get(row + 1).get(col).getType() == 2) {
                        System.out.println("row+1, col in fromA1ToH8");
                        movesOfA1.add(new Cell(col, row + 1, 0));
                        fromA1ToH8(row + 1, col);
                    } else if (board.get(row).get(col + 1).getType() == 2) {
                        System.out.println("row, col+1 in fromA1ToH8");
                        movesOfA1.add(new Cell(col + 1, row, 0));
                        fromA1ToH8(row, col + 1);
                    } else if (board.get(row + 1).get(col - 1).getType() == 2) {
                        System.out.println("row+1, col-1 in fromA1ToH8");
                        movesOfA1.add(new Cell(col - 1, row + 1, 0));
                        fromA1ToH8(row + 1, col - 1);
                    } else if (board.get(row).get(col - 1).getType() == 2) {
                        System.out.println("row, col-1 in fromA1ToH8");
                        movesOfA1.add(new Cell(col - 1, row, 0));
                        fromA1ToH8(row, col - 1);
                    }
                    /*
                    else if(board.get(row).get(col - 1).getType() == 1) {
                        blackStones.add(new Cell(row, col - 1, 1));
                    }
                    else if(board.get(row).get(col + 1).getType() == 1) {
                        blackStones.add(new Cell(row, col + 1, 1));
                    }
                    else if(board.get(row + 1).get(col - 1).getType() == 1) {
                        blackStones.add(new Cell(row + 1, col - 1, 1));
                    }
                    else if(board.get(row + 1).get(col + 1).getType() == 1) {
                        blackStones.add(new Cell(row + 1, col + 1, 1));
                    }
                    else if(board.get(row + 1).get(col).getType() == 1) {
                        blackStones.add(new Cell(row + 1, col, 1));
                    }*/
                }
            }
            System.out.println("the end of fromA1ToH8");
        }catch(Exception e){
            System.exit(0);
        }
    }

    private void checkMoveable(Cell source){
        if(board.get(source.getRow()).get(source.getCol()-1).getType() == 2)
            moveableList.add(new Cell(source.getRow(), source.getCol()-1, 2));
        else if(board.get(source.getRow()).get(source.getCol()+1).getType() == 2)
            moveableList.add(new Cell(source.getRow(), source.getCol()+1, 2));
        else if(board.get(source.getRow()+1).get(source.getCol()-1).getType() == 2)
            moveableList.add(new Cell(source.getRow()+1, source.getCol()-1, 2));
        else if(board.get(source.getRow()+1).get(source.getCol()+1).getType() == 2)
            moveableList.add(new Cell(source.getRow()+1, source.getCol()+1, 2));
        else if(board.get(source.getRow()+1).get(source.getCol()).getType() == 2)
            moveableList.add(new Cell(source.getRow()+1, source.getCol(), 2));
    }
    /**
     * Eger source cell ve target cell dogru ise
     * oyuncunun burada hamlesi gerceklestirilir.
     * yanlis ise dogru degerler alinana kadar User'dan koordinat istenmeye devam edilir.
     *
     * Eger girilen source koordinatlari A1'in koordinatlari ile uyusuyorsa
     * Baslangicta A1 konumunda bulunan beyaz pul hareket ediyor demektir.
     * Bu nedenle beyaz pulun hareketi movesOfA1 listesine yazilir.
     */
    private void playerMove() {
        Cell source = new Cell();
        Cell target = new Cell();

        do {
            do {
                System.out.print("Enter Source");
                takeInputCell(source);
                if (source.getType() != getPlayer())
                    System.out.println("Source is not same color with player!");
            } while (source.getType() != getPlayer());

            do {
                System.out.print("Enter Target");
                takeInputCell(target);
                System.out.println("target.getType():" + target.getType());
                if (target.getType() != 2)
                    System.out.println("Target is not empty!!");
            } while (target.getType() != 2);

            if (source.equals(getA1())) {
                setA1(new Cell(target.getRow(), target.getCol(), source.getType()));
                movesOfA1.add(getA1());
                if (target.getType() == 1)
                    blackStones.add(target);
            }

            System.out.println("checkMove:" + checkMove(source, target));
            if (checkMove(source, target)) {
                int targetType = board.get(target.getRow()).get(target.getCol()).getType();
                int sourceType = board.get(source.getRow()).get(source.getCol()).getType();
                //System.out.println("targetType:" + targetType + "   sourceType:" + sourceType);
                board.get(target.getRow()).get(target.getCol()).setType(sourceType);
                board.get(source.getRow()).get(source.getCol()).setType(targetType);
                //System.out.print("targetType:" + board.get(target.getX()).get(target.getY()).getType());
                //System.out.println("   sourceType:" + board.get(source.getX()).get(source.getY()).getType());
            }
        }while(!checkMove(source, target));
    }



    /**
     * User'in girdigi koordinat parametreleri alinir.
     * Girilen koordinatlar dogru olmadigi muddetce tekrar kooridnat istenir!
     * @param cell
     */
    private void takeInputCell(Cell cell){
        try {
            Scanner in = new Scanner(System.in);
            String s = new String();
            int row = -1, col = -1;
            do {
                System.out.println("Cell(e.g. A1):");
                s = in.nextLine();

                if (s != null) {
                    if(row>='A' && row<='H')
                        row = (int) s.charAt(0) - (int)'A'; // CHAR - 'A'
                    else if(row>='a' && row<='h')
                        row = (int) s.charAt(0) - (int)'a'; // CHAR - 'A'
                    col = (int) s.charAt(1) - (int)'1'; // CHAR - '1'
                }
                System.out.println("Row: " + row + "    Column:" + col);
            } while (!(row < 8 && row >= 0 && col < 8 && col >= 0));

            cell.setCell(row, col, board.get(row).get(col).getType());
           /* cell.setX(row);
            cell.setY(col);   // A = 41, User A yazarsa 0 demektir.
            cell.setType(board.get(row).get(col).getType());*/
            //System.out.println("in takeInputCell, type:" + board.get(y).get(x).getType());
        }catch(Exception e){
            System.exit(0);
        }
    }

    /**
     * hamlenin olup olamayacagina bakiyor.
     * Capraz gidebilir, saga, sola ve ileri gidebilir.
     * Geriye gidemez!
     * @param source    -> Kaynak hucre, bu hucredeki tas hareket ettirilecek
     * @param target    -> Hedef hucre, bu hucreye tas getirilecek
     * @return          -> Eger hamle olabiliyorsa true, olamiyorsa false return eder.
     */
    private boolean checkMove(Cell source, Cell target) {
        if (getPlayer() == 0) { // Geri hamle yapamaz. capraz gidebilir
            if(target.getRow() == source.getRow() && target.getCol() == source.getCol()-1)
                return true;
            else if(target.getRow() == source.getRow() && target.getCol() == source.getCol()+1)
                return true;
            else if(target.getRow() == source.getRow()+1 && target.getCol() == source.getCol()-1)
                return true;
            else if(target.getRow() == source.getRow()+1 && target.getCol() == source.getCol()+1)
                return true;
            else if(target.getRow() == source.getRow()+1 && target.getCol() == source.getCol())
                return true;
        }
        else{ //getPlayer()==1
            if(target.getRow() == source.getRow() && target.getCol() == source.getCol()-1)
                return true;
            else if(target.getRow() == source.getRow() && target.getCol() == source.getCol()+1)
                return true;
            else if(target.getRow() == source.getRow()-1 && target.getCol() == source.getCol()-1)
                return true;
            else if(target.getRow() == source.getRow()-1 && target.getCol() == source.getCol()+1)
                return true;
            else if(target.getRow() == source.getRow()-1 && target.getCol() == source.getCol())
                return true;
        }
        return false;
    }

    /**
     * Oyunun basinda A1 hucresinde bulunan beyaz pul surekli H8e ulasmis mi diye kontrol edilir.
     * Ulastiysa true
     * Ulasamadiysa false return edilir.
     * @return
     */
    private boolean checkA1(){
        if(A1.getRow() == 7 && A1.getCol() == 7)
            return true; // A1deki H8e ulasti.
        return false;
    }

    /**
     * Board ekrana yazilir.
     * Oynayan oyuncuya gore tahta cwvrilir!
     */
    public void printBoard(){
        if(getPlayer() == 0) {//Beyaz oyunuyorsa
            char ch = 'A';
            System.out.println("   1 2 3 4 5 6 7 8");
            for(int i =0; i<8; i++) {
                System.out.print(ch + " ");
                ch++;
                for (int j = 0; j < 8; j++)
                    System.out.print(" " + board.get(i).get(j).getType());
                System.out.println();
            }
        }
        else{//Siyah oyunuyorsa
            char ch = 'H';
            System.out.println("  1 2 3 4 5 6 7 8");
            for(int i = 7; i >= 0; i--) {
                System.out.print(ch);
                ch--;
                for (int j = 0; j < 8; j++)
                    System.out.print(" " + board.get(i).get(j).getType());
                System.out.println();
            }
        }
    }

    /**
     * Nesne string'e cevrilir.
     * @return
     */
    @Override
    public String toString(){
        String s = new String();
        for(int i=0; i<8; i++){
            s += "SATIR->";
            for(int j=0; j<8; j++)
                s += board.get(i).get(j).toString();
            s += "\n";
        }
        return s;
    }

    /**
     * Gelen listeyi yazdirir.
     */
    public void printList(ArrayList<Cell> list){
        for(int i=0; i<list.size(); i++){
            System.out.println(i + ". Move: (" + list.get(i).getRow() + ", " + list.get(i).getCol() + ")");
        }
    }
}
