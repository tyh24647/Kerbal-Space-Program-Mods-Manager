package Utils.XMLUtilities;

import Utils.Log;
import Utils.OSUtils.OSUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * <p>
 *     <code>XMLWriter</code> object interacts with the <code>UserData.xml</code> file in order to save
 *     the user information to be retained after the program is closed, allowing for
 *     reloading and saving data.
 * </p>
 * &nbsp
 * <p>
 *     <b>This class provides the following functionality:</b>
 *     <ul>
 *         <li>Searching/finding the 'UserData.xml file' saved in the application data</li>
 *         <li>Assigning a specified XML document to be the primary user data file</li>
 *         <li>Retrieving an XML document for writing</li>
 *         <li>Creating and setting XML tags and information</li>
 *         <li>Setting a custom save file location</li>
 *         <li>Allows user to create new XML documents</li>
 *     </ul>
 * </p>
 * &nbsp
 * <p>
 *     This class also has the ability to append, edit, and remove XML items from the loaded
 *     document, and will save the updated document.
 * </p>
 *
 * @see XMLReader for information/methods related to the functionality for reading
 * the XML data that is generated here.
 *
 * @author Tyler Hostager
 * @version 1/18/17
 */
public class XMLWriter {

    //region CONSTANTS
    private static final String
            DEFAULT_USER_DATA_FILENAME = "UserData.xml",
            DEFAULT_USER_PATH = "src/UserData/",
            DEFAULT_FULL_PATH = DEFAULT_USER_PATH + DEFAULT_USER_DATA_FILENAME,
            XML_EXTENSION = ".xml",
            EMPTY = "",
            XML_MISSING_ERROR = "'UserData.xml' does not exist",
            GENERATING_XML = "Generating new 'UserData.xml' file in " +
                    "directory '" + DEFAULT_USER_PATH + "'",
            XML_GENERATE_ERROR = "Unable to generate '" + DEFAULT_USER_DATA_FILENAME + "'",
            XML_GENERATED_SUCCESS = "'UserData.xml' file generated successfully",
            SKIPPING_PROCEDURE = "Skipping procedure",
            DEFAULT_EL_NAME = "newelement",
            DEFAULT_EL_VALUE = "defaultValue",
            FILE_SAVE_SUCCESS = "File saved successfully"
    ;
    //endregion




    //region GLOBAL VARS
    private Document doc;
    private File xmlFile;
    //endregion




    //region CONSTRUCTORS
    /**
     * Default constructor
     */
    public XMLWriter() {
        setXMLFile(new File(DEFAULT_FULL_PATH));
    }

    /**
     * Constructs a writer that uses the specified file
     * @param file  The file to write to
     */
    public XMLWriter(File file) {
        setXMLFile(file);
    }

    /**
     * Constructs an xml writer which writes a file with the specified name
     * @param fileName
     */
    public XMLWriter(String fileName) {
        try {
            setXMLFile(findFileWithName(fileName));
        } catch (Exception e) {
            Log.e(e.getMessage());
        }
    }
    //endregion




    //region FUNCTIONS
    /**
     * Locates the file with the specified name
     *
     * @param fileName  The specified file name
     * @return          The requested file
     */
    private File findFileWithName(String fileName) throws FileNotFoundException {

        // verify that file name is not null -- if null, look for default user data file
        fileName = fileName == null ? DEFAULT_USER_DATA_FILENAME : fileName;

        // ensure the file has an xml extension
        fileName += fileName.contains(XML_EXTENSION) ? EMPTY : XML_EXTENSION;

        // create file object--file will be generated if it does not exist
        File file = new File(DEFAULT_USER_PATH + fileName);

        if (!file.exists() || !file.canRead()) {
            String err = "Unable to find the file named \'" + fileName
                    + "\' in the specified path: \'" + DEFAULT_USER_PATH + "\'";
            Log.e(err);

            throw new FileNotFoundException();  // <-- File does not exist
        }

        return file;
    }

