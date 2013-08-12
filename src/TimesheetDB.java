import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * TimesheetDB interfaces with a SQLite database for Timesheet info.
 * Table: Person
 * EmployeeNumber INTEGER NOT NULL
 * Name TEXT NOT NULL
 * Shift INTEGER
 *
 * Table: TimeTypes
 * Type TEXT PRIMARY KEY
 *
 * Table: TimeGiven
 * TimeType TEXT NOT NULL
 * numHours INTEGER
 *
 * Table: TimeTaken
 * TimeType TEXT NOT NULL
 * Date INTEGER NOT NULL
 * NumHours INTEGER NOT NULL
 * Comment TEXT
 *
 * Table: TimeEarned
 * TimeType TEXT NOT NULL
 * Date INTEGER NOT NULL
 * NumHours INTEGER NOT NULL
 * Comment TEXT
 * @author Jeff Crone
 */

public class TimesheetDB {
	String dbFilename;
	
	public void setDBFilename( String newDBFilename ) throws Exception
	{//Initialize the TimesheetDB with the filename.
		dbFilename = newDBFilename;
		
		try
		{
			Class.forName("org.sqlite.JDBC");//Load the JDBC driver.
			createTables();
		}
		catch (Exception e)
		{
			System.err.println( "Error in DB constructor in TimesheetDB class with filename loading the JDBC driver: " + e.getMessage() );
			//e.printStackTrace();
			throw( e );
		}
	}
	
	public String getFilename()
	{//Return the filename
		return dbFilename;
	}
	
	private void createTables() throws Exception
	{//Create the tables.
		Connection db = null;
		Statement sqlSt = null;
		
		try
		{//Create the tables.
			db = DriverManager.getConnection("jdbc:sqlite:" + dbFilename);
			sqlSt = db.createStatement();
			sqlSt.setQueryTimeout(30);//Set timeout to 30 seconds.
			/*
			sqlSt.executeUpdate( "CREATE TABLE IF NOT EXISTS Timesheet (Version TEXT PRIMARY KEY);" );
			sqlSt.executeUpdate( "CREATE TABLE IF NOT EXISTS Person (ID TEXT NOT NULL, Name TEXT NOT NULL, Shift INTEGER, FiscalStartingMonth INTEGER, FiscalStartingDay INTEGER, PRIMARY KEY(ID));" );
			sqlSt.executeUpdate( "CREATE TABLE IF NOT EXISTS TimeTypes (Type TEXT PRIMARY KEY);" );
			sqlSt.executeUpdate( "CREATE TABLE IF NOT EXISTS TimeGiven (TimeType TEXT NOT NULL, numHours INTEGER, PRIMARY KEY(TimeType));" );
			sqlSt.executeUpdate( "CREATE TABLE IF NOT EXISTS TimeTaken (TimeType TEXT NOT NULL, Date INTEGER NOT NULL, NumHours INTEGER NOT NULL, Comment TEXT, PRIMARY KEY(TimeType, Date));" );
			sqlSt.executeUpdate( "CREATE TABLE IF NOT EXISTS TimeEarned (TimeType TEXT NOT NULL, Date INTEGER NOT NULL, NumHours INTEGER NOT NULL, Comment TEXT, PRIMARY KEY(TimeType, Date));" );
			*/
			
			sqlSt.executeUpdate( "CREATE TABLE IF NOT EXISTS Timesheet (Version TEXT PRIMARY KEY, UNIQUE(Version) ON CONFLICT REPLACE);" );
			sqlSt.executeUpdate( "CREATE TABLE IF NOT EXISTS Person (ID TEXT NOT NULL, Name TEXT NOT NULL, Shift INTEGER, FiscalStartingMonth INTEGER, FiscalStartingDay INTEGER, PRIMARY KEY(ID), UNIQUE(ID) ON CONFLICT REPLACE);" );
			sqlSt.executeUpdate( "CREATE TABLE IF NOT EXISTS TimeTypes (Type TEXT PRIMARY KEY, UNIQUE(Type) ON CONFLICT REPLACE);" );
			sqlSt.executeUpdate( "CREATE TABLE IF NOT EXISTS TimeGiven (TimeType TEXT NOT NULL, numHours INTEGER, PRIMARY KEY(TimeType), UNIQUE(TimeType) ON CONFLICT REPLACE);" );
			sqlSt.executeUpdate( "CREATE TABLE IF NOT EXISTS TimeTaken (TimeType TEXT NOT NULL, Date INTEGER NOT NULL, NumHours INTEGER NOT NULL, Comment TEXT, PRIMARY KEY(TimeType, Date), UNIQUE(TimeType, Date) ON CONFLICT REPLACE);" );
			sqlSt.executeUpdate( "CREATE TABLE IF NOT EXISTS TimeEarned (TimeType TEXT NOT NULL, Date INTEGER NOT NULL, NumHours INTEGER NOT NULL, Comment TEXT, PRIMARY KEY(TimeType, Date), UNIQUE(TimeType, Date) ON CONFLICT REPLACE);" );
			
			for( String timeType : Time.getTimeTypes() )
			{//Add each of the time types to the database.
				addTimeType( timeType );
			}
			
			//Save the timesheet version.
			sqlSt.executeUpdate( "DELETE FROM Timesheet;" );//Delete all the previous version records.
			sqlSt.executeUpdate( "INSERT OR IGNORE INTO Timesheet VALUES( '1.0' );" );
		}
		catch( Exception e )
		{
			System.err.println( "Error creating the tables in TimesheetDB: " + e.getMessage() );
			throw( e );
		}
		finally
		{
			try
			{//Catch any errors closing the database.
				if( db != null )
				{
					db.close();
				}
			}
			catch( Exception e )
			{
				System.err.println( "Error closing the database after creating the tables in TimesheetDB: " + e.getMessage() );
				throw( e );
			}
		}
	}
	
