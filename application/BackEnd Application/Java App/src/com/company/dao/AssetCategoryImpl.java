package com.company.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.company.model.AssetCategory;

public class AssetCategoryImpl implements AssetCategoryInterface{


    private Connection connection; // Your JDBC connection

    public AssetCategoryImpl(Connection connection) {
        this.connection = connection;
    }

    // Create or retrieve an AssetCategory by category name
    public AssetCategory getOrCreateAssetCategory(String category) throws SQLException {
    	try {
        AssetCategory assetCategory = getAssetCategoryByCategory(category);

        if (assetCategory == null) {
            // Category does not exist, create a new one
        	 AssetCategory newCategory = new AssetCategory();
             newCategory.setCategory(category);
             newCategory.setLendingPeriodInDays(7); // Set default values here
             newCategory.setLateReturnFee(0.0);
             newCategory.setBanPeriodInDays(0);

             assetCategory = createAssetCategory(newCategory);
        }
        return assetCategory;
    	}catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception or re-throw it as needed
            return null;
        }
       
    }

    // Retrieve an AssetCategory by category name
    public AssetCategory getAssetCategoryByCategory(String category) throws SQLException {
        String query = "SELECT * FROM categorydetails WHERE category = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, category);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createAssetCategoryFromResultSet(resultSet);
                }
            }
        }
       return null;
        
    }

    // Create a new AssetCategory
    public AssetCategory createAssetCategory(AssetCategory newCategory) throws SQLException {
        String query = "INSERT INTO categorydetails (id,category, LendingPeriodIndays, LateReturnFeePerDay, BanPeriodForLateReturn) VALUES (?, ?, ?, ?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        	preparedStatement.setInt(1, newCategory.getId());
            preparedStatement.setString(2, newCategory.getCategory());
            preparedStatement.setInt(3, newCategory.getLendingPeriodInDays());
            preparedStatement.setDouble(4, newCategory.getLateReturnFee());
            preparedStatement.setInt(5, newCategory.getBanPeriodInDays());

            preparedStatement.executeUpdate();
        }

        return newCategory;
        }

      
    

    // Helper method to create an AssetCategory object from a ResultSet
    private AssetCategory createAssetCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String categoryName = resultSet.getString("category");
        int lendingPeriodInDays = resultSet.getInt("LendingPeriodIndays");
        double lateReturnFee = resultSet.getDouble("LateReturnFeePerDay");
        int banPeriodInDays = resultSet.getInt("BanPeriodForLateReturn");

        AssetCategory assetCategory = new AssetCategory();
        assetCategory.setId(id);
        assetCategory.setCategory(categoryName);
        assetCategory.setLendingPeriodInDays(lendingPeriodInDays);
        assetCategory.setLateReturnFee(lateReturnFee);
        assetCategory.setBanPeriodInDays(banPeriodInDays);

        return assetCategory;
    }

	
}

