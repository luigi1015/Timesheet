import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 * Time class for Timesheet
 * Records the date, number of hours and the time type of a specific time taken as well as whether it's time taken or earned.
 * @author jeff
 *
 */
public class Time {
	private GregorianCalendar date;//The date that the time is taken.
	private int numHours;//The number of hours taken.
	private String timeType;//The type of time taken
	private String comment;//A user comment on the time.
	private boolean isTimeTaken;//If it's time taken. If it isn't, it's time earned.
	//private static String[] timeTypes = {"Vacation","Comp Time","Holiday","Floating Holiday","Sick","Personal","Training"};//A list of the different time types available.
	private static List<String> timeTypes = Arrays.asList( "Vacation","Comp Time","Holiday","Floating Holiday","Sick","Personal","Training");//A list of the different time types available.
	
	public Time()
	{
		date = new GregorianCalendar();
		numHours = 0;
		timeType = Time.timeTypes.get(0);
		comment = "";
		isTimeTaken = true;
	}
	
	public Time( int month, int day, int year, int hours, String type, String cmt, boolean isTaken)
	{
		date = new GregorianCalendar(year, month, day);
		numHours = hours;
		timeType = type;
		comment = cmt;
		isTimeTaken = isTaken;
	}
	
	public Time( int newDate, int hours, String type, String cmt, boolean isTaken)
	{
		//System.out.println( "Time: \"" + newDate + "\" \"" + hours + "\" \"" + type + "\" \"" + cmt + "\" \"" + isTaken + "\"" );
		date = new GregorianCalendar();
		setDate( newDate );
		numHours = hours;
		timeType = type;
		comment = cmt;
		isTimeTaken = isTaken;
		//System.out.println( "Time: \"" + getDate() + "\" \"" + numHours + "\" \"" + timeType + "\" \"" + comment + "\" \"" + isTimeTaken + "\"" );
	}
	
	public String getDate( String format )
	{//Returns the date as a string formatted according to the string parameter.
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format( date.getTime() );
	}
	
	public String getDate()
	{//Returns the date as a string in the format MM/dd/yyyy
		return getDate("MM/dd/yyyy");
	}
	
	public int getDateInt()
	{//Returns the date as an int in the format YYYYMMDD.
		return getDay() + getMonth()*100 + getYear()*10000;
	}
	
	public int getMonth()
	{//Returns the month of the date as an integer
		return date.get(GregorianCalendar.MONTH);
	}
	
	public int getDay()
	{//Returns the day of the date as an integer
		return date.get(GregorianCalendar.DAY_OF_MONTH);
	}
	
	public int getYear()
	{//Returns the year of the date as an integer
		return date.get(GregorianCalendar.YEAR);
	}
	
	public void setDate( int month, int day, int year )
	{//Sets the date to the parameter values.
		//date.set(year, month-1, day);
		date.set(year, month, day);
	}
	
	public void setDate( int newDate )
	{//Sets the date from an int in the format YYYYMMDD.
		//System.out.println( "Date: \"" + newDate + "\" \"" + newDate/10000 + "\" \"" + ((newDate/100)%100-1) + "\" \"" + newDate%100 + "\"" );
		//date.set(newDate/10000, ((newDate/100)%100-1), newDate%100);
		date.set(newDate/10000, ((newDate/100)%100), newDate%100);
	}
	
	public void setHours( int hours )
	{//Sets the number of hours to the parameter.
		numHours = hours;
	}
	
	public int getHours()
	{//Returns the number of hours.
		return numHours;
	}
	
	public void setType( String type )
	{//Sets the time type to the parameter.
		timeType = type;
	}
	
	public String getType()
	{//Returns the time type.
		return timeType;
	}
	
	public void setComment( String cmt )
	{//Sets the comment to the parameter.
		comment = cmt;
	}
	
	public String getComment()
	{//Returns the number of hours.
		return comment;
	}
	
	public void setTimeTaken()
	{//Sets the number of hours to the parameter.
		isTimeTaken = true;
	}
	
	public boolean isTimeTaken()
	{//Returns the number of hours.
		return isTimeTaken;
	}
	
	public void setTimeEarned()
	{//Sets the number of hours to the parameter.
		isTimeTaken = false;
	}
	
	public boolean isTimeEarned()
	{//Returns the number of hours.
		return !isTimeTaken;
	}
	
	public static String[] getTimeTypes()
	{//Returns the time types.
		return (String[]) timeTypes.toArray();
	}
	
	public static String getTimeType( int index )
	{//Returns the time types.
		return timeTypes.get( index );
	}
	
	public static void addTimeType( String newType )
	{//Returns the time types.
		timeTypes.add( newType );
	}
	
	public static void clearTimeTypes()
	{//Returns the time types.
		timeTypes.clear();
	}
}