    /**
     * Adds an xml element with the given element tag, and the corresponding
     * value is assigned and placed within the tag
     *
     * @param elementName   The name of the element to add
     * @param value         The value to be assigned to the new element
     * @return              True if successful, else false
     */
    public boolean addXMLElement(String elementName, String value) {
        boolean success = false;

        try {
            // setup document builder
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // setup primary XML elements
            doc = docBuilder.newDocument();
            Element rootUserElement = doc.createElement("preferences");
            doc.appendChild(rootUserElement);

            Element prefs = doc.createElement("user");
            rootUserElement.appendChild(prefs);


            /* TODO TEST TEST TEST TEST TEST TEST TEST */
            /* ADD USER SPECIFIED ELEMENT WITH VALUE */
            String newElementName = elementName == null || elementName.isEmpty()
                    ? DEFAULT_EL_NAME : elementName;
            String newElementValue = value == null || value.isEmpty() ? DEFAULT_EL_VALUE : value;

            Element newElement = doc.createElement(newElementName);
            newElement.setNodeValue(newElementValue);
            prefs.appendChild(newElement);

            Element n2 = doc.createElement("element");
            n2.appendChild(doc.createTextNode("another one"));
            prefs.appendChild(n2);

            Element testElement1 = doc.createElement("test1");
            testElement1.appendChild(doc.createTextNode("testing testing ya ya ya"));
            newElement.appendChild(testElement1);

            Element testElement2 = doc.createElement("test2");
            testElement2.appendChild(doc.createTextNode("this is another test"));
            newElement.appendChild(testElement2);

            Element testElement3 = doc.createElement("test3");
            testElement3.appendChild(doc.createTextNode("third and final test"));
            newElement.appendChild(testElement3);

            Element defaults = doc.createElement("defaults");
            rootUserElement.appendChild(defaults);

            Element osType = doc.createElement("os");
            osType.appendChild(doc.createTextNode(OSUtils.getOperatingSystemType().name()));
            defaults.appendChild(osType);

            Element d1 = doc.createElement("d1");
            Attr id = doc.createAttribute("id");
            id.setValue("defaultPref1");
            defaults.appendChild(d1);
            d1.setAttributeNode(id);

            // test attribute
            Attr testAttr = doc.createAttribute("id");
            testAttr.setValue("testID");
            newElement.setAttributeNode(testAttr);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            configureXMLTransformer(transformer);

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(DEFAULT_FULL_PATH));    // <-- Writes data
            transformer.transform(source, result);
            System.out.println();
            success = true;
        } catch (ParserConfigurationException pcon) {
            pcon.printStackTrace();
            Log.e(pcon.getMessage());
        } catch (TransformerException texc) {
            Log.e(texc.getMessage());
        }

        return success;
    }

    /**
     * Ensures that the XML document is written correctly and each new element
     * is printed on a new line
     *
     * @param transformer   The XML transformer object
     */
    private void configureXMLTransformer(Transformer transformer) {
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
    }
    //endregion




    //region SETTERS
    /**
     * Assigns the specified xml file to the writer
     * @param file  The file to be written to
     */
    public boolean setXMLFile(File file) {
        boolean success = false;
        File newXML = file == null || !file.exists()
                ? new File(DEFAULT_FULL_PATH) : file;

        // check if file exists
        if (!newXML.exists()) {
            Log.d(XML_MISSING_ERROR); // notify debugger, log 'xml missing' msg

            try {
                // init new XML instance if null or not found
                Log.d(GENERATING_XML);
                newXML = new File(DEFAULT_FULL_PATH);
                success = newXML.createNewFile();
                Log.d(XML_GENERATED_SUCCESS);
            } catch (Exception e) {
                Log.d(XML_GENERATE_ERROR);
                Log.e(e.getMessage());
                e.printStackTrace();
                Log.d(SKIPPING_PROCEDURE);
            }
        }

        return success;
    }
    //endregion

    //region GETTERS
    /**
     * Retrieves the xml file from the writer
     * @return  The corresponding xml file
     */
    public File getXmlFile() {
        return xmlFile == null || !xmlFile.exists() || !xmlFile.canRead() ?
                new File(DEFAULT_FULL_PATH) : xmlFile;
    }
    //endregion
}







/* OLD METHOD IDEAS: */
/*
public boolean setXMLFile(File file) {
    boolean success = false;
    File newXML = file == null || !file.exists() ? new File(DEFAULT_FULL_PATH) : file;

    // check if file exists
    if (!newXML.exists()) {
        Log.d(XML_MISSING_ERROR);   // notify debugger, log 'xml missing' msg

        try {
            // init new XML document instance if null or missing
            Log.d(GENERATING_XML);
            newXML = new File(DEFAULT_FULL_PATH);
            success = newXML.createNewFile();
            Log.d(XML_GENERATED_SUCCESS);
        } catch (Exception e) {
            Log.d(XML_GENERATE_ERROR);
            Log.e(e.getMessage());
            e.printStackTrace();
            Log.d(SKIPPING_PROCEDURE);
        }
    }

    try {
        success = newXML.createNewFile();   // Generate t
    } catch (Exception e) {
        Log.e(XML_GENERATE_ERROR);
        Log.d(GENERATING_XML);
        Log.e(e.getMessage());
        e.printStackTrace();
        Log.d(SKIPPING_PROCEDURE);
    }

    return success;
}
*/
/* * * * * * * * * * */