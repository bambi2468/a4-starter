// The top-left corner is coordinate x=0 and y=0. The bottom-right corner is
// coordinate x=size()-1 and y=size()-1.
public interface Grid {
  
  // return the width/height of the square grid. coordinates in the grid are
  // numbers between 0 and size()-1, inclusive.
  int size();
  
  // return the x coordinate of the painted cell
  int getPaintedCellX();
  
  // return the y coordinate of the painted cell
  int getPaintedCellY();
  
  // install a triomino tile so that the missing corner is at coordinate (x, y)
  // and the orientation of the tile is given by the orientation parameter,
  // encoded as follows: missing NE = 0, missing SE = 1, missing SW = 2, and
  // missing NW = 3. The function returns true if the tiling is possible; it
  // returns false if the tiling failed (e.g., overlap with an existing tile or
  // touches the painted cell.
  boolean setTile(int x, int y, int orientation);
  
  
  // return a boolean value indicating whether the grid has been fully tiled
  boolean isFullyTiled();
}
