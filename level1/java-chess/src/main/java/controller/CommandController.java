package controller;

import view.OutputView;

@FunctionalInterface
public interface CommandController {
    void execute(OutputView outputView);
}
