import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class TicTacToe {

    int boardWidth = 600;
    int boardHeight = 650; // 50 for text panel

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JButton resetBtn = new JButton();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;
    String winInfo = "tie";
    HashMap<String, Integer> winType = new HashMap<>();
    String winner;

    boolean gameOver = false;

    void checkWinner() {
        // Horizontal check win
        for (int r = 0; r < 3; r++) {
           if (board[r][0].getText().isEmpty()) continue;
           if (board[r][0].getText().equals(board[r][1].getText()) &&
                   board[r][1].getText().equals(board[r][2].getText())) {
               for (int i = 0; i < 3; i++){
                   setWinner(board[r][i]);
               }
               gameOver = true;
               winType.put("Horizontal", r);
               winner = currentPlayer;
               resetBtn.setVisible(true);
               winInfo = "win";
           }
        }
        // Vertical check winner
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText().isEmpty()) continue;
            if (board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText())) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                gameOver = true;
                winType.put("Vertical", c);
                winner = currentPlayer;
                resetBtn.setVisible(true);
                winInfo = "win";
            }
        }
        // Left diagonal check winner
        if (!board[0][0].getText().isEmpty() &&
                board[0][0].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][2].getText())) {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameOver = true;
            winType.put("LeftDiagonal", 4);
            winner = currentPlayer;
            resetBtn.setVisible(true);
            winInfo = "win";
        }
        // Right diagonal check winner
        if (!board[0][2].getText().isEmpty() &&
                board[0][2].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][0].getText())) {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            winType.put("RightDiagonal", 5);
            winner = currentPlayer;
            resetBtn.setVisible(true);
            winInfo = "win";
        }
        // tie
        if (allSquaresMarked() && winInfo.equals("tie")) {
            gameOver = true;
            resetBtn.setVisible(true);
        }
    }

    void setWinner(JButton tile){
        tile.setForeground(Color.green);
        tile.setBackground(Color.black);
    }

    boolean allSquaresMarked(){
        int counter = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (!board[r][c].getText().isEmpty()) {
                    counter++;
                }
            }
        }
        return counter == 9;
    }
    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.black);

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("TicTacToe");
        textLabel.setOpaque(true);

        resetBtn.setBackground(Color.darkGray);
        resetBtn.setForeground(Color.white);
        resetBtn.setFont(new Font("Arial", Font.PLAIN, 25));
        resetBtn.setFocusable(false);
        resetBtn.setContentAreaFilled(false);
        resetBtn.setOpaque(true);
        resetBtn.setText("Reset");
        resetBtn.setHorizontalAlignment(JButton.RIGHT);
        resetBtn.setVisible(false);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel, BorderLayout.CENTER);
        textPanel.add(resetBtn, BorderLayout.EAST);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3,3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        // Creating board
        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 3; c++){
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Sans-Serif", Font.BOLD, 120));
                tile.setFocusable(false);
                tile.setContentAreaFilled(false);
                tile.setOpaque(true);
                tile.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;
                        JButton tile = (JButton) e.getSource();
                        if (!tile.getText().isEmpty()) {
                            return;
                        }
                        tile.setText(currentPlayer);
                        checkWinner();
                        if (!gameOver) {
                            currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                            textLabel.setText("Player's: " + currentPlayer + " turn");
                        }
                        else {
                            textLabel.setText(winInfo.equals("tie") ? "It's a tie!" : "Player: " + winner + " won!");
                        }
                    }
                });
            }
        }
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int r = 0; r < 3; r++){
                    for (int c = 0; c < 3; c++){
                        if (!board[r][c].getText().isEmpty()) {
                            board[r][c].setText("");
                            board[r][c].setBackground(Color.darkGray);
                            board[r][c].setForeground(Color.white);
                        }
                    }
                }
                resetBtn.setVisible(false);
                gameOver = false;
                currentPlayer = playerX;
                textLabel.setText("Player's: " + currentPlayer + " turn");
            }
        });
    }
}
