package com.company.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.company.Exceptions.NoSuchUserException;
import com.company.Exceptions.UserAlreadyExistsException;
import com.company.model.UserDetails;

public class UserDaoImpl implements UserDao {

	@Override
	public boolean createUser(UserDetails user) throws UserAlreadyExistsException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EAssetsManagementDB","root","Sakshi9098@");
			connection.setAutoCommit(false);
			String query = "insert into UserDetails (ID,Name,Role,Telephone,EmailID,Password, isBanned) values (?,?,?,?,?,?,?);";
			PreparedStatement pstmt = connection.prepareStatement(query);
			
			
			pstmt.setInt(1,user.getUniqueId());
			pstmt.setString(2, user.getUserName());
			pstmt.setString(3,user.getUserRole());
			pstmt.setLong(4, user.getUserTelephone());
			pstmt.setString(5, user.getUserEmail());
			pstmt.setString(6, user.getUserPass());
			pstmt.setString(7, "0");  //by default user are not banned
			
			int result = pstmt .executeUpdate();
			
			connection.commit();
			if(result==1)
			{
				System.out.println("Data Inserted Successfully");
				return true;
			}
			
		} catch (ClassNotFoundException e ) {
			// TODO Auto-generated catch block
		    //System.out.println("Driver Class not found.");
		} catch (SQLException e) {
			//System.out.println("SQL Exception");
			throw new UserAlreadyExistsException();
		} 
		return false;
	}

	@Override
	public UserDetails readUser(int uniqueEmailId) throws NoSuchUserException {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EAssetsManagementDB","root","Sakshi9098@");
			stmt = connection.createStatement();
			String query = "select * from UserDetails";
	
			rs= stmt.executeQuery(query);
			
			UserDetails resultedUser = new UserDetails();
			while(rs.next())
			{			
				if(rs.getInt("Id") == uniqueEmailId)
				{
					resultedUser.setUniqueId(uniqueEmailId);
					resultedUser.setUserName(rs.getString("Name"));
					resultedUser.setUserRole(rs.getString("Role"));
					resultedUser.setUserTelephone(rs.getLong("Telephone"));
					resultedUser.setUserEmail(rs.getString("EmailID"));
					resultedUser.setUserPass(rs.getString("Password"));
					return resultedUser;
				}
			}	
			
		} catch (ClassNotFoundException e ) {
			// TODO Auto-generated catch block
		    System.out.println("Driver Class not found.");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NoSuchUserException();
		} finally
		{
			    try {
					if(rs !=null)
					    rs.close();
					if(stmt !=null)
					    stmt.close();
					if(connection !=null)
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Problem in closing Database connection");
				}
		}
//		throw new NoSuchUserException();
		return null;
	}

	@Override
	public boolean deleteUser(int uniqueId) throws NoSuchUserException {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users","root","root");
			connection.setAutoCommit(false);
			
			String query1="Delete from UserLoginDetails where UserId=?";
			PreparedStatement pstmt1 = connection.prepareStatement(query1);
			pstmt1.setInt(1, uniqueId);
			pstmt1.executeUpdate();
			
			String query = "Delete from UserDetails where ID = ?";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1,uniqueId);
			pstmt .executeUpdate();
			
			connection.commit();
			return true;	
		} catch (ClassNotFoundException e ) {
		    System.out.println("Driver Class not found.");
		} catch (SQLException e) {
			//System.out.println("SQL Exception");
			throw new NoSuchUserException();
		} 
		return false;
	}

	@Override
	public UserDetails updateUser(int uniqueId, UserDetails user) {
		
		Connection connection = null;
		PreparedStatement pstmt0 = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users","root","root");
			connection.setAutoCommit(false);
			
			String updateQuery1 = "UPDATE userDetails SET EmailID =?,Password =? WHERE ID =?";
			pstmt0 = connection.prepareStatement(updateQuery1);
			
			pstmt0.setString(1,user.getUserEmail());
			pstmt0.setString(2, user.getUserPass());
			pstmt0.setInt(3,uniqueId);
			pstmt0.executeUpdate();
			
			String updateQuery = "UPDATE userDetails SET Name = ?, Role = ?, Telephone = ?, EmailID =?,Password =? WHERE ID =?";
			pstmt1 = connection.prepareStatement(updateQuery);	
			
			pstmt1.setString(1, user.getUserName());
			pstmt1.setString(2, user.getUserRole());
			pstmt1.setLong(3, user.getUserTelephone());
			pstmt1.setString(4, user.getUserEmail());
			pstmt1.setString(5, user.getUserPass());
			pstmt1.setInt(6, uniqueId);
			pstmt1.executeUpdate();
			
			connection.commit();
			
		} catch (ClassNotFoundException e ) {
			// TODO Auto-generated catch block
		    System.out.println("Driver Class not found.");
		} catch (SQLException e) {
			System.out.println("SQL Exception");
		} finally
		{
			    try {
					if(rs !=null)
					    rs.close();
					if(pstmt0 !=null)
					    pstmt0.close();
					if(pstmt1!=null)
						pstmt1.close();
					if(connection !=null)
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Problem in closing Database connection");
				}
		}
		return null;
	}

	@Override
	public List<UserDetails> readAllUsers() {
		List<UserDetails> listOfAllUsers = new ArrayList<>();
		
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users","root","root");
			stmt = connection.createStatement();
			String query = "select *from UserDetails";
	
			rs= stmt.executeQuery(query);
			
			UserDetails resultedUser = new UserDetails();
			while(rs.next())
			{			
					resultedUser.setUniqueId(rs.getInt("ID"));
					resultedUser.setUserName(rs.getString("Name"));
					resultedUser.setUserRole(rs.getString("Role"));
					resultedUser.setUserTelephone(rs.getLong("Telephone"));
					resultedUser.setUserEmail(rs.getString("EmailID"));
					resultedUser.setUserPass(rs.getString("Password"));
					
					listOfAllUsers.add(resultedUser);
					
					resultedUser= new UserDetails();
			}
		
			return listOfAllUsers;
			
		} catch (ClassNotFoundException e ) {
			// TODO Auto-generated catch block
		    System.out.println("Driver Class not found.");
		} catch (SQLException e) {
			System.out.println("SQL Exception");
		} finally
		{
			    try {
					if(rs !=null)
					    rs.close();
					if(stmt !=null)
					    stmt.close();
					if(connection !=null)
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Problem in closing Database connection");
				}
		}
		return null;
	}

	

}
