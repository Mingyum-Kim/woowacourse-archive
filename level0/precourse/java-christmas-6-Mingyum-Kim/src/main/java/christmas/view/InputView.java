package christmas.view;

import christmas.controller.dto.request.OrderRequest;
import christmas.controller.dto.request.OrdersRequest;
import christmas.global.exception.CustomException;
import christmas.global.exception.ErrorMessage;
import christmas.view.console.ConsoleReader;
import christmas.view.console.ConsoleWriter;
import java.util.Arrays;
import java.util.List;

public class InputView {
    private static final String DATE_REQUEST_MESSAGE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String ORDERS_REQUEST_MESSAGE = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    private static final String ORDERS_SEPARATOR = ",";
    private static final String ORDER_SEPARATOR = "-";

    public int requestDate() {
        ConsoleWriter.printlnMessage(DATE_REQUEST_MESSAGE);
        String date = ConsoleReader.enterMessage();
        return Validator.validateNumber(
                date,
                ErrorMessage.INVALID_DATE_ERROR
        );
    }

    public OrdersRequest requestOrders() {
        ConsoleWriter.printlnMessage(ORDERS_REQUEST_MESSAGE);
        String orders = ConsoleReader.enterMessage();
        return Validator.validateOrders(orders);
    }

    private static class Validator {
        private static int validateNumber(String message, ErrorMessage errorMessage) {
            try {
                return Integer.parseInt(message);
            } catch (NumberFormatException e) {
                throw CustomException.from(errorMessage);
            }
        }

        private static OrdersRequest validateOrders(String message) {
            List<String> orders = validateOrdersSeparators(message, ORDERS_SEPARATOR);
            return new OrdersRequest(parseStringToOrderRequest(orders));
        }

        private static List<OrderRequest> parseStringToOrderRequest(List<String> orders) {
            return orders.stream()
                    .map(order -> validateOrderSeparator(order, ORDER_SEPARATOR))
                    .toList();
        }

        private static List<String> validateOrdersSeparators(String message, String separator) {
            validateEdgeSeparators(message, separator);
            validateDuplicatedSeparators(message, separator);
            return parseStringToList(message, separator);
        }

        private static void validateEdgeSeparators(String message, String separator) {
            if (hasEdgeSeparators(message, separator)) {
                throw CustomException.from(ErrorMessage.INVALID_ORDER_ERROR);
            }
        }

        private static boolean hasEdgeSeparators(String message, String separator) {
            return message.startsWith(separator) || message.endsWith(separator);
        }

        private static void validateDuplicatedSeparators(String message, String separator) {
            if (hasDuplicatedSeparators(message, separator)) {
                throw CustomException.from(ErrorMessage.INVALID_ORDER_ERROR);
            }
        }

        private static boolean hasDuplicatedSeparators(String message, String separator) {
            return message.contains(separator.repeat(2));
        }

        private static List<String> parseStringToList(String message, String separator) {
            return Arrays.stream(split(message, separator)).toList();
        }

        private static String[] split(String message, String separator) {
            return message.split(separator);
        }

        private static OrderRequest validateOrderSeparator(String message, String separator) {
            validateEdgeSeparators(message, separator);
            validateDuplicatedSeparators(message, separator);
            List<String> order = parseStringToList(message, ORDER_SEPARATOR);
            validateSeparatorCount(order, separator);
            return new OrderRequest(
                    order.get(0),
                    validateNumber(order.get(1), ErrorMessage.INVALID_ORDER_ERROR)
            );
        }

        private static void validateSeparatorCount(List<String> message, String separator) {
            if (isInvalidSeparatorCount(message)) {
                throw CustomException.from(ErrorMessage.INVALID_ORDER_ERROR);
            }
        }

        private static boolean isInvalidSeparatorCount(List<String> message) {
            return message.size() != 2;
        }
    }
}
