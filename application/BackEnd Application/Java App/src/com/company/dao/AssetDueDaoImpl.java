package com.company.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.company.Exceptions.TechnicalException;
import com.company.Exceptions.AssetAlreadyBorrowedException;
import com.company.Exceptions.AssetAlreadyExistsException;
import com.company.model.Asset;
import com.company.model.AssetCategory;
import com.company.model.AssetDue;
import com.company.model.UserDetails;

public class AssetDueDaoImpl implements AssetDueDao{
	private Connection connection = null;
	private Statement stmt = null;
	
	private void openResources() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EAssetsManagementDB", "root", "Sakshi9098@");
		stmt = connection.createStatement();
	}
	private void closeResources() throws SQLException {
		stmt.close();
		connection.close();
	}
	
	//Supporting operations
	//Give me no of days between given date and current date
	public long noOfDays(Date date) {
		try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dateStr = dateFormat.format(date);
            Date takenDate = dateFormat.parse(dateStr);
         
            //Get the current date
            Date currentDate = new Date();
            String currentDate1 = dateFormat.format(currentDate);
            currentDate = dateFormat.parse(currentDate1);

            //Calculate the time difference in milliseconds
            long timeDifferenceInMillis = currentDate.getTime() - takenDate.getTime();

            //Convert milliseconds to days
            long daysDifference = timeDifferenceInMillis / (1000 * 60 * 60 * 24);
            return daysDifference; 
        } catch (Exception e) {
            e.printStackTrace();
        }
		return 0;
	}
	
	//Give total no of due days 
	public long noOfDueDays(Date date, int NoOfDaysLended) {
		long noOfDays = noOfDays(date);
		if( noOfDays - NoOfDaysLended > 0)
			return noOfDays - NoOfDaysLended;
		return 0;
	}
	
	//Create
	@Override
	//Borrower can be able to borrow the particular asset. 
	public boolean createAssetDue(int userId, int assetId) throws TechnicalException, AssetAlreadyExistsException, AssetAlreadyBorrowedException {
		try {
			openResources();
			connection.setAutoCommit(false);
			String query = "Select u.isbanned, a.isAvailable from assetduedetailsperuser ad Join assetdetails a Join userdetails u On (ad.useruniqueId = u.id) and (ad.assetid = a.assetId) where u.ID = " + userId + " AND a.AssetID = " + assetId;
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				if(rs.getInt("isAvailable")==1 && rs.getInt("isbanned")==0) {
					query = "Insert into AssetDueDetailsPerUser(useruniqueId, assetId, DateAdded) values(?,?,?)";
					PreparedStatement pstmt = connection.prepareStatement(query);
					pstmt.setInt(1, userId);
					pstmt.setInt(2, assetId);
					java.sql.Date dateToInsert = new java.sql.Date(new Date().getTime());
					pstmt.setDate(3, dateToInsert);
					pstmt.executeUpdate();
					query = "UPDATE assetdetails SET IsAvailable = 0 WHERE assetid = ?";
				
						PreparedStatement pstmt1 = connection.prepareStatement(query);
						pstmt1.setInt(1, assetId);
		                int rowsUpdated = pstmt1.executeUpdate();
		                
		                if (rowsUpdated > 0) {
		                    System.out.println("Asset Borrowed!.");
		                } else {
		                    System.out.println("Failed to upodate asset details");
		                }
				}
				else {
					throw new AssetAlreadyBorrowedException("Asset is already borrowed");
				}
			}
			rs.close();
			connection.commit();
			closeResources();
		}
		catch(ClassNotFoundException ex) {
			throw new TechnicalException();
		}
		catch(SQLException ex) {
			throw new AssetAlreadyExistsException("Asset is not available for borrow.");
		}
		return false;
	}
	
	//Borrower can be able to see all the due assets taken by him/her. 
	@Override
	public List<Asset> readAllDueAsset(int userId) {
		try {
			openResources(); 
			String query = "SELECT DISTINCT * from assetdetails a, assetduedetailsperuser ad, categorydetails c where a.AssetID = ad.AssetID and ad.UserUniqueID = " + userId;
			ResultSet rs = stmt.executeQuery(query);
			List<Asset> assets = new ArrayList();
			Asset asset = new Asset();
			while(rs.next()) {
				java.sql.Date sqlDueDate = rs.getDate("ad.DateAdded");
                // Convert SQL date to java.util.Date
                Date DueDate = new Date(sqlDueDate.getTime());
				if(noOfDueDays(DueDate ,rs.getInt("c.LendingPeriodInDays")) > 0) {
					asset.setId(rs.getInt("assetId"));
					
					AssetCategory assetCategory  = new AssetCategory();
					assetCategory.setId(rs.getInt("c.id"));
			        assetCategory.setCategory(rs.getString("c.category"));
			        assetCategory.setLendingPeriodInDays(rs.getInt("lendingPeriodInDays"));
			        assetCategory.setLateReturnFee(rs.getInt("LateReturnFeePerDay"));
			        assetCategory.setBanPeriodInDays(rs.getInt("BanPeriodForLateReturn"));
			        
					asset.setCategory(assetCategory);
					asset.setName(rs.getString("NameOfAsset"));
					asset.setDescription(rs.getString("DescriptionOfAsset"));
					asset.setDateAdded(rs.getDate("dateAdded"));
					if(rs.getInt("isAvailable") == 1)
						asset.setAvailable(true);
					else
						asset.setAvailable(false);
					
					assets.add(asset);
				}
			}
			return assets;
		}catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Borrower can saw all the asset details
	@Override
	public List<Asset> readAllAsset(int userId) {
		List<Asset> assets = null;
		try {
			openResources();
			String query = "Select a.assetId, a.categorydetailsId, a.nameofasset, a.descriptionofasset, a.dateadded, a.isavailable, a.islended, c.id, c.category, c.lendingPeriodInDays, c.LateReturnFeePerDay, c.BanPeriodForLateReturn from assetdetails a, assetduedetailsperuser ad, userdetails u, categorydetails c where a.AssetID = ad.AssetID and ad.UserUniqueID = " + userId;
			ResultSet rs = stmt.executeQuery(query);
			assets = new ArrayList();
			Asset asset = new Asset();
			while(rs.next()) {
				asset.setId(rs.getInt("assetId")); 
				
				AssetCategory assetCategory  = new AssetCategory();
				assetCategory.setId(rs.getInt("c.id"));
		        assetCategory.setCategory(rs.getString("c.category"));
		        assetCategory.setLendingPeriodInDays(rs.getInt("lendingPeriodInDays"));
		        assetCategory.setLateReturnFee(rs.getInt("LateReturnFeePerDay"));
		        assetCategory.setBanPeriodInDays(rs.getInt("BanPeriodForLateReturn"));
		        
				asset.setCategory(assetCategory); //pass object of category asset
				asset.setName(rs.getString("NameOfAsset"));
				asset.setDescription(rs.getString("DescriptionOfAsset"));
				asset.setDateAdded(rs.getDate("dateAdded"));
				if(rs.getInt("isAvailable") == 1)
					asset.setAvailable(true);
				else
					asset.setAvailable(false);
				
				assets.add(asset);
			}
			return assets;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Admin can see all the over due users who have exceeded lending days. 
	@Override
	public List<AssetDue> readAllOverDueUser() {
		try {
			openResources(); 
			String query = "SELECT DISTINCT * from assetdetails a, assetduedetailsperuser ad, userdetails u, categorydetails c where a.AssetID = ad.AssetID;";
			ResultSet rs = stmt.executeQuery(query);
			List<AssetDue> assetdues = new ArrayList();
			AssetDue asset = new AssetDue();
			while(rs.next()) {
				java.sql.Date sqlDueDate = rs.getDate("ad.DateAdded");
                // Convert SQL date to java.util.Date
                Date DueDate = new Date(sqlDueDate.getTime());
                
				if(noOfDueDays(DueDate ,rs.getInt("lendingPeriodInDays")) > 0) {
					UserDetails user = new UserDetails();
					user.setUniqueId(rs.getInt("u.Id"));
					user.setUserName(rs.getString("u.Name"));
					user.setUserRole(rs.getString("Role"));
					user.setUserTelephone(rs.getLong("Telephone"));
					user.setUserEmail(rs.getString("EmailID"));
					user.setUserPass(rs.getString("Password"));
					
					asset.setBorrower(user);
					asset.setAssets(readAllDueAsset(rs.getInt("u.Id")));
					asset.setAddedDate(rs.getDate("ad.dateAdded"));
					
					assetdues.add(asset);
				}
			}
			return assetdues;	
		}
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	//Update
	
	//Delete
	@Override
	
	//Return the borrow asset
	public boolean returnAsset(int userId, int assetId) {
		try {
			openResources(); 
			String query = "DELETE FROM assetduedetailsperuser Where useruniqueId = " + userId + " and assetId = " + assetId;
			PreparedStatement pstmt = connection.prepareStatement(query);
			int rowsDeleted = pstmt.executeUpdate();
            
            if (rowsDeleted > 0) {
                return true; // Asset deleted successfully
            }
		}
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//operation
	@Override
	//Calculate the total due fees
	public double calculateDueFee(int userId, int assetId){
		try {
			openResources();
			String query = "Select  ad.DateAdded, c.lendingPeriodInDays, c.LateReturnFeePerDay from assetdetails a, assetduedetailsperuser ad, userdetails u, categorydetails c where a.AssetID = ad.AssetID and ad.UserUniqueID = " + userId + " and ad.AssetID = " + assetId;
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				java.sql.Date sqlDueDate = rs.getDate("ad.DateAdded");
                Date DueDate = new Date(sqlDueDate.getTime());
                int noOflendingPeriod = rs.getInt("c.lendingPeriodInDays");
                int lateReturnFee = rs.getInt("c.LateReturnFeePerDay");
				double lateFee = calculateLateFee(DueDate, noOflendingPeriod, lateReturnFee);
				return lateFee;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
//	@Override
	//Use to calculate due fees
	public double calculateLateFee(Date date, int noOflendingPeriod, int lateReturnFee) { 
		long noOfdays = noOfDueDays(date, noOflendingPeriod); 
		if(noOfdays >  0)
			return noOfdays*lateReturnFee; 
		return 0;
	}
}
