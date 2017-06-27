
// To handle files, imported the IO package 

import java.io.*;
import java.util.*;  // Imports the scanner 
import java.sql.*;   // To handle SQL statements 

// Class Definition
public class Project_3 
{
   // Main Method
   public static void main(String[] args) throws FileNotFoundException
   {
      // Final named constant for the database URL
      final String DB_URL = "jdbc:derby:ArenasDBB;create=true";
      // boolean var for the loop and String for user input
      boolean exit = false;
      int userChoice = 0;
      String input = "";
      
      Scanner repeat = new Scanner(System.in);
      Scanner userInput = new Scanner(System.in);
      
      do
      {  
         // Display options 
         System.out.println("Select an option from the following: " + "\n\n");
         System.out.print("1. Build / Rebuild Arena Table" + "\n");
         System.out.print("2. Display all of the Arena info in the Arena table" + "\n");
         System.out.print("3. Display all of the Arena info for Basketball Tenants" + "\n");
         System.out.print("4. Display the average capacity for all Arenas" + "\n");
         System.out.print("5. Display the Venue and year Opened info for all Arenas Opened " +
            "after 200 in ascending order by year\n");
         System.out.print("6. Display the Total Attendance for Arenas in California" + "\n");
         System.out.print("7. Exit" + "\n\n");
         
         System.out.print("Enter your choice (1-7): ");
         
         userChoice = userInput.nextInt();
         
         while(userChoice < 1 || userChoice > 7)
         {
            System.out.print("Sorry that choice is not one of the ones listed, please choose 1-7" + "\n");
            
            userChoice = userInput.nextInt();
         
         }  
         // Loop to progress or exit the program
         if(userChoice == 7)
         {
            exit = true;
         }
         else
         {
            //The try / catch code to build the Arena database
            try
            {
               // creating a connection to the database
               Connection conn = DriverManager.getConnection(DB_URL);
               
               // else-if for choices
               if(userChoice == 1)
               {
                  // if the DB already exists, it drops the arenas table
                  dropTable(conn);
                  
                  // building the arena table by calling the method
                  buildArenasTable(conn);
               }
               else if(userChoice == 2)
               {
                  // displaying all arena info from the table
                  DisplayArenasInfoQuery(conn);
               }
               else if(userChoice == 3)
               {
                  DisplayBasketball(conn);
               }
               else if(userChoice == 4)
               {
                  DisplayAverageCapacity(conn);
               }
               else if(userChoice == 5)
               {
                  DisplayVenueYearOpened(conn);
               }
               else if(userChoice == 6)
               {
                  DisplayTotalAttendanceCali(conn);
               }
               
               // close connection
               conn.close();
            }
            catch (SQLException ex)
            {
               System.out.println("ERROR: " + ex.getMessage());
            } // end try catch
         
            System.out.print("\n\nWould you like to make another selection?" +
               " y/yes to continue, anything else to exit.");
            
            input = repeat.nextLine();
            
            if(input.equalsIgnoreCase("y"))
            {
               exit = false;
            }
            else
            {
               exit = true;
            }
         
         } // end else
         
      } while(exit == false); // end while loop
      
   } // End Main
   
   public static void dropTable(Connection conn)
   {
      // Message to let user know that tables are being checked
      System.out.println("Checking for existing tables.");
      
      try
      {
         // Statement Object
         Statement stmt = conn.createStatement();
         stmt.execute("DROP TABLE Arenas");
         System.out.println("\nArenas table dropped.");
      }
      catch (SQLException ex)
      {
         // blank so if the table does not exist, it displays nothing
      }
   }
   
   // Building the arena table
   // Method creates table and add rows while reading from the file
   public static void buildArenasTable(Connection conn) throws FileNotFoundException
   {
      
      // Open a file for reading, by creating a file object
      File f = new File("IndoorArenasTwo.txt");
      
      // Create a Scanner object to use the file for reading
      Scanner inputFile = new Scanner(f); // Uses the file object as a source of input
      
      // Variables to store data in
      String venueName_str, city_str, state_str, maxCapacity_str, yearOpened_str; 
      String attendance_str, teamName_str, sport_str, league_str;
      
      try
      {
         Statement stmt = conn.createStatement();
         
         // Creating the table
         stmt.execute("CREATE TABLE Arenas (" +
                      "Venue CHAR(45) PRIMARY KEY, " +
                      "City CHAR(15), " +
                      "State CHAR(15), " +
                      "MaxCapacity INT, " +
                      "YearOpened INT, " +
                      "TeamName CHAR(30), " +
                      "Sport CHAR(10), " +
                      "League CHAR(15), " + 
                      "Attendance INT)");
      }
      catch (SQLException ex)
      {
         System.out.println("ERROR: " + ex.getMessage());
      }
      
      try
      {  
         Statement stmt = conn.createStatement();
             
         // A loop to read in data from the file and build the database
         while(inputFile.hasNext())
         {
            // Reading the file one line at a time and storing it in the DB
            venueName_str = inputFile.nextLine();
            city_str = inputFile.nextLine();
            state_str = inputFile.nextLine();
            maxCapacity_str = inputFile.nextLine();
            yearOpened_str = inputFile.nextLine();
            teamName_str = inputFile.nextLine();
            sport_str = inputFile.nextLine();
            league_str = inputFile.nextLine();
            attendance_str = inputFile.nextLine();
            inputFile.nextLine(); // For the blank line 
            
            // Creating an instance of the Arena class
            Arena temp = new Arena(venueName_str, city_str, state_str, 
               Integer.parseInt(maxCapacity_str), Integer.parseInt(yearOpened_str),
                   teamName_str, sport_str, league_str, Integer.parseInt(attendance_str));
         
            // Storing data into the database from the temp object
            stmt.execute("INSERT INTO Arenas VALUES( '" +
                         temp.getVenueName() + "', '"+
                         temp.getCity() + "', '"+
                         temp.getState() + "', "+
                         temp.getMaxCapacity() + ", "+
                         temp.getYearOpened() + ", '"+
                         temp.team.getTeamName() + "', '"+
                         temp.team.getSport() + "', '"+
                         temp.team.getLeague() + "', "+
                         temp.getAttendance() + ")");
         } 
      }
      catch (SQLException ex)
      {
         System.out.println("ERROR: " + ex.getMessage());
      }
      // A message that informs on if the tables were created
      System.out.print("\nArenas table created!");
   }
   
