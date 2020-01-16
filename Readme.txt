Instructions to test, build and run the application.

1.- In order to test the application the Java 8 must be used.
2.- Uncompress the Restaurant.zip file, it will contain a folder named Restaurant with the sources (project) and a 
	runnable file named Restaurant.jar.


Instructions to run the application using the Restaurant.jar file:
1.- Open a console in the same directory than the Restaurant.jar file and enter the following command:
	java -jar Restaurant.jar
2.- The application will run in the console and it will provide instructions and error messages (if any data is wrong).
3.- The format for the date is yyyy-MM-dd so 2020-01-16 will work.
4.- The format for the time is HH:mm (hours must be 24 based) so 09:49 will work for an am example, 23:00 will work for a pm example.
5.- Path of the file must include extension, example c:\\restaurant_hours.csv
6.- At the end the application will show 2 lists(for better understanding), the first one will show the restaurants that are opened on the
	selected date and time and a second list of restaurants that remain opened from a day before (in case the time entered is for example 
	1:00 of a Saturday and the restaurant remain opened from Friday).


Instructions to load the application into an IDE and run it.
1.- Eclipse STS was used to create the project so it's suggested to use that IDE.
2.- Open STS, click on the File menu and then in the Import... menu item.
3.- Under the folder Maven select Existing Maven Projects and then click the button Next.
4.- Navigate to the folder where the project's pom.xml file is located and select it then click the button Finish that will load the 
	project into the IDE.
5.- Once the project is loaded, right click it and select Maven then Update Project... that will download the required libraries.
6.- Right click the pom.xml and select Run As then 3 Maven clean.
7.- Right click the pom.xml and select Run As then 6 Maven test; that will run the project's unit tests.
8.- In order to run the application using the IDE go to the file named Starter.java inside the package com.example.restaurant.
9.- Once in the file, righ click it and select Run As then 2 Java Application and then in the IDE's console the application will
	be running, follow the instructions described in the previous section.
