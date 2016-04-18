package tetris;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

// Imports para guardar
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * The {@code Tetris} class is responsible for handling much of the game logic
 * and reading user input.
 *
 * @Author Jose Humberto Garza Rosado
 * @Matricula A00808689
 * @Title Tetris
 * @OriginalAuthor Brendan Jones
 *
 */
public class Tetris extends JFrame {

    /**
     * Variables de sonido
     */
    private SoundClip souFondo;
    private SoundClip souLaser;

    /**
     * Variables par guardar el juego
     */
    private String nombreArchivo;
    private boolean bGuardando;

    /**
     * La Sombra del tile
     */
    private int iGlow;
    /**
     * Incrementar o decrementar el glow
     */
    private boolean bGrow;

    /**
     * The Serial Version UID.
     */
    private static final long serialVersionUID = -4722429764792514382L;

    /**
     * The number of milliseconds per frame.
     */
    private static final long FRAME_TIME = 1000L / 50L;

    /**
     * The number of pieces that exist.
     */
    private static final int TYPE_COUNT = TileType.values().length;

    /**
     * The BoardPanel instance.
     */
    private BoardPanel board;

    /**
     * The SidePanel instance.
     */
    private SidePanel side;

    /**
     * Whether or not the game is paused.
     */
    private boolean isPaused;

    /**
     * Whether or not we've played a game yet. This is set to true initially and
     * then set to false when the game starts.
     */
    private boolean isNewGame;

    /**
     * Whether or not the game is over.
     */
    private boolean isGameOver;

    /**
     * The current level we're on.
     */
    private int level;

    /**
     * The current score.
     */
    private int score;

    /**
     * The random number generator. This is used to spit out pieces randomly.
     */
    private Random random;

    /**
     * The clock that handles the update logic.
     */
    private Clock logicTimer;

    /**
     * The current type of tile.
     */
    public TileType currentType;

    /**
     * The next type of tile.
     */
    public TileType nextType;
    /**
     * The tile on hold
     */
    public TileType savedType;

    /**
     * The current column of our tile.
     */
    private int currentCol;

    /**
     * The current row of our tile.
     */
    private int currentRow;

    /**
     * The current rotation of our tile.
     */
    private int currentRotation;

    /**
     * Ensures that a certain amount of time passes after a piece is spawned
     * before we can drop it.
     */
    private int dropCooldown;

    /**
     * The speed of the game.
     */
    private float gameSpeed;

