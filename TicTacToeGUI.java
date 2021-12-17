
import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;


@SuppressWarnings("serial")
public class TicTacToeGUI extends JFrame{//Class for Graphical user Interface
	
	TicTacToeBoard ticTacToeBoard = new TicTacToeBoard();//Create new Game by creating an object of the Board class
	final JButton[] buttons = new JButton[TicTacToeBoard.SIZE];//Creating an array for buttons of tic tac toe board
	
	public TicTacToeGUI() {//Constructor
		setLayout(new GridLayout(TicTacToeBoard.DIM,TicTacToeBoard.DIM));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
      //Setting Layout
        for(int i=0;i<TicTacToeBoard.SIZE;i++){//Iterating through all buttons
            final JButton button = createButton();
            buttons[i] = button;//Assigning the button to the array

            final int index = i;


            button.addMouseListener(new MouseAdapter() {//Adding MouseListener to wait for the event of pressing mouse

                @Override
                public void mousePressed(MouseEvent e) {
                	
                    if(ticTacToeBoard.tttBoard[index]==' '){
                    	
                        button.setText("" + ticTacToeBoard.turn);//Setting Turn text on button
                        ticTacToeBoard.move(index);//Making the move
                        
                        if(!ticTacToeBoard.isGameEnd()){//if the game is not over, let Computer proceed
                            int best = ticTacToeBoard.bestMove();//finding best move for Computer
                            buttons[best].setText("" + ticTacToeBoard.turn);//Setting Turn text on button where best move has been made
                            ticTacToeBoard.move(best);//making the move
                        }
                        
                        if(ticTacToeBoard.isGameEnd()){//If the game is over, display respective message
                            String message = "";
                            
                            if(ticTacToeBoard.win('X'))
                                message = "Congratulations!!!\nYou won!" ;
                            
                            else if(ticTacToeBoard.win('O'))
                                message = "OOPs!!!\nYou Lose!\nTry Again..." ;
                            
                            else
                                message = "Game Tied!!!\nTry Again.";
                            
                            JOptionPane.showMessageDialog(null, message);
                            new TicTacToeGUI();

                        }}
                }
            });
        }
        pack();//packing all attributes for GUI
        setVisible(true);//Displaying the GUI
	}
	
	 private JButton createButton(){//Method for Creating Complete GUI
	        JButton button = new JButton();//Creating Complete UI
	        button.setPreferredSize(new Dimension(550,650));//Setting Size of GUI
	        button.setBackground(Color.WHITE);//Setting Color of background for GUI
	        button.setOpaque(true);//Setting Opacity as true
	        button.setFont(new Font(null, Font.PLAIN, 100 ));//Setting font size of buttons
	        add(button);
	        return button;
	  }
	 
	
	public static void main(String[] args) {//main function for the program
		
		System.out.println("Enter the value of 'N' for N*N Tic-Tac-Toe : ");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TicTacToeGUI frame = new TicTacToeGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