	private void addTimeType( String newTimeType ) throws Exception
	{//Create the tables.
		Connection db = null;
		Statement sqlSt = null;
		
		try
		{//Create the tables.
			db = DriverManager.getConnection("jdbc:sqlite:" + dbFilename);
			sqlSt = db.createStatement();
			sqlSt.setQueryTimeout(30);//Set timeout to 30 seconds.
			sqlSt.executeUpdate( "INSERT OR IGNORE INTO TimeTypes (Type) VALUES (\"" + newTimeType + "\");" );
		}
		catch( Exception e )
		{
			System.err.println( "Error adding a time type in TimesheetDB: " + e.getMessage() );
			throw( e );
		}
		finally
		{
			try
			{//Catch any errors closing the database.
				if( db != null )
				{
					db.close();
				}
			}
			catch( Exception e )
			{
				System.err.println( "Error closing the database after adding a time type in TimesheetDB: " + e.getMessage() );
				throw( e );
			}
		}
	}
	
	public List<String> getTimeTypes()
	{//Saves the personal info to the database.
		Connection db = null;
		Statement sqlSt = null;
		ResultSet results;
		List<String> timeTypes = new ArrayList<String>();
		
		try
		{
			db = DriverManager.getConnection("jdbc:sqlite:" + dbFilename);
			sqlSt = db.createStatement();
			sqlSt.setQueryTimeout(30);//Set timeout to 30 seconds.
			results = sqlSt.executeQuery( "SELECT Type FROM TimeTypes;" );
			while( results.next() )
			{//Get the employee number.
				timeTypes.add( results.getString("Type") );
			}
		}
		catch( Exception e )
		{
			System.err.println( "Error getting info in getTimeTypes in TimesheetDB class: " + e.getMessage() );
		}
		finally
		{
			try
			{//Catch any errors closing the database.
				if( db != null )
				{
					db.close();
				}
			}
			catch( Exception e )
			{
				System.err.println( "Error closing the database in getTimeTypes in TimesheetDB class: " + e.getMessage() );
			}
		}
		
		return timeTypes;
	}
	
	public void setPersonalInfo( String newID, String newName, int newShift, int fiscalStartingMonth, int fiscalStartingDay )
	{//Saves the personal info to the database.
		Connection db = null;
		Statement sqlSt = null;
		
		
		try
		{
			db = DriverManager.getConnection("jdbc:sqlite:" + dbFilename);
			sqlSt = db.createStatement();
			sqlSt.setQueryTimeout(30);//Set timeout to 30 seconds.
			sqlSt.executeUpdate( "DELETE FROM Person;" );//Delete all the existing records.
			sqlSt.executeUpdate( "INSERT OR IGNORE INTO Person (ID, Name, Shift, FiscalStartingMonth, FiscalStartingDay) VALUES( '" + newID + "','" + newName + "'," + newShift + "," + fiscalStartingMonth + "," + fiscalStartingDay + ");" );
		}
		catch( Exception e )
		{
			System.err.println( "Error setting personal info in setPersonalInfo in TimesheetDB class: " + e.getMessage() );
			//throw( e );
		}
		finally
		{
			try
			{//Catch any errors closing the database.
				if( db != null )
				{
					db.close();
				}
			}
			catch( Exception e )
			{
				System.err.println( "Error closing the database in setPersonalInfo in TimesheetDB class: " + e.getMessage() );
				//throw( e );
			}
		}
	}
	
