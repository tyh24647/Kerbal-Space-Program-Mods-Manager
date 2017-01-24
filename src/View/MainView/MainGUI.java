package View.MainView;

import Utils.Log;
import View.MainView.Subviews.MenuBar.TMenuBar;
import View.MainView.Subviews.Panels.TPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * The primary user interface for the program
 *
 * @author Tyler Hostager
 * @version 1/18/17
 */
public class MainGUI extends JFrame implements ActionListener {
    private File userDataXML;
    private TMenuBar menuBar;
    private TPanel mainPanel;

    /**
     * Constructs a new instance of the user interface
     */
    public MainGUI() {
        initDefaults();

        boolean hasExistingXMLDoc = checkForUserData();
        if (hasExistingXMLDoc) {
            Log.d("UserData.xml file already exists");
            Log.d("Skipping procedure");
        }

        initMainFrame();
    }

    /**
     * Constructs a UI instance with the given user data file
     * @param file  The user data file
     */
    public MainGUI(File file) {
        setUserDataXML(file);
    }

    private void initDefaults() {
        mainPanel = new TPanel();
    }

    private void initMainFrame() {
        menuBar = new TMenuBar();

        /* COMMENTED OUT FOR TESTING PURPOSES
        menuBar.add(new TFileMenu());
        menuBar.add(new TEditMenu());
        */

        ///* UNCOMMENT FOR TESTING
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);
        exitItem.addActionListener(this);
        menuBar.add(fileMenu);
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);
        menuBar.setVisible(true);
        //

        setJMenuBar(menuBar);


        if (mainPanel == null) {
            mainPanel = new TPanel();
        }

        add(mainPanel, BorderLayout.CENTER);


        setPreferredSize(new Dimension(1000, 800));
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private boolean checkForUserData() {
        File userDataFile = new File("src/UserData/UserData.xml");
        return userDataFile.exists();
    }

    private boolean checkForUserData(String fileName) {
        File userDataFile = new File("src/UserData/" + fileName);
        return userDataFile.exists();
    }

    private File getDataFileWithName(String fileName) {

        /*

        TODO: this is not how it should be done below
         */

        return new File(fileName);
    }

    /* TODO SHOULD BE MOVED TO THE XMLWRITER/XMLREADER CLASSES */
    public File setUserDataXML(File xmlFile) {

        // check for null file or a file format that cannot be read
        boolean fileIsInvalid = xmlFile == null || !xmlFile.canRead();

        // if file can't be read
        return userDataXML = fileIsInvalid ? new File("UserData.xml") : xmlFile;
    }

    /**
     * Checks the file and ensures that it can be read
     *
     * @param xmlFile   The file to be parsed
     * @return          True if valid, else false
     */
    private boolean isValidFileFormat(File xmlFile) {
        // check if file is null
        if (fileIsNull(xmlFile)) {
            return false;
        }

        // check if file can be read
        if (!canParseFile(xmlFile)) {

        }

        return true;
    }

    /**
     * Checks whether or not the specified file can be parsed
     *
     * @param file  The file to be parsed
     * @return      True if it can be parsed
     */
    private boolean canParseFile(File file) {
        boolean canParseFile = false;

        if (!fileIsNull(file)) {
            canParseFile = file.canRead();
        }

        return canParseFile;
    }

    public TMenuBar getJMenuBar() {
        return menuBar == null ? new TMenuBar() : menuBar;
    }

    public TPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Checks if the specified file is null
     *
     * @param file  The file to be checked
     * @return      True if null
     */
    private boolean fileIsNull(File file) {
        return file == null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO

        ///* remove for actual usage -- this is a test
        System.exit(0);
        //*/
    }

    /*

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    */
}
