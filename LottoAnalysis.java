import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.JOptionPane;
public class LottoAnalysis
{
    public static void main(String[] args) throws IOException, ParseException, AssertionError {
        File Numbers = new File("SampleLottoData.txt");
        Scanner in = new Scanner(Numbers);
        if (Numbers.exists()) { //checking the file exists
            boolean valid = ArgumentCheck(args); //calling a method
            if (valid) {
                            if (args.length == 1) { //choosing which part of the program to call
	                            int choice = Integer.parseInt(args[0]);
                                JackpotOrBonus(args, in, choice);
                            } else if (args.length == 2) {
	                            int choice = Integer.parseInt(args[0]);
	                            String drawtype = args[1];
                                TypeOfDraw(args, in, choice, drawtype);
                            } else if (args.length == 3) {
	                            int choice = Integer.parseInt(args[0]);
	                            String drawtype = args[1];
	                            int day = Integer.parseInt(args[2]);
                                DrawOnADay(args, in, choice, drawtype, day);
                            } else if (args.length == 4) {
	                            int choice = Integer.parseInt(args[0]);
	                            String drawtype = args[1];
	                            int day = Integer.parseInt(args[2]);
	                            int year = Integer.parseInt(args[3]);
                                DrawsForAYear(args, in, choice, drawtype, day, year);
                            } else if (args.length == 5) {
	                            int choice = Integer.parseInt(args[0]);
	                            String drawtype = args[1];
	                            int day = Integer.parseInt(args[2]);
                                BetweenTwoDates(args, in, choice, drawtype, day);
                           }   else if(args.length == 6) {
	                           CheckNumbersFor6(args);
                            } else if (args.length == 7) {
                        		checkNumbersIfWon(args);
	                        }    
                        }
        } else
            JOptionPane.showMessageDialog(null, "The file SampleLottoData doesn't exist"); //displaying an error if the file doesn't exist
    }
    
  public static boolean ArgumentCheck(String [] args) //a method to validate user input
  {
   String error = "Error usage: java LottoAnalysis 1-7arguments";
   String pattern1 = "[1-2]{1}", pattern2 = "[0-2]{1}", year = "[0-9]{4}",number = "[0-9]{1,2}";
   boolean valid = true;
   int num;
    if (args.length < 1 || args.length > 7) //checking the right amount of arguments are entered
     {
     JOptionPane.showMessageDialog(null,error,"Incorrect amount of arguments entered",2);
     valid = false;
     }
   else
    {
     if ((args.length == 1 || args.length == 2 || args.length == 3 || args.length == 4 || args.length == 5) && !(args[0].matches(pattern1))) //checking that the first argument is 1 or 2
      {
      JOptionPane.showMessageDialog(null,"The first argument must be 1 or 2","Incorrect argument entered",2);
      valid = false;
      }
       if ((args.length == 2 || args.length == 3 || args.length == 4 || args.length == 5) && !(args[1].equals("A") || args[1].equals("S") || args[1].equals("R") || args[1].equals("LP1") || args[1].equals("LP2"))) //checking that the second argument is a valid draw
        {
        JOptionPane.showMessageDialog(null,"The second argument must be A,R,S,LP1 or LP2","Incorrect argument entered",2);
        valid = false;
        }
         if((args.length == 3 || args.length == 4 || args.length == 5) && !(args[2].matches(pattern2))) //checking that the third argument is 0,1 or 2
          {
          JOptionPane.showMessageDialog(null,"The third argument must be 0,1 or 2","Incorrect argument entered",2);
          valid = false;
          }
           if (args.length == 4 && !(args[3].matches(year)))
            {
            JOptionPane.showMessageDialog(null,"The fourth argument must be a year","Incorrect argument entered",2); //checking that the fourth argument is a valid year
            valid = false;
             }
              if (args.length == 5) //checking that the dates entered are valid
              {
               valid =  ValidDate(args);
               }
                else if (args.length == 6 || args.length == 7) //checking that the numbers entered are between 1 and 45
                 {
	             for(int counter = 0;counter<args.length;counter++)
	             {
	             num = Integer.parseInt(args[counter]);
	              if (num < 1 || num > 45)
	              {
	               JOptionPane.showMessageDialog(null,"All arguments must be between 1 and 45","Incorrect number entered",2);
	               valid = false;	
	              }
	             }
                 }
	   }
	   return valid;
  }
  
