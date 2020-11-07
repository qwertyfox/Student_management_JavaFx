# Student_management_JavaFx
Desktop UI application using Core Java, JavaFx and FXML. 

## Overall

- Designed using Scene Builder (FXML) instead of Java codes <br>
- Uses Sqlite3 as datasource<br>
- Library path are set in place of Maven Dependencies (being very early project)<br>
- Creates report in MS Word format<br>
- Is a multithreaded desktop application

## Codes

- Context Menu (right mouse click menu) is enabled in main UI using .setrowFactory(...)<br>
- For instant update in main UI text field, .addListner is used in Tableview (main UI structure)<br>
- [CourseSubjectsWindow](src/sample/fxml/CourseSubjectsWindow.fxml) is not Modal (does not disable main UI when it is opened)<br>
- Uses Singleton class for [DAO class](/src/sample/dao/DbAccess.java)<br>
- Uses Task class to avoid using UI thread and TimerTask class to periodically validate conditions<br>
- Most SQL queries are seperated and stored in [SQLStatement class](src/sample/dao/SQLStatements.java)<br>
- Uses PreparedStatement as well as Statement class<br>
- ResultSet data is combined using "=" and sent to other methods which extracts the data by .split(<em>regex</em> "=") and stores in Array. It is extracted later using <em>ArrayName</em>[<em>element#</em>]<br>
- For certain insert queries, autocommit is set to false until all conditions are fulfilled <br>
- Custom Buttons are created and default buttons are set <br> 
- Delibrate Thread.sleep() enabled to see the progress bar<br>
- DateTimeFormatter.ofPattern("dd-MM-yyyy") is used to set to Australian date format ;)<br>
- Uses BufferedWriter and FileWriter to generate MS Word report on chosen student<br>
- Uses lambda expressions<br>


## TODO
- Java 9 Modularity is not used (Future iteration will include)<br>
- Certain methods will be moved to new classes<br>
- Adding new Student will not immediately update the Student List in main UI (Future iteration will resolve)<br>
- Does not include CSS (yet) to customize UI<br>

### License & Copyright
[License Link](LICENSE). <br>

