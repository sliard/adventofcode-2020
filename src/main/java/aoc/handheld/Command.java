package aoc.handheld;

public enum Command {
    JUMP("jmp"),
    ACCUMULATOR("acc"),
    NOP("nop");

    String name;

    Command(String name) {
        this.name = name;
    }

    public static Command getFromName(String name) {
        for(Command op : Command.values()) {
            if(op.name.equals(name)) {
                return op;
            }
        }
        throw new IllegalArgumentException("Operation : bad name "+name);
    }
}
