# Asset Jets - E-Asset Management Portal

## Table of Contents

- [Introduction](#introduction)
- [Getting Started](#getting-started)
  - [Front End](#front-end)
  - [Back End](#back-end)
- [Folder Structure](#folder-structure)
- [Why Multiple Folders?](#why-multiple-folders)
- [Code Repetition Justification](#avoiding-code-repetition)
- [Documentation](#documentation)
- [Important Notes](#important-notes)

## Introduction

Welcome to Asset Jets, an advanced E-Asset Management Portal. This platform is designed to simplify asset management for businesses. Whether you're an administrator or a user, Asset Jets has got you covered. This README will guide you through the setup process and provide insights into the project structure.

## Getting Started

### Front End

1. **Landing Page**: To access the system, start with the landing page.

2. **Login**: Use any dummy login credentials. For example, you can use:
   - **Username:** [Your Choice]
   - **Password:** Test@123 (Ensure the password meets security requirements).

3. **Admin Page**: Upon successful login, you will be redirected to the Admin page. Here, you can explore all the administrative functionalities.

4. **User Page**: If you wish to enter the user side of the portal, navigate to the 'user application' folder, and open the 'user/index' file.

### Back End

To set up the backend, follow these steps:

1. **Java and MySQL**: Ensure you have Java 11 and MySQL version 8 installed on your system.

2. **Download JDBC JAR**: Download the JDBC JAR file from [your preferred source] and save it to a location on your system.

3. **Connect JDBC to Build Path**: Now, let's connect the JDBC JAR to your project's build path. Here's how:
   - Open your project in your preferred Java development environment.
   - Locate the JDBC JAR file you downloaded.
   - Right-click on your project in the project explorer.
   - Select "Properties" or "Build Path" depending on your IDE.
   - Under the "Libraries" or "Build Path" tab, click on "Add External JARs" or "Add JARs" and navigate to the downloaded JDBC JAR file.
   - Select the JAR file and click "OK" to add it to your project's build path.

4. **Execute SQL Queries**: Execute SQL queries from the `backend application/sql queries` folder to create the necessary database tables.

5. **Edit Connection URLs**: In the following classes, edit the connection URLs to connect to your MySQL database. Use the Java convention:
   - `AssetDueDaoImpl`
   - `UserDaoImpl`
   - `AssetCategoryImpl`
   - `AssetDao`

6. **Execute Main.java**: Run the `Main.java` file located in the package `com.company.ui` to start the backend of the Asset Jets portal.

## Folder Structure

In the Asset Jets project, you'll find the following folder structure:

- `/application`: This directory contains all the code files related to the application's front-end and back-end.

- `/documents`: All necessary documentation, manuals, and guides are stored here.

- `/images`: This folder holds essential images and assets required for the application's user interface. We have organized them into separate folders for better management.

## Why Multiple Folders?

You might wonder why we have organized the project into multiple folders. Here are the key reasons:

- **Modularity**: Separating code files, documents, and images enhances modularity, making it easier to manage and maintain the project.

- **Clarity**: The use of separate folders enhances clarity, helping developers quickly locate the resources they need.

- **Resource Management**: Storing images and assets in dedicated folders streamlines resource management, ensuring that important visuals are easily accessible.

## Code Repetition Justification

You may have noticed that some CSS and JavaScript code is repeated in the project. This repetition is intentional and serves an important purpose:

- **Performance**: By replicating code when necessary, we reduce the number of HTTP requests, which can significantly improve page load times.

- **Offline Use**: In some cases, assets are cached locally to ensure the application functions even when the user is offline.

- **Reduced Dependency**: Minimizing reliance on external resources enhances the project's independence and robustness.

## Documentation

For detailed documentation, refer to the following:

1. **PPT**: Find key points in the presentation.

2. **Implementation Report**: Get insights into the project's implementation.

3. **Flow Diagram**: Visualize the system's workflow.

4. **Test Cases**: Review test cases for the system.

5. **Use Case Diagram**: Understand system use cases.

6. **ER Diagram**: Explore the entity-relationship diagram.

## Important Notes

- **Security**: Please note that the provided 'Test@123' password is a strong example. In a real-world scenario, it's essential to enforce robust password policies to enhance security.

By following these instructions, you'll be able to set up and explore the Asset Jets E-Asset Management Portal effectively. We wish you the best of luck in the competition! If you have any questions or need further assistance, feel free to reach out to us.
