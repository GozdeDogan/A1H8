import java.util.*;

/**
 * Created and implemented by GozdeDogan on 4.01.2017.
 */
public class Game {
    /**
     * OYUN TAHTASI
     */
    private int[][] board;

    /**
     * Bir Game nesnesi olsuturuldugunda Game'in sahip oldugu attributelar olusturulur.
     * board olusturulur. Ve pullar tahtaya yerlestirilir.
     */
    Game(){
        board = new int[8][8];
        fillBoard();
    }

    /**
     * @return
     */
    public int[][] getBoard(){
        return this.board;
    }

    /**
     * @param board -> nesnenin board attribute'unu doldurmak icin alinan int[][] parametresi
     */
    public void setBoard(int[][] board){
        int i, j;
        for(i=0; i<8; i++) //row
            for(j=0; j<8; j++) //col
                this.board[i][j]=board[i][j];
    }


    /**
     * Oyun tahtasi doldurulur.
     * Koyulacak tas sayisi random olarak belirlendi. 3 ile 9 arasinda!
     * Taslarin koyulacagi yerler de random olarak belirlendi.
     * -1 -> SIYAH, 0 -> BOS
     */
    private void fillBoard(){

        Random rand = new Random();
        int n = rand.nextInt(8) + 1;
        int m;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                board[i][j] = 0;
            }
        }

        int numOfBlack = 0, maxBlackStump;
        /*
         * koyulacak siyah pul sayisi random olarak belirlendi.
         * siyah pul sayisi 3 ve 9 arasinda olacagi icin
         * bu islem  3<=maxBlackStump<=9 olmadigi muddetce devam ediyor!
         */
        do {
            maxBlackStump = rand.nextInt(9) + 3;
        }while(maxBlackStump < 10 && maxBlackStump >= 3);

        /*
         * Belirlenen maxBlackStump sayisi kadar siyah pul tahtaya yerlesitiriliyor.
         */
        do {
            n = rand.nextInt(8) + 1;
            m = rand.nextInt(8) + 1;
            if (n < 8 && n >= 0 && m < 8 && m >= 0) {
                board[n][m] = -1;
                numOfBlack++;
            }
        }while(numOfBlack < maxBlackStump);
    }

    /**
     * Oyuncunun hamlesi gerceklestirilir.
     * Oyun bittikten sonra A1in H8e ulasirken yaptigi hamleler ekrana yazilir
     * Ve karsilasilan siyah pullar ekrana yazilir
     */
    public void playGame(){
        System.out.println("The Beginning Of The Game!!");
        solveProblem();
        printBoard();
        printWayAndBlackStamp();
        System.out.println("The End Of The Game!!");

    }

    /**
     * A1 baslangic konumu olarak secildi. (7, 0)
     * Burdan H8e kadar ilerlendi!
     * H8 ve A1 hucreleri 2 ile yani beyaz pulun izledigi yol olarak dodluruldu!
     */
    private void solveProblem(){
        Cell current = new Cell();
        current.row = 7;
        current.col = 0;
        board[7][0] = 2;
        board[0][7] = 2;

        while(current.col != 7 && current.row != 0){
            if(board[current.row - 1][current.col + 1] == 0){
                current.row--;
                current.col++;
                checkTable(current.row,current.col);
                board[current.row][current.col] = 2;
            }else if(board[current.row - 1][current.col] == 0){
                current.row--;
                checkTable(current.row, current.col);
                board[current.row][current.col] = 2;
            }else if(board[current.row][current.col + 1] == 0){
                current.col++;
                checkTable(current.row, current.col);
                board[current.row][current.col] = 2;
            }else if(board[current.row - 1][current.col - 1] == 0 && current.row != 0 && current.col != 0){
                current.col--;
                current.row--;
                checkTable(current.row, current.col);
                board[current.row][current.col] = 2;
            }else if(board[current.row][current.col - 1] == 0){
                current.col--;
                checkTable(current.row, current.col);
                board[current.row][current.col] = 2;
            }
            while(current.row == 0 && current.col != 7){
                board[0][current.col++] = 2;
            }
        }
    }


    /**
     * Siyah olan pullar, ki bu benim oyun tahtam icin -1 demek, karsilasilan siyah pullar olarak , -2,
     * gosterildi!
     * @param x
     * @param y
     */
    private void checkTable(int x, int y) {
        if(x != 0 && y != 0 && y != 7 && x != 7){
            if(board[x - 1][y - 1] == -1){
                board[x - 1][y - 1] = -2;
            }
            if(board[x - 1][y] == -1){
                board[x - 1][y] = -2;
            }
            if(board[x - 1][y + 1] == -1){
                board[x - 1][y + 1] = -2;
            }
            if(board[x][y - 1] == -1){
                board[x][y - 1] = -2;
            }
            if(board[x + 1][y - 1] == -1){
                board[x + 1][y - 1] = -2;
            }
            if(board[x + 1][y] == -1){
                board[x + 1][y] = -2;
            }
            if(board[x + 1][y + 1] == -1){
                board[x + 1][y + 1] = -2;
            }
        }
    }

    /**
     * Board ekrana yazilir.
     */
    public void printBoard(){
        System.out.println("----------------------------------");
        char ch = 'H';
        System.out.println("    8   7   6   5   4   3   2   1");
        for(int i = 0; i < 8 ; ++i ){
            System.out.print(ch + " ");
            ch--;
            for(int j =0; j < 8 ; ++j ){
                if(board[i][j] == 2 ){
                    if(j<7)
                        System.out.printf("| . ");
                    else if(j==7)
                        System.out.printf("| . |");
                }else if(board[i][j] == -2){
                    if(j<7)
                        System.out.printf("| * ");
                    else if(j==7)
                        System.out.printf("| * |");
                }else {
                    if(j<7)
                        System.out.printf("|   ");
                    else if(j==7)
                        System.out.printf("|   |");
                }
            }
            System.out.printf("\n");
        }
        System.out.println("----------------------------------");
        System.out.println("* : Rastlanilan Siyah Pullar");
        System.out.println(". : Yol");
        System.out.println("----------------------------------");

    }

    /**
     * Burada tahta incelendi, 2 olan yerler, bunlar beyaz pulun izledigi yol olarak ekrana yazildi.
     * Sonra tahta tekrar incelendi, -2 olan yerler, bunlar karsilasilan siyah pullar olarak ekrana yazildi.
     */
    private void printWayAndBlackStamp(){
        System.out.println("Beyaz pulun, A1den H8e izledigi yol ->>");
        int numberOfMove = 1;
        for(int i=7; i>=0; i--){
            for(int j=0; j<8; j++){
                if(board[i][j] == 2 ) {
                    System.out.println(numberOfMove + ". move : (" + i + ", " + j + ")");
                    numberOfMove++;
                }
            }
        }

        System.out.println("Karsilasilan siyah pullarin konumu ->>");
        numberOfMove = 1;
        for(int i=7; i>=0; i--){
            for(int j=0; j<8; j++){
                if(board[i][j] == -2 ) {
                    System.out.println(numberOfMove + ". move : (" + i + ", " + j + ")");
                    numberOfMove++;
                }
            }
        }
    }
}
