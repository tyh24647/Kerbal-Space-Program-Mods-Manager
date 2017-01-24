package View;

import Models.KSPMMModel;
import Models.KSPMMTableModel;
import Models.TableViewController;
import View.MainView.MainGUI2;
import View.MainView.Subviews.MenuBar.TMenuBar;
import View.MainView.Subviews.Panels.TPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Custom view controller object which acts as a delegate between the model and
 * the view/GUI classes
 *
 * @author Tyler Hostager
 * @version 1/18/17
 */
public class UIViewController implements ActionListener {


    /*

    TODO MASSIVE FIX: MAKE IT SO THE CONTROLLER  **ONLY** SERVES AS A DELEGATE, NOT A GUI CLASS

     */

    /*
    private static final String FILE = "File", EDIT = "Edit", VIEW = "View",
            TOOLS = "Tools", WINDOW = "Window", HELP = "Help";

    private static final TMenu
            FILE_MENU = new TMenu(FILE),
            EDIT_MENU = new TMenu(EDIT),
            VIEW_MENU = new TMenu(VIEW),
            TOOLS_MENU = new TMenu(TOOLS),
            WINDOW_MENU = new TMenu(WINDOW),
            HELP_MENU = new TMenu(HELP);

    private static final TMenu[] MENUS = {
        FILE_MENU, EDIT_MENU, VIEW_MENU, TOOLS_MENU, WINDOW_MENU, HELP_MENU
    };
    */

    private KSPMMModel model;
    private TPanel mainPanel;
    private TMenuBar menuBar;
    //private MainGUI ui;
    private MainGUI2 ui;

    /**
     * Default constructor
     */
    //public UIViewController(KSPMMModel model, MainGUI view) {
    public UIViewController(KSPMMModel model, MainGUI2 view) {

        ui = view == null ? initMainGUI() : view;
        //ui = initMainGUI();
        //mainPanel = ui.getMainPanel();
        //ui.add(mainPanel, BorderLayout.CENTER);
        //mainPanel = new TPanel();

        setModel(model);
        //setMainPanel(new TPanel());


        //TFileMenuItem[] fileMenuItems = ui.getJMenuBar().getFileMenu()


        // init view objects
        //initMenuBar();

        //ui.pack();
        //ui.setVisible(true);
    }

    private MainGUI2 initMainGUI() {
    //private MainGUI initMainGUI() {
        if (ui == null) {
            //ui = new MainGUI();
            ui = new MainGUI2(new TableViewController(new KSPMMTableModel()));
        }

        return ui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if (e.getSource() == fileMenuItem) {
        //if (e.getSource() == ui.getJMenuBar().getFileMenu().getFileMenuItem()) {

        //}
    }

    private void setMainPanel(TPanel panel) {
        mainPanel = panel == null ? new TPanel() : panel;
    }

    /*
    private void initMenuBar() {
        menuBar = menuBar == null ? new TMenuBar() : menuBar;
        //initSubMenus();;

        ui.setJMenuBar(menuBar);
    }
    */

    /*
    private void setMenuListeners() {
        ui.getJMenuBar().getFileMenu().getFileMenuItem().addActionListener(this);
        //ui.getJMenuBar().
    }
    */

    /*
    private void initSubMenus() {
        fileMenu = generateMenu(FILE);
        editMenu = generateMenu(EDIT);

        menuBar.setFileMenu(fileMenu);

        menuBar.add(fileMenu)
    }
    */

    private void assignMenuItems() {
        //fileMenuItem = ui.getJMenuBar().getFileMenu().getFileMenuItem();
    }

    private void setModel(KSPMMModel model) {
        this.model = model == null ? new KSPMMModel() : model;
    }

    /*
    public void setMainGUI(MainGUI ui) {
        this.ui = ui == null ? new MainGUI() : ui;
    }
    */

    public void setMainGUI(MainGUI2 ui) {
        this.ui = ui == null ? new MainGUI2(new TableViewController(new KSPMMTableModel())) : ui;
    }

    public KSPMMModel getModel() {
        return model == null ? new KSPMMModel() : model;
    }

    /*
    public MainGUI getUi() {
        return ui;
    }
    */

    public MainGUI2 getUi() {
        return ui;
    }


}
