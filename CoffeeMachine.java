package machine;

import java.util.Scanner;


public class CoffeeMachine {

    public enum TypeOfCoffee {

        ESPRESSO(250, 0, 16, 4),
        LATTE(350, 75, 20, 7),
        CAPPUCCINO(200, 100, 12, 6);

        final int water;
        final int milk;
        final int beans;
        final int cost;

        TypeOfCoffee(int water, int milk, int beans, int cost) {
            this.water = water;
            this.milk = milk;
            this.beans = beans;
            this.cost = cost;
        }

    }

    public enum StatesOfMachine {
        CHOOSING_ACTION, CHOOSING_COFFEE, CHOOSING_FILL_BEANS, CHOOSING_FILL_CUPS,
        CHOOSING_FILL_MILK, CHOOSING_FILL_WATER
    }


    private int water;
    private int milk;
    private int beans;
    private int money;
    private int cups;
    private StatesOfMachine state;

    CoffeeMachine() {
        water = 400;
        milk = 540;
        beans = 120;
        money = 550;
        cups = 9;
        state = StatesOfMachine.CHOOSING_ACTION;
        System.out.println("Write action (buy, fill, take, remaining, exit)");
    }


    public void action(String action) {
        switch (state) {
            case CHOOSING_ACTION: {
                executeMainActions(action);
                break;
            }
            case CHOOSING_COFFEE: {
                executeChoosingCoffee(action);
                break;
            }
            case CHOOSING_FILL_WATER: {
                water += Integer.parseInt(action);
                state = StatesOfMachine.CHOOSING_FILL_MILK;
                System.out.println("Write how many ml of milk do you want to add:");
                break;
            }
            case CHOOSING_FILL_MILK: {
                milk += Integer.parseInt(action);
                state = StatesOfMachine.CHOOSING_FILL_BEANS;
                System.out.println("Write how many grams of coffee beans do you want to add:");
                break;
            }
            case CHOOSING_FILL_BEANS: {
                beans += Integer.parseInt(action);
                state = StatesOfMachine.CHOOSING_FILL_CUPS;
                System.out.println("Write how many disposable cups of coffee do you want to add:");
                break;
            }
            case CHOOSING_FILL_CUPS: {
                cups += Integer.parseInt(action);
                state = StatesOfMachine.CHOOSING_ACTION;
                System.out.println("Write action (buy, fill, take, remaining, exit)");
                break;
            }
            default: {
                return;
            }
        }
    }

    private void executeChoosingCoffee(String action) {

        switch (action) {
            case "1": {
                buyCoffee(TypeOfCoffee.ESPRESSO);
                break;
            }
            case "2" : {
                buyCoffee(TypeOfCoffee.LATTE);
                break;
            }
            case "3" : {
                buyCoffee(TypeOfCoffee.CAPPUCCINO);
                break;
            }
            default: {
                state = StatesOfMachine.CHOOSING_ACTION;
                break;
            }

        }
        System.out.println("Write action (buy, fill, take, remaining, exit)");
    }

    private void buyCoffee(TypeOfCoffee type) {
        boolean enoughResourses = true;
        if (water - type.water < 0) {
            System.out.println("Sorry, not enough water!");
            enoughResourses = false;
        }
        if (milk - type.milk < 0) {
            System.out.println("Sorry, not enough milk!");
            enoughResourses = false;
        }
        if (beans - type.beans < 0) {
            System.out.println("Sorry, not enough beans!");
            enoughResourses = false;
        }
        if (cups - 1 < 0) {
            System.out.println("Sorry, not enough cups!");
            enoughResourses = false;
        }

        if (!enoughResourses) {
            state = StatesOfMachine.CHOOSING_ACTION;
            return;
        }

        water -= type.water;
        milk -= type.milk;
        beans -= type.beans;
        money += type.cost;
        cups--;
        state = StatesOfMachine.CHOOSING_ACTION;
    }


    private void executeMainActions(String action) {
        switch (action) {
            case "remaining": {
                System.out.println("The coffee machine has:");
                System.out.println(water + " of water");
                System.out.println(milk + " of milk");
                System.out.println(beans + " of coffee beans");
                System.out.println(cups + " of disposable cups");
                System.out.println(money + " of money");
                state = StatesOfMachine.CHOOSING_ACTION;
                System.out.println("Write action (buy, fill, take, remaining, exit)");
                break;
            }
            case "buy": {
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                state = StatesOfMachine.CHOOSING_COFFEE;
                break;
            }
            case "fill": {
                System.out.println("Write how many ml of water do you want to add:");
                state = StatesOfMachine.CHOOSING_FILL_WATER;
                break;
            }
            case "take": {
                System.out.println("I gave you $" + money);
                money = 0;
                state = StatesOfMachine.CHOOSING_ACTION;
                System.out.println("Write action (buy, fill, take, remaining, exit)");
                break;
            }
            default: {
                return;
            }
        }
    }




    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine();


        while (true) {

            String action = scanner.nextLine();
            if (action.compareTo("exit") == 0) {
                return;
            }
            coffeeMachine.action(action);


        }
    }
}