	public String getID()
	{//Saves the personal info to the database.
		Connection db = null;
		Statement sqlSt = null;
		ResultSet results;
		String ID = "";
		
		try
		{
			db = DriverManager.getConnection("jdbc:sqlite:" + dbFilename);
			sqlSt = db.createStatement();
			sqlSt.setQueryTimeout(30);//Set timeout to 30 seconds.
			results = sqlSt.executeQuery( "SELECT ID FROM Person;" );
			while( results.next() )
			{//Get the employee number.
				ID = results.getString("ID");
			}
		}
		catch( Exception e )
		{
			System.err.println( "Error setting personal info in getID in TimesheetDB class: " + e.getMessage() );
		}
		finally
		{
			try
			{//Catch any errors closing the database.
				if( db != null )
				{
					db.close();
				}
			}
			catch( Exception e )
			{
				System.err.println( "Error closing the database in getID in TimesheetDB class: " + e.getMessage() );
			}
		}
		
		return ID;
	}
	
	public String getEmployeeName()
	{//Saves the personal info to the database.
		Connection db = null;
		Statement sqlSt = null;
		ResultSet results;
		String employeeName = "";
		
		try
		{
			db = DriverManager.getConnection("jdbc:sqlite:" + dbFilename);
			sqlSt = db.createStatement();
			sqlSt.setQueryTimeout(30);//Set timeout to 30 seconds.
			results = sqlSt.executeQuery( "SELECT Name FROM Person;" );
			while( results.next() )
			{//Get the employee number.
				employeeName = results.getString("Name");
			}
		}
		catch( Exception e )
		{
			System.err.println( "Error getting name in getEmployeeName in TimesheetDB class: " + e.getMessage() );
		}
		finally
		{
			try
			{//Catch any errors closing the database.
				if( db != null )
				{
					db.close();
				}
			}
			catch( Exception e )
			{
				System.err.println( "Error closing the database in getEmployeeName in TimesheetDB class: " + e.getMessage() );
			}
		}
		
		return employeeName;
	}
	
	public int getEmployeeShift()
	{//Saves the personal info to the database.
		Connection db = null;
		Statement sqlSt = null;
		ResultSet results;
		int shift = 0;
		
		try
		{
			db = DriverManager.getConnection("jdbc:sqlite:" + dbFilename);
			sqlSt = db.createStatement();
			sqlSt.setQueryTimeout(30);//Set timeout to 30 seconds.
			results = sqlSt.executeQuery( "SELECT Shift FROM Person;" );
			while( results.next() )
			{//Get the employee number.
				shift = Integer.parseInt( results.getString("Shift") );
			}
		}
		catch( Exception e )
		{
			System.err.println( "Error getting shift in getEmployeeShift in TimesheetDB class: " + e.getMessage() );
		}
		finally
		{
			try
			{//Catch any errors closing the database.
				if( db != null )
				{
					db.close();
				}
			}
			catch( Exception e )
			{
				System.err.println( "Error closing the database in getEmployeeShift in TimesheetDB class: " + e.getMessage() );
			}
		}
		
		return shift;
	}
	
	public int getFiscalStartingMonth()
	{//Saves the personal info to the database.
		Connection db = null;
		Statement sqlSt = null;
		ResultSet results;
		int fiscalStartingMonth = 0;
		
		try
		{
			db = DriverManager.getConnection("jdbc:sqlite:" + dbFilename);
			sqlSt = db.createStatement();
			sqlSt.setQueryTimeout(30);//Set timeout to 30 seconds.
			results = sqlSt.executeQuery( "SELECT FiscalStartingMonth FROM Person;" );
			while( results.next() )
			{//Get the employee ID.
				fiscalStartingMonth = Integer.parseInt(results.getString("FiscalStartingMonth"));
			}
		}
		catch( Exception e )
		{
			System.err.println( "Error setting personal info in getFiscalStartingMonth in TimesheetDB class: " + e.getMessage() );
		}
		finally
		{
			try
			{//Catch any errors closing the database.
				if( db != null )
				{
					db.close();
				}
			}
			catch( Exception e )
			{
				System.err.println( "Error closing the database in getFiscalStartingMonth in TimesheetDB class: " + e.getMessage() );
			}
		}
		
		return fiscalStartingMonth;
	}
	
