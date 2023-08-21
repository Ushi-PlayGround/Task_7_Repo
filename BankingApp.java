import java.util.Scanner;

public class BankingApp {
    
    private static final Scanner scanner = new Scanner (System.in);

    
    public static void main(String[] args) {
        
        final String CLEAR = "\033[H\033[2J";
        final String COLOR_BLUE_BOLD = "\033[34;1m";
        final String COLOR_RED_BOLD = "\033[31;1m";
        final String COLOR_GREEN_BOLD = "\033[33;1m";
        final String RESET = "\033[0m";


        final String DASHBOARD = "\u270B Welcome to Smart Banking System";
        final String CREATE_NEW_ACCOUNT = "➕ Create New Account";
        final String DEPOSITS = "\u2705 Deposits";
        final String WITHDRAWALS = "\u26D4 Withdrawals";
        final String TRANSFER = "\u23E9 Transfer";
        final String CHECK_BALANCE = "\u2753 Check A/C Balance";
        final String DELETE_ACCOUNT = "\u274C Delete Accounts";

        final String ERROR_MSG = String.format("\t%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
        final String SUCCESS_MSG = String.format("\t%s%s%s\n", COLOR_GREEN_BOLD, "%s", RESET);
        final String CONTINUE_MSG = String.format("\t%s%s%s",COLOR_BLUE_BOLD,"Do You Want to Continue (Y/n) ? ",RESET);

        String accountNum;
        String name;
        Double intialDeposit;
        double deposit;
        Double newBalance;
        Double newBalance2;
        Double withdraw;
        Double transfer;
        String inputAcNum;
        String inputAcNum2 = "";
        
        
        String[][] acArray = new String[0][];
        
        String screen = DASHBOARD;

        mainloop:
        do{
            final String APP_TITLE = String.format("%s%s%s",COLOR_BLUE_BOLD, screen, RESET);
            System.out.println(CLEAR);
            System.out.println("\t" + APP_TITLE + "\n");
            
            switch(screen){ 
                case DASHBOARD:{
                    
                    System.out.println("\t[1]. Create New Account ");
                    System.out.println("\t[2]. Deposits ");
                    System.out.println("\t[3]. Withdrawals");
                    System.out.println("\t[4]. Transfer");
                    System.out.println("\t[5]. Check A/C Balance");
                    System.out.println("\t[6]. Delete Account");
                    System.out.println("\t[7]. Exit\n");
        
                    System.out.print("\tEnter an Option: ");
                    int option = scanner.nextInt();
                    scanner.nextLine();

                    switch(option){

                        case 1:screen = CREATE_NEW_ACCOUNT;break;
                        case 2:screen = DEPOSITS;break;
                        case 3:screen = WITHDRAWALS;break;
                        case 4:screen = TRANSFER;break;
                        case 5:screen = CHECK_BALANCE;break;
                        case 6:screen = DELETE_ACCOUNT;break;
                        case 7:
                            System.out.println(CLEAR);
                            System.exit(0);
                        default:continue;
                    }
                    break;
                }case CREATE_NEW_ACCOUNT:{

                    boolean exist;
                   
                    do{
                        exist = false;
                        accountNum = String.format("SDB-%05d",(acArray.length+1));
                        System.out.printf("Account Number: %s \n",accountNum);

                        System.out.print("Enter Name: ");
                        name = scanner.nextLine().strip();

                        if (AccName(name,acArray) == true){
                            do{
                                System.out.print("Enter the Intial Deposit: ");
                                intialDeposit = scanner.nextDouble();
                                scanner.nextLine();
        
                                if (intialDeposit < 5000){
                                    System.out.printf(ERROR_MSG,"Insufficient Amount. Please Deposit Above Rs.5000! \n");
                                    exist = false;
                                    System.out.print(CONTINUE_MSG);
                                    if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                                    screen = DASHBOARD;
                                }else {
                                    System.out.printf(SUCCESS_MSG,String.format("%s : %s has been created an account successfully. \n",accountNum,name) );
                                    exist = true;
                                }    
                            }while (!exist);
        
                            String[][] tempArray = new String[acArray.length +1][3];
        
                            for (int i = 0; i < acArray.length; i++) {
                                tempArray[i] = acArray[i];
                            }
        
                            tempArray[tempArray.length -1][0] = accountNum;
                            tempArray[tempArray.length -1][1] = name;
                            tempArray[tempArray.length -1][2] = intialDeposit + "";
        
                            acArray = tempArray;

                        }
                        
                    }while (!exist);

                    System.out.print(CONTINUE_MSG);
                    if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue mainloop;
                    screen = DASHBOARD;
                    break;
                

                }case DEPOSITS:{
                    boolean valid;
                    
                    do{
                        valid = false;
                        System.out.print("Enter A/C Number: ");
                        inputAcNum = scanner.nextLine().strip();

                        if (AccNum(inputAcNum, acArray) == false)  valid = false;
                        else valid = true;
                        
                    }while(!valid);

                    int newIndex = acNumExist(acArray, inputAcNum);

                    if (newIndex >= 0){
                        System.out.printf("Current Balance: Rs.%,.2f \n",(Double.valueOf(acArray[newIndex][2])));
                        System.out.print("Deposit Amount: Rs.");
                        deposit = scanner.nextDouble();
                        scanner.nextLine();
                    
                
                        if (deposit < 500){
                            System.out.printf(ERROR_MSG, "Insufficient Amount. Minimum Amount to Deposit is 500. \n");
                            System.out.println(CONTINUE_MSG);
                            if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                            screen = DASHBOARD;
                            break;
                        }else{
                            newBalance = Double.valueOf(acArray[newIndex][2]) + deposit;
                            System.out.printf("New Bank Balance: Rs.%,.2f \n",newBalance);
                
                            acArray[newIndex][2] = newBalance + "";
                        }
                    }else System.out.printf("%sA/C Number Not Found%s \n",COLOR_RED_BOLD,RESET);

                    System.out.print(CONTINUE_MSG);
                    if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                    screen = DASHBOARD;
                    break; 

                }case WITHDRAWALS:{

                    boolean valid;
                    do{
                        valid =false;
                        System.out.print("Enter A/C Number: ");
                        inputAcNum = scanner.nextLine().strip();

                        if (AccNum(inputAcNum, acArray) == false) valid = false;
                        else valid = true;

                    }while (!valid);

                    int newIndex = acNumExist(acArray, inputAcNum);

                    if (newIndex >= 0){

                        System.out.printf("Current Balance: Rs.%,.2f \n",(Double.valueOf(acArray[newIndex][2])));
                        
                        System.out.print("Withdraw Amount: Rs.");
                        withdraw = scanner.nextDouble();
                        scanner.nextLine();
            
                        if (withdraw < 100){
                            System.out.printf(ERROR_MSG, "Can not Withdraw. Minimum Amount to Withdraw is 500.");
                            System.out.print(CONTINUE_MSG);
                            if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                            screen = DASHBOARD;
                            break;
                        }else{
            
                            newBalance = Double.valueOf(acArray[newIndex][2]) - withdraw;
                            if (newBalance < 500){
                                System.out.printf(ERROR_MSG,"New Bank Balance is Lower than Rs.500.00. Can not Withdraw!");
                                System.out.print(CONTINUE_MSG);
                                if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                                screen = DASHBOARD;
                                break;
                            }
                                
                            System.out.printf("New Bank Balance: Rs.%,.2f \n",newBalance);
            
                            acArray[newIndex][2] = newBalance + "";
                        }                            
                    }else System.out.printf("%sA/C Number Not Found%s \n",COLOR_RED_BOLD,RESET);
                        
                    System.out.print(CONTINUE_MSG);
                    if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                    screen = DASHBOARD;
                    break; 

                }case TRANSFER:{

                    boolean valid;
                    do{
                        valid = false;
                        System.out.print("Enter From A/C Number: ");
                        inputAcNum = scanner.nextLine().strip();

                        if (AccNum(inputAcNum, acArray) == false){
                            valid =false;
                            System.out.print(CONTINUE_MSG);
                            if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                            screen = DASHBOARD;
                            break;
                             
                        }else {

                            System.out.print("Enter To A/C Number: ");
                            inputAcNum2 = scanner.nextLine().strip();
                                
                            if (AccNum(inputAcNum2, acArray) == false) {
                                valid =false;
                                System.out.print(CONTINUE_MSG);
                                if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                                screen = DASHBOARD;
                                break;

                            }else valid =true;
                        }

      
                    }while(!valid);
                    
                    int newIndex1 = acNumExist(acArray,inputAcNum);
                    int newIndex2 = acNumExist(acArray,inputAcNum2);

                    if ((newIndex1 >= 0) && (newIndex2 >= 0)){
                        System.out.printf("From A/C Holder: %s\n",acArray[newIndex1][1] );
                        System.out.printf("From A/C Balance: %,.2f \n", Double.valueOf(acArray[newIndex1][2]));
                        System.out.printf("To A/C Holder: %s\n",acArray[newIndex2][1] );
                        System.out.printf("To A/C Balance: Rs.%,.2f \n", Double.valueOf(acArray[newIndex2][2]));

                        do {
                            valid = false;
                            System.out.print("Transfer Amount: Rs. ");
                            transfer = scanner.nextDouble();
                            scanner.nextLine();
            
                            if (transfer < 100){
                                System.out.printf(ERROR_MSG,"Can not Transfer. Minimum Transfer Amount is 100.");
                                valid = false;
                            }else valid = true;
            
                        }while(!valid);

                        newBalance = ((Double.valueOf(acArray[newIndex1][2]) - transfer) - 0.02*transfer);
                        acArray[newIndex1][2] = newBalance + "";
                        if (Double.valueOf(acArray[newIndex1][2]) < 500){
                            System.out.printf(ERROR_MSG,"New Bank Balance is Lower than Rs.500.00. Can not Tranfer!");
                            System.out.print(CONTINUE_MSG);
                            if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                            screen = DASHBOARD;
                            break; 

                        }
            
                        newBalance2 = Double.valueOf(acArray[newIndex2][2]) + transfer;
                        acArray[newIndex2][2] = newBalance2 + "";
            
                        System.out.printf(SUCCESS_MSG,"Transaction Successfull!");
                                    
                    }else System.out.printf(ERROR_MSG,"A/C Number not Found!");

                    System.out.print(CONTINUE_MSG);
                    if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                    screen = DASHBOARD;
                    break; 

                }case CHECK_BALANCE:{

                    boolean valid;
                    do{
                        valid = false;
                        System.out.print("Enter A/C Number: ");
                        inputAcNum = scanner.nextLine().strip();

                        if (AccNum(inputAcNum, acArray) == false)valid = false;
                        else valid = true;
        
                    }while(!valid);
                    
                    int newIndex = acNumExist(acArray,inputAcNum);
                    if (newIndex >= 0){
                               
                        System.out.printf("A/C Holder Name: %s \n",acArray[newIndex][1]);
                        System.out.printf("Current Balance: Rs.%,.2f \n",(Double.valueOf(acArray[newIndex][2])));
                        String availableBalanceForWithdraw = (Double.valueOf(acArray[newIndex][2]) - 500.00) + "";
                        System.out.printf("Available Balance for Withdraw: %.2f \n",Double.valueOf(availableBalanceForWithdraw));
            
                        System.out.print(CONTINUE_MSG);
                        if (scanner.nextLine().strip().toUpperCase().equals("Y")) continue;
                        screen = DASHBOARD;
                        break;

                    }else System.out.printf(ERROR_MSG,"A/C Number not Found!");
                                
                    System.out.print(CONTINUE_MSG);
                    if (scanner.nextLine().strip().toUpperCase().equals("Y"))continue;
                    screen = DASHBOARD;
                    break; 
                   

                }case DELETE_ACCOUNT:{
                    boolean valid;
                do{
                    valid = false;
                    System.out.print("Enter A/C Number: ");
                    inputAcNum = scanner.nextLine().strip();

                    if (AccNum(inputAcNum, acArray) == false) valid = false;
                    else valid = true;
                }while (!valid);

                int newIndex = acNumExist(acArray,inputAcNum);
                if (newIndex >= 0){
                                
                    String[][] newAccounts = new String[acArray.length - 1][3];
                                
                    for (int i = 0; i < acArray.length; i++) {
                        if (i < newIndex){
                            newAccounts[i][0] = acArray[i][0];
                            newAccounts[i][1] = acArray[i][1];
                            newAccounts[i][2] = acArray[i][2];
                        }else if (i == newIndex){
                            continue;
                        }else{
                            newAccounts[i-1][0] = acArray[i][0];
                            newAccounts[i-1][1] = acArray[i][1];
                            newAccounts[i-1][2] = acArray[i][2];
                        }
                    }
                    
                    System.out.printf(SUCCESS_MSG,"You have successfully deleted the Account",acArray[newIndex][1]);
                    acArray = newAccounts;
                }else  System.out.printf(ERROR_MSG,"A/C Number not Found!");
                               
                System.out.print(CONTINUE_MSG);
                if (scanner.nextLine().strip().toUpperCase().equals("Y"))continue;
                screen = DASHBOARD;
                break; 
            }
            default:System.exit(0);
            break;
            }
           
        }while(true);
    }


    //Account Number Validation
    public static boolean AccNum(String input,String[][]...array){
        boolean valid = false;

        if (input.isBlank()){
            System.out.printf("\t%sA/C Number can't be Empty!%s \n","\033[31;1m","\033[0m");
            valid = false;
        }else if (!(input.startsWith("SDB-"))){
            System.out.printf("\t%sInvalid A/C Number%s \n","\033[31;1m","\033[0m");
            valid = false;
        }else{
            valid = true;
        }

        return valid;
    }

    //Account Number Exist?

    public static int acNumExist(String[][] acArray, String input){

        int index = -1;
    
        for (int i = 0; i < acArray.length; i++) {
            if (input.equals(acArray[i][0])){
                index = i;
                break;
            }
        }
        return index;
    }

    //Account Name Validation
    public static boolean AccName (String input, String[][] array){
            boolean valid = false;

            if (input.isBlank()){
                System.out.printf("\t%sCustomer name can't be empty%s \n","\033[31;1m","\033[0m");
                valid = false;
                
            }
            for (int i = 0; i < input.length(); i++) {
                if (!((Character.isLetter(input.charAt(i))) || Character.isSpaceChar(input.charAt(i)))) {
                    System.out.printf("\t%sInvalid Input%s \n","\033[31;1m","\033[0m");
                    valid = false;
                    break;
                }else{
                    valid = true;   
                }  
            }
            return valid;     
    }

}

    

