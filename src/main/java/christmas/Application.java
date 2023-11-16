package christmas;

import christmas.controller.DecemberEventController;

public class Application {

    public static void main(String[] args) {
        DecemberEventController restaurant = new DecemberEventController();
        restaurant.run();
    }
}
