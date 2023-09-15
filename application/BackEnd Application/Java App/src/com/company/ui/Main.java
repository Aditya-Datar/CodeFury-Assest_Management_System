package com.company.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.company.Exceptions.NoSuchUserException;
import com.company.Exceptions.UserAlreadyExistsException;
import com.company.dao.*;
import com.company.model.*;
import com.company.service.AssetDueService;
import com.company.service.AssetService;
import com.company.service.AssetServiceInterface;
import com.company.service.UserServiceImpl;


public class Main {
	
    public static void main(String[] args) {
    	System.out.println("                        ===========================================================");
         System.out.println("                                                   Welcome                             ");
         System.out.println("                        ===========================================================");
         Scanner sc = new Scanner(System.in);
         
         UserDao dao = new UserDaoImpl();
         UserServiceImpl userService = new UserServiceImpl();
         userService.setDao(dao);
         
         String jdbcUrl = "jdbc:mysql://localhost:3306/EAssetsManagementDB";
         String dbUser = "root";
         String dbPassword = "Sakshi9098@";
         
         try {
			Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
		} catch (SQLException e) {
			System.out.println("There is some technical issues happened.");
		}
         AssetDao assetDao = new AssetDao();
         AssetService assetService = new AssetService();
         assetService.setDao(assetDao);
         
         
         AssetDueDaoImpl assetDueDaoService = new AssetDueDaoImpl();
         AssetDueService assetdueservice = new AssetDueService();
         assetdueservice.setDao(assetDueDaoService);
         
         
         //Service Layer dependency set up
         
         
         System.out.println("1.Login");
         System.out.println("2.Register");
         System.out.println("Enter your choice:");
         int choose = sc.nextInt();
         
         if(choose==1) {
         //User will be asked to login with credentials
         System.out.println("ID:");
         int id = sc.nextInt();
         System.out.println("Password:");
         String password = sc.next();
         try {
			UserDetails user = userService.readUser(id);
			if(user.getUserRole().equalsIgnoreCase("Admin"))
			{
				int choice=0;
				do {
				System.out.println("1.Display All Users information");
				System.out.println("2.Add Asset");
				System.out.println("3.Read all overdue Users");
				System.out.println("Press 0 to exit from this menu");
				
				System.out.println("What would you like to do?");
				choice = sc.nextInt();
				switch(choice) {
				case 1:
					List<UserDetails> listOfUsers = new ArrayList<>();
					listOfUsers = userService.readAllUsers();
					for(UserDetails users :listOfUsers)
					{
						System.out.println(users);
					}
					break;
				case 2:
					Asset asset = new Asset();
					System.out.println("Enter Asset Details:");
					
					System.out.println("Asset ID :");
					int assetId = sc.nextInt();
					asset.setId(assetId);
					
				
					System.out.println("Asset Name :");
					String assetName = sc.next();
					asset.setName(assetName);
					
					
					System.out.println("Asset Description");
					String assetDesc = sc.next();
					asset.setDescription(assetDesc);

					System.out.println("Asset Catagory Details.....");
					
					AssetCategory assetcat = new AssetCategory();
					
					System.out.println("Catagory id:");
					int catId = sc.nextInt();
					assetcat.setId(catId);
					
					System.out.println("Catagory:");
					String catName = sc.next();
					assetcat.setCategory(catName);
					
					System.out.println("Lending Period in Days:");
					int lp = sc.nextInt();
					assetcat.setLendingPeriodInDays(lp);
					
					System.out.println("Ban Period in Days:");
					int bp = sc.nextInt();
					assetcat.setLendingPeriodInDays(bp);
					
					asset.setCategory(assetcat);
					
					try {
						assetService.addAsset(asset);
						System.out.println("Asset added successfully");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						System.out.println("Asset Not Added");
					}
					
					break;
				case 3:
					List<AssetDue> overDueUsers = assetdueservice.searchAllOverDueUser();
					   if(overDueUsers != null) {
						   for(AssetDue assetdueuser: overDueUsers) {
							   System.out.println(assetdueuser);
						   }
					   }
					   else
						   System.out.println("There is no record to display here!");
					break;
				case 0:
					System.out.println("Exiting from the system.....");
					System.exit(choice);
					break;
				default:
					System.out.println("Invalid Choice");
				} 
				
				}while(true);
			}
			else if(user != null)
			{
				//BORROWER Functionalities
				System.out.println("1.Search Asset By Id");//--> search by name , id, AllAssets
				System.out.println("2.Get Asset");
				System.out.println("3.Get overdue assets");
				System.out.println("4.Return Asset");
				
				System.out.println("Enter Your Choice:");
				int ch1 = sc.nextInt();
				
				switch(ch1){
					case 1:
						System.out.println("1.Get Asset By ID");
						System.out.println("2.Get All Assets");
						System.out.println("3.Get overdue assets");
						System.out.println("4.Return Asset");
						
						System.out.println("Enter Choice:");
						int ch2= sc.nextInt();
						switch(ch2) 
						{
						
						case 1:
							System.out.println("Enter Asset ID :");
							int assetid = sc.nextInt();
							Asset assetbyId = assetService.getAssetById(assetid);
							System.out.println(assetbyId);
							break;
						case 2:
							System.out.println("All Assets are as Follows:");
							List<Asset> assets = assetService.getAllAssets();
							for(Asset asset : assets)
							{
								System.out.println(asset);
							}
							
							break;
	                    default:
	                    	System.out.println("Invalid choice");
						}
						break;
					case 2:
                        System.out.println("All assets are :");
                        List<Asset> list =assetDueDaoService.readAllAsset(user.getUniqueId());
                        for(Asset asset : list)
                        {
                        	System.out.println(asset);
                        }
                       
						break;
					case 3:
						List<AssetDue> overDueUsers = assetDueDaoService.readAllOverDueUser();
						   if(overDueUsers != null) {
							   for(AssetDue assetdueuser: overDueUsers) {
								   System.out.println(assetdueuser);
							   }
						   }
						   else
							   System.out.println("There is no record to display here!");
						   break;
					case 4:
						System.out.println("Enter User ID :");
						int userid = sc.nextInt();
						System.out.println("Enter Asset ID:");
						int assetid = sc.nextInt();
//						if(assetdueservice.returnAsset(userid,assetid))
//						{
//							System.out.println("Asset Returned!");
//						}
						double lateFee =  assetdueservice.calculateDueFee(userid,assetid);
						   if(lateFee > 0) {
							   System.out.println("You have exccedded the lending time period and you have a late fees for this asset is " + lateFee);
							   System.out.println("Do you want to preceed further, Enter 1 for Yes and 0 for No : ");
					 
							   int ch = sc.nextInt();  
							   if(ch == 1) {
									   boolean isReturned = assetdueservice.returnAsset(userid,assetid);
									   if(isReturned == true)
										   System.out.println("Asset returned successfully!"); 
							   }
						   }else {
							   boolean isReturned = assetdueservice.returnAsset(userid,assetid);
							   if(isReturned == true)
								   System.out.println("Asset returned successfully!"); 
						   }
						break;
					default :
						System.out.println("Invalid Choice");
					
				}
			}
			
         }catch (NoSuchUserException e) {
			 // TODO Auto-generated catch block
        	 System.out.print(e.getMessage());
		 }
         catch (NullPointerException e) {
        	 System.out.print(e.getMessage());
         }
		
			
		 }
         else
         {
        	 UserDetails newUser = new UserDetails();
        	 System.out.println("Enter User Details:");
        	 
        	 System.out.println("Enter Name:");
        	 String Name = sc.next();
        	 newUser.setUserEmail(Name);
        	 
        	 System.out.println("Enter Role(Admin/Borrower):");
        	 String Role = sc.next();
        	 newUser.setUserRole(Role);
        	 
        	 System.out.println("Enter Telephone:");
        	 Long Telephone = sc.nextLong();
        	 newUser.setUserTelephone(Telephone);
        	 
        	 System.out.println("Enter Email:");
        	 String Email = sc.next();
        	 newUser.setUserEmail(Email);
        	 
        	 System.out.println("Enter Password:");
        	 String pass = sc.next();
        	 newUser.setUserPass(pass);
        	 
        	 try {
				userService.createUser(newUser);
				System.out.println("User Registered Successfully");
			} catch (UserAlreadyExistsException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
         }
         
    }

         
  }