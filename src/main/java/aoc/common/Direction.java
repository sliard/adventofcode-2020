package aoc.common;

public enum Direction {
    NORTH("N",  0, -1),
    NORTH_EAST("NE",  1, -1),
    EAST("E", 1, 0),
    SOUTH_EAST("SE", 1, 1),
    SOUTH("S",  0, 1),
    SOUTH_WEST("SW",  -1, 1),
    WEST("W",  -1, 0),
    NORTH_WEST("NW",  -1, -1);

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
        return null;
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
        throw new IllegalArgumentException("Bad angle for opposite");
    }

}