	public int getFiscalStartingDay()
	{//Saves the personal info to the database.
		Connection db = null;
		Statement sqlSt = null;
		ResultSet results;
		int fiscalStartingDay = 0;
		
		try
		{
			db = DriverManager.getConnection("jdbc:sqlite:" + dbFilename);
			sqlSt = db.createStatement();
			sqlSt.setQueryTimeout(30);//Set timeout to 30 seconds.
			results = sqlSt.executeQuery( "SELECT FiscalStartingDay FROM Person;" );
			while( results.next() )
			{//Get the employee ID.
				fiscalStartingDay = Integer.parseInt(results.getString("FiscalStartingDay"));
			}
		}
		catch( Exception e )
		{
			System.err.println( "Error setting personal info in getFiscalStartingDay in TimesheetDB class: " + e.getMessage() );
		}
		finally
		{
			try
			{//Catch any errors closing the database.
				if( db != null )
				{
					db.close();
				}
			}
			catch( Exception e )
			{
				System.err.println( "Error closing the database in getFiscalStartingDay in TimesheetDB class: " + e.getMessage() );
			}
		}
		
		return fiscalStartingDay;
	}
	
	public void addTimeEarned( Time newTime )
	{//Saves the personal info to the database.
		Connection db = null;
		Statement sqlSt = null;
		
		try
		{
			db = DriverManager.getConnection("jdbc:sqlite:" + dbFilename);
			sqlSt = db.createStatement();
			sqlSt.setQueryTimeout(30);//Set timeout to 30 seconds.
			sqlSt.executeUpdate( "INSERT OR IGNORE INTO TimeEarned (TimeType, Date, NumHours, Comment) VALUES( '" + newTime.getType() + "'," + newTime.getDateInt() + "," + newTime.getHours() + ",'" + newTime.getComment() + "');" );
		}
		catch( Exception e )
		{
			System.err.println( "Error inserting time in addTimeEarned in TimesheetDB class: " + e.getMessage() );
		}
		finally
		{
			try
			{//Catch any errors closing the database.
				if( db != null )
				{
					db.close();
				}
			}
			catch( Exception e )
			{
				System.err.println( "Error closing the database in addTimeEarned in TimesheetDB class: " + e.getMessage() );
			}
		}
	}
	
	public List<Time> getTimeEarned()
	{//Saves the personal info to the database.
		Connection db = null;
		Statement sqlSt = null;
		ResultSet results;
		List<Time> times = new ArrayList<Time>();
		
		try
		{//Get the data from the database.
			db = DriverManager.getConnection("jdbc:sqlite:" + dbFilename);
			sqlSt = db.createStatement();
			sqlSt.setQueryTimeout(30);//Set timeout to 30 seconds.
			results = sqlSt.executeQuery( "SELECT TimeType, Date, NumHours, Comment FROM TimeEarned;" );
			while( results.next() )
			{//Get the employee number.
				//System.out.println( "Got time for \"" + results.getInt("Date") + "\" \"" + results.getInt("NumHours") + "\" \"" + results.getString("TimeType") + "\" \"" + results.getString("Comment") + "\" \"" + false + "\"");
				//System.out.println( "Got time for \"" + Integer.parseInt(results.getString("Date")) + "\" \"" + Integer.parseInt(results.getString("NumHours")) + "\" \"" + results.getString("TimeType") + "\" \"" + results.getString("Comment") + "\" \"" + false + "\"");
				//Time test1 = new Time(results.getInt("Date"), results.getInt("NumHours"), results.getString("TimeType"), results.getString("Comment"), false);
				//times.add( new Time(Integer.parseInt(results.getString("Date")), Integer.parseInt(results.getString("NumHours")), results.getString("TimeType"), results.getString("Comment"), false) );
				times.add( new Time(results.getInt("Date"), results.getInt("NumHours"), results.getString("TimeType"), results.getString("Comment"), false) );
			}
		}
		catch( Exception e )
		{
			System.err.println( "Error getting info in getTimeEarned in TimesheetDB class: " + e.getMessage() );
		}
		finally
		{
			try
			{//Catch any errors closing the database.
				if( db != null )
				{
					db.close();
				}
			}
			catch( Exception e )
			{
				System.err.println( "Error closing the database in getTimeEarned in TimesheetDB class: " + e.getMessage() );
			}
		}
		
		return times;
	}
	
	public void addTimeTaken( Time newTime )
	{//Saves the personal info to the database.
		Connection db = null;
		Statement sqlSt = null;
		
		try
		{
			db = DriverManager.getConnection("jdbc:sqlite:" + dbFilename);
			sqlSt = db.createStatement();
			sqlSt.setQueryTimeout(30);//Set timeout to 30 seconds.
			sqlSt.executeUpdate( "INSERT OR IGNORE INTO TimeTaken (TimeType, Date, NumHours, Comment) VALUES( '" + newTime.getType() + "'," + newTime.getDateInt() + "," + newTime.getHours() + ",'" + newTime.getComment() + "');" );
		}
		catch( Exception e )
		{
			System.err.println( "Error inserting time in addTimeTaken in TimesheetDB class: " + e.getMessage() );
		}
		finally
		{
			try
			{//Catch any errors closing the database.
				if( db != null )
				{
					db.close();
				}
			}
			catch( Exception e )
			{
				System.err.println( "Error closing the database in addTimeTaken in TimesheetDB class: " + e.getMessage() );
			}
		}
	}
	
