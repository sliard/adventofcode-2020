package aoc.handheld;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class Instruction {
    @NonNull
    public Command command;

    @NonNull
    public int val;

    public boolean visited = false;
}
