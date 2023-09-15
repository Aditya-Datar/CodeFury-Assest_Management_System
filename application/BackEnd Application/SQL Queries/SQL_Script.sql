CREATE DATABASE IF NOT EXISTS EAssetsManagementDB;

# Switch to the "EAssetManagementDB" database
USE EAssetsManagementDB;


-- Create the "UserDetails" table
CREATE TABLE IF NOT EXISTS UserDetails (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Role ENUM('Admin', 'Borrower') NOT NULL,
    Telephone VARCHAR(15),
    EmailID VARCHAR(255) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL
);

ALTER TABLE UserDetails
ADD COLUMN isBanned BOOLEAN DEFAULT FALSE;
-- Create the "UserLoginDetails" table
CREATE TABLE IF NOT EXISTS UserLoginDetails (
    Email VARCHAR(255) PRIMARY KEY,
    Password VARCHAR(255) NOT NULL,
    UserId INT,
    FOREIGN KEY (UserId) REFERENCES UserDetails(ID)
);


-- Create an INSERT trigger
DELIMITER //
CREATE TRIGGER UserDetails_Insert
AFTER INSERT ON UserDetails
FOR EACH ROW
BEGIN
    INSERT INTO UserLoginDetails (Email, Password, UserId)
    VALUES (NEW.EmailID, NEW.Password, NEW.ID)
    ON DUPLICATE KEY UPDATE
    Email = NEW.EmailID, Password = NEW.Password;
END;
//
DELIMITER ;

-- Create an UPDATE trigger
DELIMITER //
CREATE TRIGGER UserDetails_Update
AFTER UPDATE ON UserDetails
FOR EACH ROW
BEGIN
    IF NEW.EmailID != OLD.EmailID OR NEW.Password != OLD.Password THEN
        UPDATE UserLoginDetails
        SET Email = NEW.EmailID, Password = NEW.Password
        WHERE UserId = NEW.ID;
    END IF;
END;
//
DELIMITER ;


-- Create a trigger to enforce the constraint on INSERT and UPDATE
DELIMITER //
CREATE TRIGGER EnsureConsistency
BEFORE INSERT ON AssetDetails
FOR EACH ROW
BEGIN
    IF NEW.IsAvailable = FALSE THEN
        SET NEW.IsLended = FALSE;
    END IF;
END;
//

CREATE TRIGGER EnsureConsistencyOnUpdate
BEFORE UPDATE ON AssetDetails
FOR EACH ROW
BEGIN
    IF NEW.IsAvailable = FALSE THEN
        SET NEW.IsLended = FALSE;
    END IF;
END;
//
DELIMITER ;

-- Create the "AssetDueDetailsPerUser" table with computed column and a foreign key reference to UserDetails
CREATE TABLE IF NOT EXISTS AssetDueDetailsPerUser (
    UserUniqueID INT,
    AssetID INT,
    DateAdded DATE NOT NULL,
    FOREIGN KEY (UserUniqueID) REFERENCES UserDetails(ID),
    FOREIGN KEY (AssetID) REFERENCES AssetDetails(AssetID)
);
