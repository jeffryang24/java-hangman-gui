package aoop.helper;

import aoop.app.*;
import java.sql.*;

/*
 * @author			: Jeffry Angtoni
 * @date			: January 13, 2017
 * @desc			: Java DB Handler
 */

public class DBContext {
	private static Connection _con;
	private static Statement _st;
	
	public static Connection getConnection() {
		return _con;
	}
	public static Statement getStatement() {
		return _st;
	}
	
	/*
	 * @method		: Connect
	 * @param		: -
	 * @return		: boolean
	 */
	public static boolean Connect(){
		return __Connect();
	}
	
	/*
	 * Connect method for debugging
	 */
	public static String DebugConnect(){
		return __DebugConnect();
	}
	
	
	
	/*
	 * @method		: Close
	 * @param		: -
	 * @return		: boolean
	 */
	public static boolean Close(){
		return __Close();
	}
	
	private static boolean __Connect(){
		try{
			Class.forName(DBConfig.DB_DRIVER);
			_con = DriverManager.getConnection(DBConfig.DB_CON_STRING);
			_st = _con.createStatement(1004, 1008);
			return true;
		}catch(Exception ex){
			__Close();
			return false;
		}
	}
	
	/*
	 * For debugging
	 */
	private static String __DebugConnect(){
		try{
			Class.forName(DBConfig.DB_DRIVER);
			_con = DriverManager.getConnection(DBConfig.DB_CON_STRING);
			_st = _con.createStatement(1004, 1008);
			return "SUCCESS";
		}catch(Exception ex){
			__Close();
			return ex.getMessage();
		}
	}
	
	private static boolean __Close(){
		try{
			if (_st != null){
				_st.close();
				_st = null;
			}
			if (_con != null){
				_con.close();
				_con = null;
			}
			return true;
		}catch(Exception ex){
			return false;
		}
	}
		
}
