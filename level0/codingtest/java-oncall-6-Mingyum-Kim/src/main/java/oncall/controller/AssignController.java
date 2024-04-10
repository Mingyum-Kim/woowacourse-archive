package oncall.controller;

import java.util.function.Supplier;
import oncall.controller.dto.DateInfo;
import oncall.controller.dto.MonthAndStartDayOfWeek;
import oncall.controller.dto.WorkersInfo;
import oncall.controller.dto.WorkersName;
import oncall.domain.AssignManager;
import oncall.domain.Workers;
import oncall.domain.constants.CustomDayOfWeek;
import oncall.domain.constants.Month;
import oncall.view.InputView;
import oncall.view.OutputView;
import oncall.view.console.ConsoleWriter;

public class AssignController {
    private final InputView inputView;
    private final OutputView outputView;

    public AssignController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        DateInfo dateInfo = getDateInfo();
        WorkersInfo workersInfo = getWorkersInfo();

        AssignManager assignManager = new AssignManager(
                workersInfo.weekday(),
                workersInfo.weekend()
        );
        Workers result = assignManager.assign(
                dateInfo.month(),
                dateInfo.startDayOfWeek().toDayOfWeek()
        );
        outputView.printResult(dateInfo.month(), dateInfo.startDayOfWeek().toDayOfWeek(), result);
    }

    private DateInfo getDateInfo() {
        return retry(() -> {
            MonthAndStartDayOfWeek monthAndStartDayOfWeek = inputView.readMonthAndStartDayOfWeek();
            Month month = Month.from(monthAndStartDayOfWeek.month());
            CustomDayOfWeek dayOfWeek = CustomDayOfWeek.from(monthAndStartDayOfWeek.startDayOfWeek());
            return new DateInfo(month, dayOfWeek);
        });
    }

    private WorkersInfo getWorkersInfo() {
        return retry(() -> {
            WorkersName workersName = inputView.readWorkerInfo();
            return new WorkersInfo(
                    Workers.from(workersName.weekday()),
                    Workers.from(workersName.weekend())
            );
        });
    }

    private static <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                ConsoleWriter.printlnMessage(e.getMessage());
            }
        }
    }
}
