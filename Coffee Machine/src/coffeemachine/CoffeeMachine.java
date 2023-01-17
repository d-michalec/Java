package coffeemachine;

import java.util.Scanner;


    public class CoffeeMachine {
        static Resources resources = new Resources(400, 540, 120, 9, 550);
        static boolean isFinished = false;

        public static void main(String[] args) {
            while(!isFinished) {
                isFinished = executeCommand(resources);
            }
        }

        private static boolean executeCommand(Resources resources) {
            Scanner sc = new Scanner(System.in);
            String input;
            System.out.println();
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            input = sc.nextLine();

            if (input.equals("exit")) {
                return true;
            }else {
                switch (input) {
                    case "buy" -> buy(resources);
                    case "fill" -> fill(resources);
                    case "take" -> take(resources);
                    case "remaining" -> printResources(resources);
                }
            }
            return false;
        }

        private static void take(Resources resources) {
            System.out.println("I gave you $" + resources.getMoney());
            resources.setMoney(0);
        }

        private static void fill(Resources resources) {
            Scanner sc = new Scanner(System.in);
            String input;

            System.out.println("Write how many ml of water you want to add: ");
            input = sc.nextLine();
            resources.setWaterMl(resources.getWaterMl() + Integer.parseInt(input));

            System.out.println("Write how many ml of milk you want to add: ");
            input = sc.nextLine();
            resources.setMilkMl(resources.getMilkMl() + Integer.parseInt(input));

            System.out.println("Write how many grams of coffee beans you want to add: ");
            input = sc.nextLine();
            resources.setCoffeeBeans(resources.getCoffeeBeans() + Integer.parseInt(input));

            System.out.println("Write how many disposable cups you want to add: ");
            input = sc.nextLine();
            resources.setCups(resources.getCups() + Integer.parseInt(input));
        }

        private static void buy(Resources resources) {
            Scanner sc = new Scanner(System.in);
            boolean enoughRes;
            String input;
            System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
            input = sc.nextLine();
            if(!input.equals("back")) {
                switch (input) {
                    case "1" -> {
                        enoughRes = calculate(input, resources);
                        if(enoughRes) {
                            resources.setWaterMl(resources.getWaterMl() - 250);
                            resources.setCoffeeBeans(resources.getCoffeeBeans() - 16);
                            resources.setMoney(resources.getMoney() + 4);
                            resources.setCups(resources.getCups() - 1);
                        }
                    }
                    case "2" -> {
                        enoughRes = calculate(input, resources);
                        if(enoughRes) {
                            resources.setWaterMl(resources.getWaterMl() - 350);
                            resources.setMilkMl(resources.getMilkMl() - 75);
                            resources.setCoffeeBeans(resources.getCoffeeBeans() - 20);
                            resources.setMoney(resources.getMoney() + 7);
                            resources.setCups(resources.getCups() - 1);
                        }
                    }
                    case "3" -> {
                        enoughRes = calculate(input, resources);
                        if(enoughRes) {
                            calculate(input, resources);
                            resources.setWaterMl(resources.getWaterMl() - 200);
                            resources.setMilkMl(resources.getMilkMl() - 100);
                            resources.setCoffeeBeans(resources.getCoffeeBeans() - 12);
                            resources.setMoney(resources.getMoney() + 6);
                            resources.setCups(resources.getCups() - 1);
                        }
                    }
                }
            }
        }


        private static void printResources(Resources resources){
            System.out.println("The coffee machine has:");
            System.out.println(resources.getWaterMl() + " ml of water");
            System.out.println(resources.getMilkMl() + " ml of milk");
            System.out.println(resources.getCoffeeBeans() + " g of coffee beans");
            System.out.println(resources.getCups() + " disposable cups");
            System.out.println("$" + resources.getMoney() + " of money");
        }

        private static boolean calculate(String input, Resources resources) {
            int water = 0, milk = 0, coffeeBeans = 0, cupsNum = resources.getCups();

            if(cupsNum > 0) {

                //resources needed
                if (input.equals("1")) {
                    water = 250;
                    coffeeBeans = 16;
                }

                if (input.equals("2")) {
                    water = 350;
                    milk = 75;
                    coffeeBeans = 20;
                }

                if (input.equals("3")) {
                    water = 200;
                    milk = 100;
                    coffeeBeans = 12;
                }
                if(water > resources.getWaterMl()) {
                    System.out.println("Sorry, not enough water!");
                    return false;
                }
                if(milk > resources.getMilkMl()) {
                    System.out.println("Sorry, not enough milk!");
                    return false;
                }
                if(coffeeBeans > resources.getCoffeeBeans()) {
                    System.out.println("Sorry, not enough coffee beans!");
                    return false;
                }

                System.out.println("I have enough resources, making you a coffee!");
                return true;
            }else{
                System.out.println("Sorry not enough cups!");
            }
            return false;
        }
    }
