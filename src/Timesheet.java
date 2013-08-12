import java.util.HashMap;
import java.util.List;
//import java.util.GregorianCalendar;
import java.util.Map;

public class Timesheet {
	private String name;//The user's name.
	private String ID;//The user's corporate ID text/ID.
	private int shift;//The user's shift (1 for 1st, 2 for 2nd, 3 for 3rd, etc).
	private List<Time> timeEarned;//List of time earned
	private List<Time> timeTaken;//List of time taken
	private Map<String, Integer> timeGiven;//List of time given in the format Name, amount.
	private TimesheetDB tDB;//The database object.
	private int fiscalStartingMonth;//The starting month of the fiscal year.
	private int fiscalStartingDay;//The starting day of the fiscal year.
	
	public Timesheet()
	{//Default constructor.
		/*
		name = "";
		ID = "";
		shift = 0;
		timeEarned.clear();
		timeTaken.clear();
		timeGiven.clear();
		*/
		timeGiven = new HashMap<String, Integer>();
		tDB = new TimesheetDB();
		openDB( "timesheet.db" );
	}
	
	public Timesheet( String newName, String newID, int newshift )
	{//Default constructor.
		name = newName;
		ID = newID;
		shift = newshift;
		timeEarned.clear();
		timeTaken.clear();
		timeGiven.clear();
		try
		{//Start up the database.
			tDB.setDBFilename( "timesheet.db" );
		}
		catch( Exception e )
		{
			System.err.println( "Error starting the database in regular constructor of Timesheet: " + e.getMessage() );
			e.printStackTrace();
		}
	}
	
	public void openDB( String newDBFilename )
	{//Open a database at the filename newDBFilename
		try
		{//Start up the database.
			tDB.setDBFilename( "timesheet.db" );
			name = tDB.getEmployeeName();
			ID = tDB.getID();
			shift = tDB.getEmployeeShift();
			timeEarned = tDB.getTimeEarned();
			timeTaken = tDB.getTimeTaken();
			/*vacationTimeGiven = tDB.getVacationTimeGiven();
			holidayTimeGiven = tDB.getHolidayTimeGiven();
			floatingHolidayTimeGiven = tDB.getFloatingHolidayTimeGiven();
			personalTimeGiven = tDB.getPersonalTimeGiven();
			*/
			for( String timeType : tDB.getTimeTypes() )
			{//For each of the time types, get the time given.
				timeGiven.put(timeType, tDB.getTimeGiven(timeType) );
			}
			
			fiscalStartingMonth = tDB.getFiscalStartingMonth();
			fiscalStartingDay = tDB.getFiscalStartingDay();
		}
		catch( Exception e )
		{
			System.err.println( "Error opening database " + newDBFilename + " in Timesheet: " + e.getMessage() );
			e.printStackTrace();
		}
	}
	
	public void saveDB()
	{//Save the current data to the database.
		try
		{
			tDB.setPersonalInfo(ID, name, shift, fiscalStartingMonth, fiscalStartingDay);
			//tDB.saveTimeGiven( vacationTimeGiven, holidayTimeGiven, floatingHolidayTimeGiven, personalTimeGiven );
			//timeGiven
			for( Map.Entry<String, Integer> tGiven: timeGiven.entrySet() )
			{//Add each of the times in timeGiven
				tDB.saveTimeGiven(tGiven.getKey(), tGiven.getValue() );
			}
			for( Time tEarned: timeEarned )
			{//Add each of the times in timeEarned
				tDB.addTimeEarned(tEarned);
			}
			for( Time tTaken: timeTaken )
			{//Add each of the times in timeTaken
				tDB.addTimeEarned(tTaken);
			}
		}
		catch( Exception e )
		{
			System.err.println( "Error saving data to database " + tDB.getFilename() + " in Timesheet: " + e.getMessage() );
			e.printStackTrace();
		}
	}
	
	public void saveDB( String newDBFilename )
	{//Save the current data to a database at newDBFilename.
		try
		{
			//tDB.createTables();
			tDB.setDBFilename(newDBFilename);
			saveDB();
		}
		catch( Exception e )
		{
			System.err.println( "Error saving data to database " + tDB.getFilename() + " in Timesheet: " + e.getMessage() );
			e.printStackTrace();
		}
	}
	
	public String getName()
	{//Returns the user's name as a string.
		return name;
	}
	
	public void setName( String newName )
	{//Sets the name to newName.
		name = newName;
	}
	
	public String getID()
	{//Returns the user's ID as a string.
		return ID;
	}
	
	public void setID( String newID )
	{//Sets the ID to newID.
		ID = newID;
	}
	
	public int getShift()
	{//Returns the user's shift as an int (1 for 1st, 2 for 2nd, 3 for 3rd, etc).
		return shift;
	}
	
	public void setShift( int newShift )
	{//Sets the shift to newShift (1 for 1st, 2 for 2nd, 3 for 3rd, etc).
		shift = newShift;
	}
	
	public int getFiscalStartingMonth()
	{//Returns the user's ID as a string.
		return fiscalStartingMonth;
	}
	
