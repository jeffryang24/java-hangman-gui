package aoop.app;

/*
 * @author		: Jeffry Angtoni
 * @date		: January 13, 2017
 * @desc		: Java Database Configuration
 * @note		: For implementation, please see DBContext.java
 */

public class DBConfig {
	
	/*
	 * CURRENTLY DEFAULT DRIVER FOR THIS APP IS ACCESS (ODBC)
	 * Please kindly add your own driver configuration here if you want... :)
	 */
	
	// Access Driver Class
	public static final String DB_DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
	
	// Access Database File
	public static final String DB_FILE = "C:\\Users\\Jeffry Angtoni\\workspace\\java-hangman-gui\\gamedb.mdb";
	
	// Access Connection String
	// ATTENTION!!
	// If you are using 32-bit JDK/JRE/JVM, please uncomment below variable
	//public static String DB_CON_STRING = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + DB_FILE + ";";
	// If you are using 64-bit JDK/JRE/JVM, please uncomment below variable
	public static String DB_CON_STRING = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + DB_FILE + ";";
	
}
>>>>>>> ca5ef1b6b0d8a4e153cf2c8c7c5ad981c913befa
