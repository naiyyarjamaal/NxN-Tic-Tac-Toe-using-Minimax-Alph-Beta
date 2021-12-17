
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Comparator;

public class TicTacToeBoard{

	static Scanner sc = new Scanner(System.in);
    private static int n = sc.nextInt();//dimension of board
    public static final int DIM = n;//dimension of board
    public static final int SIZE = DIM * DIM;//Size will be DIM x DIM
    public char[] tttBoard;//current set up of board
    public char turn;//current turn 'X' or 'O'
    private Map<Integer, Integer> gameMap = new HashMap<Integer, Integer>();//Hashmap used for caching each key value pair of each tic tac toe board 

    public TicTacToeBoard(){//Constructor with no parameters

        this.turn = 'X';
        tttBoard = new char[SIZE];
        for (int i = 0; i < SIZE; i++) {
            tttBoard[i] = ' ';
        }
    }

    public TicTacToeBoard(char[] board, char turn) {//Constructor with current board and turn
        this.tttBoard = board;
        this.turn = turn;

    }

    public TicTacToeBoard(String str) {//Constructor with current board in string
        this.tttBoard = str.toCharArray();
        this.turn = 'X';
    }

    public TicTacToeBoard(String str, char turn) {//Constructor with current board in string and current turn
        this.tttBoard = str.toCharArray();
        this.turn = turn;
    }


    public boolean win(char turn) {//Checking whether it is a win for the current turn
        boolean isWin = false;
        for (int i = 0; i < SIZE; i += DIM) {//Checking win in rows
            isWin = isWin || winLine(turn, i, i + DIM, 1);
            if(isWin == true)
                return isWin;
        }
        for (int i = 0; i < DIM; i++) {//Checking win in columns
            isWin = isWin || winLine(turn, i, SIZE, DIM);
            if(isWin == true)
                return isWin;
        }
        isWin = isWin || winLine(turn, 0, SIZE, DIM + 1);//Checking win for major diagonal
        if(isWin == true)
            return isWin;
        isWin = isWin || winLine(turn, DIM - 1, SIZE - 1, DIM - 1);//Checking win for minor diagonal
        return isWin;
    }

    public boolean winLine(char turn, int start, int end, int step) {//Iterating throw a particular row/column/diagonal to see if it is win for turn
        for (int i = start; i < end; i += step) {
            if (tttBoard[i] != turn)
                return false;
        }

        return true;
    }

    public int blanks() {//Number of blanks left in the current board
        int total = 0;
        for (int i = 0; i < SIZE; i++) {
            if (tttBoard[i] == ' ')
                total++;
        }
        return total;
    }

    public int mapKey() {//Returning a key for hashing
        int value = 0;
        for (int i = 0; i < SIZE; i++) {
            value = value * 3;
            if (tttBoard[i] == 'X')
                value += 1;
            else if (tttBoard[i] == 'O')
                value += 2;
        }
        return value;
    }



    public int minimax(int alpha,int beta) {//minimax method implemented using alpha beta pruning

        Integer key = mapKey();
        Integer value = gameMap.get(key);
        
        if (value != null)//if map(hash) found
            return value;
        
        if (win('X'))//if win for 'X'
            return blanks()+1;
        
        if (win('O'))//if win for '0'
            return -blanks()-1;
        
        if (blanks() == 0)//if Draw
            return 0;
        
        List<Integer> list = new ArrayList<>();
        if(turn == 'X') {//Code for Maximising element
            int v = -100, mm;
            for (Integer index : possibleMoves()) {
                mm = move(index).minimax(alpha,beta);
                unMove(index);//Unmoving because we just want to find out value after move and not make the move
                v = v > mm ? v : mm;
                alpha = alpha > v ? alpha : v;
                if (beta <= alpha)//alpha beta pruning
                    break;
                list.add(mm);

            }
        }
        else if(turn == 'O')//Code for Minimising element
        {
            int v = 100, mm;
            for (Integer index : possibleMoves()) {
                mm = move(index).minimax(alpha,beta);
                unMove(index);//Unmoving because we just want to find out value after move and not make the move
                v = v < mm ? v : mm;
                alpha = alpha < v ? alpha : v;
                if (beta <= alpha)//alpha beta pruning
                    break;
                list.add(mm);
            }
        }

        value = turn == 'X' ? Collections.max(list) : Collections.min(list);//Returning max if turn = 'X' else returning min of list
        gameMap.put(key, value);//storing the hash  
        return value;
    }


