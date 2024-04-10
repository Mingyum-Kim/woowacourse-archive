package oncall.domain;

import java.time.DayOfWeek;
import oncall.domain.constants.CustomDayOfWeek;
import oncall.domain.constants.Holiday;
import oncall.domain.constants.Month;

public class AssignManager {
    private Workers weekday;
    private Workers weekend;

    public AssignManager(Workers weekday, Workers weekend) {
        this.weekday = weekday;
        this.weekend = weekend;
    }

    /**
     * 모든 날짜를 순회하며 평일, 휴일 순번의 비상 근무표를 배정하는 메서드
     *
     * @return 1일부터 마지막 날까지 근무를 담당하는 근무자들의 목록
     */
    public Workers assign(Month month, DayOfWeek startDayOfWeek) {
        Workers result = new Workers();
        for (int day = 1; day <= month.getDays(); day++) {
            DayOfWeek dayOfWeek = getCurrentDayOfWeek(startDayOfWeek, day);
            if (isWeekday(month, day, dayOfWeek)) {
                add(result, weekday);
                continue;
            }
            add(result, weekend);
        }
        return result;
    }

    private DayOfWeek getCurrentDayOfWeek(DayOfWeek startDayOfWeek, int day) {
        return startDayOfWeek.plus(day - 1);
    }

    private void add(Workers result, Workers workers) {
        if (result.isEmpty()) {
            addFirstWorker(result, workers);
            return;
        }
        if (isEqualsWithLastWorker(result, workers)) {
            Worker holding = workers.popFront();
            addFirstWorker(result, workers);
            workers.addFront(holding);
            return;
        }
        addFirstWorker(result, workers);
    }

    private void addFirstWorker(Workers result, Workers workers) {
        Worker worker = workers.popFront();
        result.addBack(worker);
        workers.addBack(worker);
    }

    private boolean isEqualsWithLastWorker(Workers result, Workers workers) {
        return result.getBack().equals(workers.getFront());
    }

    /**
     * 해당 날짜가 평일인지 확인하는 메서드
     * </p>
     * 주말이거나 법정 공휴일이라면 false, 그 외에는 true를 반환한다.
     */
    private boolean isWeekday(Month month, int day, DayOfWeek dayOfWeek) {
        if (Holiday.isHoliday(month, day) || CustomDayOfWeek.isWeekend(dayOfWeek)) {
            return false;
        }
        return true;
    }
}


