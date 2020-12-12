package aoc.common;

public enum Rotation {
    LEFT("L"),
    RIGHT("R"),
    FORWARD("F");

    String name;

    Rotation(String name) {
        this.name = name;
    }

    public static Rotation fromName(String val) {
        for(Rotation op : Rotation.values()) {
            if(op.name.equals(val.substring(0,1))) {
                return op;
            }
        }
        return null;
    }

    public Direction newDirection(Direction currentDirection, int angle) {
        switch (angle) {
            case 0 :
                return currentDirection;
            case 90 :
                switch (currentDirection) {
                    case NORTH:
                        return this.equals(Rotation.RIGHT) ? Direction.EAST : Direction.WEST;
                    case EAST:
                        return this.equals(Rotation.RIGHT) ? Direction.SOUTH : Direction.NORTH;
                    case SOUTH:
                        return this.equals(Rotation.RIGHT) ? Direction.WEST : Direction.EAST;
                    case WEST:
                        return this.equals(Rotation.RIGHT) ? Direction.NORTH : Direction.SOUTH;
                }
            case 180 :
                return currentDirection.opposite();
            case 270 :
                switch (currentDirection) {
                    case NORTH:
                        return this.equals(Rotation.RIGHT) ? Direction.WEST : Direction.EAST;
                    case EAST:
                        return this.equals(Rotation.RIGHT) ? Direction.NORTH : Direction.SOUTH;
                    case SOUTH:
                        return this.equals(Rotation.RIGHT) ? Direction.EAST : Direction.EAST;
                    case WEST:
                        return this.equals(Rotation.RIGHT) ? Direction.SOUTH : Direction.NORTH;
                }
            default:
                throw new IllegalArgumentException("Bad angle : "+angle);
        }
    }

}