    public int evaluation() { //calculates value for evaluation function(only when dimension>4)

        int  sumX =0, sumO =0; int numX,numO;
        for (int i = 0; i < SIZE; i += DIM) {
            numX = 0; numO = 0;
            for (int j = i; j < i + DIM; j++) {
                if (tttBoard[j] == 'X')
                    numX++;
                else if (tttBoard[j] == 'O')
                    numO++;

            }
            if(numX>0 && numO>0)
            {
                sumX += 0;
                sumO += 0;
            }
            else
            if(numX>0 && numO==0){
                sumX += (numX * 2)-1;
            }
            else
            if(numX==0 && numO>0){
                sumO += (numO * 2)-1;
            }
        }
        for (int i = 0; i < DIM; i++) {
            numX = 0; numO = 0;
            for (int j = i; j < SIZE; j=j+DIM) {
                if (tttBoard[j] == 'X')
                    numX++;
                else if (tttBoard[j] == 'O')
                    numO++;

            }
            if(numX>0 && numO>0)
            {
                sumX += 0;
                sumO += 0;
            }
            else if(numX>0 && numO==0){
                sumX += (numX * 2)-1;
            }
            else
            if(numX==0 && numO>0){
                sumO += (numO * 2)-1;
            }
        }

        numX = 0; numO = 0;
        for (int j = 0; j < SIZE; j+=DIM+1) {
            if (tttBoard[j] == 'X')
                numX++;
            else if (tttBoard[j] == 'O')
                numO++;
        }
        if(numX>0 && numO>0)
        {
            sumX += 0;
            sumO += 0;
        }
        else
        if(numX>0 && numO==0){
            sumX += (numX * 2)-1;
        }
        else
        if(numX==0 && numO>0){
            sumO += (numO * 2)-1;
        }
        numX = 0; numO = 0;
        for (int j = DIM-1; j < SIZE-1; j+=DIM-1) {
            if (tttBoard[j] == 'X')
                numX++;
            else if (tttBoard[j] == 'O')
                numO++;
        }
        if(numX>0 && numO>0)
        {
            sumX += 0;
            sumO += 0;
        }
        else
        if(numX>0 && numO==0){
            sumX += (numX * 2)-1;
        }
        else
        if(numX==0 && numO>0){
            sumO += (numO * 2)-1;
        }
        return sumX-sumO; // returning value of evaluation function
    }

    public int evalFunction(int depth) {//if dimension is greater than 4, use evaluation function instead of utility function

        if (win('X'))
            return blanks()+1;
        if (win('O'))
            return -blanks()-1;
        if (blanks() == 0)
            return 0;
        if (depth == 0 )
            return evaluation();
        int maxIndex=possibleMoves().get(0);
        int minIndex=possibleMoves().get(0);
        if(turn == 'X') {
            int max=0,mm;
            for (Integer index : possibleMoves()) {
                mm = move(index).evalFunction(depth-1);
                unMove(index);
                if(mm>max)//storing max value of evaluation function
                {
                    max = mm;
                    maxIndex = index;
                }

            }
        }
        else if(turn == 'O')
        {
            int min=0,mm;
            for (Integer index : possibleMoves()) {
                mm = move(index).evalFunction(depth-1);
                unMove(index);
                if(mm<min)//storing minimum value of evaluation function
                {
                    min = mm;
                    minIndex = index;
                }

            }
        }


        return turn == 'X' ? maxIndex : minIndex;//returning corresponding index
    }


    public int bestMove() { //Finding best move for Computer

        if (DIM > 4) {

                return evalFunction(1);//if dimension is greater than 4, use evaluation function instead of utility function

        }
        Comparator<Integer> cmp = new Comparator<Integer>() { // Using Comparator for finding best move for Computer according to minimax value returned
            public int compare(Integer first, Integer second) {
                int a = move(first).minimax(-100,100);
                unMove(first);
                int b = move(second).minimax(-100,100);
                unMove(second);
                return a - b;
            }

        };
        List<Integer> list = possibleMoves();
        return turn == 'X' ? Collections.max(list, cmp): Collections.min(list, cmp);//returning best index
    }


    @Override
    public String toString() {
        return new String(tttBoard);
    }//Overriding toString method

    public TicTacToeBoard move(int index){//making a move on the board

        tttBoard[index] = turn;
        turn = turn == 'X' ? 'O':'X';
        return this;

    }

    public TicTacToeBoard unMove(int index){//making a unMove on the board because we just want to find out value after move and not make the move
        tttBoard[index] = ' ';
        turn = turn == 'X' ? 'O':'X';
        return this;

    }


    public List<Integer> possibleMoves(){ // Checking for list of possible moves available
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<tttBoard.length;i++){
            if(tttBoard[i]==' ')
                list.add(i);
        }
        return list;
    }

    public boolean isGameEnd(){//Checking if game is over
        return win('X') || win('O') || blanks() == 0;
    }


}