  public static boolean ValidDate(String [] args) //a method to validate the dates entered
  {
   String startDate = args[3], endDate = args[4];
   String pattern = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}";
   boolean validStart = false, validEnd = false,returned = true;
    if (startDate.matches(pattern)) //checking that the dates are in the right format
     validStart = true;
      else 
       JOptionPane.showMessageDialog(null,"Format of start date is incorrect","Incorrect Format",2);		
        if (endDate.matches(pattern))
         validEnd = true;
          else 
           JOptionPane.showMessageDialog(null,"Format of end date is incorrect","Incorrect Format",2); 
            if (validStart && validEnd)
             {
                if(isValidDate(startDate)) //calling a method to check if the date is valid
                 validStart = true;
                  else 
                  {
                   JOptionPane.showMessageDialog(null,"The start date is not a valid date","Invalid Date",2);
                   validStart = false;
                   }
                    if (isValidDate(endDate))
                     validEnd = true;
                      else 
                      {
                       JOptionPane.showMessageDialog(null,"The end date is not a valid date","Invalid Date",2);
                       validEnd = false;
                      }
   if (validStart == false || validEnd == false) //checking that both dates were valid
    returned = false;
   }
   return returned; 
  }
  
  public static boolean isValidDate(String date) //checking that the date passed to it is valid
  {
   int firstSlash , lastSlash, ddlnt, mmlnt, yylnt;
   int [] Days = {31,28,31,30,31,30,31,31,30,31,30,31};
   boolean dateValid = true;
   String mlnt;
   firstSlash = date.indexOf("/");
   lastSlash = date.lastIndexOf("/");
   ddlnt = Integer.parseInt(date.substring(0,firstSlash)); //converting the dates from a string to a date format
   mmlnt = Integer.parseInt(date.substring(firstSlash+1,lastSlash));	  
   yylnt = Integer.parseInt(date.substring(lastSlash+1));
   if ((ddlnt==0)||(mmlnt==0)||(yylnt==0)) //checking that just 0s arn't entered
    dateValid = false;
      else if (mmlnt>12) //checking that are less than 13 months entered
       dateValid = false;
        else if((ddlnt==29)&&(mmlnt == 2) && ((((yylnt%4==0)&&(yylnt%100!=0)||(yylnt%400==0))))) //checking if it is a leap year
         dateValid =true;
          else if (ddlnt > Days[mmlnt - 1]) //checking that the days is valid for the month
           dateValid = false;
   return dateValid;
  }

    public static void JackpotOrBonus(String[] args, Scanner in, int choice)throws IOException { //a method to analyse just the jackpot or bonus for the whole file
        int[] jackpotnumbers = new int[45];
        int[] bonusnumbers = new int[45];
        String data = "";
        String[] temp = new String[9];
        if (choice == 1) { //checking if the user wants the jackpot or the bonus analysed
            while (in.hasNext()) { //reading from the file
                data = in.nextLine();
                temp = data.split(",");
                jackpotnumbers = JackpotFrequency(temp,jackpotnumbers); //calling a method the store the frequency of each number
                }
                in.close(); //closing the file
            }
         else { //analysing bonus numbers only
            while (in.hasNext()) {
                data = in.nextLine();
                temp = data.split(",");
                bonusnumbers = BonusFrequency(temp,bonusnumbers);
            }
            in.close();
        }
        Printing(args, jackpotnumbers, bonusnumbers, choice);//calling a method to print the results
    }

    public static void TypeOfDraw(String[] args, Scanner in, int choice,String drawtype) throws IOException { //analysing for a specific draw
        int[] jackpotnumbers = new int[45];
        int[] bonusnumbers = new int[45];
        String data = "";
        String[] temp = new String[9];
        while (in.hasNext()) { //reading from the file
	     data = in.nextLine();
         temp = data.split(","); //putting the file into an array
         if (temp[8].contentEquals(drawtype) || drawtype.contentEquals("A")) //checking if the line from the file matches the user requested type
         {
        if(choice == 1 || choice == 2)
        {
                    jackpotnumbers = JackpotFrequency(temp,jackpotnumbers);
                    }
        if (choice == 2) {  //analysing bonus numbers only
                if (temp[8].contentEquals(drawtype)
                        || drawtype.contentEquals("A"))
                   bonusnumbers = BonusFrequency(temp,bonusnumbers);
            }
        }
    }
         in.close();
        Printing(args, jackpotnumbers, bonusnumbers, choice); //calling the print method to output the results
    }

    public static void DrawOnADay(String[] args, Scanner in, int choice,String drawtype, int day) throws IOException { //analysing for a specific day
        int[] jackpotnumbers = new int[45];
        int[] bonusnumbers = new int[45];
        String data = "";
        int weekday;
        String[] temp = new String[9];
        while(in.hasNext()) { //reading the file
	     data = in.nextLine();
         temp = data.split(",");
         weekday = convertDateToWeekDay(temp); //calling a method to convert a date into a day
         if ((weekday == day || day == 0) && (temp[8].contentEquals(drawtype) || drawtype.contentEquals("A")))  //checking that it is the right day and draw
         {
        if (choice == 1 || choice == 2) {
                        jackpotnumbers = JackpotFrequency(temp,jackpotnumbers); //calling a method the store the frequency of each number
                        }
            if (choice == 2) {
                        bonusnumbers = BonusFrequency(temp,bonusnumbers);
                    }
            }
          }
        in.close(); //closing the file
        Printing(args, jackpotnumbers, bonusnumbers, choice);
    }

    public static void DrawsForAYear(String[] args, Scanner in, int choice,String drawtype, int day, int year) //analysing for a specific year
    throws IOException, ParseException {
        int[] jackpotnumbers = new int[45];
        int[] bonusnumbers = new int[45];
        String data = "";
        int weekday, years = 0;
        String temporary = "";
        String[] temps;
        String[] temp = new String[9];
        while (in.hasNext()) { //reading the file
            data = in.nextLine();
            temp = data.split(",");
            temporary = temp[0];
            temps = temporary.split("/");
            years = Integer.parseInt(temps[2]); //getting the year of the line of the file
            weekday = convertDateToWeekDay(temp);
            if ((year == years) && (temp[8].contentEquals(drawtype) || drawtype.contentEquals("A"))&& (weekday == day || day == 0)) 
            {
                if (choice == 1 || choice == 2) {
                    jackpotnumbers = JackpotFrequency(temp,jackpotnumbers);
                    }
                if (choice == 2) {
                       bonusnumbers = BonusFrequency(temp,bonusnumbers);
                }
            }
        }
        in.close();
        EvenOrOdd(args, choice, year); //calling a method to check for low winning numbers and to check for even and odd numbers
        Printing(args, jackpotnumbers, bonusnumbers, choice); //outputting the results
    }

    public static void EvenOrOdd(String[] args, int choice, int year) 
            throws IOException, ParseException {
        int length, number, odd = 0, even = 0, lowexceedshighr = 0, lowexceedshighs = 0, lowexceedshighlp1 = 0, lowexceedshighlp2 = 0;
        String data = "", weekday;
        String[] temp = new String[9];
        int[] jackpot = new int[6];
        if (choice == 1) //deciding on if to include the bonus number or not
            length = 6;
        else
            length = 7;
        File Number = new File("SampleLottoData.txt");
        Scanner keyboard = new Scanner(Number);
        while (keyboard.hasNext()) { //reading the file
            int oddcounter = 0, evencounter = 0;
            data = keyboard.nextLine();
            temp = data.split(",");
            String temporary = temp[0];
            String[] temps = temporary.split("/");
            for (int counter = 0; counter < length; counter++) {
                number = Integer.parseInt(temp[counter + 1]);
                if (number % 2 == 0) //checking if the number is even
                    evencounter++; 
                else
                    oddcounter++; //checking if its odd
            }
            if (evencounter == length) //if all numbers are even it increments even
                even++;
            else if (oddcounter == length) //if all numbers are odd it increments even
                odd++;
            int years = Integer.parseInt(temps[2]);
            if (year == years) {
                int high = 36; //deciding what the highest number is for this date
                String s39 = "22/08/1992"; 
                String s42 = "24/09/1994";
                String s45 = "4/11/2006";
                String datayear = temp[0];
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date datas = df.parse(datayear); //changing the dates from string to date
                Date ss39 = df.parse(s39);
                Date ss42 = df.parse(s42);
                Date ss45 = df.parse(s45);
                if (datas.equals(ss39)
                        || (datas.after(ss39) && datas.before(ss42)))
                    high = 39;
                else if (datas.equals(ss42)
                        || (datas.after(ss42) && datas.before(ss45)))
                    high = 42;
                else if (datas.equals(ss45) || (datas.after(ss45)))
                    high = 45;
                int limit = (high - 10);
                for (int count = 0; count < jackpot.length; count++)
                    jackpot[count] = Integer.parseInt(temp[count + 1]);
                int lowwinning = 0, highwinning = 0;
                for (int counter = 0; counter < jackpot.length; counter++) {  //checking if the number is low or high
                    if (jackpot[counter] <= 10)
                        lowwinning++;
                    else if (jackpot[counter] >= limit)
                        highwinning++;
                }
                if (lowwinning > highwinning) { //checking if low exceeds high
                    if (temp[8].contentEquals("R")) //checking which draw it is for
                        lowexceedshighr++;
                    else if (temp[8].contentEquals("S"))
                        lowexceedshighs++;
                    else if (temp[8].contentEquals("LP1"))
                        lowexceedshighlp1++;
                    else if (temp[8].contentEquals("LP2"))
                        lowexceedshighlp2++;
                }
            }
            }
            keyboard.close(); //closing the file
            String results = "";
            if (choice == 1) { //printing out the results
                results = "All Jackpot numbers were even " + even + " times.\n";
                results += "All Jackpot numbers were odd " + odd + " times.\n";
            } else {
                results = "All Jackpot and Bonus numbers were even " + even
                        + " times.\n";
                results += "All Jackpot and Bonus numbers were odd " + odd
                        + " times.\n";
            }
            results += "In " + args[3]
                    + " low winning numbers exceeded high winning numbers\n";
            results += lowexceedshighr + " in regular draws.\n";
            results += lowexceedshighs + " in special draws.\n";
            results += lowexceedshighlp1 + " in Lotto Plus 1 draws.\n";
            results += lowexceedshighlp2 + " in Lotto Plus 2 draws.";
            JOptionPane.showMessageDialog(null, results, "", 1);
        }
        
    public static void BetweenTwoDates(String[] args, Scanner in, int choice,String drawtype, int day) //anaylsing between two dates
    throws IOException, ParseException, AssertionError {
        int[] jackpotnumbers = new int[45];
        int[] bonusnumbers = new int[45];
        String data = "";
        int weekday = 0;
        String[] temp = new String[9];
        while (in.hasNext()) { //reading the file
            data = in.nextLine();
            temp = data.split(",");
            String firstyear = args[3];
            String secondyear = args[4];
            String datayear = temp[0];
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date first = df.parse(firstyear); //changing dates from strings to date
            Date second = df.parse(secondyear);
            Date datas = df.parse(datayear);
            weekday = convertDateToWeekDay(temp); //changing the date to a day
            if (weekday == day || day == 0)
            {
                if ((temp[8].contentEquals(drawtype) || drawtype.contentEquals("A")) && ((datas.equals(first)) || (datas.equals(second)) || ((datas.after(first)) && (datas.before(second))))) {
                        if (choice == 1 || choice == 2) {
                            jackpotnumbers = JackpotFrequency(temp,jackpotnumbers); //getting the frequency of jackpotnumber
                            }
                        if (choice == 2) {
                           bonusnumbers = BonusFrequency(temp,bonusnumbers);
                        }
                    }
                }
            }
        in.close();
        Printing(args, jackpotnumbers, bonusnumbers, choice);
    }

    public static int convertDateToWeekDay(String[] temp) { //a method to convert a date to a day
        int result = 0;
        int a, b, d, m, y, dayOfWeek;
        String date = temp[0];
        String[] dates = date.split("/");
        d = Integer.parseInt(dates[0]);
        m = Integer.parseInt(dates[1]);
        y = Integer.parseInt(dates[2]);
        if (m == 1 || m == 2) { 
            m += 12;
            y -= 1;
        }
        a = y % 100;
        b = y / 100;
        dayOfWeek = ((d + (((m + 1) * 26) / 10) + a + (a / 4) + (b / 4)) + (5 * b)) % 7; //finding if it is a saturday or wednesday
        switch (dayOfWeek) { //returning if it is a saturday or a wednesday
        case 0:
            result = 2;
            break;
        case 4:
            result = 1;
            break;
        }
        return result; //returning the day
    }
    
    public static int[] JackpotFrequency(String [] temp,int [] jackpotnumbers) //getting the frequency of each number for jackpot numbers
    {
	 int[] jackpot = new int[6]; 
	 for (int count = 0; count < jackpot.length;count++) {
                        jackpot[count] = Integer.parseInt(temp[count + 1]);
                        jackpotnumbers[jackpot[count] - 1]++;
                    }
       return jackpotnumbers; 
    }
    
    public static int[] BonusFrequency(String [] temp,int [] bonusnumbers) //getting the frequency of each number for bonus numbers
    {
	 int bonus = Integer.parseInt(temp[7]);
     bonusnumbers[bonus - 1]++; 
     return bonusnumbers; 
    }

    public static void Printing(String[] args, int[] jackpotnumbers, int[] bonusnumbers, int choice) //a method to output the results
        {
        String results = "",results2 = "";
        int length = args.length;
        int limit = 24;
        if (length == 1) { //decing what results to output
            if (choice == 1) {
                results = "An Analysis of Jackpot Numbers for the entire file.\n\n";
                for (int counter = 0; counter < 45; counter++) {
                    int num = counter + 1;
                    if(counter <= limit)
                    results += num + " was a jackpot number "
                            + jackpotnumbers[counter] + " times.\n";
                     else results2 += num + " was a jackpot number "
                            + jackpotnumbers[counter] + " times.\n";
                }
            } 
            else {
                results = "An Analysis of Bonus Numbers for the entire file.\n\n";
                for (int counter = 0; counter < 45; counter++) 
                {
                    int num = counter + 1;
                    if(counter <= limit)
                    results += num + " was a bonus number "
                            + bonusnumbers[counter] + " times.\n";
                      else results2 += num + " was a bonus number "
                            + bonusnumbers[counter] + " times.\n";
                }
            }
        }
        else if (length == 2) {
            if (choice == 1) {
                if ((args[1].toUpperCase()).contentEquals("A"))
                    results = "An Analysis of Jackpot Numbers for the entire file.\n\n";
                else if ((args[1].toUpperCase()).contentEquals("R"))
                    results = "An Analysis of Jackpot Numbers for all regular draws.\n\n";
                else if ((args[1].toUpperCase()).contentEquals("S"))
                    results = "An Analysis of Jackpot Numbers for all special draws.\n\n";
                else if ((args[1].toUpperCase()).contentEquals("LP1"))
                    results = "An Analysis of Jackpot Numbers for all Lotto Plus 1 draws.\n\n";
                else
                    results = "An Analysis of Jackpot Numbers for all Lotto Plus 2 draws.\n\n";
                for (int counter = 0; counter < 45; counter++) {
                    int num = counter + 1;
                    if(counter <= limit)
                    results += num + " was a jackpot number "
                            + jackpotnumbers[counter] + " times.\n";
                     else 
                     results += num + " was a jackpot number "
                            + jackpotnumbers[counter] + " times.\n";
                }
            } else {
                if ((args[1].toUpperCase()).contentEquals("A"))
                    results = "An Analsis of Jackpot and Bonus Numbers for the entire file.\n\n";
                else if ((args[1].toUpperCase()).contentEquals("R"))
                    results = "An Analysis of Jackpot and Bonus Numbers for all regular draws.\n\n";
                else if ((args[1].toUpperCase()).contentEquals("S"))
                    results = "An Analysis of Jackpot and Bonus Numbers for all special draws.\n\n";
                else if ((args[1].toUpperCase()).contentEquals("LP1"))
                    results = "An Analysis of Jackpot and Bonus Numbers for all Lotto Plus 1 draws.\n\n";
                else
                    results = "An Analysis of Jackpot and Bonus Numbers for all Lotto Plus 2 draws.\n\n";
                for (int counter = 0; counter < 45; counter++) {
                    int num = counter + 1;
                    if(counter <= limit)
                    results += num + " was a bonus number "
                            + bonusnumbers[counter]
                            + " times and a jackpot number "
                            + jackpotnumbers[counter] + " times.\n";
                     else
                     results2 += num + " was a bonus number "
                            + bonusnumbers[counter]
                            + " times and a jackpot number "
                            + jackpotnumbers[counter] + " times.\n";
                }
            }
        }
               else if (length == 3) {
                    int numb = Integer.parseInt(args[2]);
                    if (choice == 1) {
                        if ((args[1].toUpperCase()).contentEquals("A"))
                            results = "An Analysis of Jackpot Numbers for the entire file";
                        else if ((args[1].toUpperCase()).contentEquals("R"))
                            results = "An Analysis of Jackpot Numbers for all regular draws";
                        else if ((args[1].toUpperCase()).contentEquals("S"))
                            results = "An Analysis of Jackpot Numbers for all special draws";
                        else if ((args[1].toUpperCase()).contentEquals("LP1"))
                            results = "An Analysis of Jackpot Numbers for all Lotto Plus 1 draws";
                        else
                            results = "An Analysis of Jackpot Numbers for all Lotto Plus 2 draws";
                        if (numb == 2)
                            results += "on Wednesday draws.\n\n";
                        else if (numb == 1)
                            results += " on Saturday draws.\n\n";
                        else
                            results += " for all draws.\n\n";
                        for (int counter = 0; counter < 45; counter++) {
                            int num = counter + 1;
                            if(counter <= limit)
                            results += num + " was a jackpot number "
                                    + jackpotnumbers[counter] + " times.\n";
                         else
                         results2 += num + " was a jackpot number "
                                    + jackpotnumbers[counter] + " times.\n";
                        }
                    } else {
                        if ((args[1].toUpperCase()).contentEquals("A"))
                            results = "An Analsis of Jackpot and Bonus Numbers for the entire file";
                        else if ((args[1].toUpperCase()).contentEquals("R"))
                            results = "An Analysis of Jackpot and Bonus Numbers for all regular draws";
                        else if ((args[1].toUpperCase()).contentEquals("S"))
                            results = "An Analysis of Jackpot and Bonus Numbers for all special draws";
                        else if ((args[1].toUpperCase()).contentEquals("LP1"))
                            results = "An Analysis of Jackpot and Bonus Numbers for all Lotto Plus 1 draws";
                        else
                            results = "An Analysis of Jackpot and Bonus Numbers for all Lotto Plus 2 draws";
                        if (numb == 2)
                            results += " on Wednesday draws.\n\n";
                        else if (numb == 1)
                            results += " on Saturday draws.\n\n";
                        else
                            results += " for all draws.\n\n";
                        for (int counter = 0; counter < 45; counter++) {
                            int num = counter + 1;
                            if(counter <= limit)
                            results += num + " was a bonus number "
                                    + bonusnumbers[counter]
                                    + " times and a jackpot number "
                                    + jackpotnumbers[counter] + " times.\n";
                            else
                            results2 += num + " was a bonus number "
                                    + bonusnumbers[counter]
                                    + " times and a jackpot number "
                                    + jackpotnumbers[counter] + " times.\n";
                        }
                    }
                }
               else if (length == 4) {
                    int numb = Integer.parseInt(args[2]);
                    if (choice == 1) {
                        if ((args[1].toUpperCase()).contentEquals("A"))
                            results = "An Analysis of Jackpot Numbers for the entire file";
                        else if ((args[1].toUpperCase()).contentEquals("R"))
                            results = "An Analysis of Jackpot Numbers for all regular draws";
                        else if ((args[1].toUpperCase()).contentEquals("S"))
                            results = "An Analysis of Jackpot Numbers for all special draws";
                        else if ((args[1].toUpperCase()).contentEquals("LP1"))
                            results = "An Analysis of Jackpot Numbers for all Lotto Plus 1 draws";
                        else
                            results = "An Analysis of Jackpot Numbers for all Lotto Plus 2 draws";
                        if (numb == 2)
                            results += " on Wednesday draws\n\n";
                        else if (numb == 1)
                            results += " on Saturday draws\n\n";
                        else
                            results += " for all draws\n\n";
                        results += " for " + args[3] + ".\n\n";
                        for (int counter = 0; counter < 45; counter++) {
                            int num = counter + 1;
                            if(counter <= limit)
                            results += num + " was a jackpot number "
                                    + jackpotnumbers[counter] + " times.\n";
                            else
                            results2 += num + " was a jackpot number "
                                    + jackpotnumbers[counter] + " times.\n";
                        }
                    } else {
                        if ((args[1].toUpperCase()).contentEquals("A"))
                            results = "An Analsis of Jackpot and Bonus Numbers for the entire file";
                        else if ((args[1].toUpperCase()).contentEquals("R"))
                            results = "An Analysis of Jackpot and Bonus Numbers for all regular draws";
                        else if ((args[1].toUpperCase()).contentEquals("S"))
                            results = "An Analysis of Jackpot and Bonus Numbers for all special draws";
                        else if ((args[1].toUpperCase()).contentEquals("LP1"))
                            results = "An Analysis of Jackpot and Bonus Numbers for all Lotto Plus 1 draws";
                        else
                            results = "An Analysis of Jackpot and Bonus Numbers for all Lotto Plus 2 draws";
                        if (numb == 2)
                            results += " on Wednesday draws";
                        else if (numb == 1)
                            results += " on Saturday draws";
                        else
                            results += " for all draws";
                        results += " for " + args[3] + ".\n\n";
                        for (int counter = 0; counter < 45; counter++) {
                            int num = counter + 1;
                            if(counter <= limit)
                            results += num + " was a bonus number "
                                    + bonusnumbers[counter]
                                    + " times and a jackpot number "
                                    + jackpotnumbers[counter] + " times.\n";
                            else
                            results2 += num + " was a bonus number "
                                    + bonusnumbers[counter]
                                    + " times and a jackpot number "
                                    + jackpotnumbers[counter] + " times.\n";
                        }
                    }
                }
              else  if (length == 5) {
                    int numb = Integer.parseInt(args[2]);
                    if (choice == 1) 
                    {
                        if ((args[1].toUpperCase()).contentEquals("A"))
                            results = "An Analysis of Jackpot Numbers for the entire file";
                        else if ((args[1].toUpperCase()).contentEquals("R"))
                            results = "An Analysis of Jackpot Numbers for all regular draws";
                        else if ((args[1].toUpperCase()).contentEquals("S"))
                            results = "An Analysis of Jackpot Numbers for all special draws";
                        else if ((args[1].toUpperCase()).contentEquals("LP1"))
                            results = "An Analysis of Jackpot Numbers for all Lotto Plus 1 draws";
                        else
                            results = "An Analysis of Jackpot Numbers for all Lotto Plus 2 draws";
                        if (numb == 2)
                            results += " on Wednesday draws\n";
                        else if (numb == 1)
                            results += " on Saturday draws\n";
                        else
                            results += " for all draws\n";
                        results += "between " + args[3] + " and " + args[4]
                                + ".\n\n";
                        for (int counter = 0; counter < 45; counter++) {
                            int num = counter + 1;
                            if(counter <= limit)
                            results += num + " was a jackpot number "
                                    + jackpotnumbers[counter] + " times.\n";
                            else
                            results2 += num + " was a jackpot number "
                                    + jackpotnumbers[counter] + " times.\n";
                        }
                    } 
                  else if(choice == 2)
                  {
                        if ((args[1].toUpperCase()).contentEquals("A"))
                            results = "An Analysis of Jackpot and Bonus Numbers for the entire file";
                        else if ((args[1].toUpperCase()).contentEquals("R"))
                            results = "An Analysis of Jackpot and Bonus Numbers for all regular draws";
                        else if ((args[1].toUpperCase()).contentEquals("S"))
                            results = "An Analysis of Jackpot and Bonus Numbers for all special draws";
                        else if ((args[1].toUpperCase()).contentEquals("LP1"))
                            results = "An Analysis of Jackpot and Bonus Numbers for all Lotto Plus 1 draws";
                        else
                            results = "An Analysis of Jackpot and Bonus Numbers for all Lotto Plus 2 draws";
                        if (numb == 2)
                            results += " on Wednesday draws\n";
                        else if (numb == 1)
                            results += " on Saturday draws\n";
                        else
                            results += " for all draws\n";
                        results += "between " + args[3] + " and " + args[4]
                                + ".\n\n";
                        for (int counter = 0; counter < 45; counter++) {
                            int num = counter + 1;
                            if(counter <= limit)
                            results += num + " was a bonus number "
                                    + bonusnumbers[counter]
                                    + " times and a jackpot number "
                                    + jackpotnumbers[counter] + " times.\n";
                            else
                            results2 += num + " was a bonus number "
                                    + bonusnumbers[counter]
                                    + " times and a jackpot number "
                                    + jackpotnumbers[counter] + " times.\n";
                        }
                    }
        }
        JOptionPane.showMessageDialog(null, results, "Analysing the Lotto", 1);
        JOptionPane.showMessageDialog(null, results2, "Analysing the Lotto", 1);
    }
    public static void CheckNumbersFor6(String[] args) throws IOException
	{	
		int punterNumbers[] = new int[6];
		int lotteryNumbers[] = new int[6];
		String result = "";
		
		for (int i = 0; i < 6; i++)
		{
			punterNumbers[i] = Integer.parseInt(args[i]);
		}
		
		String fileItem[], fileName = "";
		File inputFile = new File(fileName);
		
		
		fileName = "SampleLottoData.txt";
		inputFile = new File(fileName);
		Scanner fileReader = new Scanner(inputFile);
		
		
		while(fileReader.hasNext())
		{
			fileItem = (fileReader.nextLine()).split(",");
			
			for(int i = 1; i <= 6; i++)
			{
				lotteryNumbers[i-1] = Integer.parseInt(fileItem[i]);
			}
			int numbersMatched = 0;
			boolean bonusNumberMatch = false;
			 
			if (lotteryNumbers[5] == punterNumbers[5])
			        bonusNumberMatch = true;
			       
			for (int i = 0; i < lotteryNumbers.length - 1; i++)
			{
			        boolean matchFound = false;
			        for (int j = 0; j < punterNumbers.length - 1 && !matchFound; j++)
			        {
			                if (lotteryNumbers[i] == punterNumbers [j])
			                {
			                        matchFound = true;
			                        numbersMatched++;
			                }
		        	}
			}			 
			if (numbersMatched == 6 ) // if the punter’s numbers match the drawn numbers i.e. Jackpot Winner
			    result += fileItem[0] + " 6 numbers matched from file\n";
							
			else if (numbersMatched == 5 ) // if 5 of the punter’s numbers and his/her bonus number match those drawn
			    result += fileItem[0] + " 5 numbers matched from file\n";
			       			       
			else if (numbersMatched == 4 ) // if 4 of the punter’s numbers and his/her bonus number match those drawn
			    result += fileItem[0] + " 4 numbers matched from file\n";
			       						       
			else if (numbersMatched == 3 )// if 3 of the punter’s numbers and his/her bonus number match those drawn
			    result += fileItem[0] + " 3 numbers matched from file\n";
			       
		}
		JOptionPane.showMessageDialog(null, result);
	}
    
    public static void checkNumbersIfWon(String[] args) throws IOException
	{	
		int punterNumbers[] = new int[7];
		int lotteryNumbers[] = new int[7];
		String result = "";
		
		for (int i = 0; i < 7; i++)
		{
			punterNumbers[i] = Integer.parseInt(args[i]);
		}
		
		String fileItem[], fileName = "";
		File inputFile = new File(fileName);
		
		
		fileName = "SampleLottoData.txt";
		inputFile = new File(fileName);
		Scanner fileReader = new Scanner(inputFile);
		
		
		while(fileReader.hasNext())
		{
			fileItem = (fileReader.nextLine()).split(",");
			
			for(int i = 1; i <= 7; i++)
			{
				lotteryNumbers[i-1] = Integer.parseInt(fileItem[i]);
			}
			int numbersMatched = 0;
			boolean bonusNumberMatch = false;
			 
			if (lotteryNumbers[6] == punterNumbers[6])
			        bonusNumberMatch = true;
			       
			for (int i = 0; i < lotteryNumbers.length - 1; i++)
			{
			        boolean matchFound = false;
			        for (int j = 0; j < punterNumbers.length - 1 && !matchFound; j++)
			        {
			                if (lotteryNumbers[i] == punterNumbers [j])
			                {
			                        matchFound = true;
			                        numbersMatched++;
			                }
		        	}
			}
			 
			if (numbersMatched == 6 && bonusNumberMatch) // if the punter’s numbers match the drawn numbers i.e. Jackpot Winner
			    result += fileItem[0] + " 6 numbers + bonus number. JACKPOT!\n";
			
			else if (numbersMatched == 6 && !bonusNumberMatch)
				result += fileItem[0] + " 6 numbers + no bonus number\n";
			       
			else if (numbersMatched == 5 && bonusNumberMatch) // if 5 of the punter’s numbers and his/her bonus number match those drawn
			    result += fileItem[0] + " 5 numbers + bonus number\n";
			       
			else if (numbersMatched == 5 && !bonusNumberMatch) // if 5 of the punter’s numbers match the drawn numbers
			    result += fileItem[0] + " 5 numbers + no bonus number\n";
			       
			else if (numbersMatched == 4 && bonusNumberMatch)// if 4 of the punter’s numbers and his/her bonus number match those drawn
			    result += fileItem[0] + " 4 numbers + bonus number\n";
			       
			else if (numbersMatched == 4 && !bonusNumberMatch)// if 4 of the punter’s numbers match the drawn numbers
			    result += fileItem[0] + " 4 numbers + no bonus number\n";
			       
			else if (numbersMatched == 3 && bonusNumberMatch) // if 3 of the punter’s numbers and his/her bonus number match those drawn
			    result += fileItem[0] + " 3 numbers + bonus number\n";
			       
			else if (numbersMatched == 3 && !bonusNumberMatch)// if 3 of the punter’s numbers match the drawn numbers
			    result += fileItem[0] + " 3 numbers + no bonus number\n";
		}
		JOptionPane.showMessageDialog(null, result);
	}
    
    
}