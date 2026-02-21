public class MissingTile {
  public static void tileGrid(Grid board) {
    tilingHelper(board.size(), 0, 0, board.getPaintedCellX(), board.getPaintedCellY(), board);
  }

  // Recursively tile a size x size subgrid whose top-left corner is (topX, topY).
  // The painted (already-occupied) cell within this subgrid is at (paintedX, paintedY).
  static void tilingHelper(int size, int topX, int topY, int paintedX, int paintedY, Grid bd) {
    // Base case: a 1x1 grid has only the painted cell, nothing to tile.
    if (size == 1) return;

    int half = size / 2;
    int midX = topX + half; // x-coordinate where the right half starts
    int midY = topY + half; // y-coordinate where the bottom half starts

    // Determine which quadrant contains the painted cell.
    // Quadrant layout (using compass-style naming matching the orientation encoding):
    //   NW (top-left)  | NE (top-right)
    //   ----------------+-----------------
    //   SW (bottom-left)| SE (bottom-right)

    // For each quadrant, we need a "painted cell". For the quadrant containing the
    // actual painted cell, it keeps its painted cell. For the other three quadrants,
    // we will place a triomino at the center to create a virtual painted cell in each.

    // The center triomino's missing corner points toward the quadrant that already
    // has a painted cell.

    // Coordinates of the cell closest to the center in each quadrant:
    int nwX = midX - 1, nwY = midY - 1; // bottom-right cell of NW quadrant
    int neX = midX,     neY = midY - 1; // bottom-left cell of NE quadrant
    int swX = midX - 1, swY = midY;     // top-right cell of SW quadrant
    int seX = midX,     seY = midY;     // top-left cell of SE quadrant

    // Determine which quadrant the painted cell is in
    boolean paintedInNW = (paintedX < midX && paintedY < midY);
    boolean paintedInNE = (paintedX >= midX && paintedY < midY);
    boolean paintedInSW = (paintedX < midX && paintedY >= midY);
    // paintedInSE is the remaining case

    // Place a triomino at the center. The missing corner faces the quadrant
    // that already has a painted cell.
    // Orientation encoding: missing NE = 0, missing SE = 1, missing SW = 2, missing NW = 3
    if (paintedInNW) {
      // Missing corner = NW (orientation 3), placed at center with missing corner at (nwX, nwY)
      bd.setTile(nwX, nwY, 3);
    } else if (paintedInNE) {
      // Missing corner = NE (orientation 0), placed at center with missing corner at (neX, neY)
      bd.setTile(neX, neY, 0);
    } else if (paintedInSW) {
      // Missing corner = SW (orientation 2), placed at center with missing corner at (swX, swY)
      bd.setTile(swX, swY, 2);
    } else {
      // Painted in SE: Missing corner = SE (orientation 1), placed at center with missing corner at (seX, seY)
      bd.setTile(seX, seY, 1);
    }

    // Now determine the "painted cell" for each quadrant's recursive call.
    // For the quadrant that already has the painted cell, use the actual painted cell.
    // For the other three quadrants, the triomino just placed covers one of their center cells,
    // making that cell the virtual "painted cell" for recursion.

    int pNW_X, pNW_Y, pNE_X, pNE_Y, pSW_X, pSW_Y, pSE_X, pSE_Y;

    if (paintedInNW) {
      pNW_X = paintedX; pNW_Y = paintedY;
      pNE_X = neX;      pNE_Y = neY;
      pSW_X = swX;      pSW_Y = swY;
      pSE_X = seX;      pSE_Y = seY;
    } else if (paintedInNE) {
      pNW_X = nwX;      pNW_Y = nwY;
      pNE_X = paintedX; pNE_Y = paintedY;
      pSW_X = swX;      pSW_Y = swY;
      pSE_X = seX;      pSE_Y = seY;
    } else if (paintedInSW) {
      pNW_X = nwX;      pNW_Y = nwY;
      pNE_X = neX;      pNE_Y = neY;
      pSW_X = paintedX; pSW_Y = paintedY;
      pSE_X = seX;      pSE_Y = seY;
    } else {
      pNW_X = nwX;      pNW_Y = nwY;
      pNE_X = neX;      pNE_Y = neY;
      pSW_X = swX;      pSW_Y = swY;
      pSE_X = paintedX; pSE_Y = paintedY;
    }

    // Recurse on each quadrant
    tilingHelper(half, topX, topY,      pNW_X, pNW_Y, bd); // NW quadrant
    tilingHelper(half, midX, topY,      pNE_X, pNE_Y, bd); // NE quadrant
    tilingHelper(half, topX, midY,      pSW_X, pSW_Y, bd); // SW quadrant
    tilingHelper(half, midX, midY,      pSE_X, pSE_Y, bd); // SE quadrant
  }
}
