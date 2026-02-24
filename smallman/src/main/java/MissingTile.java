public class MissingTile {

    public static void tileGrid(Grid board) {
        // Kick off the recursion using the grid's dimensions and painted cell
        tilingHelper(board.size(), 0, 0, board.getPaintedCellX(), board.getPaintedCellY(), board);
    }

    /**
     * A recursive helper method to tile a subgrid.
     * * @param size The current width/height of the subgrid (a power of 2)
     * @param topX The x-coordinate of the top-left corner of the subgrid
     * @param topY The y-coordinate of the top-left corner of the subgrid
     * @param paintedX The x-coordinate of the painted/occupied cell in this subgrid
     * @param paintedY The y-coordinate of the painted/occupied cell in this subgrid
     * @param board The main Grid reference
     */
    private static void tilingHelper(int size, int topX, int topY, int paintedX, int paintedY, Grid board) {
        // Base Case: A 1x1 grid is fully "tiled" by its one painted cell
        if (size == 1) {
            return;
        }

        int half = size / 2;
        int cx = topX + half; // Center X of the current subgrid
        int cy = topY + half; // Center Y of the current subgrid

        // Calculate the central coordinates for the 4 interior corners 
        // that touch the exact center of the current subgrid.
        int nwCenterX = cx - 1, nwCenterY = cy - 1;
        int neCenterX = cx,     neCenterY = cy - 1;
        int swCenterX = cx - 1, swCenterY = cy;
        int seCenterX = cx,     seCenterY = cy;

        // Determine which quadrant contains the painted cell, place the central 
        // tile to fill the *other* three quadrants, and then recursively tile all four.
        
        if (paintedX < cx && paintedY < cy) {
            // Painted cell is in the North-West quadrant. 
            // Place central tile missing the NW corner (Orientation 3)
            board.setTile(nwCenterX, nwCenterY, 3);
            
            tilingHelper(half, topX, topY, paintedX, paintedY, board); // Recursively tile NW
            tilingHelper(half, cx, topY, neCenterX, neCenterY, board); // Recursively tile NE
            tilingHelper(half, topX, cy, swCenterX, swCenterY, board); // Recursively tile SW
            tilingHelper(half, cx, cy, seCenterX, seCenterY, board);   // Recursively tile SE
            
        } else if (paintedX >= cx && paintedY < cy) {
            // Painted cell is in the North-East quadrant.
            // Place central tile missing the NE corner (Orientation 0)
            board.setTile(neCenterX, neCenterY, 0);
            
            tilingHelper(half, topX, topY, nwCenterX, nwCenterY, board);
            tilingHelper(half, cx, topY, paintedX, paintedY, board);     
            tilingHelper(half, topX, cy, swCenterX, swCenterY, board);   
            tilingHelper(half, cx, cy, seCenterX, seCenterY, board);     
            
        } else if (paintedX < cx && paintedY >= cy) {
            // Painted cell is in the South-West quadrant.
            // Place central tile missing the SW corner (Orientation 2)
            board.setTile(swCenterX, swCenterY, 2);
            
            tilingHelper(half, topX, topY, nwCenterX, nwCenterY, board); 
            tilingHelper(half, cx, topY, neCenterX, neCenterY, board);   
            tilingHelper(half, topX, cy, paintedX, paintedY, board);     
            tilingHelper(half, cx, cy, seCenterX, seCenterY, board);     
            
        } else {
            // Painted cell is in the South-East quadrant.
            // Place central tile missing the SE corner (Orientation 1)
            board.setTile(seCenterX, seCenterY, 1);
            
            tilingHelper(half, topX, topY, nwCenterX, nwCenterY, board); 
            tilingHelper(half, cx, topY, neCenterX, neCenterY, board);   
            tilingHelper(half, topX, cy, swCenterX, swCenterY, board);   
            tilingHelper(half, cx, cy, paintedX, paintedY, board);       
        }
    }
}