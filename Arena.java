// Arena class that will contain attributes for an arena

public class Arena
{
// Fields
   private String venueName;
   private String city;
   private String state;
   private int maxCapacity;
   private int yearOpened;
   private int attendance;
   
   // Uses an aggregate field 
   public Tenant team;  // creates a has-a relationship 

// Constructors
   // no-arg Constructor
   public Arena()
   {
      venueName = "";
      city = "";
      state = "";
      maxCapacity = 0;
      yearOpened = 0;
      attendance = 0;
      team = new Tenant(); // calls the no-arg constructor 
   }
   
   //Constructor that has arguments
   public Arena(String v, String c, String s, int m, int y, String tn, String sp, String lg, int a)
   {
      this();
      setVenueName(v);
      setCity(c);
      setState(s);
      setMaxCapacity(m);
      setYearOpened(y);
      setTenant(tn, sp, lg);
      setAttendance(a);
   }  

// Accessors
   public String getVenueName()
   {
      return venueName;
   }
   
   public String getCity()
   {
      return city;
   }
   
   public String getState()
   {
      return state;
   }
   
   public int getMaxCapacity()
   {
      return maxCapacity;
   }
   
   public int getYearOpened()
   {
      return yearOpened;
   }
   
   public int getAttendance()
   {
      return attendance;
   }
   
   // Accessor for the aggregate field
   public Tenant getTenant()
   {
      return team;
   }
   
// Mutators
   public void setVenueName(String v)
   {
      venueName = v;
   }
   
   public void setCity(String c)
   {
      city = c;
   }
   
   public void setState(String s )
   {
      state = s;
   }
   
   public void setMaxCapacity(int m)
   {
      maxCapacity = m;
   }
   
   public void setYearOpened(int y)
   {
      yearOpened = y;
   }
   
   public void setAttendance(int a)
   {
      attendance = a;
   }
   
   // Mutator to set the attributes of the aggregate field
   public void setTenant(String tn, String sp, String lg)
   {
      team.setTeamName(tn); // Calls mutator to set the team name
      team.setSport(sp); // Same thing as above
      team.setLeague(lg);
   }
   
// Other Methods
   // toString Method
   public String toString()
   {
      return "Venue:       " + getVenueName() + "\nLocation:    " + getCity() + ", " +
               getState() + "\nCapacity:    " + getMaxCapacity() + "\nYear Opened: " +
                  getYearOpened() + "\n" + team.toString();
   }
} // end Arena class