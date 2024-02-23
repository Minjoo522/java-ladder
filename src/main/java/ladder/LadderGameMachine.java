package ladder;

import java.util.List;
import java.util.function.Supplier;
import ladder.domain.LadderHeight;
import ladder.domain.Lines;
import ladder.domain.UserNames;
import ladder.dto.LadderResult;
import ladder.dto.LineResult;
import ladder.util.ConsoleReader;
import ladder.util.RandomBooleanGenerator;
import ladder.view.InputView;
import ladder.view.OutputView;

public class LadderGameMachine {
    private static final ConsoleReader CONSOLE = new ConsoleReader();

    public void run() {
        UserNames userNames = initNames();
        LadderHeight ladderHeight = initLadderHeight();
        LadderResult ladderResult = createLadderResult(
                new RandomBooleanGenerator(),
                ladderHeight.value(),
                userNames);
        OutputView.printLadderResult(ladderResult);
    }

    private UserNames initNames() {
        try {
            List<String> input = InputView.readNames(CONSOLE);
            return UserNames.from(input);
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
            return initNames();
        }
    }

    private LadderHeight initLadderHeight() {
        try {
            int input = InputView.readLadderHeight(CONSOLE);
            return new LadderHeight(input);
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
            return initLadderHeight();
        }
    }

    private LadderResult createLadderResult(
            final Supplier<Boolean> generator,
            final int ladderHeight,
            final UserNames userNames) {
        Lines lines = Lines.of(generator, ladderHeight, userNames.getUserCount());
        List<LineResult> lineResults = lines.getLines().stream()
                .map(line -> new LineResult(line.getLine()))
                .toList();
        return new LadderResult(userNames.getUserNames(), lineResults);
    }
}