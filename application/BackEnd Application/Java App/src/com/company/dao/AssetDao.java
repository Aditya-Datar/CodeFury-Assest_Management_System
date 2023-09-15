package com.company.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.company.model.Asset;
import com.company.model.AssetCategory;

public class AssetDao implements AssetDaoInterface {
	private Connection connection; // JDBC Connection

	public AssetDao() {
		
	}
    public AssetDao(Connection connection) {
        this.connection = connection;
    }

    // Add a new asset to the database
    public boolean addAsset(Asset asset) throws SQLException  {
    	 if (asset.getCategory() == null) {
             throw new IllegalArgumentException("Asset must have an associated category.");
         }

         // Check if the category already exists
         AssetCategoryImpl assetCategoryDAO = new AssetCategoryImpl(connection);
         AssetCategory category = assetCategoryDAO.getOrCreateAssetCategory(asset.getCategory().getCategory());

    	
    	
    	String query = "INSERT INTO assetdetails (assetId,CategoryDetailsId,NameOfAsset, DescriptionOfAsset, DateAdded, IsAvailable) VALUES (?, ?, ?, ?, ?,?)";
    	
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        	preparedStatement.setInt(1, asset.getId());
        	preparedStatement.setInt(2, category.getId());
            preparedStatement.setString(3, asset.getName());
           
            preparedStatement.setString(4, asset.getDescription());
            preparedStatement.setDate(5, new java.sql.Date(asset.getDateAdded().getTime()));
            preparedStatement.setBoolean(6, asset.isAvailable());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0; // Asset added successfully if rows were inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Asset addition failed
        }
    }

    // Update an existing asset in the database
    public boolean updateAsset(Asset asset) {
        String query = "UPDATE assetdetails SET NameOfAsset = ?, type = ?, DescriptionOfAsset = ?, DateAdded = ?, IsAvailable = ? WHERE assetId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, asset.getName());
          //  preparedStatement.setString(2, asset.getType());
            preparedStatement.setString(3, asset.getDescription());
            preparedStatement.setDate(4, new java.sql.Date(asset.getDateAdded().getTime()));
            preparedStatement.setBoolean(5, asset.isAvailable());
            preparedStatement.setInt(6, asset.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0; // Asset updated successfully if rows were affected
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Asset update failed
        }
    }

    // Delete an asset from the database by its ID
    public boolean deleteAsset(int assetId) {
        String query = "DELETE FROM assetdetails WHERE  assetId= ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, assetId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0; // Asset deleted successfully if rows were affected
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Asset deletion failed
        }
    }

    // Retrieve a specific asset by its ID
    public Asset getAssetById(int assetId) {
        String query = "SELECT * FROM assetdetails WHERE assetId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, assetId);
            ResultSet resultSet = preparedStatement.executeQuery();
              if (resultSet.next()) {
                Asset asset = new Asset();
                asset.setId(resultSet.getInt("id"));
                asset.setName(resultSet.getString("name"));
                
                asset.setDescription(resultSet.getString("description"));
                asset.setDateAdded(resultSet.getDate("date_added"));
                asset.setAvailable(resultSet.getBoolean("is_available"));

                return asset;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Asset not found
    }

    // Retrieve a list of all assets from the database
    public List<Asset> getAllAssets() {
    	 List<Asset> assets = new ArrayList<>();

         String query = "SELECT * FROM assetdetails";

         try (PreparedStatement preparedStatement = connection.prepareStatement(query);
              ResultSet resultSet = preparedStatement.executeQuery()) {

             while (resultSet.next()) {
                 Asset asset = new Asset();
                 asset.setId(resultSet.getInt("id"));
                 asset.setName(resultSet.getString("name"));
                 //asset.setType(resultSet.getString("type"));
                 asset.setDescription(resultSet.getString("description"));
                 asset.setDateAdded(resultSet.getDate("date_added"));
                 asset.setAvailable(resultSet.getBoolean("is_available"));

                 assets.add(asset);
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }

         return assets;
     }
    
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   
    }
    
    
    
    
    

   

