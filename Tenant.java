// Tenant class is going to be an aggregate of the Arena class

public class Tenant
{
// Fields
   private String teamName; // team name
   private String sport; // sport type
   private String league; // league type
   
// Constructors
   // Default no ARG constructor 
   public Tenant()
   {
      teamName = "";
      sport = "";
      league = "";
   }
   
   // Constructor with arguments
   public Tenant(String t, String s, String l)
   {
      this(); // Calls the default constructor
      teamName = t;
      sport = s;
      league = l;
   }
// Accessors AKA Getters
   public String getTeamName()
   {
      return teamName;
   }  
   
   public String getSport()
   {
      return sport;
   }  
   
   public String getLeague()
   {
      return league;
   }
   

// Mutators AKA Setters
   public void setTeamName(String t)
   {
      teamName = t;
   }  
   
   public void setSport(String s)
   {
      sport = s;
   }
   
   public void setLeague(String l)
   {
      league = l;
   }

// Other Methods
   //toString Method
   public String toString()
   {
      return "Team Name:   " + getTeamName()+ "\nSport:       " + getSport() +
         "\nLeague:      " + getLeague();
   }  
}