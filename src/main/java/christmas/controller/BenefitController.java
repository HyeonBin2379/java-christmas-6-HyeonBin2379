package christmas.controller;

import camp.nextstep.edu.missionutils.Console;
import christmas.model.event.EventBenefit;
import christmas.model.event.Giveaway;
import christmas.model.order.Order;
import christmas.model.order.OrderDate;
import christmas.view.OutputView;

public class BenefitController {

    private final OutputView outputView;
    private final OrderDate date;
    private final Order totalOrder;

    public BenefitController(OutputView outputView, OrderDate date, Order totalOrder) {
        this.outputView = outputView;
        this.date = date;
        this.totalOrder = totalOrder;
    }

    public void finish() {
        getTotalEventResults();
        Console.close();
    }

    private void getTotalEventResults() {
        int beforeDiscount = totalOrder.getTotalCost();
        Giveaway giveaway = new Giveaway(totalOrder);
        EventBenefit benefit = new EventBenefit(totalOrder);

        giveaway.addMenu("샴페인", 1);
        benefit.addBenefit(date.getDate(), giveaway);
        outputView.printAllEventsDetails(giveaway, benefit, beforeDiscount);
    }
}
