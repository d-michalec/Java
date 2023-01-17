package simplebankingsystem;

import java.util.Random;
import java.util.Scanner;
import simplebankingsystem.User;

    public class Main {
        enum Status {
            EXIT, LOG, NOT_LOG
        }

        static Status status = Status.NOT_LOG;
        static User user = new User();
        static App app = new App();

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);

            createNewTable();
            while (status != Status.EXIT) {
                if (status == Status.NOT_LOG)
                    displayMainMenu();

                if (status == Status.LOG)
                    displayLogMenu();

                runProgram(sc.nextLine());
            }
            System.out.println("Bye!");
        }


        private static void runProgram(String nextLine) {
            if (nextLine.equals("0"))
                status = Status.EXIT;
            else {
                if (status == Status.NOT_LOG) {
                    switch (nextLine) {
                        case "1" -> createAccount();
                        case "2" -> logIn();
                    }
                } else {
                    switch (nextLine) {
                        case "1" -> displayBalance();
                        case "2" -> addIncome();
                        case "3" -> transferMoney();
                        case "4" -> closeAccount();
                        case "5" -> logOut();
                    }
                }
            }
        }

        public static void addIncome(){
            app.addIncome(user);
        }
        public static void closeAccount(){
            app.closeAccount(user);
        }
        private static void createNewTable() {
            app.createNewTable();
        }
        private static void transferMoney() {
            Scanner sc = new Scanner(System.in);
            String cardNumber;
            System.out.println("Transfer");
            System.out.println("Enter card number: ");
            cardNumber = sc.next();

            if(correctLastNumber(cardNumber)) {
                if(app.checkCardBalance(cardNumber) != -1) {
                    if(!user.getCardNumber().equals(cardNumber)){
                        System.out.println("Enter how much money you want to transfer: ");
                        app.handleTransfer(user, cardNumber);
                    }else{
                        System.out.println("You can't transfer money to the same account.");
                    }
                }else{
                    System.out.println("Such a card does not exist.");
                }
            }else{
                System.out.println("Probably you made a mistake in the card number. Please try again!");
            }
        }
        private static boolean correctLastNumber(String cardNumber) {
            String numberCheck = cardNumber.substring(0,cardNumber.length() - 1);
            if(cardNumber.equals(addChecksum(new StringBuilder(numberCheck)))){
                return true;
            }else{
                return false;
            }
        }

        private static void logOut() {
            System.out.println("You have successfully logged out!");
            status = Status.NOT_LOG;
        }

        private static void displayBalance() {
            System.out.println("Balance " + user.getBalance());
        }

        private static void displayLogMenu() {
            System.out.println("1. Balance");
            System.out.println("2. Add income");
            System.out.println("3. Do transfer");
            System.out.println("4. Close account");
            System.out.println("5. Log out");
            System.out.println("0. Exit");
        }

        private static void logIn() {
            Scanner sc = new Scanner(System.in);
            String cardNumber, pin;

            System.out.println("Enter your card number");
            cardNumber = sc.nextLine();

            System.out.println("Enter your PIN:");
            pin = sc.nextLine();

            if (app.validation(user, cardNumber, pin)) {
                status = Status.LOG;
                System.out.println("You have successfully logged in!");

            } else {
                System.out.println("Wrong card number of PIN!");
            }
        }
        private static void createAccount() {
            user.setBalance(0);
            user.setCardNumber(createCard());
            System.out.println("Your card has been created");
            System.out.println("Your card number:");
            System.out.println(user.getCardNumber());

            user.setPin(createPin());
            System.out.println("Your card PIN:");
            System.out.println(user.getPin());
            app.insertData(user.getCardNumber(), user.getPin(), user.getBalance());
        }
        private static String createPin() {
            Random rand = new Random();
            StringBuilder cardNumber = new StringBuilder();
            int randomNo;
            for (int i = 0; i < 4; i++) {
                randomNo = rand.nextInt(9);
                cardNumber.append(randomNo);
            }
            return cardNumber.toString();
        }
        private static void displayMainMenu() {
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
        }
        public static String createCard() {
            Random rand = new Random();
            StringBuilder cardNumber = new StringBuilder("400000");
            int randomNo;
            for (int i = 0; i < 9; i++) {
                randomNo = rand.nextInt(10);
                cardNumber.append(randomNo);
            }
            return addChecksum(cardNumber);
        }
        static String addChecksum(StringBuilder numberWithoutChecksum) {
            int checksum = 0;
            for (int i = 0; i < numberWithoutChecksum.length(); i++) {
                int digit = Integer.parseInt(String.valueOf(numberWithoutChecksum.charAt(i)));
                digit = i % 2 == 0 ? digit * 2 : digit;
                checksum += digit > 9 ? digit - 9 : digit;
            }
            return String.valueOf(numberWithoutChecksum.append((checksum * 10 - checksum % 10) % 10));
        }

    }