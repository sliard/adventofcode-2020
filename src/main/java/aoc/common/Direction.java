package aoc.common;

public enum Direction {
    NORTH("U",  0, -1),
    NORTH_EAST("UR",  1, -1),
    EAST("R", 1, 0),
    SOUTH_EAST("DR", 1, 1),
    SOUTH("D",  0, 1),
    SOUTH_WEST("DL",  -1, 1),
    WEST("L",  -1, 0),
    NORTH_WEST("UL",  -1, -1);

    String val;
    public int deltaX;
    public int deltaY;

    Direction(String val, int dx, int dy) {
        this.val = val;
        this.deltaX = dx;
        this.deltaY = dy;
    }

    public static Direction fromVal(String val) {
        for(Direction op : Direction.values()) {
            if(op.val.equals(val.substring(0,1))) {
                return op;
            }
        }
        return NORTH;
    }

    public Direction opposite() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            case SOUTH:
                return NORTH;
        }
        return NORTH;
    }

}
