package christmas.controller;

import christmas.model.order.Order;
import christmas.model.order.OrderDate;
import christmas.view.InputView;
import christmas.view.OutputView;

public class OrderController {

    private final InputView inputView;
    private final OutputView outputView;
    private final OrderDate date;
    private final Order order;

    public OrderController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.date = new OrderDate();
        this.order = new Order();
    }

    public BenefitController start() {
        outputView.printStart();
        getValidDate();
        getValidOrder();
        outputView.printOrderDetail(date, order);
        return new BenefitController(outputView, date, order);
    }

    private void getValidDate() {
        while (true) {
            try {
                date.setValidDate(inputView.inputDate());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void getValidOrder() {
        while (true) {
            try {
                order.setValidOrderTable(inputView.inputMenuOrder());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                order.clearOrder();
            }
        }
    }
}
