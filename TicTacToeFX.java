import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Random;

public class TicTacToeFX extends Application {
    private char[][] board = new char[3][3];
    private boolean playerXTurn = true;
    private boolean isSinglePlayer = false; // Default to PvP
    private Button[][] buttons = new Button[3][3];
    private Label statusLabel = new Label("Tic Tac Toe - Player X's Turn");
    private Random random = new Random();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tic Tac Toe");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button(" ");
                buttons[i][j].setMinSize(100, 100);
                buttons[i][j].setStyle("-fx-font-size: 24px;");

                final int row = i, col = j;
                buttons[i][j].setOnAction(e -> handleMove(row, col));
                grid.add(buttons[i][j], j, i);
                board[i][j] = ' ';
            }
        }

        Button resetButton = new Button("Restart Game");
        resetButton.setOnAction(e -> resetBoard());

        Button modeButton = new Button("Switch to AI Mode");
        modeButton.setOnAction(e -> {
            isSinglePlayer = !isSinglePlayer;
            modeButton.setText(isSinglePlayer ? "Switch to PvP Mode" : "Switch to AI Mode");
            resetBoard();
        });

        HBox controls = new HBox(10, resetButton, modeButton);
        controls.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, statusLabel, controls, grid);
        root.setAlignment(Pos.CENTER);
        root.setMinSize(400, 300);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleMove(int row, int col) {
        if (board[row][col] != ' ') return;

        char currentPlayer = playerXTurn ? 'X' : 'O';
        board[row][col] = currentPlayer;
        buttons[row][col].setText(String.valueOf(currentPlayer));

        if (checkWin(currentPlayer)) {
            statusLabel.setText("Player " + currentPlayer + " Wins!");
            disableBoard();
            return;
        } else if (isBoardFull()) {
            statusLabel.setText("It's a Tie!");
            return;
        }

        playerXTurn = !playerXTurn;
        statusLabel.setText("Player " + (playerXTurn ? "X" : "O") + "'s Turn");

        if (isSinglePlayer && !playerXTurn) {
            aiMove();
        }
    }

    private void aiMove() {
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != ' ');

        handleMove(row, col);
    }

    private boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
               (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') return false;
            }
        }
        return true;
    }

    private void disableBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setDisable(true);
            }
        }
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
                buttons[i][j].setText(" ");
                buttons[i][j].setDisable(false);
            }
        }
        playerXTurn = true;
        statusLabel.setText("Tic Tac Toe - Player X's Turn");
    }

    public static void main(String[] args) {
        launch(args);
    }
}