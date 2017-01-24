package View.MainView;

import Models.KSPMMTableModel;
import Models.TableViewController;
import Utils.ApplicatioinUtilities.AppUtils;
import Utils.Log;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.basic.BasicMenuBarUI;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import static AppData.Constants.*;

/**
 * The primary user interface for the application - presented on program execution
 *
 * @author Tyler Hostager
 * @version 1/19/17
 */
public class MainGUI2 extends JFrame implements ActionListener, KeyListener, MouseListener {

    //region INIT VARS
    private JMenu fileMenu, editMenu, viewMenu, toolsMenu, windowMenu, helpMenu;
    private JPanel southPanel, mainPanel, centerPanel, northPanel, westPanel, eastPanel;
    private JMenuBar menuBar;
    private JMenuItem
            // file menu items
            importItem = new JMenuItem(IMPORT),
            openItem = new JMenuItem(OPEN),
            newItem = new JMenuItem(NEW),
            saveItem = new JMenuItem(SAVE),
            saveAsItem = new JMenuItem(SAVE_AS),
            prefsItem = new JMenuItem(PREFERENCES),
            exitItem = new JMenuItem(EXIT),

            // edit menu items
            undoItem = new JMenuItem(UNDO),
            redoItem = new JMenuItem(REDO),
            cutItem = new JMenuItem(CUT),
            copyItem = new JMenuItem(COPY),
            pasteItem = new JMenuItem(PASTE),
            findItem = new JMenuItem(FIND),

            // view menu items
            consoleItem = new JMenuItem(SHOW_DEBUG_CONSOLE),
            errMsgsItem = new JMenuItem(SHOW_ERROR_MSGS),
            appLogsItem = new JMenuItem(SHOW_APP_LOGS),

            // tools menu items
            debugModeItem = new JMenuItem(ENABLE_DEBUG),
            showConsoleItem = new JMenuItem(SHOW_CONSOLE),

            // window menu items
            fullscreenItem = new JMenuItem(FULLSCREEN),
            tableColumnsItems = new JMenuItem(TABLE_COLUMNS),

            // help menu items
            aboutItem = new JMenuItem(ABOUT),
            contactItem = new JMenuItem(CONTACT)
    ;

    private JMenuItem[] allMenuItems = {
            importItem, openItem, newItem, saveItem, saveAsItem, prefsItem, exitItem, undoItem,
            redoItem, cutItem, copyItem, pasteItem, findItem, consoleItem, errMsgsItem,
            appLogsItem, debugModeItem, showConsoleItem, fullscreenItem, tableColumnsItems,
            aboutItem, contactItem
    }, fileMenuItems = {
            importItem, openItem, newItem, saveItem, saveAsItem, prefsItem, exitItem
    }, editMenuItems = {
            undoItem, redoItem, cutItem, copyItem, pasteItem, findItem
    }, viewMenuItems = {
            consoleItem, errMsgsItem, appLogsItem
    }, toolsMenuItems = {
            debugModeItem, showConsoleItem
    }, windowMenuItems = {
            fullscreenItem, tableColumnsItems
    }, helpMenuItems = {
            aboutItem, contactItem
    };

    private String[] menuTitles = {
            FILE, EDIT, VIEW, TOOLS, WINDOW, HELP
    }, fileMenuItemTitles = {
            IMPORT, OPEN, NEW, SAVE, SAVE_AS, PREFERENCES, EXIT
    }, editMenuItemTitles = {
            UNDO, REDO, CUT, COPY, PASTE, FIND
    }, viewMenuItemTitles = {
            SHOW_DEBUG_CONSOLE, SHOW_ERROR_MSGS, SHOW_APP_LOGS
    }, toolsMenuItemTitles = {
            ENABLE_DEBUG, SHOW_CONSOLE
    }, windowMenuItemTitles = {
            FULLSCREEN, TABLE_COLUMNS
    }, helpMenuItemTitles = {
            ABOUT, CONTACT
    };

    private JMenu[] menus = {
            fileMenu, editMenu, viewMenu, toolsMenu, windowMenu, helpMenu
    };

    private JScrollPane centerScrollPane;
    private TableViewController tableViewController;
    private KSPMMTableModel tableModel;
    private JLabel topLabel, bottomLabel;
    private JFileChooser fileChooser = new JFileChooser();
    //endregion


    public MainGUI2(TableViewController tableViewController) {
        setDefaultLookAndFeelDecorated(true);
        initTableModel();
        setTableViewController(tableViewController);
        initMainPanel();
        configureMenuBar();
        generateCenterPanel();
        configureMainFrame();
    }

    private void setTableViewController(TableViewController tvc) {
        if (tvc != null) {
            this.tableViewController = tvc;
        }
    }

