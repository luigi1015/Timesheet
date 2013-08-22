import javax.swing.JFrame;
//import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.ScrollPaneConstants;
//import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerListModel;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
//import javax.swing.border.Border;
//import javax.swing.border.LineBorder;
//import javax.swing.BorderFactory;
//import java.awt.Color;
//import javax.swing.JPanel;
import java.awt.event.KeyEvent;
//import java.util.Calendar;
//import java.util.Date;

/**
 * Timesheet GUI
 * @author Jeff Crone
 *
 */
public class TimesheetGUI extends JFrame implements ActionListener {
	
	static final long serialVersionUID = 42L;//Eclipse says a definition of serialVersionUID is necessary for serializable classes.
	
	private static final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};//Months in the year, for date gui
	
	private Timesheet ts;//Processes the timesheet data.
	
	private JFileChooser fc;
	
	private JPanel mainPanel;//The main panel.
	
	//File menu
	private JMenuBar mainMenuBar;//Main menu
	private JMenu fileMenu;//File menu
	private JMenuItem openMenuItem;//Open file menu button
	private JMenuItem saveMenuItem;//Save file menu button
	private JMenuItem saveAsMenuItem;//Save file menu button
	private JMenuItem exitMenuItem;//Exit file menu button
	
	//Tabbed Pane
	private JTabbedPane mainTabbedPane;//Main tabs.
	private JPanel personalPanel;//Tab for personal tab.
	private JPanel timeEarnedPanel;//Tab for time earned.
	private JPanel timeTakenPanel;//Tab for time taken.
	private JPanel timesheetPanel;//Tab for the timesheet.
	
	//Personal Info Panel
	private JLabel nameLabel;//Name label
	private JTextField nameTextField;//Name textfield
	private JLabel IDLabel;//Employee ID label
	private JTextField IDTextField;//Employee ID textfield
	private ButtonGroup shiftGroup;//Shift radio button group
	private JRadioButton firstShiftRadioButton;//Radio button for first shift
	private JRadioButton secondShiftRadioButton;//Radio button for second shift
	private JRadioButton thirdShiftRadioButton;//Radio button for third shift
	private JLabel vacationInitialHoursLabel;//Vacation initial label
	private JSpinner vacationInitialHoursSpinner;//Vacation initial spinner
	private JLabel holidayInitialHoursLabel;//Holiday initial label
	private JSpinner holidayInitialHoursSpinner;//Holiday initial spinner
	private JLabel floatingholidayInitialHoursLabel;//Floating Holiday initial label
	private JSpinner floatingholidayInitialHoursSpinner;//Floating Holiday initial spinner
	private JLabel personalInitialHoursLabel;//Personal initial label
	private JSpinner personalInitialHoursSpinner;//Personal initial spinner
	private JLabel fiscalStartLabel;//Start of the fiscal year label
	private JSpinner fiscalStartMonthSpinner;//Starting month of the fiscal year spinner
	private JSpinner fiscalStartDaySpinner;//Starting day of the fiscal year spinner
	private JSpinner fiscalStartYearSpinner;//Starting year of the fiscal year spinner
	//private JSpinner fiscalStartDateSpinner;//Starting date of the fiscal year.
	//private SpinnerDateModel fiscalStartDateModel;//Starting date model.
	
	//Time Earned Panel
	private JScrollPane timeEarnedScrollPane;//Scroll pane for the time earned table.
	private JTable timeEarnedTable;//Lists the time earned.
	private DefaultTableModel timeEarnedDataModel;//The data for the time earned table.
	private JLabel addTimeEarnedLabel;//Add time earned label
	private JLabel addTimeEarnedMonthLabel;//Add time earned month label
	private JLabel addTimeEarnedDayLabel;//Add time earned day label
	private JLabel addTimeEarnedYearLabel;//Add time earned year label
	//private JSpinner addTimeEarnedDateSpinner;//Add time earned date spinner
	//private SpinnerDateModel addTimeEarnedDateModel;//Add time earned date Model
	private JLabel addTimeEarnedHoursLabel;//Add time earned hours label
	private JLabel addTimeEarnedCommentLabel;//Add time earned comment label
	private JLabel addTimeEarnedTimeTypeLabel;//Add time earned Time Type label
	private JSpinner addTimeEarnedMonthSpinner;//Month of the time earned spinner
	private JSpinner addTimeEarnedDaySpinner;//Day of the  time earned spinner
	private JSpinner addTimeEarnedYearSpinner;//Year of the time earned spinner
	private JSpinner addTimeEarnedHoursSpinner;//Number of hours earned spinner
	private JSpinner addTimeEarnedTimeTypeSpinner;//Time Types spinner
	private JTextField addTimeEarnedCommentTextField;//Comment on the time earned
	private JButton addTimeEarnedButton;//To execute the add for time earned.
	
	//Time Taken Panel
	private JTable timeTakenTable;//Lists the time taken.
	private JScrollPane timeTakenScrollPane;//Scroll pane for the time earned table.
	private DefaultTableModel timeTakenDataModel;//The data for the time earned table.
	private JLabel addTimeTakenLabel;//Add time taken label
	private JLabel addTimeTakenMonthLabel;//Add time taken month label
	private JLabel addTimeTakenDayLabel;//Add time taken day label
	private JLabel addTimeTakenYearLabel;//Add time taken year label
	//private JSpinner addTimeTakenDateSpinner;//Add time taken date spinner
	//private SpinnerDateModel addTimeTakenDateModel;//Add time taken date Model
	private JLabel addTimeTakenHoursLabel;//Add time taken hours label
	private JLabel addTimeTakenCommentLabel;//Add time taken comment label
	private JLabel addTimeTakenTimeTypeLabel;//Add time taken Time Type label
	private JSpinner addTimeTakenMonthSpinner;//Month of the time taken spinner
	private JSpinner addTimeTakenDaySpinner;//Day of the  time taken spinner
	private JSpinner addTimeTakenYearSpinner;//Year of the time taken spinner
	private JSpinner addTimeTakenHoursSpinner;//Number of hours taken spinner
	private JSpinner addTimeTakenTimeTypeSpinner;//Time Types spinner
	private JTextField addTimeTakenCommentTextField;//Comment on the time earned
	private JButton addTimeTakenButton;//To execute the add for time taken.
	
	//Timesheet Panel
	//private JLabel timesheetLabel;//Holds the text of the timesheet.
	private JTextArea timesheetTextArea;//Holds the text of the timesheet.
	private JScrollPane timesheetScrollPane;//Holds the timesheet text area and provides scroll panes.
	private JLabel timesheetStartLabel;//Timesheet Start Date label
	private JLabel timesheetStartMonthLabel;//Timesheet Start Month label
	private JLabel timesheetStartDayLabel;//Timesheet Start Day label
	private JLabel timesheetStartYearLabel;//Timesheet Start Year label
	private JSpinner timesheetStartMonthSpinner;//Timesheet Start Month spinner
	private JSpinner timesheetStartDaySpinner;//Timesheet Start Day spinner
	private JSpinner timesheetStartYearSpinner;//Timesheet Start Year spinner
	//private JSpinner timesheetStartDateSpinner;//Timesheet Start Date spinner
	//private SpinnerDateModel timesheetStartDateModel;//Timesheet Start Date Model
	private JLabel timesheetEndLabel;//Timesheet End Date label
	//private JSpinner timesheetEndDateSpinner;//Timesheet Start Date spinner
	//private SpinnerDateModel timesheetEndDateModel;//Timesheet Start Date Model
	private JLabel timesheetEndMonthLabel;//Timesheet End Month label
	private JLabel timesheetEndDayLabel;//Timesheet End Day label
	private JLabel timesheetEndYearLabel;//Timesheet End Year label
	private JSpinner timesheetEndMonthSpinner;//Timesheet End Month spinner
	private JSpinner timesheetEndDaySpinner;//Timesheet End Day spinner
	private JSpinner timesheetEndYearSpinner;//Timesheet End Year spinner
	private JButton createTimesheetButton;//To create and display the timesheet.
	
	//private JButton okButton;//Temp Button
	
	public TimesheetGUI()
	{
		fc = new JFileChooser();
		ts = new Timesheet();
		initGUI();
		openDB();
	}
	
	private final void initGUI()
	{//Initialize the GUI.
		//Window setup
		setTitle( "Timesheet" );//Set the title.
		setSize( 500, 700 );//Set the size of the window to 500 by 600 pixels.
		setLocationRelativeTo(null);//Set the window to the center.
		setDefaultCloseOperation( EXIT_ON_CLOSE );//Set the windows to close on clicking the close button.
		
		//Main Panel Setup
		mainPanel = new JPanel();//Create the main panel.
		mainPanel.setLayout( new BorderLayout() );//Set the layout to the default.
		getContentPane().add( mainPanel );
		
		//Tabbed Pane Setup
		createPersonalPanel();
		createTimeEarnedPanel();
		createTimeTakenPanel();
		createTimesheetPanel();
		mainTabbedPane = new JTabbedPane();
		mainTabbedPane.addTab( "Personal Info", personalPanel );
		mainTabbedPane.addTab( "Time Earned", timeEarnedPanel );
		mainTabbedPane.addTab( "Time Taken", timeTakenPanel );
		mainTabbedPane.addTab( "Timesheet", timesheetPanel );
		//mainTabbedPane.setPreferredSize(new Dimension(200,200));
		//mainTabbedPane.setLocation(50,0);
		mainPanel.add( mainTabbedPane );

		
		//Menu Bar setup
		createFileMenu();
		/*
		mainMenuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setMnemonic(KeyEvent.VK_C);
		exitMenuItem.setToolTipText("Exit Timesheet");
		exitMenuItem.addActionListener( new ActionListener() 
		{//Define the ActionListener function.
			public void actionPerformed(ActionEvent event)
			{//Just exit the program.
				System.exit(0);
			}
		});
		
		fileMenu.add(exitMenuItem);
		mainMenuBar.add(fileMenu);
		setJMenuBar(mainMenuBar);
		*/
	}
	
	private void createFileMenu()
	{//Menu Bar setup
		mainMenuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		//Open button
		openMenuItem = new JMenuItem("Open");
		openMenuItem.setMnemonic(KeyEvent.VK_O);
		openMenuItem.setToolTipText("Open Database File");
		openMenuItem.addActionListener( new ActionListener() 
		{//Define the ActionListener function.
			public void actionPerformed(ActionEvent event)
			{//Open a user chosen database.
				FileNameExtensionFilter fileFilter = new FileNameExtensionFilter( "SQLite db files", "db");//Create the file filter for the SQLite database files.
				fc.setFileFilter( fileFilter );//Set the filter on the file chooser.
				//FileNameExtensionFilter fileFilter = new FileNameExtensionFilter( "JPG & GIF Images", "jpg", "gif");
				//int returnVal = fc.showDialog(TimesheetGUI.this, "Open Database");//Show the open file dialog.
				int returnVal = fc.showOpenDialog(TimesheetGUI.this);//Show the open file dialog.
				if( returnVal == JFileChooser.APPROVE_OPTION )
				{//If the user clicked open, open the database file.
					openDB( fc.getSelectedFile().getName() );
				}
			}
		});
		fileMenu.add(openMenuItem);
		
		//Save button
		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setMnemonic(KeyEvent.VK_S);
		saveMenuItem.setToolTipText("Save Database File");
		saveMenuItem.addActionListener( new ActionListener() 
		{//Define the ActionListener function.
			public void actionPerformed(ActionEvent event)
			{//Just exit the program.
				saveDB();
			}
		});
		fileMenu.add(saveMenuItem);
		
		//Save As button
		saveAsMenuItem = new JMenuItem("Save As...");
		//saveAsMenuItem.setMnemonic(KeyEvent.VK_S);
		saveAsMenuItem.setToolTipText("Save Database File");
		saveAsMenuItem.addActionListener( new ActionListener() 
		{//Define the ActionListener function.
			public void actionPerformed(ActionEvent event)
			{//Just exit the program.
				FileNameExtensionFilter fileFilter = new FileNameExtensionFilter( "SQLite db files", "db");//Create the file filter for the SQLite database files.
				fc.setFileFilter( fileFilter );//Set the filter on the file chooser.
				//FileNameExtensionFilter fileFilter = new FileNameExtensionFilter( "JPG & GIF Images", "jpg", "gif");
				int returnVal = fc.showSaveDialog(TimesheetGUI.this);//Show the open file dialog.
				//int returnVal = fc.showDialog(TimesheetGUI.this, "Open Database");//Show the open file dialog.
				if( returnVal == JFileChooser.APPROVE_OPTION )
				{//If the user clicked open, open the database file.
					saveDB( fc.getSelectedFile().getName() );
				}
			}
		});
		fileMenu.add(saveAsMenuItem);
		
		fileMenu.addSeparator();//Separator
		
		//Exit button
		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setMnemonic(KeyEvent.VK_C);
		exitMenuItem.setToolTipText("Exit Timesheet");
		exitMenuItem.addActionListener( new ActionListener() 
		{//Define the ActionListener function.
			public void actionPerformed(ActionEvent event)
			{//Just exit the program.
				System.exit(0);
			}
		});
		
		fileMenu.add(exitMenuItem);
		mainMenuBar.add(fileMenu);
		setJMenuBar(mainMenuBar);
		
	}
	
	private void createPersonalPanel()
	{//Initialize the personal tab.
		personalPanel = new JPanel();
		personalPanel.setLayout(null);
		
		/*
		//Temp Button
		okButton = new JButton("Ok");//Create the button
		okButton.setBounds( 30, 35, 80, 25 );
		okButton.addActionListener(this);
		personalPanel.add( okButton );
		*/
		
		//Name info
		nameLabel = new JLabel("Name:");
		nameLabel.setBounds(20, 20, 50, 25 );
		personalPanel.add(nameLabel);
		nameTextField = new JTextField();
		nameTextField.setBounds(80, 20, 80, 25 );
		personalPanel.add(nameTextField);
		
		//ID info
		IDLabel = new JLabel("ID: ");
		IDLabel.setBounds(20, 50, 30, 25);
		personalPanel.add(IDLabel);
		IDTextField = new JTextField();
		IDTextField.setBounds(80, 50, 80, 25);
		personalPanel.add(IDTextField);
		
		//Shift Info
		firstShiftRadioButton = new JRadioButton("1st");
		secondShiftRadioButton = new JRadioButton("2nd");
		thirdShiftRadioButton = new JRadioButton("3rd");
		//Add to the radio group
		shiftGroup = new ButtonGroup();
		shiftGroup.add(firstShiftRadioButton);
		shiftGroup.add(secondShiftRadioButton);
		shiftGroup.add(thirdShiftRadioButton);
		//Add to the panel
		firstShiftRadioButton.setBounds(20, 80, 80, 25);
		secondShiftRadioButton.setBounds(20, 110, 80, 25);
		thirdShiftRadioButton.setBounds(20, 140, 80, 25);
		personalPanel.add(firstShiftRadioButton);
		personalPanel.add(secondShiftRadioButton);
		personalPanel.add(thirdShiftRadioButton);
		
		//Initial hours given for the year
		//Vacation
		vacationInitialHoursLabel = new JLabel("Initial Vacation: ");
		vacationInitialHoursLabel.setBounds(220, 20, 120, 25);
		personalPanel.add(vacationInitialHoursLabel);
		vacationInitialHoursSpinner = new JSpinner(new SpinnerNumberModel(0,0,300,1));//Create a spinner that will use numbers (SpinnerNumberModel) with initial balue of 0, minimum value of 0, max value of 300, and step of 1.
		vacationInitialHoursSpinner.setBounds(390, 20, 50, 25);
		personalPanel.add(vacationInitialHoursSpinner);
		//Holiday
		holidayInitialHoursLabel = new JLabel("Initial Holiday: ");
		holidayInitialHoursLabel.setBounds(220, 50, 120, 25);
		personalPanel.add(holidayInitialHoursLabel);
		holidayInitialHoursSpinner = new JSpinner(new SpinnerNumberModel(0,0,300,1));//Create a spinner that will use numbers (SpinnerNumberModel) with initial balue of 0, minimum value of 0, max value of 300, and step of 1.
		holidayInitialHoursSpinner.setBounds(390, 50, 50, 25);
		personalPanel.add(holidayInitialHoursSpinner);
		//Floating Holiday
		floatingholidayInitialHoursLabel = new JLabel("Initial Floating Holiday: ");
		floatingholidayInitialHoursLabel.setBounds(220, 80, 170, 25);
		personalPanel.add(floatingholidayInitialHoursLabel);
		floatingholidayInitialHoursSpinner = new JSpinner(new SpinnerNumberModel(0,0,300,1));//Create a spinner that will use numbers (SpinnerNumberModel) with initial balue of 0, minimum value of 0, max value of 300, and step of 1.
		floatingholidayInitialHoursSpinner.setBounds(390, 80, 50, 25);
		personalPanel.add(floatingholidayInitialHoursSpinner);
		//Personal
		personalInitialHoursLabel = new JLabel("Initial Personal: ");
		personalInitialHoursLabel.setBounds(220, 110, 120, 25);
		personalPanel.add(personalInitialHoursLabel);
		personalInitialHoursSpinner = new JSpinner(new SpinnerNumberModel(0,0,300,1));//Create a spinner that will use numbers (SpinnerNumberModel) with initial balue of 0, minimum value of 0, max value of 300, and step of 1.
		personalInitialHoursSpinner.setBounds(390, 110, 50, 25);
		personalPanel.add(personalInitialHoursSpinner);
		
		//Fiscal year info
		fiscalStartLabel = new JLabel("Start of the fiscal year: ");
		fiscalStartLabel.setBounds(220, 170, 170, 25);
		personalPanel.add(fiscalStartLabel);
		fiscalStartMonthSpinner = new JSpinner( new SpinnerListModel(months) );//Create a spinner using the text from months as items.
		fiscalStartMonthSpinner.setBounds(220, 200, 90, 25);
		personalPanel.add(fiscalStartMonthSpinner);
		fiscalStartDaySpinner = new JSpinner( new SpinnerNumberModel(1,1,31,1) );
		fiscalStartDaySpinner.setBounds(320, 200, 40, 25);
		personalPanel.add(fiscalStartDaySpinner);
		fiscalStartYearSpinner = new JSpinner( new SpinnerNumberModel(2013,2010,2050,1) );
		fiscalStartYearSpinner.setEditor( new JSpinner.NumberEditor(fiscalStartYearSpinner, "####")  );
		fiscalStartYearSpinner.setBounds(370, 200, 50, 25);
		personalPanel.add(fiscalStartYearSpinner);
		
		/*
		//Test calendar spinner.
		Calendar cal = Calendar.getInstance();
		Date initDate = cal.getTime();
		cal.add(Calendar.YEAR, -100);
		Date firstDate = cal.getTime();
		cal.add(Calendar.YEAR, 200);
		Date lastDate = cal.getTime();
		fiscalStartDateModel = new SpinnerDateModel(initDate,firstDate,lastDate,Calendar.DAY_OF_MONTH);
		fiscalStartDateSpinner = new JSpinner( fiscalStartDateModel );
		fiscalStartDateSpinner.setEditor( new JSpinner.DateEditor(fiscalStartDateSpinner, "MM/dd/yyyy")  );
		fiscalStartDateSpinner.setBounds(220, 170, 90, 25);
		personalPanel.add(fiscalStartDateSpinner);
		*/
	}
	
	private void createTimeEarnedPanel()
	{//Initialize the Time Earned tab.
		String colNames[] = {"Date", "Hours", "Time Type", "Comment"};
		//String data[][] = {{"Date", "Hours", "Time Type", "Comment"},{"Date", "Hours", "Time Type", "Comment"}};
		
		timeEarnedPanel = new JPanel();
		timeEarnedPanel.setLayout(null);
		
		timeEarnedDataModel = new DefaultTableModel(colNames, 0);//Create a data model with column names from colnames and 0 rows.
		timeEarnedTable = new JTable(timeEarnedDataModel);
		//timeEarnedDataModel.addRow(new String[]{"1","2","3","4"});
		
		timeEarnedScrollPane = new JScrollPane(timeEarnedTable);
		timeEarnedScrollPane.setBounds(0, 0, 450, 400);
		timeEarnedPanel.add(timeEarnedScrollPane);
		
		//Set up the stuff to add time earned.
		addTimeEarnedLabel = new JLabel( "Add time earned:" );
		addTimeEarnedLabel.setBounds(20, 425, 170, 25);
		timeEarnedPanel.add(addTimeEarnedLabel);
		addTimeEarnedMonthLabel = new JLabel( "Month" );
		addTimeEarnedMonthLabel.setBounds(20, 450, 90, 25);
		timeEarnedPanel.add(addTimeEarnedMonthLabel);
		addTimeEarnedDayLabel = new JLabel( "Day" );
		addTimeEarnedDayLabel.setBounds(130, 450, 50, 25);
		timeEarnedPanel.add(addTimeEarnedDayLabel);
		addTimeEarnedYearLabel = new JLabel( "Year" );
		addTimeEarnedYearLabel.setBounds(180, 450, 50, 25);
		timeEarnedPanel.add(addTimeEarnedYearLabel);
		addTimeEarnedHoursLabel = new JLabel( "<html>Hours<br>Earned</html>" );
		addTimeEarnedHoursLabel.setBounds(250, 425, 60, 50);
		timeEarnedPanel.add( addTimeEarnedHoursLabel );
		addTimeEarnedCommentLabel = new JLabel( "Comment" );
		addTimeEarnedCommentLabel.setBounds(320, 450, 100, 25);
		timeEarnedPanel.add(addTimeEarnedCommentLabel);
		addTimeEarnedMonthSpinner = new JSpinner( new SpinnerListModel(months) );
		addTimeEarnedMonthSpinner.setBounds(20, 475, 90, 25);
		timeEarnedPanel.add(addTimeEarnedMonthSpinner);
		addTimeEarnedDaySpinner = new JSpinner( new SpinnerNumberModel(1,1,31,1) );
		addTimeEarnedDaySpinner.setBounds(130, 475, 40, 25);
		timeEarnedPanel.add(addTimeEarnedDaySpinner);
		addTimeEarnedYearSpinner = new JSpinner( new SpinnerNumberModel(2013,2010,2050,1) );
		addTimeEarnedYearSpinner.setEditor( new JSpinner.NumberEditor(addTimeEarnedYearSpinner, "####")  );
		addTimeEarnedYearSpinner.setBounds(180, 475, 60, 25);
		timeEarnedPanel.add(addTimeEarnedYearSpinner);
		/*
		Calendar cal = Calendar.getInstance();
		Date initDate = cal.getTime();
		cal.add(Calendar.YEAR, -100);
		Date firstDate = cal.getTime();
		cal.add(Calendar.YEAR, 200);
		Date lastDate = cal.getTime();
		addTimeEarnedDateModel = new SpinnerDateModel(initDate,firstDate,lastDate,Calendar.DAY_OF_MONTH);
		addTimeEarnedDateSpinner = new JSpinner( addTimeEarnedDateModel );
		addTimeEarnedDateSpinner.setEditor( new JSpinner.DateEditor(addTimeEarnedDateSpinner, "MM/dd/yyyy")  );
		addTimeEarnedDateSpinner.setBounds(20, 450, 90, 25);
		timeEarnedPanel.add(addTimeEarnedDateSpinner);
		*/
		addTimeEarnedHoursSpinner = new JSpinner( new SpinnerNumberModel(0,0,24,1) );
		addTimeEarnedHoursSpinner.setBounds(250, 475, 40, 25);
		timeEarnedPanel.add(addTimeEarnedHoursSpinner);
		addTimeEarnedCommentTextField = new JTextField();
		addTimeEarnedCommentTextField.setBounds(320, 475, 150, 25);
		timeEarnedPanel.add(addTimeEarnedCommentTextField);
		addTimeEarnedTimeTypeLabel = new JLabel( "Type" );
		addTimeEarnedTimeTypeLabel.setBounds(20, 510, 50, 25);
		timeEarnedPanel.add(addTimeEarnedTimeTypeLabel);
		addTimeEarnedTimeTypeSpinner = new JSpinner( new SpinnerListModel(Time.getTimeTypes()) );
		addTimeEarnedTimeTypeSpinner.setBounds(20, 540, 120, 25);
		timeEarnedPanel.add(addTimeEarnedTimeTypeSpinner);
		addTimeEarnedButton = new JButton("Add");//Create the button to add time earned.
		addTimeEarnedButton.setBounds( 30, 570, 80, 25 );
		//addTimeEarnedButton.addActionListener(this);
		addTimeEarnedButton.addActionListener(new ActionListener() 
		{//Define the ActionListener function.
			public void actionPerformed(ActionEvent event)
			{//Add the time earned
				try
				{
					//timeEarnedDataModel.addRow(new Object[]{getNumMonth((String)addTimeEarnedMonthSpinner.getValue()) + "/" + ((Integer)addTimeEarnedDaySpinner.getValue()).toString() + "/" + ((Integer)addTimeEarnedYearSpinner.getValue()).toString(), ((Integer)addTimeEarnedHoursSpinner.getValue()).toString(), (String)addTimeEarnedTimeTypeSpinner.getValue(), addTimeEarnedCommentTextField.getText()});
					addTimeEarned( (String)addTimeEarnedMonthSpinner.getValue(), (Integer)addTimeEarnedDaySpinner.getValue(), (Integer)addTimeEarnedYearSpinner.getValue(), (Integer)addTimeEarnedHoursSpinner.getValue(), (String)addTimeEarnedTimeTypeSpinner.getValue(), addTimeEarnedCommentTextField.getText());
				}
				catch( Exception e )
				{
					System.err.println( "While adding the time earned: " + e.getMessage() );
				}
				//timeEarnedDataModel.addRow(new Object[]{((String)addTimeEarnedMonthSpinner.getValue()) + " " + ((Integer)addTimeEarnedDaySpinner.getValue()).toString() + ", " + ((Integer)addTimeEarnedYearSpinner.getValue()).toString(), ((Integer)addTimeEarnedHoursSpinner.getValue()).toString(), (String)addTimeEarnedTimeTypeSpinner.getValue(), addTimeEarnedCommentTextField.getText()});
			}
		});
		timeEarnedPanel.add( addTimeEarnedButton );
	}
	
	private void addTimeEarned( String month, Integer day, Integer year, Integer hours, String timeType, String comment ) throws Exception
	{//Add time earned to the GUI and the Timesheet object.
		timeEarnedDataModel.addRow(new Object[]{getNumMonth(month) + "/" + day.toString() + "/" + year.toString(), hours.toString(), timeType, comment});
		ts.addTimeEarned(getNumMonth(month), day.intValue(), year.intValue(), hours.intValue(), timeType, comment);
	}
	
	private void createTimeTakenPanel()
	{//Initialize the Time Taken tab.
		String colNames[] = {"Date", "Hours", "Time Type", "Comment"};
		timeTakenPanel = new JPanel();
		timeTakenPanel.setLayout(null);
		
		timeTakenDataModel = new DefaultTableModel(colNames, 0);//Create a data model with column names from colnames and 0 rows.
		timeTakenTable = new JTable(timeTakenDataModel);
		//timeEarnedDataModel.addRow(new String[]{"1","2","3","4"});
		
		timeTakenScrollPane = new JScrollPane(timeTakenTable);
		timeTakenScrollPane.setBounds(0, 0, 450, 400);
		timeTakenPanel.add(timeTakenScrollPane);
		
		//Set up the stuff to add time earned.
		addTimeTakenLabel = new JLabel( "Add time earned:" );
		addTimeTakenLabel.setBounds(20, 425, 170, 25);
		timeTakenPanel.add(addTimeTakenLabel);
		addTimeTakenMonthLabel = new JLabel( "Month" );
		addTimeTakenMonthLabel.setBounds(20, 450, 90, 25);
		timeTakenPanel.add(addTimeTakenMonthLabel);
		addTimeTakenDayLabel = new JLabel( "Day" );
		addTimeTakenDayLabel.setBounds(130, 450, 50, 25);
		timeTakenPanel.add(addTimeTakenDayLabel);
		addTimeTakenYearLabel = new JLabel( "Year" );
		addTimeTakenYearLabel.setBounds(180, 450, 50, 25);
		timeTakenPanel.add(addTimeTakenYearLabel);
		addTimeTakenHoursLabel = new JLabel( "<html>Hours<br>Taken</html>" );
		addTimeTakenHoursLabel.setBounds(250, 425, 60, 50);
		timeTakenPanel.add( addTimeTakenHoursLabel );
		addTimeTakenCommentLabel = new JLabel( "Comment" );
		addTimeTakenCommentLabel.setBounds(320, 450, 100, 25);
		timeTakenPanel.add(addTimeTakenCommentLabel);
		addTimeTakenMonthSpinner = new JSpinner( new SpinnerListModel(months) );
		addTimeTakenMonthSpinner.setBounds(20, 475, 90, 25);
		timeTakenPanel.add(addTimeTakenMonthSpinner);
		addTimeTakenDaySpinner = new JSpinner( new SpinnerNumberModel(1,1,31,1) );
		addTimeTakenDaySpinner.setBounds(130, 475, 40, 25);
		timeTakenPanel.add(addTimeTakenDaySpinner);
		addTimeTakenYearSpinner = new JSpinner( new SpinnerNumberModel(2013,2010,2050,1) );
		addTimeTakenYearSpinner.setEditor( new JSpinner.NumberEditor(addTimeTakenYearSpinner, "####")  );
		addTimeTakenYearSpinner.setBounds(180, 475, 60, 25);
		timeTakenPanel.add(addTimeTakenYearSpinner);
		/*
		//Test calendar spinner.
		Calendar cal = Calendar.getInstance();
		Date initDate = cal.getTime();
		cal.add(Calendar.YEAR, -100);
		Date firstDate = cal.getTime();
		cal.add(Calendar.YEAR, 200);
		Date lastDate = cal.getTime();
		addTimeTakenDateModel = new SpinnerDateModel(initDate,firstDate,lastDate,Calendar.DAY_OF_MONTH);
		addTimeTakenDateSpinner = new JSpinner( addTimeTakenDateModel );
		addTimeTakenDateSpinner.setEditor( new JSpinner.DateEditor(addTimeTakenDateSpinner, "MM/dd/yyyy")  );
		addTimeTakenDateSpinner.setBounds(220, 300, 90, 25);
		timeTakenPanel.add(addTimeTakenDateSpinner);
		*/
		addTimeTakenHoursSpinner = new JSpinner( new SpinnerNumberModel(0,0,24,1) );
		addTimeTakenHoursSpinner.setBounds(250, 475, 40, 25);
		timeTakenPanel.add(addTimeTakenHoursSpinner);
		addTimeTakenCommentTextField = new JTextField();
		addTimeTakenCommentTextField.setBounds(320, 475, 150, 25);
		timeTakenPanel.add(addTimeTakenCommentTextField);
		addTimeTakenTimeTypeLabel = new JLabel( "Type" );
		addTimeTakenTimeTypeLabel.setBounds(20, 510, 50, 25);
		timeTakenPanel.add(addTimeTakenTimeTypeLabel);
		addTimeTakenTimeTypeSpinner = new JSpinner( new SpinnerListModel(Time.getTimeTypes()) );
		addTimeTakenTimeTypeSpinner.setBounds(20, 540, 120, 25);
		timeTakenPanel.add(addTimeTakenTimeTypeSpinner);
		addTimeTakenButton = new JButton("Add");//Create the button to add time taken.
		addTimeTakenButton.setBounds( 30, 570, 80, 25 );
		addTimeTakenButton.addActionListener(new ActionListener() 
		{//Define the ActionListener function.
			public void actionPerformed(ActionEvent event)
			{//Add the time earned
				try
				{//Set the fiscal starting month.
					//timeTakenDataModel.addRow(new Object[]{ getNumMonth((String)addTimeTakenMonthSpinner.getValue()) + "/" + ((Integer)addTimeTakenDaySpinner.getValue()).toString() + "/" + ((Integer)addTimeTakenYearSpinner.getValue()).toString(), ((Integer)addTimeTakenHoursSpinner.getValue()).toString(), (String)addTimeTakenTimeTypeSpinner.getValue(), addTimeTakenCommentTextField.getText()});
					addTimeTaken( (String)addTimeTakenMonthSpinner.getValue(), (Integer)addTimeTakenDaySpinner.getValue(), (Integer)addTimeTakenYearSpinner.getValue(), (Integer)addTimeTakenHoursSpinner.getValue(), (String)addTimeTakenTimeTypeSpinner.getValue(), addTimeTakenCommentTextField.getText());
				}
				catch( Exception e )
				{
					System.err.println( "While adding the time taken: " + e.getMessage() );
				}
				//timeTakenDataModel.addRow(new Object[]{((String)addTimeTakenMonthSpinner.getValue()) + " " + ((Integer)addTimeTakenDaySpinner.getValue()).toString() + ", " + ((Integer)addTimeTakenYearSpinner.getValue()).toString(), ((Integer)addTimeTakenHoursSpinner.getValue()).toString(), (String)addTimeTakenTimeTypeSpinner.getValue(), addTimeTakenCommentTextField.getText()});
			}
		});
		timeTakenPanel.add( addTimeTakenButton );
	}
	
	private void addTimeTaken( String month, Integer day, Integer year, Integer hours, String timeType, String comment ) throws Exception
	{//Add time earned to the GUI and the Timesheet object.
		timeTakenDataModel.addRow(new Object[]{getNumMonth(month) + "/" + day.toString() + "/" + year.toString(), hours.toString(), timeType, comment});
		ts.addTimeTaken(getNumMonth(month), day.intValue(), year.intValue(), hours.intValue(), timeType, comment);
	}
	
	private void createTimesheetPanel()
	{//Initialize the Timesheet tab.
		timesheetPanel = new JPanel();
		timesheetPanel.setLayout(null);
		
		/*
		//Initialize the timesheet label.
		timesheetLabel = new JLabel();
		timesheetLabel.setBounds(0, 0, 450, 400);
		timesheetLabel.setBorder(BorderFactory.createLineBorder(Color.black));//Create a black border around the label.
		timesheetPanel.add(timesheetLabel);
		*/
		
		//Initialize the timesheet text area.
		timesheetTextArea = new JTextArea();
		timesheetTextArea.setEditable( false );//Make sure the user can't edit the timesheet in the program for data integrity.
		timesheetScrollPane = new JScrollPane( timesheetTextArea );
		timesheetScrollPane.setBounds( 0, 0, 450, 400 );
		timesheetScrollPane.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		timesheetScrollPane.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		timesheetPanel.add( timesheetScrollPane );
		
		//Initialize the Start Time elements.
		timesheetStartLabel = new JLabel( "Start Date:" );
		timesheetStartLabel.setBounds(20, 425, 170, 25);
		timesheetPanel.add(timesheetStartLabel);
		timesheetStartMonthLabel = new JLabel( "Month" );
		timesheetStartMonthLabel.setBounds(20, 450, 90, 25);
		timesheetPanel.add(timesheetStartMonthLabel);
		timesheetStartDayLabel = new JLabel( "Day" );
		timesheetStartDayLabel.setBounds(130, 450, 50, 25);
		timesheetPanel.add(timesheetStartDayLabel);
		timesheetStartYearLabel = new JLabel( "Year" );
		timesheetStartYearLabel.setBounds(180, 450, 50, 25);
		timesheetPanel.add(timesheetStartYearLabel);
		timesheetStartMonthSpinner = new JSpinner( new SpinnerListModel(months) );
		timesheetStartMonthSpinner.setBounds(20, 475, 90, 25);
		timesheetPanel.add(timesheetStartMonthSpinner);
		timesheetStartDaySpinner = new JSpinner( new SpinnerNumberModel(1,1,31,1) );
		timesheetStartDaySpinner.setBounds(130, 475, 40, 25);
		timesheetPanel.add(timesheetStartDaySpinner);
		timesheetStartYearSpinner = new JSpinner( new SpinnerNumberModel(2013,2010,2050,1) );
		timesheetStartYearSpinner.setEditor( new JSpinner.NumberEditor(timesheetStartYearSpinner, "####")  );
		timesheetStartYearSpinner.setBounds(180, 475, 60, 25);
		timesheetPanel.add(timesheetStartYearSpinner);
		/*
		Calendar cal = Calendar.getInstance();
		Date initDate = cal.getTime();
		cal.add(Calendar.YEAR, -100);
		Date firstDate = cal.getTime();
		cal.add(Calendar.YEAR, 200);
		Date lastDate = cal.getTime();
		timesheetStartDateModel = new SpinnerDateModel(initDate,firstDate,lastDate,Calendar.DAY_OF_MONTH);
		timesheetStartDateSpinner = new JSpinner( timesheetStartDateModel );
		timesheetStartDateSpinner.setEditor( new JSpinner.DateEditor(timesheetStartDateSpinner, "MM/dd/yyyy")  );
		timesheetStartDateSpinner.setBounds(20, 450, 90, 25);
		timesheetPanel.add( timesheetStartDateSpinner );
		*/
		
		//Initialize the End Time elements.
		timesheetEndLabel = new JLabel( "End Date:" );
		timesheetEndLabel.setBounds(260, 425, 170, 25);
		timesheetPanel.add(timesheetEndLabel);
		timesheetEndMonthLabel = new JLabel( "Month" );
		timesheetEndMonthLabel.setBounds(260, 450, 90, 25);
		timesheetPanel.add(timesheetEndMonthLabel);
		timesheetEndDayLabel = new JLabel( "Day" );
		timesheetEndDayLabel.setBounds(370, 450, 50, 25);
		timesheetPanel.add(timesheetEndDayLabel);
		timesheetEndYearLabel = new JLabel( "Year" );
		timesheetEndYearLabel.setBounds(420, 450, 50, 25);
		timesheetPanel.add(timesheetEndYearLabel);
		timesheetEndMonthSpinner = new JSpinner( new SpinnerListModel(months) );
		timesheetEndMonthSpinner.setBounds(260, 475, 90, 25);
		timesheetPanel.add(timesheetEndMonthSpinner);
		timesheetEndDaySpinner = new JSpinner( new SpinnerNumberModel(1,1,31,1) );
		timesheetEndDaySpinner.setBounds(370, 475, 40, 25);
		timesheetPanel.add(timesheetEndDaySpinner);
		timesheetEndYearSpinner = new JSpinner( new SpinnerNumberModel(2013,2010,2050,1) );
		timesheetEndYearSpinner.setEditor( new JSpinner.NumberEditor(timesheetEndYearSpinner, "####")  );
		timesheetEndYearSpinner.setBounds(420, 475, 60, 25);
		timesheetPanel.add(timesheetEndYearSpinner);
		/*
		timesheetEndDateModel = new SpinnerDateModel(initDate,firstDate,lastDate,Calendar.DAY_OF_MONTH);
		timesheetEndDateSpinner = new JSpinner( timesheetEndDateModel );
		timesheetEndDateSpinner.setEditor( new JSpinner.DateEditor(timesheetEndDateSpinner, "MM/dd/yyyy")  );
		timesheetEndDateSpinner.setBounds(260, 450, 90, 25);
		timesheetPanel.add( timesheetEndDateSpinner );
		*/
		
		//Initialize the create timesheet button
		createTimesheetButton = new JButton("Create Timesheet");//Create the button to add time taken.
		createTimesheetButton.setBounds( 20, 525, 160, 25 );
		//createTimesheetButton.addActionListener(this);
		createTimesheetButton.addActionListener(new ActionListener() 
		{//Define the ActionListener function.
			public void actionPerformed(ActionEvent event)
			{//Add the time earned
				try
				{//Set the fiscal starting month.
					timeTakenDataModel.addRow(new Object[]{ getNumMonth((String)addTimeTakenMonthSpinner.getValue()) + "/" + ((Integer)addTimeTakenDaySpinner.getValue()).toString() + "/" + ((Integer)addTimeTakenYearSpinner.getValue()).toString(), ((Integer)addTimeTakenHoursSpinner.getValue()).toString(), (String)addTimeTakenTimeTypeSpinner.getValue(), addTimeTakenCommentTextField.getText()});
					//getTimesheet( getNumMonth((String)timesheetStartMonthSpinner.getValue()), ((Integer)timesheetStartDaySpinner.getValue()).intValue(), ((Integer)timesheetStartYearSpinner.getValue()).intValue(), getNumMonth((String)timesheetEndMonthSpinner.getValue()), ((Integer)timesheetEndDaySpinner.getValue()).intValue(), ((Integer)timesheetEndYearSpinner.getValue()).intValue());
				}
				catch( Exception e )
				{
					System.err.println( "While getting the timesheet in TimesheetGUI: " + e.getMessage() );
				}
				//timeTakenDataModel.addRow(new Object[]{((String)addTimeTakenMonthSpinner.getValue()) + " " + ((Integer)addTimeTakenDaySpinner.getValue()).toString() + ", " + ((Integer)addTimeTakenYearSpinner.getValue()).toString(), ((Integer)addTimeTakenHoursSpinner.getValue()).toString(), (String)addTimeTakenTimeTypeSpinner.getValue(), addTimeTakenCommentTextField.getText()});
			}
		});
		timesheetPanel.add( createTimesheetButton );
	}
	
	public void getTimesheet( int startMonth, int startDay, int startYear, int endMonth, int endDay, int endYear )
	{//Put the timesheet on the timesheet label.
		timesheetTextArea.setText( ts.getTimesheet(startMonth, startDay, startYear, endMonth, endDay, endYear) );
	}
	
	public void actionPerformed( ActionEvent e )
	{//Process UI events.
		/*
		if( okButton.getText().equals("Ok") )
		{//Change the label.
			okButton.setText("Ko");
		}
		else if( okButton.getText().equals("Ko") )
		{//Change the label.
			okButton.setText("Ok");
		}
		*/
	}
	
	private void openDB( String dbFilename )
	{//Open and read from a database at filename dbFilename.
		ts.openDB( dbFilename );
		nameTextField.setText( ts.getName() );
		IDTextField.setText( ts.getID() );
		if( ts.getShift() == 1 )
		{//If got first shift, set the first shift radio button.
			firstShiftRadioButton.setSelected( true );
			secondShiftRadioButton.setSelected( false );
			thirdShiftRadioButton.setSelected( false );
		}
		else if( ts.getShift() == 2 )
		{//If got first shift, set the first shift radio button.
			firstShiftRadioButton.setSelected( false );
			secondShiftRadioButton.setSelected( true );
			thirdShiftRadioButton.setSelected( false );
		}
		else if( ts.getShift() == 3 )
		{//If got first shift, set the first shift radio button.
			firstShiftRadioButton.setSelected( false );
			secondShiftRadioButton.setSelected( false );
			thirdShiftRadioButton.setSelected( true );
		}
		
		//Get the fiscal starting date.
		
		fiscalStartMonthSpinner.setValue( months[ts.getFiscalStartingMonth()-1] );
		fiscalStartDaySpinner.setValue( new Integer(ts.getFiscalStartingDay()) );
		fiscalStartYearSpinner.setValue( new Integer(ts.getFiscalStartingYear()) );
		/*
		Calendar cal = Calendar.getInstance();
		cal.set( Calendar.MONTH, ts.getFiscalStartingMonth()-1 );
		cal.set( Calendar.DAY_OF_MONTH, ts.getFiscalStartingDay() );
		cal.set( Calendar.YEAR, ts.getFiscalStartingYear() );
		fiscalStartDateModel.setValue( cal );
		*/
		
		//Get the different types of time given.
		vacationInitialHoursSpinner.setValue( new Integer(ts.getTimeGiven( "Vacation" )) );
		holidayInitialHoursSpinner.setValue( new Integer(ts.getTimeGiven( "Holiday" )) );
		floatingholidayInitialHoursSpinner.setValue( new Integer(ts.getTimeGiven( "Floating Holiday" )) );
		personalInitialHoursSpinner.setValue( new Integer(ts.getTimeGiven( "Personal" )) );
		
		//Put the time earned on the time earned data model.
		for( Time t : ts.getTimeEarned() )
		{//Go through each time earned time and add it to the data model.
			timeEarnedDataModel.addRow(new Object[]{t.getDate(), t.getHours(), t.getType(), t.getComment()});
		}
		
		//Put the time earned on the time earned data model.
		for( Time t : ts.getTimeTaken() )
		{//Go through each time earned time and add it to the data model.
			timeTakenDataModel.addRow(new Object[]{t.getDate(), t.getHours(), t.getType(), t.getComment()});
		}
	}
	
	private void openDB()
	{//Open and read from a database at a default filename.
		openDB("timesheet.db");
	}
	
	public void saveDB()
	{//Save all the data to the default filename.
		saveDB( "timesheet.db" );
	}
	
	public void saveDB( String dbFilename )
	{//Save all the data to the given filename.
		//ts.openDB( dbFilename );
		ts.setName( nameTextField.getText() );
		ts.setID( IDTextField.getText() );
		if( firstShiftRadioButton.isSelected() )
		{//If the first shift radio button is selected, save that.
			ts.setShift(1);
		}
		else if( secondShiftRadioButton.isSelected() )
		{//If the second shift radio button is selected, save that.
			ts.setShift(2);
		}
		else if( thirdShiftRadioButton.isSelected() )
		{//If the third shift radio button is selected, save that.
			ts.setShift(3);
		}
		
		//Save the different types of time given.
		ts.addTimeGiven( "Vacation", ((Integer)vacationInitialHoursSpinner.getValue()).intValue() );
		ts.addTimeGiven( "Holiday", ((Integer)holidayInitialHoursSpinner.getValue()).intValue() );
		ts.addTimeGiven( "Floating Holiday", ((Integer)floatingholidayInitialHoursSpinner.getValue()).intValue() );
		ts.addTimeGiven( "Personal", ((Integer)personalInitialHoursSpinner.getValue()).intValue() );
		
		//Save the starting date of the fiscal year.
		/*
		if( ((String)fiscalStartMonthSpinner.getValue()).equals("January") )
		{//If it's January, set it to 1 in the Timesheet.
			ts.setFiscalStartingMonth(1);
		}
		else if( ((String)fiscalStartMonthSpinner.getValue()).equals("February") )
		{//If it's January, set it to 1 in the Timesheet.
			ts.setFiscalStartingMonth(2);
		}
		else if( ((String)fiscalStartMonthSpinner.getValue()).equals("March") )
		{//If it's January, set it to 1 in the Timesheet.
			ts.setFiscalStartingMonth(3);
		}
		else if( ((String)fiscalStartMonthSpinner.getValue()).equals("April") )
		{//If it's January, set it to 1 in the Timesheet.
			ts.setFiscalStartingMonth(4);
		}
		else if( ((String)fiscalStartMonthSpinner.getValue()).equals("May") )
		{//If it's January, set it to 1 in the Timesheet.
			ts.setFiscalStartingMonth(5);
		}
		else if( ((String)fiscalStartMonthSpinner.getValue()).equals("June") )
		{//If it's January, set it to 1 in the Timesheet.
			ts.setFiscalStartingMonth(6);
		}
		else if( ((String)fiscalStartMonthSpinner.getValue()).equals("July") )
		{//If it's January, set it to 1 in the Timesheet.
			ts.setFiscalStartingMonth(7);
		}
		else if( ((String)fiscalStartMonthSpinner.getValue()).equals("August") )
		{//If it's January, set it to 1 in the Timesheet.
			ts.setFiscalStartingMonth(8);
		}
		else if( ((String)fiscalStartMonthSpinner.getValue()).equals("September") )
		{//If it's January, set it to 1 in the Timesheet.
			ts.setFiscalStartingMonth(9);
		}
		else if( ((String)fiscalStartMonthSpinner.getValue()).equals("October") )
		{//If it's January, set it to 1 in the Timesheet.
			ts.setFiscalStartingMonth(10);
		}
		else if( ((String)fiscalStartMonthSpinner.getValue()).equals("November") )
		{//If it's January, set it to 1 in the Timesheet.
			ts.setFiscalStartingMonth(11);
		}
		else if( ((String)fiscalStartMonthSpinner.getValue()).equals("December") )
		{//If it's January, set it to 1 in the Timesheet.
			ts.setFiscalStartingMonth(12);
		}*/
		try
		{//Set the fiscal starting month.
			ts.setFiscalStartingMonth( getNumMonth((String)fiscalStartMonthSpinner.getValue()) );
			//ts.setFiscalStartingMonth( fiscalStartDateModel.get );
		}
		catch( Exception e )
		{
			System.err.println( "In SaveDB while saving the fiscal starting month: " + e.getMessage() );
		}
		ts.setFiscalStartingDay( ((Integer)fiscalStartDaySpinner.getValue()).intValue() );//Save the starting fiscal day.
		ts.setFiscalStartingYear( ((Integer)fiscalStartYearSpinner.getValue()).intValue() );//Save the starting fiscal day.
		
		//Save the database to dbFilename.
		ts.saveDB( dbFilename );
	}
	
	private int getNumMonth( String monthName ) throws Exception
	{//Returns the number of the month (1-12) from the name (January-December)
		if( monthName.equals("January") )
		{
			return 1;
		}
		else if( monthName.equals("February") )
		{
			return 2;
		}
		else if( monthName.equals("March") )
		{
			return 3;
		}
		else if( monthName.equals("April") )
		{
			return 4;
		}
		else if( monthName.equals("May") )
		{
			return 5;
		}
		else if( monthName.equals("June") )
		{
			return 6;
		}
		else if( monthName.equals("July") )
		{
			return 7;
		}
		else if( monthName.equals("August") )
		{
			return 8;
		}
		else if( monthName.equals("September") )
		{
			return 9;
		}
		else if( monthName.equals("October") )
		{
			return 10;
		}
		else if( monthName.equals("November") )
		{
			return 11;
		}
		else if( monthName.equals("December") )
		{
			return 12;
		}
		else
		{//Invalid name, throw error.
			throw( new Exception("Invalid Month") );
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TimesheetGUI TSG = new TimesheetGUI();
		TSG.setVisible(true);
	}

}