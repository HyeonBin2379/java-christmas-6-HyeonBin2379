package christmas.controller;

import christmas.view.InputView;
import christmas.view.OutputView;

public class DecemberEventController {

    private final InputView inputView;
    private final OutputView outputView;

    public DecemberEventController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void run() {
        OrderController ordering = new OrderController(inputView, outputView);
        BenefitController calculating = ordering.start();
        calculating.finish();
    }
}