   public static void DisplayArenasInfoQuery(Connection conn)
   {
      System.out.println("\n\nAll arenas info query:");
      
      try
      {
         Statement stmt = conn.createStatement();
         
         // SQL statement to execute query
         String sqlStatement = "SELECT * FROM Arenas";
         
         //Statement to be sent to the database
         ResultSet result = stmt.executeQuery(sqlStatement);
         
         //Displaying the results of the query
         while(result.next())
         {
            System.out.print(result.getString("Venue") + "\t");
            System.out.print(result.getString("City") + "\t");
            System.out.print(result.getString("State") + "\t");
            System.out.print(result.getInt("MaxCapacity") + "\t");
            System.out.print(result.getInt("YearOpened") + "\t");
            System.out.print(result.getString("TeamName") + "\t");
            System.out.print(result.getString("Sport") + "\t");
            System.out.print(result.getString("League")+ "\t");
            System.out.printf("%,d \n", result.getInt("Attendance"));
         }
         
      }
      catch(SQLException ex)
      {
         System.out.println("ERROR: " + ex.getMessage());
      }
   }
   
   
   
   public static void DisplayBasketball(Connection conn)
   {
      System.out.println("\nAll Arena info for Basketball Tenants: \n");
      
      try
      {
         Statement stmt = conn.createStatement();
         
         String sqlStatement = "SELECT * FROM Arenas WHERE Sport = 'Basketball'";
         
         ResultSet result = stmt.executeQuery(sqlStatement);
         
         while(result.next())
         {
            System.out.print(result.getString("Venue") + "\t");
            System.out.print(result.getString("City") + "\t");
            System.out.print(result.getString("State") + "\t");
            System.out.print(result.getInt("MaxCapacity") + "\t");
            System.out.print(result.getInt("YearOpened") + "\t");
            System.out.print(result.getString("TeamName") + "\t");
            System.out.print(result.getString("Sport") + "\t");
            System.out.print(result.getString("League")+ "\t");
            System.out.printf("%,d \n", result.getInt("Attendance"));
         } // end while  
      }
      catch (SQLException ex)
      {
         System.out.println("ERROR: " + ex.getMessage()); 
      }
   } // end DisplayBasketball
   
   public static void DisplayAverageCapacity(Connection conn)
   {
      System.out.println("\nThe average capacity for all arenas: \n");
      
      try
      {
         Statement stmt = conn.createStatement();
         
         String sqlStatement = "SELECT AVG(MaxCapacity) FROM Arenas";
         
         ResultSet result = stmt.executeQuery(sqlStatement);
         
         result.next();
         
         int average = result.getInt(1);
         
         System.out.printf("%,d \n",  average);
      }
      catch (SQLException ex)
      {
         System.out.println("ERROR: " + ex.getMessage()); 
      }
   }  // end DisplayAverageCapacity
   
   public static void DisplayVenueYearOpened(Connection conn)
   {
      System.out.println("\nThe Venue and Year Opened info for Arenas opened after 2000 are: \n");
      
      try
      {
         Statement stmt = conn.createStatement();
         
         String sqlStatement = "SELECT Venue,YearOpened FROM Arenas WHERE YearOpened > 2000 ORDER BY YearOpened";
         
         ResultSet result = stmt.executeQuery(sqlStatement);
         
         while(result.next())
         {
            System.out.print(result.getString("Venue") + "\t");
            System.out.print(result.getInt("YearOpened") + "\n");
         }
         
      }
      catch (SQLException ex)
      {
         System.out.println("ERROR: " + ex.getMessage()); 
      }
      
   }  // End DisplayVenueYearOpened
   
   public static void DisplayTotalAttendanceCali(Connection conn)
   {
      System.out.println("\nTotal Attendance for Arenas in California: \n");
      
      try
      {
         Statement stmt = conn.createStatement();
         
         String sqlStatement = "SELECT SUM(Attendance) FROM Arenas WHERE State = 'California'";         
         
         ResultSet result = stmt.executeQuery(sqlStatement);
         
         result.next();
         
         int total = result.getInt(1);
         
         System.out.printf("%,d \n", total);
         
      }
      catch (SQLException ex)
      {
         System.out.println("ERROR: " + ex.getMessage()); 
      }
   
   } // end DisplayTotalAttendanceCali
   
} // End Project_3

