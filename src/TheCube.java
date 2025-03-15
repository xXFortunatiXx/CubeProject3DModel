import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
//this is my version for the 3D model
//========= Cube state configuration =========
public class TheCube {
    private char[][][] cube;
    private int moveCount = 0;
    private ArrayList<String> moveHistory = new ArrayList<>();
    public static final String[] VALID_MOVES = {"U", "D", "R", "L", "F", "B", "U'", "D'", "R'", "L'", "F'", "B'"};
    public static final String[] FACE_NAMES = {"Front", "Back", "Left", "Right", "Up", "Down"};
    public static final char[] COLORS = {'r', 'b', 'o', 'g', 'y', 'w'};
    public static final int CUBE_SIZE = 3;

    //===== Cube state management =====

    public TheCube(){
        cube = new char[6][CUBE_SIZE][CUBE_SIZE];
        moveHistory = new ArrayList<>();
        moveCount = 0;

        for (int face = 0; face < 6; face++) {
            for (int row = 0; row < CUBE_SIZE; row++) {
                for (int col = 0; col < CUBE_SIZE; col++) {
                    cube[face][row][col] = COLORS[face];
                }
            }
        }
    }



    public char[][][] getCubeState(){
        return cube;
    }





    public void printCube() {
        for (int face = 0; face < 6; face++) {
            System.out.println("Face " + FACE_NAMES[face] + ":");
            for (int row = 0; row < CUBE_SIZE; row++) {
                for (int col = 0; col < CUBE_SIZE; col++) {
                    System.out.print(cube[face][row][col]);
                    if (col < CUBE_SIZE - 1) {
                        System.out.print("|");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }
    
    public boolean isCubeSolved() {
        for (int face = 0; face < 6; face++) {
            char color = cube[face][0][0];
            for (int row = 0; row < CUBE_SIZE; row++) {
                for (int col = 0; col < CUBE_SIZE; col++) {
                    if (cube[face][row][col] != color) {
                        return false;

                    } 
                }
            }
        }
        return true;
    }    
    
    public void shuffleCube() {
        Random rand = new Random();
        for (int i = 0; i < 20; i++) { // 20 random moves
            String move = VALID_MOVES[rand.nextInt(VALID_MOVES.length)];
            applyMove(move);
        }
        
    }
    
    //===============User input handling==================
    public void projectTester(Scanner userInput){
            System.out.println("Enter a row of moves separated by spaces: ");
            String moves = userInput.nextLine().trim().toUpperCase(); 

            if (moves.isEmpty()) {
                System.out.println("No moves entered.");
                return;
            }
            String[] moveArray = moves.split(" "); // Split the input into an array of moves
            for (String move : moveArray) { 
                if (validateMove(move)) {
                    applyMove(move);


                }
            
            }
            printCube();
            System.out.print("Total moves made: " + moveCount + "\n");
        }

        
    public boolean validateMove(String move) {
            for (String validMove : VALID_MOVES) {
                if (move.equals(validMove)) {
                    return true;
                }
            }
            System.out.println("Invalid move. Enter one of: U, D, R, L, F, B, U', D', R', L', F', B'");
            return false;
        }


    public void gameLoop(Scanner userInput) {
        
        printCube(); 

        moveCount = 0; // Reset move count

        while (true) {
            System.out.print("Enter move, 'shuffle' to scramble, 'args' to enter test mode, 'solve' to return to solved state,'Hint' to show the solution , 'exit' to quit: ");
            String move = userInput.nextLine().trim().toUpperCase();
            // Handle special commands
            switch (move) {
                case "EXIT" -> {
                    System.out.println("Thanks for playing!");
                    return;
                }
                case "SHUFFLE" -> {
                    shuffleCube();
                    System.out.println("The cube has been scrambled with 20 random moves.");
                    printCube();
                    continue;
                }
                case "SOLVE" -> {
                    solveCube();
                    System.out.println("The cube has been reset.");
                    printCube();
                    continue;
                }
                case "ARGS" -> {
                    projectTester(userInput);
                    continue;
                }
                case "HINT" -> {
                    playerHint();
                    continue;
                }
            }

            // Validate move before applying
            if (validateMove(move)) {
                applyMove(move);
                printCube();
                System.out.println("Total moves made: " + moveCount);

                // Now check if cube is solved after move is applied
                if (isCubeSolved()) {
                    System.out.println("Congratulations! You have solved the cube!");
                    break;
                }
            }
        }
    }

    public void playerHint() {
        ArrayList<String> reverseMoves = new ArrayList<>(moveHistory);

        System.out.println("The solution is: ");
        for (int i = reverseMoves.size() - 1; i >= 0; i--) {
            String move = reverseMoves.get(i);
            String reversedMove = switch (move) {
                case "U"  -> "U'";
                case "U'" -> "U";
                case "D"  -> "D'";
                case "D'" -> "D";
                case "R"  -> "R'";
                case "R'" -> "R";
                case "L"  -> "L'";
                case "L'" -> "L";
                case "F"  -> "F'";
                case "F'" -> "F";
                case "B"  -> "B'";
                case "B'" -> "B";
                default -> "";
            };
            System.out.print(reversedMove + " ");
        }
        System.out.println();
    }
    
    
    //============== Move execution and tracking ===============
    public void solveCube() {
        System.out.println("Reversing moves to solve the cube...");

        ArrayList<String> reverseMoves = new ArrayList<>(moveHistory);
        
        

        for (int i = reverseMoves.size() - 1; i >= 0; i--) {
            String move = reverseMoves.get(i);
            
    
            // Reverse the move direction
            String reversedMove = switch (move) {
                case "U"  -> "U'";
                case "U'" -> "U";
                case "D"  -> "D'";
                case "D'" -> "D";
                case "R"  -> "R'";
                case "R'" -> "R";
                case "L"  -> "L'";
                case "L'" -> "L";
                case "F"  -> "F'";
                case "F'" -> "F";
                case "B"  -> "B'";
                case "B'" -> "B";
                default -> "";
            };
    
            if (!reversedMove.isEmpty()) { 
                applyMove(reversedMove); // Apply the reverse move
            }
        }
        moveHistory.clear(); // Clear move history to avoid infinite loop
        moveCount = 0; // Reset move count before solving
        System.out.println("Cube should now be in its solved state.");
    }
    
    public char[][][] applyMove(String move) {
        // Determine the face and direction of rotation
        int face; // needed to declare it 
        boolean clockwise = true;

        switch (move) {
            case "U"  -> face = 4;
            case "U'" -> { face = 4; clockwise = false; }
            case "D"  -> face = 5;
            case "D'" -> { face = 5; clockwise = false; }
            case "R"  -> face = 3;
            case "R'" -> { face = 3; clockwise = false; }
            case "L"  -> face = 2;
            case "L'" -> { face = 2; clockwise = false; }
            case "F"  -> face = 0;
            case "F'" -> { face = 0; clockwise = false; }
            case "B"  -> face = 1;
            case "B'" -> { face = 1; clockwise = false; }
            default -> {
                System.out.println("Invalid move detected in applyMove.");
                return getCubeState();
            }
        }

        // Rotate the face and adjacent layers
        rotateFaceAndAdjacent(face, clockwise);
        moveHistory.add(move);
        moveCount++;
        return getCubeState();
    } 

    
    //================== Cube rotation methods ==================
    public void rotateFaceAndAdjacent(int face, boolean clockwise) {
        // Rotate the target face
        rotateFace(face, clockwise);

        // Define adjacent faces and their affected rows/columns
        int[] adjacentFaces;
        int rowOrCol;
        boolean isRow;

        switch (face) {
            case 4: // Up face
                adjacentFaces = new int[]{0, 3, 1, 2}; // Front, Right, Back, Left
                rowOrCol = 0; // Top row of adjacent faces(first row)
                isRow = true;  
                break;
            case 5: // Down face
                adjacentFaces = new int[]{0, 2, 1, 3}; // Front, Left, Back, Right
                rowOrCol = CUBE_SIZE - 1; // Bottom row of adjacent faces
                isRow = true;
                break;
            case 3: // Left face
                adjacentFaces = new int[]{0, 4, 1, 5}; // Front, Up, Back, Down
                rowOrCol = CUBE_SIZE - 1; // Right column of adjacent faces
                isRow = false;
                break;
            case 2: // Right face
                adjacentFaces = new int[]{0, 5, 1, 4}; // Front, Down, Back, Up
                rowOrCol = 0; // Left column of adjacent faces
                isRow = false;
                break;
            case 0: // Front face
                adjacentFaces = new int[]{4, 3, 5, 2}; // Up, Right, Down, Left
                rowOrCol = CUBE_SIZE - 1; // Front row/column of adjacent faces
                isRow = true;
                break;
            case 1: // Back face
                adjacentFaces = new int[]{4, 2, 5, 3}; // Up, Left, Down, Right
                rowOrCol = 0; // Back row/column of adjacent faces
                isRow = true;
                break;
            default:
                return;
        }

        // Rotate the adjacent layers
        if (isRow) {
            rotateAdjacentRows(adjacentFaces, rowOrCol, clockwise);
        } else {
            rotateAdjacentColumns(adjacentFaces, rowOrCol, clockwise);
        }
    }

    public void rotateAdjacentRows(int[] adjacentFaces, int row, boolean clockwise) {
        char[] temp = new char[CUBE_SIZE];// temp array to store the row
        if (clockwise) {
            // Save the first face's row
            System.arraycopy(cube[adjacentFaces[0]][row], 0, temp, 0, CUBE_SIZE);
            // Shift rows from adjacent faces
            for (int i = 0; i < adjacentFaces.length - 1; i++) {
                System.arraycopy(cube[adjacentFaces[i + 1]][row], 0, cube[adjacentFaces[i]][row], 0, CUBE_SIZE);
            }
            // Restore the first face's row to the last face
            System.arraycopy(temp, 0, cube[adjacentFaces[adjacentFaces.length - 1]][row], 0, CUBE_SIZE);
        } else {
            // Save the last face's row
            System.arraycopy(cube[adjacentFaces[adjacentFaces.length - 1]][row], 0, temp, 0, CUBE_SIZE);
            // Shift rows from adjacent faces in reverse
            for (int i = adjacentFaces.length - 1; i > 0; i--) {
                System.arraycopy(cube[adjacentFaces[i - 1]][row], 0, cube[adjacentFaces[i]][row], 0, CUBE_SIZE);
            }
            // Restore the last face's row to the first face
            System.arraycopy(temp, 0, cube[adjacentFaces[0]][row], 0, CUBE_SIZE);
        }
    }

    public void rotateAdjacentColumns(int[] adjacentFaces, int col, boolean clockwise) {
        char[] temp = new char[CUBE_SIZE];
        if (clockwise) {
            // Save the first face's column
            for (int i = 0; i < CUBE_SIZE; i++) temp[i] = cube[adjacentFaces[0]][i][col];
            // Shift columns from adjacent faces
            for (int i = 0; i < adjacentFaces.length - 1; i++) {
                for (int j = 0; j < CUBE_SIZE; j++) {
                    cube[adjacentFaces[i]][j][col] = cube[adjacentFaces[i + 1]][j][col];
                }
            }
            // Restore the first face's column to the last face
            for (int i = 0; i < CUBE_SIZE; i++) {
                cube[adjacentFaces[adjacentFaces.length - 1]][i][col] = temp[i];
            }
        } else {
            // Save the last face's column
            for (int i = 0; i < CUBE_SIZE; i++) temp[i] = cube[adjacentFaces[adjacentFaces.length - 1]][i][col];
            // Shift columns from adjacent faces in reverse
            for (int i = adjacentFaces.length - 1; i > 0; i--) {
                for (int j = 0; j < CUBE_SIZE; j++) {
                    cube[adjacentFaces[i]][j][col] = cube[adjacentFaces[i - 1]][j][col];
                }
            }
            // Restore the last face's column to the first face
            for (int i = 0; i < CUBE_SIZE; i++) {
                cube[adjacentFaces[0]][i][col] = temp[i];
            }
        }
    }

    // Rotate a face (clockwise or counter-clockwise)
    public void rotateFace(int face, boolean clockwise) {
        char[][] tempArray = new char[CUBE_SIZE][CUBE_SIZE]; // temp array to store rotated face
        // Copy the face to be rotated to the temp array
        for (int i = 0; i < CUBE_SIZE; i++) {          
            for (int j = 0; j < CUBE_SIZE; j++) {      
                if (clockwise) {
                    //Move the old row to the new column
                    tempArray[j][CUBE_SIZE - 1 - i] = cube[face][i][j];  
                } else {
                    tempArray[CUBE_SIZE - 1 - j][i] = cube[face][i][j];   
                    // Moving element (i, j) to its rotated position.
                    // For clockwise rotation: row i becomes column j in reverse order.
                    // For counterclockwise rotation: column j becomes row i in reverse order.

                }
            }
        }// Copy the rotated face back to the original cube face
        for (int i = 0; i < CUBE_SIZE; i++) { 
            System.arraycopy(tempArray[i], 0, cube[face][i], 0, CUBE_SIZE);
        }
    }

    //================== Main method ==================
    public static void main(String[] args) {
        System.out.println("""
        ========================================================================================================
                                        Welcome to my Rubik's Game!                          
                                Solve the cube to win by entering moves below.
        Enter 'exit' to exit the game. Move format: U, D, R, L, F, B, U', D', R', L', F', B'
        ========================================================================================================
        """);

        Scanner userInput = new Scanner(System.in);

        TheCube myCube = new TheCube();
        myCube.gameLoop(userInput);
        userInput.close();
    }
}