    /**
     * Creates a new Tetris instance. Sets up the window's properties, and adds
     * a controller listener.
     */
    private Tetris() {
        /*
		 * Set the basic properties of the window.
         */
        super("Tetris");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        /*
		 * Initialize the BoardPanel and SidePanel instances.
         */
        this.board = new BoardPanel(this);
        this.side = new SidePanel(this);

        /*
		 * Add the BoardPanel and SidePanel instances to the window.
         */
        add(board, BorderLayout.CENTER);
        add(side, BorderLayout.EAST);

        /*
		 * Logica de guardado
         */
        nombreArchivo = "Loaded.txt";

        /*
		 * Adds a custom anonymous KeyListener to the frame.
         */
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {

                    /*
					 * Drop - When pressed, we check to see that the game is not
					 * paused and that there is no drop cooldown, then set the
					 * logic timer to run at a speed of 25 cycles per second.
                     */
                    case KeyEvent.VK_DOWN:
                        if (!isPaused && dropCooldown == 0) {
                            logicTimer.setCyclesPerSecond(25.0f);
                        }
                        break;

                    /*
				 	 * Move Left - When pressed, we check to see that the game is
					 * not paused and that the position to the left of the current
					 * position is valid. If so, we decrement the current column by 1.
                     */
                    case KeyEvent.VK_LEFT:
                        if (!isPaused && board.isValidAndEmpty(currentType, currentCol - 1, currentRow, currentRotation)) {
                            currentCol--;
                        }
                        break;

                    /*
					 * Move Right - When pressed, we check to see that the game is
					 * not paused and that the position to the right of the current
					 * position is valid. If so, we increment the current column by 1.
                     */
                    case KeyEvent.VK_RIGHT:
                        if (!isPaused && board.isValidAndEmpty(currentType, currentCol + 1, currentRow, currentRotation)) {
                            currentCol++;
                        }
                        break;

                    /*
				 	 * Rotate Anticlockwise - When pressed, check to see that the game is not paused
					 * and then attempt to rotate the piece anticlockwise. Because of the size and
					 * complexity of the rotation code, as well as it's similarity to clockwise
					 * rotation, the code for rotating the piece is handled in another method.
                     */
                    case KeyEvent.VK_M:
                        if (!isPaused) {
                            rotatePiece((currentRotation == 0) ? 3 : currentRotation - 1);
                        }
                        break;

                    /*
			    	 * Rotate Clockwise - When pressed, check to see that the game is not paused
					 * and then attempt to rotate the piece clockwise. Because of the size and
					 * complexity of the rotation code, as well as it's similarity to anticlockwise
					 * rotation, the code for rotating the piece is handled in another method.
                     */
                    case KeyEvent.VK_UP:
                        if (!isPaused) {
                            rotatePiece((currentRotation == 3) ? 0 : currentRotation + 1);
                        }
                        break;

                    /*
					 * Start Game - When pressed, check to see that we're in either a game over or new
					 * game state. If so, reset the game.
                     */
                    case KeyEvent.VK_ENTER:
                        if (isGameOver || isNewGame) {
                            resetGame();
                        }
                        break;

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

                switch (e.getKeyCode()) {

                    /*
					 * Drop - When released, we set the speed of the logic timer
					 * back to whatever the current game speed is and clear out
					 * any cycles that might still be elapsed.
                     */
                    case KeyEvent.VK_DOWN:
                        logicTimer.setCyclesPerSecond(gameSpeed);
                        logicTimer.reset();
                        break;


                    /*
					* Pause Game - When pressed, check to see that we're currently playing a game.
					* If so, toggle the pause variable and update the logic timer to reflect this
					* change, otherwise the game will execute a huge number of updates and essentially
					* cause an instant game over when we unpause if we stay paused for more than a
					* minute or so.
                     */
                    case KeyEvent.VK_P:
                        if (!isGameOver && !isNewGame) {
                            isPaused = !isPaused;
                            logicTimer.setPaused(isPaused);
                        }
                        break;
                    /*
					 * Hold - when pressed, the current tile will be hold, it will only work if it fits
                     */
                    case KeyEvent.VK_SPACE:
                    	// In case there is no saved available, just keep the current one and spawn a new one
                        if (savedType == null) {
                            savedType = currentType;
                            currentRotation = 0;
                            currentType = nextType;
                            nextType = TileType.values()[random.nextInt(TYPE_COUNT)];
                        } else {
                        	// Otherwise, go through the board and make sure the switch can take place
                            for (int col = 0; col < savedType.getDimension(); col++) {
                                for (int row = 0; row < savedType.getDimension(); row++) {
                                    if (savedType.isTile(col, row, currentRotation)) {
                                        int iTileCol = col + currentCol;
                                        int iTIleRow = row + currentRow;
                                        if (iTileCol > board.getColCount() || iTIleRow > board.getRowCount() || iTileCol < 0 || iTIleRow < 0) {
                                            return;
                                        }
                                        TileType tile = board.getTile(iTileCol, iTIleRow);
                                        if (tile != null) {
                                            return;
                                        }

                                    }
                                }
                            }
                            // Make the switch
                            TileType tilAux = currentType;
                            currentType = savedType;
                            savedType = tilAux;
                        }
                        break;

                    /*
					 * Save - Save the current state of the game even if is in pause
                     */
                    case KeyEvent.VK_G:
                        if(!isNewGame && !isGameOver && !isPaused) {
                            try {
                                guardarJuego();
                            } catch (IOException ex) {
                                Logger.getLogger(Tetris.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    /*
					 * Load - Load the game even if is paused. It will keep the pause state
                     */
                    case KeyEvent.VK_C:
                        if(!isNewGame && !isGameOver && !isPaused) {
                            try {   
                                    cargarJuego();
                            } catch (IOException ex) {
                                Logger.getLogger(Tetris.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                }

            }

        });

        /*
		 * Here we resize the frame to hold the BoardPanel and SidePanel instances,
		 * center the window on the screen, and show it to the user.
         */
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        System.out.println(getContentPane().getSize());
    }

    /**
     * cargarJuego
     *
     * cargar el estado del juego
     *
     */
    private void cargarJuego() throws IOException {

    	// Variable to make sure we dont save too many times during one save
        bGuardando = true;
        BufferedReader fileIn;
        fileIn = new BufferedReader(new FileReader(nombreArchivo));
        iGlow = Integer.parseInt(fileIn.readLine());
        bGrow = Boolean.valueOf(fileIn.readLine());
        isPaused = Boolean.valueOf(fileIn.readLine());
        isNewGame = Boolean.valueOf(fileIn.readLine());
        isGameOver = Boolean.valueOf(fileIn.readLine());
        level = Integer.parseInt(fileIn.readLine());
        score = Integer.parseInt(fileIn.readLine());
        currentCol = Integer.parseInt(fileIn.readLine());
        currentRow = Integer.parseInt(fileIn.readLine());
        currentRotation = Integer.parseInt(fileIn.readLine());
        dropCooldown = Integer.parseInt(fileIn.readLine());
        gameSpeed = Float.parseFloat(fileIn.readLine());

        int iTipo;
        //Board
        board.clear();
        for (int row = board.getHiddenRow(); row < board.getRowCount(); row++) {
            for (int col = 0; col < board.getColCount(); col++) {
                iTipo = Integer.parseInt(fileIn.readLine());
                if (iTipo != -1) {
                    TileType tilAux = TileType.values()[iTipo];
                    board.setTile(col, row, tilAux);
                }
            }
        }
        //Side
        //CurrentType
        iTipo = Integer.parseInt(fileIn.readLine());
        currentType = TileType.values()[iTipo];
        //NextType
        iTipo = Integer.parseInt(fileIn.readLine());
        nextType = TileType.values()[iTipo];
        //SavedType
        iTipo = Integer.parseInt(fileIn.readLine());
        if (iTipo != -1) {
            savedType = TileType.values()[iTipo];
        } else {
            savedType = null;
        }

        fileIn.close();
        System.out.println("Juego Cargado!");
        bGuardando = false;
    }

    /**
     * guardarJuego
     *
     * Guardar el estado del juego
     *
     */
    private void guardarJuego() throws IOException {
        if (!isGameOver && !isNewGame) {
            bGuardando = true;
            PrintWriter fileOut = new PrintWriter(nombreArchivo);

            fileOut.println(iGlow);
            fileOut.println(bGrow);
            fileOut.println(isPaused);
            fileOut.println(isNewGame);
            fileOut.println(isGameOver);
            fileOut.println(level);
            fileOut.println(score);
            fileOut.println(currentCol);
            fileOut.println(currentRow);
            fileOut.println(currentRotation);
            fileOut.println(dropCooldown);
            fileOut.println(gameSpeed);

            //Board
            for (int row = board.getHiddenRow(); row < board.getRowCount(); row++) {
                for (int col = 0; col < board.getColCount(); col++) {
                    TileType tile = board.getTile(col, row);
                    if (tile != null) {
                        fileOut.println(tile.getTipo());
                    } else {
                        fileOut.println(-1);
                    }
                }
            }

            //Side
            //CurrentType
            fileOut.println(currentType.getTipo());
            //NextType
            fileOut.println(nextType.getTipo());
            //SavedType
            if (savedType != null) {
                fileOut.println(savedType.getTipo());
            } else {
                fileOut.println(-1);
            }

            fileOut.close();
            System.out.println("Juego Guardado!");
            bGuardando = false;
        }
    }

    /**
     * Starts the game running. Initializes everything and enters the game loop.
     */
    private void startGame() {
        // Logica de sonido
        souFondo = new SoundClip("Fondo.wav");
        souLaser = new SoundClip("Laser.wav");
        souFondo.setLooping(true);
        souFondo.play();

        // Logica de guardado
        this.bGuardando = false;

        /*
		 * Initialize our random number generator, logic timer, and new game variables.
         */
        this.random = new Random();
        this.isNewGame = true;
        this.gameSpeed = 1.0f;
        this.iGlow = 0;
        this.bGrow = true;

        /*
		 * Setup the timer to keep the game from running before the user presses enter
		 * to start it.
         */
        this.logicTimer = new Clock(gameSpeed);
        logicTimer.setPaused(true);

        while (true) {
            //Get the time that the frame started.
            long start = System.nanoTime();

            //Update the logic timer.
            logicTimer.update();

            /*
			 * If a cycle has elapsed on the timer, we can update the game and
			 * move our current piece down.
             */
            if (logicTimer.hasElapsedCycle() && !isGameOver) {
                updateGame();
            }

            //Decrement the drop cool down if necessary.
            if (dropCooldown > 0) {
                dropCooldown--;
            }

            //Display the window to the user.
            renderGame();

            /*
			 * Sleep to cap the framerate.
             */
            long delta = (System.nanoTime() - start) / 1000000L;
            if (delta < FRAME_TIME) {
                try {
                    Thread.sleep(FRAME_TIME - delta);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Updates the game and handles the bulk of it's logic.
     */
    private void updateGame() {
        /*
		 * Check to see if the piece's position can move down to the next row.
         */
        if (board.isValidAndEmpty(currentType, currentCol, currentRow + 1, currentRotation)) {
            //Increment the current row if it's safe to do so.
            currentRow++;
        } else {
            souLaser.play();
            /*
			 * We've either reached the bottom of the board, or landed on another piece, so
			 * we need to add the piece to the board.
             */
            board.addPiece(currentType, currentCol, currentRow, currentRotation);

            /*
			 * Check to see if adding the new piece resulted in any cleared lines. If so,
			 * increase the player's score. (Up to 4 lines can be cleared in a single go;
			 * [1 = 100pts, 2 = 200pts, 3 = 400pts, 4 = 800pts]).
             */
            int cleared = board.checkLines();
            if (cleared > 0) {
                score += 50 << cleared;
            }

            /*
			 * Increase the speed slightly for the next piece and update the game's timer
			 * to reflect the increase.
             */
            gameSpeed += 0.035f;
            logicTimer.setCyclesPerSecond(gameSpeed);
            logicTimer.reset();

            /*
			 * Set the drop cooldown so the next piece doesn't automatically come flying
			 * in from the heavens immediately after this piece hits if we've not reacted
			 * yet. (~0.5 second buffer).
             */
            dropCooldown = 25;

            /*
			 * Update the difficulty level. This has no effect on the game, and is only
			 * used in the "Level" string in the SidePanel.
             */
            level = (int) (gameSpeed * 1.70f);

            /*
			 * Spawn a new piece to control.
             */
            spawnPiece();
        }
    }

    /**
     * Forces the BoardPanel and SidePanel to repaint.
     */
    private void renderGame() {
        board.repaint();
        side.repaint();
    }

    /**
     * Resets the game variables to their default values at the start of a new
     * game.
     */
    private void resetGame() {
        this.level = 1;
        this.score = 0;
        this.gameSpeed = 1.0f;
        this.nextType = TileType.values()[random.nextInt(TYPE_COUNT)];
        this.savedType = null;

        this.isNewGame = false;
        this.isGameOver = false;
        board.clear();
        logicTimer.reset();
        logicTimer.setCyclesPerSecond(gameSpeed);
        spawnPiece();
    }

    /**
     * Spawns a new piece and resets our piece's variables to their default
     * values.
     */
    private void spawnPiece() {
        /*
		 * Poll the last piece and reset our position and rotation to
		 * their default variables, then pick the next piece to use.
         */
        this.currentType = nextType;
        this.currentCol = currentType.getSpawnColumn();
        this.currentRow = currentType.getSpawnRow();
        this.currentRotation = 0;
        this.nextType = TileType.values()[random.nextInt(TYPE_COUNT)];

        /*
		 * If the spawn point is invalid, we need to pause the game and flag that we've lost
		 * because it means that the pieces on the board have gotten too high.
         */
        if (!board.isValidAndEmpty(currentType, currentCol, currentRow, currentRotation)) {
            this.isGameOver = true;
            logicTimer.setPaused(true);
        }
    }

    /**
     * Attempts to set the rotation of the current piece to newRotation.
     *
     * @param newRotation The rotation of the new peice.
     */
    private void rotatePiece(int newRotation) {
        /*
		 * Sometimes pieces will need to be moved when rotated to avoid clipping
		 * out of the board (the I piece is a good example of this). Here we store
		 * a temporary row and column in case we need to move the tile as well.
         */
        int newColumn = currentCol;
        int newRow = currentRow;

        /*
		 * Get the insets for each of the sides. These are used to determine how
		 * many empty rows or columns there are on a given side.
         */
        int left = currentType.getLeftInset(newRotation);
        int right = currentType.getRightInset(newRotation);
        int top = currentType.getTopInset(newRotation);
        int bottom = currentType.getBottomInset(newRotation);

        /*
		 * If the current piece is too far to the left or right, move the piece away from the edges
		 * so that the piece doesn't clip out of the map and automatically become invalid.
         */
        if (currentCol < -left) {
            newColumn -= currentCol - left;
        } else if (currentCol + currentType.getDimension() - right >= BoardPanel.COL_COUNT) {
            newColumn -= (currentCol + currentType.getDimension() - right) - BoardPanel.COL_COUNT + 1;
        }

        /*
		 * If the current piece is too far to the top or bottom, move the piece away from the edges
		 * so that the piece doesn't clip out of the map and automatically become invalid.
         */
        if (currentRow < -top) {
            newRow -= currentRow - top;
        } else if (currentRow + currentType.getDimension() - bottom >= BoardPanel.ROW_COUNT) {
            newRow -= (currentRow + currentType.getDimension() - bottom) - BoardPanel.ROW_COUNT + 1;
        }

        /*
		 * Check to see if the new position is acceptable. If it is, update the rotation and
		 * position of the piece.
         */
        if (board.isValidAndEmpty(currentType, newColumn, newRow, newRotation)) {
            currentRotation = newRotation;
            currentRow = newRow;
            currentCol = newColumn;
        }
    }

    /**
     * Regresa el glow de la sombra
     *
     * @return The column.
     */
    public int getGlow() {
        return iGlow;
    }

    /**
     * Asigna el valor del brillo
     *
     * @param int iGlow
     */
    public void setGlow(int iBrillo) {
        iGlow = iBrillo;
    }

    /**
     * Regresa el grow de la sombra
     *
     * @return The column.
     */
    public boolean getGrow() {
        return bGrow;
    }

    /**
     * Asigna el valor del brillo
     *
     * @param boolean bCrecer
     */
    public void setGrow(boolean bCrecer) {
        bGrow = bCrecer;
    }

    /**
     * Checks to see whether or not the game is paused.
     *
     * @return Whether or not the game is paused.
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Checks to see whether or not the game is over.
     *
     * @return Whether or not the game is over.
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Checks to see whether or not we're on a new game.
     *
     * @return Whether or not this is a new game.
     */
    public boolean isNewGame() {
        return isNewGame;
    }

    /**
     * Gets the current score.
     *
     * @return The score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the current level.
     *
     * @return The level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the current type of piece we're using.
     *
     * @return The piece type.
     */
    public TileType getPieceType() {
        return currentType;
    }

    /**
     * Gets the next type of piece we're using.
     *
     * @return The next piece.
     */
    public TileType getNextPieceType() {
        return nextType;
    }

    public TileType getSavedPieceType() {
        return savedType;
    }

    /**
     * Gets the column of the current piece.
     *
     * @return The column.
     */
    public int getPieceCol() {
        return currentCol;
    }

    /**
     * Gets the row of the current piece.
     *
     * @return The row.
     */
    public int getPieceRow() {
        return currentRow;
    }

    /**
     * Gets the rotation of the current piece.
     *
     * @return The rotation.
     */
    public int getPieceRotation() {
        return currentRotation;
    }

    /**
     * Entry-point of the game. Responsible for creating and starting a new game
     * instance.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        Tetris tetris = new Tetris();
        tetris.startGame();
    }

}
