import java.util.Random;
import java.util.Scanner;

public class SmartBankingApp {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {
        final String CLEAR = "\033[H\033[2J";
        final String COLOR_RED_BOLD = "\033[31;1m";
        final String COLOR_GREEN_BOLD = "\033[33;1m";
        final String RESET = "\033[0;0m";

        final String DASHBOARD = "Welcome to Smart Banking App";
        final String OPEN_ACCOUNT = "Open New Account";
        final String DEPOSIT_MONEY = "Deposit Money";
        final String WITHDRAW_MONEY = "Withdraw Money";
        final String TRANSFER_MONEY = "Transfer Money";
        final String CHECK_ACCOUNT_BALANCE = "Check Account Balance";
        final String DROP_ACCOUNT = "Delete Existing Account";

        final String ERROR_MSG = String.format("\t%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
        final String SUCCESS_MSG = String.format("\t%s%s%s\n", COLOR_GREEN_BOLD, "%s", RESET);

        String[] accountNumbers = new String[0];
        String[] accountNames = new String[0];
        double[] accountBalance = new double[0];

        String screen = DASHBOARD;

        mainLoop:
        do {
            final String APP_TITLE = String.format("%s", screen);

            System.out.println(CLEAR);
            System.out.println("-".repeat(40));
            System.out.println("\033[32;1m ".repeat((40 - (APP_TITLE.length()))/2).concat(APP_TITLE).concat("\033[32;0m"));
            System.out.println("-".repeat(40));

            switch(screen) {
                case DASHBOARD:
                    System.out.println("\t[1] Open New Account\n\t[2] Deposit Money\n\t[3] Withdraw Money\n\t[4] Transfer Money\n\t[5] Check Account Balance\n\t[6] Drop Existing Account\n\t[7] Exit");
                    System.out.print("\nEnter an option to continue > ");
                    int option = SCANNER.nextInt();
                    SCANNER.nextLine();

                    switch(option) {
                        case 1: screen = OPEN_ACCOUNT; break;
                        case 2: screen = DEPOSIT_MONEY; break;
                        case 3: screen = WITHDRAW_MONEY; break;
                        case 4: screen = TRANSFER_MONEY; break;
                        case 5: screen = CHECK_ACCOUNT_BALANCE; break;
                        case 6: screen = DROP_ACCOUNT; break;
                        case 7: System.exit(0); break;
                        default: continue;
                    }
                    break;

                
                case OPEN_ACCOUNT:
                    int number = (int) (Math.random() * Math.pow(4, 10));
                    String formattedNumber = String.format("SDB-%05d", number);
                    System.out.printf("\tNew Account Number : %s \n", formattedNumber);

                    boolean valid;
                    String name;
                    double amount = 0;
                    do{
                        valid = true;
                        System.out.print("\tEnter Account Holder's Name : ");
                        name = SCANNER.nextLine().strip();
                        if (name.isBlank()){
                            System.out.printf(ERROR_MSG, "Name can't be empty");
                            valid = false;
                            continue;
                        }
                        for (int i = 0; i < name.length(); i++) {
                            if (!(Character.isLetter(name.charAt(i)) || 
                                Character.isSpaceChar(name.charAt(i))) ) {
                                System.out.printf(ERROR_MSG, "Invalid Name");
                                valid = false;
                                break;
                            }
                        }
                        boolean valid1;
                        do {
                            valid1 = true;
                            System.out.print("\tEnter Initial Deposit Amount : ");
                            amount = SCANNER.nextDouble();
                            SCANNER.nextLine();
                        
                            if (amount < 5000) {
                                System.out.printf(ERROR_MSG, "Insufficient Initial Amount");
                                valid1 = false;
                                continue;
                            }valid = true;
                        } while (!valid1);

                    }while(!valid);
                    String[] newAccNumber = new String[accountNumbers.length + 1];
                    String[] newAccName = new String[newAccNumber.length];
                    double[] newAccBalance = new double[newAccNumber.length];

                    for (int i = 0; i < accountNumbers.length; i++) {
                        newAccNumber[i] = accountNumbers[i];
                        newAccName[i] = accountNames[i];
                        newAccBalance[i] = accountBalance[i];
                    }
                    newAccNumber[newAccNumber.length-1] = formattedNumber;
                    newAccName[newAccName.length-1] = name;
                    newAccBalance[newAccBalance.length-1] = amount;
                    accountNumbers = newAccNumber;
                    accountNames = newAccName;
                    accountBalance = newAccBalance;

                    System.out.printf(SUCCESS_MSG, String.format("%s : %s Added Successfully!", formattedNumber, name));
                    System.out.print("Do you want to go back? (Y/n) : ");
                    if(SCANNER.nextLine().strip().toUpperCase().equals("Y")) screen = DASHBOARD;
                    break;
                    
                
                case DEPOSIT_MONEY:
                    String accNumberSearch;
                    int index = 0;
                    do {
                        valid = true;
                        System.out.print("\tEnter the Account Number : ");
                        accNumberSearch = SCANNER.nextLine().toUpperCase().strip();
                        if (accNumberSearch.isBlank()){
                            System.out.printf(ERROR_MSG, "Account Number can't be empty");
                            valid = false;
                        }else if (!accNumberSearch.startsWith("SDB-") || accNumberSearch.length() < 5){
                            System.out.printf(ERROR_MSG, "Invalid Account Number format");
                            valid = false;
                        }else{
                            String accNumber = accNumberSearch.substring(4);
                            for (int i = 0; i < accNumber.length(); i++) {
                                if (!Character.isDigit(accNumber.charAt(i))){
                                    System.out.printf(ERROR_MSG, "Invalid Account Number format");
                                    valid = false;
                                    break;
                                }
                            }
                            boolean exists = false;
                            for (int i = 0; i < accountNumbers.length; i++) {
                                if (accountNumbers[i].equals(accNumberSearch)){
                                    index = i;
                                    exists = true;
                                    break;
                                }
                            }    
                            if (!exists){
                                valid = false;
                                System.out.printf(ERROR_MSG, "Account Number does not exist");
                            }
                        }
                        if (!valid) {
                            System.out.print("\n\tDo you want to try again? (Y/n)");
                            if (!SCANNER.nextLine().strip().toUpperCase().equals("Y")){
                                screen = DASHBOARD;
                                continue mainLoop;
                            }
                            System.out.println();
                        }
                    }while (!valid);
                    System.out.printf("\tAccount Holder : %s\n",accountNames[index]);
                    System.out.printf("\tCurrent Balance : Rs.%,.2f\n",accountBalance[index]);
                    boolean valid1;
                        do {
                            valid1 = true;
                            System.out.print("\tEnter Your Deposit Amount : ");
                            amount = SCANNER.nextDouble();
                            SCANNER.nextLine();
                        
                            if (amount < 500) {
                                System.out.printf(ERROR_MSG, "Insufficient Amount, Should be more than Rs.500/=");
                                valid1 = false;
                                continue;
                            }valid = true;
                        } while (!valid1);
                    accountBalance[index] += amount;
                    System.out.printf("\tNew Balance : Rs.%,.2f\n",accountBalance[index]);
                    System.out.println();
                    System.out.printf(SUCCESS_MSG, String.format("%s : %s Deposited Successfully!", accountNumbers[index], accountNames[index]));
                    System.out.print("\tDo you want to continue adding (Y/n)? ");
                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) continue;
                    screen = DASHBOARD;
                    break;


                    

                default: continue;
            }

        } while (true);
    }
}