    private void generateCenterPanel() {
        centerPanel = centerPanel == null ? new JPanel(new BorderLayout()) : centerPanel;
        centerPanel.setPreferredSize(DEFAULT_CENTER_PANEL_SIZE);

        // add scroll pane with jtable
        centerScrollPane = tableModel.getScrollPane();
        centerScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        centerScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        menuBar.setOpaque(true);
        menuBar.setUI(new BasicMenuBarUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                g.setColor(Color.CYAN.darker());
                g.fillRect(0, 0, c.getWidth(), c.getHeight());
            }
        });

        menuBar.setBorder(BorderFactory.createEmptyBorder());

        // add top bar
        northPanel = new JPanel(new BorderLayout()) {
            @Override
            public void setBorder(Border border) {
                super.setBorder(BorderFactory.createEmptyBorder());
            }
        };

        northPanel.setPreferredSize(TOP_PANE_PREFERRED_SIZE);
        topLabel = new JLabel(WINDOW_TITLE) {
            @Override
            public void setOpaque(boolean isOpaque) {
                super.setOpaque(true);
            }

            @Override
            public void setFont(Font font) {
                String tableFont = tableModel.getTable().getFont().deriveFont(Font.PLAIN).getFontName();
                super.setFont(new Font(tableFont, Font.PLAIN, 20));
            }

            @Override
            public void setBackground(Color bg) {
                super.setBackground(Color.cyan.darker());
            }
        };

        topLabel.setOpaque(false);
        topLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topLabel.setVerticalAlignment(SwingConstants.CENTER);
        topLabel.setPreferredSize(northPanel.getSize());
        northPanel.add(topLabel, BorderLayout.CENTER);
        mainPanel.add(northPanel, BorderLayout.NORTH);


        // init south panel
        southPanel = new JPanel(new BorderLayout()) {
            @Override
            public void setBorder(Border border) {
                super.setBorder(BorderFactory.createEmptyBorder());
            }
        };

        southPanel.setPreferredSize(BOTTOM_PANE_PREFERRED_SIZE);
        bottomLabel = new JLabel(COPYRIGHT_MSG) {
            @Override
            public void setOpaque(boolean isOpaque) {
                super.setOpaque(true);
            }

            @Override
            public void setFont(Font font) {
                String tblFnt = tableModel.getTable().getFont().deriveFont(Font.PLAIN).getFontName();
                super.setFont(new Font(tblFnt, Font.PLAIN, 14));
            }

            @Override
            public void setBackground(Color bg) {
                super.setBackground(Color.CYAN.darker().darker().darker());
            }

            @Override
            public void setForeground(Color fg) {
                super.setForeground(Color.CYAN.brighter().brighter().brighter().brighter());
            }
        };

        bottomLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomLabel.setVerticalAlignment(SwingConstants.CENTER);
        bottomLabel.setPreferredSize(southPanel.getSize());
        southPanel.add(bottomLabel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // add scrollpane to main panel
        mainPanel.add(centerScrollPane, BorderLayout.CENTER);
        centerScrollPane.setViewportView(tableViewController.getModel().getTable());
        repaint();
    }

    private void configureMainFrame() {
        setTitle("KSP Mods Manager");
        setPreferredSize(DEFAULT_WINDOW_SIZE);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Log.d("Exiting application...");
                AppUtils.exitApplication();
            }
        });
        setResizable(true);
        pack();
        setVisible(true);
    }

    private void initMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(true);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setPreferredSize(DEFAULT_WINDOW_SIZE);
        add(mainPanel);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Log.d("Exiting application...");
                AppUtils.exitApplication();
            }
        });
    }

    private void initTableModel() {
        tableModel = tableModel == null ? new KSPMMTableModel() : tableModel;
    }

    private void configureMenuBar() {
        UIManager.put("Table.focusCellHighlightBorder",
                new BorderUIResource.LineBorderUIResource(Color.CYAN));
        menuBar = new JMenuBar();

        // init menu bar item listeners
        for (JMenuItem item : allMenuItems) item.addActionListener(this);

        // create menus
        fileMenu = new JMenu(FILE);
        editMenu = new JMenu(EDIT);
        viewMenu = new JMenu(VIEW);
        toolsMenu = new JMenu(TOOLS);
        windowMenu = new JMenu(WINDOW);
        helpMenu = new JMenu(HELP);

        fileMenu.setOpaque(false);
        editMenu.setOpaque(false);
        viewMenu.setOpaque(false);
        toolsMenu.setOpaque(false);
        windowMenu.setOpaque(false);
        helpMenu.setOpaque(false);

        // add items to file menu
        for (JMenuItem item : fileMenuItems) {
            fileMenu.add(item);
            if (item.equals(importItem) || item.equals(openItem) || item.equals(saveAsItem) || item.equals(prefsItem)) {
                fileMenu.add(new JSeparator());
            }
        }

        // add items to edit menu
        for (JMenuItem item : editMenuItems) {
            editMenu.add(item);

            if (item.equals(redoItem) || item.equals(pasteItem)) {
                editMenu.add(new JSeparator());
            }
        }

        for (JMenuItem item : viewMenuItems) viewMenu.add(item);
        for (JMenuItem item : toolsMenuItems) toolsMenu.add(item);
        for (JMenuItem item : windowMenuItems) windowMenu.add(item);
        for (JMenuItem item : helpMenuItems) helpMenu.add(item);

        // add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(toolsMenu);
        menuBar.add(windowMenu);
        menuBar.add(helpMenu);
        menuBar.setOpaque(false);

        // set menu bar
        setJMenuBar(menuBar);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(importItem)) {
            fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Import Mod Jar File");
            fileChooser.addActionListener(this);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.zip", "zip"));
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.jar","jar"));
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.showOpenDialog(new JDialog());
        } else if (source.equals(openItem)) {
            fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Mods Folder");
            fileChooser.addActionListener(this);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory();
                }

                @Override
                public String getDescription() {
                    return "KSP Application Folder";
                }
            });
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileHidingEnabled(true);
            fileChooser.showOpenDialog(new JDialog());

            /*
            TODO figure out how to only show/open the mod jar files or the mods folder itself
             */
        } else if (source.equals(saveItem)) {

        } else if (source.equals(saveAsItem)) {

        }


        else if (source.equals(fullscreenItem)) {
            fullscreenItem.setText(EXIT_FSCREEN);
            //setPreferredSize(AppEvent.FullScreenEvent);
        }

        else if (source.equals(exitItem)) {
            Log.d("Exiting application");
            AppUtils.exitApplication();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            Log.d("HEYYYOOOOO");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    protected void processMouseEvent(MouseEvent e) {


        super.processMouseEvent(e);
    }
}