	public List<Time> getTimeTaken()
	{//Saves the personal info to the database.
		Connection db = null;
		Statement sqlSt = null;
		ResultSet results;
		List<Time> times = new ArrayList<Time>();
		
		try
		{
			db = DriverManager.getConnection("jdbc:sqlite:" + dbFilename);
			sqlSt = db.createStatement();
			sqlSt.setQueryTimeout(30);//Set timeout to 30 seconds.
			results = sqlSt.executeQuery( "SELECT TimeType, Date, NumHours, Comment FROM TimeTaken;" );
			while( results.next() )
			{//Get the employee number.
				times.add( new Time(Integer.parseInt(results.getString("Date")), Integer.parseInt(results.getString("NumHours")), results.getString("TimeType"), results.getString("Comment"), true) );
			}
		}
		catch( Exception e )
		{
			System.err.println( "Error getting info in getTimeTaken in TimesheetDB class: " + e.getMessage() );
		}
		finally
		{
			try
			{//Catch any errors closing the database.
				if( db != null )
				{
					db.close();
				}
			}
			catch( Exception e )
			{
				System.err.println( "Error closing the database in getTimeTaken in TimesheetDB class: " + e.getMessage() );
			}
		}
		
		return times;
	}
	
	//public void saveTimeGiven( int vacationTime, int holidayTime, int floatingHolidayTime, int personalTime )
	public void saveTimeGiven( String timeType, int timeGiven )
	{//Save the types of time given indicated by timeType to the database.
		Connection db = null;
		Statement sqlSt = null;
		
		try
		{
			db = DriverManager.getConnection("jdbc:sqlite:" + dbFilename);
			sqlSt = db.createStatement();
			sqlSt.setQueryTimeout(30);//Set timeout to 30 seconds.
			sqlSt.executeUpdate( "INSERT OR REPLACE INTO TimeGiven (TimeType, numHours) VALUES( '" + timeType + "'," + timeGiven + ");" );
			//sqlSt.executeUpdate( "INSERT OR REPLACE INTO TimeGiven VALUES( 'Vacation'," + vacationTime + ");" );
			//sqlSt.executeUpdate( "INSERT OR REPLACE INTO TimeGiven VALUES( 'Holiday'," + holidayTime + ");" );
			//sqlSt.executeUpdate( "INSERT OR REPLACE INTO TimeGiven VALUES( 'Floating Holiday'," + floatingHolidayTime + ");" );
			//sqlSt.executeUpdate( "INSERT OR REPLACE INTO TimeGiven VALUES( 'Personal'," + personalTime + ");" );
		}
		catch( Exception e )
		{
			System.err.println( "Error inserting time in saveTimeGiven in TimesheetDB class: " + e.getMessage() );
		}
		finally
		{
			try
			{//Catch any errors closing the database.
				if( db != null )
				{
					db.close();
				}
			}
			catch( Exception e )
			{
				System.err.println( "Error closing the database in saveTimeGiven in TimesheetDB class: " + e.getMessage() );
			}
		}
	}
	
	public int getTimeGiven( String timeType )
	{//Returns the time given indicated by timeType.
		Connection db = null;
		Statement sqlSt = null;
		ResultSet results;
		int numHours = 0;
		
		try
		{
			db = DriverManager.getConnection("jdbc:sqlite:" + dbFilename);
			sqlSt = db.createStatement();
			sqlSt.setQueryTimeout(30);//Set timeout to 30 seconds.
			results = sqlSt.executeQuery( "SELECT numHours FROM TimeGiven WHERE TimeType='" + timeType + "';" );
			while( results.next() )
			{//Get the employee number.
				numHours = Integer.parseInt( results.getString("numHours") );
			}
		}
		catch( Exception e )
		{
			System.err.println( "Error getting info in getTimeGiven in TimesheetDB class: " + e.getMessage() );
		}
		finally
		{
			try
			{//Catch any errors closing the database.
				if( db != null )
				{
					db.close();
				}
			}
			catch( Exception e )
			{
				System.err.println( "Error closing the database in getTimeGiven in TimesheetDB class: " + e.getMessage() );
			}
		}
		
		return numHours;
	}
}
