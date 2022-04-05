import java.util.Arrays;

public class HexGame {
    private final DisjointSet disjointSet;
    private static int TOP_EDGE;
    private static int RIGHT_EDGE;
    private static int LEFT_EDGE;
    private static int BOTTOM_EDGE;
    public enum Color{
        RED,
        BLUE,
        NONE
    }
    private final Color[] grid;
    private static int rowCount;
    // constructors based on row count. If none is provided defaults to 11x11 grid
    public HexGame(int rowCount){
        HexGame.rowCount = rowCount;
        int gridSize = rowCount*rowCount;
        disjointSet = new DisjointSet(gridSize+5);
        TOP_EDGE = gridSize+1;
        BOTTOM_EDGE = gridSize+2;
        RIGHT_EDGE = gridSize+3;
        LEFT_EDGE = gridSize+4;
        grid = new Color[gridSize+5];
        Arrays.fill(grid, Color.NONE);
        grid[TOP_EDGE] = Color.RED;
        grid[BOTTOM_EDGE] = Color.RED;
        grid[LEFT_EDGE] = Color.BLUE;
        grid[RIGHT_EDGE] = Color.BLUE;
    }
    public HexGame(){
        HexGame.rowCount = 11;
        int gridSize = rowCount*rowCount;
        disjointSet = new DisjointSet(gridSize+5);
        TOP_EDGE = gridSize+1;
        BOTTOM_EDGE = gridSize+2;
        RIGHT_EDGE = gridSize+3;
        LEFT_EDGE = gridSize+4;
        grid = new Color[gridSize+5];
        Arrays.fill(grid, Color.NONE);
        grid[TOP_EDGE] = Color.RED;
        grid[BOTTOM_EDGE] = Color.RED;
        grid[LEFT_EDGE] = Color.BLUE;
        grid[RIGHT_EDGE] = Color.BLUE;
    }
    // checks if valid play. checks for neighbors to union with. checks if game has been won by blue
    public boolean playBlue(int position, boolean displayNeighbors){
        if (displayNeighbors) {
            displayNeighbors(position);
        }
        if (position < 1 || position > rowCount*rowCount){
            System.out.println("Not a valid position");
            return false;
        }
        else if (isOccupied(position)){
            return false;
        }
        else {
            grid[position] = Color.BLUE;
            if (getTopLeftHex(position) != TOP_EDGE && getHexColor(getTopLeftHex(position)).equals(Color.BLUE)){
                disjointSet.union(position, getTopLeftHex(position));
            }
            if (getTopRightHex(position) != TOP_EDGE && getTopRightHex(position) != RIGHT_EDGE &&
            getHexColor(getTopRightHex(position)).equals(Color.BLUE)){
                disjointSet.union(position, getTopRightHex(position));
            }
            if (getLeftHex(position) != LEFT_EDGE && getHexColor(getLeftHex(position)).equals(Color.BLUE)){
                disjointSet.union(position, getLeftHex(position));
            }
            else if (getLeftHex(position) == LEFT_EDGE) {
                disjointSet.union(position, LEFT_EDGE);
            }
            if (getRightHex(position) != RIGHT_EDGE && getHexColor(getRightHex(position)).equals(Color.BLUE)){
                disjointSet.union(position, getRightHex(position));
            }
            else if (getRightHex(position) == RIGHT_EDGE){
                disjointSet.union(position, RIGHT_EDGE);
            }
            if (getBottomLeftHex(position) != BOTTOM_EDGE && getBottomLeftHex(position) != LEFT_EDGE &&
                    getHexColor(getBottomLeftHex(position)).equals(Color.BLUE)){
                disjointSet.union(position, getBottomLeftHex(position));
            }
            if (getBottomRightHex(position) != BOTTOM_EDGE && getHexColor(getBottomRightHex(position)).equals(Color.BLUE)){
                disjointSet.union(position, getBottomRightHex(position));
            }
        }
        return disjointSet.find(LEFT_EDGE) == disjointSet.find(RIGHT_EDGE);
    }
    // play red method, same as play blue but checks top and bottom edges and only red neighbors before union.
    public boolean playRed(int position, boolean displayNeighbors){
        if (position == 121){
            System.out.println();
        }
        if (displayNeighbors) {
            displayNeighbors(position);
        }
        if (position < 1 || position > rowCount*rowCount){
            System.out.println("Not a valid position");
            return false;
        }
        else if (isOccupied(position)){
            return false;
        }
        else {
            grid[position] = Color.RED;
            if (getTopLeftHex(position) != TOP_EDGE && getHexColor(getTopLeftHex(position)).equals(Color.RED)){
                disjointSet.union(position, getTopLeftHex(position));
            }
            else if (getTopLeftHex(position) == TOP_EDGE){
                disjointSet.union(position, TOP_EDGE);
            }
            if (getTopRightHex(position) != TOP_EDGE && getTopRightHex(position) != RIGHT_EDGE &&
                    getHexColor(getTopRightHex(position)).equals(Color.RED)){
                disjointSet.union(position, getTopRightHex(position));
            }
            else if (getTopRightHex(position) == TOP_EDGE){
                disjointSet.union(position, TOP_EDGE);
            }
            if (getLeftHex(position) != LEFT_EDGE && getHexColor(getLeftHex(position)).equals(Color.RED)){
                disjointSet.union(position, getLeftHex(position));
            }
            if (getRightHex(position) != RIGHT_EDGE && getHexColor(getRightHex(position)).equals(Color.RED)){
                disjointSet.union(position, getRightHex(position));
            }
            if (getBottomLeftHex(position) != BOTTOM_EDGE && getBottomLeftHex(position) != LEFT_EDGE &&
                    getHexColor(getBottomLeftHex(position)).equals(Color.RED)){
                disjointSet.union(position, getBottomLeftHex(position));
            }
            else if (getBottomLeftHex(position) == BOTTOM_EDGE){
                disjointSet.union(position, BOTTOM_EDGE);
            }
            if (getBottomRightHex(position) != BOTTOM_EDGE && getHexColor(getBottomRightHex(position)).equals(Color.RED)){
                disjointSet.union(position, getBottomRightHex(position));
            }
            else if (getBottomRightHex(position) == BOTTOM_EDGE){
                disjointSet.union(position, BOTTOM_EDGE);
            }
        }
        return disjointSet.find(TOP_EDGE) == disjointSet.find(BOTTOM_EDGE);
    }
    // checks if hex is occupied
    private boolean isOccupied(int hex){
        return grid[hex] != Color.NONE;
    }
    // gets the neighbors at one point even if are not in the same color group
    private void displayNeighbors(int position) {
        String message = "%d -> %d, %d, %d, %d, %d, %d\n";
        System.out.printf(message,position, getTopLeftHex(position), getTopRightHex(position), getLeftHex(position),
                getRightHex(position), getBottomLeftHex(position), getBottomRightHex(position));
    }
    // various get methods for the surrounding hexes based on row count and position
    private int getTopLeftHex(int position) {
        if (position - rowCount <= 0) {
            return TOP_EDGE;
        }
        else return position-rowCount;
    }
    private int getTopRightHex(int position){
            if (position - rowCount <= 0) {
                return TOP_EDGE;
            }
            else return position-rowCount+1;
    }
    private int getLeftHex(int position){
        if ((position-1) % rowCount == 0) {
            return LEFT_EDGE;
        }
        else return position-1;
    }
    private int getRightHex(int position){
        if ((position+1) % rowCount == 1) {
            return RIGHT_EDGE;
        }
        else return position+1;
    }
    private int getBottomLeftHex(int position) {
        if ((position + rowCount) > (rowCount*rowCount)) {
            return BOTTOM_EDGE;
        }
        else return position+rowCount-1;
    }
    private int getBottomRightHex(int position){
        if (position + rowCount > rowCount*rowCount) {
            return BOTTOM_EDGE;
        }
        else return position+rowCount;
    }

    public Color[] getGrid(){
        return grid;
    }
    public Color getHexColor(int position){
        return grid[position];
    }

    public static int getRowCount() {
        return rowCount;
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        int counter = 0;
        while(counter < rowCount){
            message.append("  ".repeat(Math.max(0, counter)));
            counter++;
        }
        return message.toString();
    }
}
