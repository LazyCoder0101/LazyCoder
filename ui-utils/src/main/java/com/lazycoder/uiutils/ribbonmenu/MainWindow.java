package com.lazycoder.uiutils.ribbonmenu;
import java.awt.BorderLayout;
import javax.swing.JFrame;

//代码摘自 https://github.com/csekme/JRibbonMenu
public class MainWindow extends JFrame implements IRibbonBar {

	private static final long serialVersionUID = 6524936981221127992L;
	
	RibbonBar ribbonBar;
 
	/**
	 * Create the frame.
	 */
	public MainWindow() {
		initGUI();
		buildMenu();
	}
	
	
	public void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		{
			//Add ribbon bar to NORTH (Suggested)
			this.ribbonBar = new RibbonBar();
// If you find the auto collapse is a problem uncomment the disable call			
//			this.ribbonBar.disableCollapse();
			getContentPane().add(this.ribbonBar, BorderLayout.NORTH);
		}
	}


	@Override
	public void buildMenu() {
		
		//Create a first tab
		Tab tbHome = RibbonBar.addTab("Home");
		{
			//Add some button
			{
				Button btnUser = tbHome.addButton("User");
				btnUser.setImage(Util.accessImageFile("dist/user.png"));
				btnUser.addToolTip("Add new user");
			}
			{
				Button btnSettings = tbHome.addButton("Settings\nConfigurations"); // \n <- line break 
				btnSettings.setImage(Util.accessImageFile("dist/settings.png"));
				btnSettings.addToolTip("Customize your settings.");
				{
					// some ways to add the submenu
					btnSettings.addSubMenu(a -> {
					}, "Restore pont");

					RibbonMenuItem dr = new RibbonMenuItem("Very long text for menu item");
					dr.setIcon(Util.accessImageFile("dist/paste.png", 16, 16));
					btnSettings.addSubMenu(dr);
					RibbonMenuItem r = new RibbonMenuItem("Check something", false);
					btnSettings.addSubMenu(r);
				}
			}
			
			tbHome.setGroupName("Base"); //Between two separator you can set groupname
			tbHome.addSeperator();
			{
				Button btnCopy = tbHome.addButton("Copy");
				btnCopy.setEnabled(false);
				btnCopy.setImage(Util.accessImageFile("dist/copy.png"));
				btnCopy.addToolTip("Copy to Clipboard.");
			}
			{
				Button btnPaste = tbHome.addButton("Paste\nfrom clipboard");
				btnPaste.setImage(Util.accessImageFile("dist/paste.png"));
				btnPaste.addToolTip("Paste from Clipboard");
			}
			tbHome.setGroupName("Clipboard");
			tbHome.addSeperator();
			{
				Button tbLetter = tbHome.addSlimButton("Send email");
				tbLetter.setImage(Util.accessImageFile("dist/letter.png"));
				tbLetter.addToolTip("Compose and Send Email.");
			}
			{
				Button btnFavourites = tbHome.addSlimButton("Favourites");
				btnFavourites.setImage(Util.accessImageFile("dist/kedvencek.png"));
				btnFavourites.addToolTip("View list of your favourites.");
			}
			tbHome.setGroupName("Contacts");
			tbHome.addSeperator();
			 
		}
		
	
		Tab tbView = RibbonBar.addTab("Security"); //Second tab
		{
			Button btnReminder = tbView.addButton("Reminder");
			btnReminder.setImage(Util.accessImageFile("dist/remind.png"));
			btnReminder.addToolTip("Manage your Reminders.");
		}
		{
			Button btnFingerPrint = tbView.addButton("Fingerprint");
			btnFingerPrint.setImage(Util.accessImageFile("dist/fingerprint.png"));
			btnFingerPrint.addToolTip("Toggle FingerPrint Security.");
		}
	
	}

}