	public void setFiscalStartingMonth( int newFiscalStartingMonth )
	{//Sets the ID to newID.
		fiscalStartingMonth = newFiscalStartingMonth;
	}
	
	public int getFiscalStartingDay()
	{//Returns the user's ID as a string.
		return fiscalStartingDay;
	}
	
	public void setFiscalStartingDay( int newFiscalStartingDay )
	{//Sets the ID to newID.
		fiscalStartingDay = newFiscalStartingDay;
	}
	
	public void addTimeEarned( int month, int day, int year, int hours, String type, String cmt )
	{//Add a time earned entry.
		timeEarned.add( new Time(month, day, year, hours, type, cmt, false) );
	}
	
	public void addTimeEarned( Time newTime )
	{//Add a time earned entry. **Note: Does not check if newTime is marked as time taken or earned.**
		timeEarned.add( newTime );
	}
	
	public List<Time> getTimeEarned()
	{//Returns all the time earned as a list of Time objects.
		return timeEarned;
	}
	
	public Time getTimeEarned( int index )
	{//Returns the time earned object at index index
		return timeEarned.get( index );
	}
	
	public int sumTimeEarned( String timeType )
	{//Returns the time earned object at index index
		int sum = 0;
		for( Time t : timeEarned )
		{//Iterate over every time in timeEarned and sum up the ones that match timeType.
			if( t.getType().equals(timeType) )
			{//It matches, so add to the sum.
				sum += t.getHours();
			}
		}
		return sum;
	}
	
	public void addTimeGiven( String newName, int newValue )
	{//Add a time given entry.
		timeGiven.put(newName, newValue);
	}
	
	public void clearTimeGiven()
	{//Add a time given entry.
		timeGiven.clear();
	}
	
	public Map<String, Integer> getTimeGiven()
	{//Returns all the time earned as a list of Time objects.
		return timeGiven;
	}
	
	public int getTimeGiven( String name )
	{//Returns the time earned object at index index
		return (timeGiven.get( name )).intValue();
	}
	
	public void addTimeTaken( int month, int day, int year, int hours, String type, String cmt )
	{//Add a time taken entry.
		timeTaken.add( new Time(month, day, year, hours, type, cmt, true) );
	}
	
	public void addTimeTaken( Time newTime )
	{//Add a time taken entry. **Note: Does not check if newTime is marked as time taken or earned.**
		timeTaken.add( newTime );
	}
	
	public List<Time> getTimeTaken()
	{//Returns all the time taken as a list of Time objects.
		return timeTaken;
	}
	
	public Time getTimeTaken( int index )
	{//Returns the time taken object at index index
		return timeTaken.get( index );
	}
	
	public int sumTimeTaken( String timeType )
	{//Returns the time earned object at index index
		int sum = 0;
		for( Time t : timeTaken )
		{//Iterate over every time in timeEarned and sum up the ones that match timeType.
			if( t.getType().equals(timeType) )
			{//It matches, so add to the sum.
				sum += t.getHours();
			}
		}
		return sum;
	}
	
	public String getTimesheet( int startMonth, int startDay, int startYear, int endMonth, int endDay, int endYear )
	{//Returns a String with the timesheet for the given start and end date with the current data.
		String timesheet = "";
		//Start and ID info
		timesheet += "==============================START OF REPORT=================================================================================================================\n";
		timesheet += "Name: " + getName() + "\tFedEx ID: " + getID() + "\tShift: " + getShift() + "\n\n";
		
		//Time Earned
		timesheet += "TIME EARNED THIS PAY PERIOD";
		timesheet += "==========================================================================\n";
		timesheet += "Type\tHours\tDates Earned\tComments\n";
		for( Time t: timeEarned )
		{//Go through each of the time earned entries and append the text to timesheet.
			timesheet += t.getType() + "\t" + t.getHours() + "\t" + t.getDate() + "\t" + t.getComment() + "\n";
		}
		timesheet += "==========================================================================\n\n\n";
		
		//Time Taken
		timesheet += "TIME TAKEN THIS PAY PERIOD\n";
		timesheet += "==========================================================================\n";
		timesheet += "Type\tHours\tDates Earned\tComments\n";
		for( Time t: timeTaken )
		{//Go through each of the time earned entries and append the text to timesheet.
			timesheet += t.getType() + "\t" + t.getHours() + "\t" + t.getDate() + "\t" + t.getComment() + "\n";
		}
		timesheet += "==========================================================================\n\n\n";
		
		//Time Remaining
		timesheet += "TIME REMAINING IN HOURS\n";
		timesheet += "==========================================================================\n";
		timesheet += "Type\tHours\n";
		
		for( String timeType : tDB.getTimeTypes() )
		{//Go through each of the time types and calculate how many hours are left.
			timesheet += timeType + ": " + ( getTimeGiven(timeType) + sumTimeEarned(timeType) - sumTimeTaken(timeType) ) + "\n";
		}
		
		timesheet += "==============================END OF REPORT=================================================================================================================\n";
		return timesheet;
	}
}
