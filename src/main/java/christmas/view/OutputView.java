package christmas.view;

import static christmas.constants.message.IOMessageConstants.NOTHING;
import static christmas.constants.message.IOMessageConstants.START_MESSAGE;
import static christmas.constants.message.MessageFormat.BENEFIT_FORMAT;
import static christmas.constants.message.MessageFormat.MENU_FORMAT;
import static christmas.constants.message.MessageFormat.MONEY_FORMAT;
import static christmas.constants.message.MessageFormat.PREVIEW_TITLE_FORMAT;
import static christmas.constants.message.Title.AFTER_DISCOUNT;
import static christmas.constants.message.Title.BEFORE_DISCOUNT;
import static christmas.constants.message.Title.BENEFIT_DETAIL;
import static christmas.constants.message.Title.EVENT_BADGE;
import static christmas.constants.message.Title.GIVEAWAY;
import static christmas.constants.message.Title.MENU;
import static christmas.constants.message.Title.TOTAL_BENEFIT;
import static christmas.constants.others.MarksAndConstants.MONTH;

import christmas.model.event.EventBadge;
import christmas.model.event.EventBenefit;
import christmas.model.event.EventName;
import christmas.model.event.Giveaway;
import christmas.model.menu.Menu;
import christmas.model.order.Order;
import christmas.model.order.OrderDate;
import java.util.Map;

public class OutputView {

    public void printStart() {
        System.out.printf(START_MESSAGE, MONTH);
    }

    public void printOrderDetail(OrderDate date, Order order) {
        printPreview(date.getDate());
        printMenu(order.getOrder());
        printBeforeDiscount(order.getTotalCost());
    }

    private void printPreview(int date) {
        System.out.printf(PREVIEW_TITLE_FORMAT, MONTH, date);
    }

    private void printMenu(Map<Menu, Integer> totalOrder) {
        System.out.println(MENU.getTitle());
        for (Menu menu : totalOrder.keySet()) {
            System.out.printf(MENU_FORMAT, menu.getName(), totalOrder.get(menu));
        }
    }

    private void printBeforeDiscount(int totalCost) {
        System.out.println(BEFORE_DISCOUNT.getTitle());
        System.out.printf(MONEY_FORMAT, totalCost);
    }

    public void printAllEventsDetails(Giveaway giveaway, EventBenefit benefit, int beforeDiscount) {
        int totalBenefit = benefit.getTotalBenefit();
        printGiveaway(giveaway.getGiveaway());
        printBenefitDetails(benefit.getEventTable(), totalBenefit);
        printTotalBenefit(totalBenefit);
        printAfterDiscount(benefit.getAfterDiscounts(beforeDiscount, giveaway));
        printEventBadge(totalBenefit);
    }

    private void printGiveaway(Map<Menu, Integer> giveAwayMenu) {
        System.out.println(GIVEAWAY.getTitle());
        if (giveAwayMenu.isEmpty()) {
            System.out.println(NOTHING);
            return;
        }
        printAnyGiveawayMenu(giveAwayMenu);
    }

    private void printAnyGiveawayMenu(Map<Menu, Integer> giveAwayMenu) {
        for (Menu menu : giveAwayMenu.keySet()) {
            System.out.printf(MENU_FORMAT, menu.getName(), giveAwayMenu.get(menu));
        }
    }

    private void printBenefitDetails(Map<EventName, Integer> totalEvent, int totalBenefit) {
        System.out.println(BENEFIT_DETAIL.getTitle());
        if (totalEvent.isEmpty() || totalBenefit == 0) {
            System.out.println(NOTHING);
            return;
        }
        printAnyBenefitDetails(totalEvent);
    }

    private void printAnyBenefitDetails(Map<EventName, Integer> totalEvent) {
        for (EventName eventName : totalEvent.keySet()) {
            String money = String.format(MONEY_FORMAT, totalEvent.get(eventName));
            System.out.printf(BENEFIT_FORMAT, eventName.getEventName(), money);
        }
    }

    private void printTotalBenefit(int totalBenefit) {
        System.out.println(TOTAL_BENEFIT.getTitle());
        System.out.printf(MONEY_FORMAT, totalBenefit);
    }

    private void printAfterDiscount(int totalCost) {
        System.out.println(AFTER_DISCOUNT.getTitle());
        System.out.printf(MONEY_FORMAT, totalCost);
    }

    private void printEventBadge(int totalBenefit) {
        System.out.println(EVENT_BADGE.getTitle());
        EventBadge badge = EventBadge.getBadge(totalBenefit);
        System.out.println(badge.getName());
    }
}