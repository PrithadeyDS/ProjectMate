\# ProjectMate



ProjectMate is a Java-based Student Project Team Finder that helps students discover projects, find teammates with relevant skills, apply to projects, and form project teams.



\## Features



\- Student Registration and Login

\- Create Project

\- Add Required Skills

\- Browse Projects

\- Search Projects by Skill

\- Apply to Projects

\- View Applications

\- Accept / Reject Applicants

\- Automatic Team Formation

\- View Team Members

\- Student Profile

\- Logout and Session Handling



\## Tech Stack



\- Java

\- Java Swing

\- JDBC

\- MySQL

\- MySQL Workbench

\- Eclipse IDE



\## Database Tables



\- users

\- skills

\- projects

\- project\_requirements

\- applications

\- team\_members



\## Application Flow



Login / Register  

↓  

Dashboard  

↓  

Create Project  

↓  

Add Required Skills  

↓  

Browse Projects  

↓  

Apply to Project  

↓  

Accept / Reject Application  

↓  

Team Formation  



\## JDBC Architecture



Java Swing GUI  

↓  

Java Application Logic  

↓  

JDBC  

↓  

MySQL Database



\## JDBC Operations Used



\- INSERT

\- SELECT

\- UPDATE

\- JOIN

\- PreparedStatement

\- ResultSet



\## Project Objective



Students often have project ideas but struggle to find teammates with the right technical skills. Team formation usually happens through informal WhatsApp groups or personal contacts.



ProjectMate provides a structured platform where students can publish project ideas, specify required skills, browse available projects, apply to teams, and manage project collaboration.



\## Software Requirements



\- JDK 17+

\- Eclipse IDE

\- MySQL Server

\- MySQL Workbench

\- MySQL Connector/J



\## Database Connection



The MySQL password is stored using the environment variable:



`PROJECTMATE\_DB\_PASSWORD`



This prevents the database password from being exposed in the source code.



\## Future Scope



\- Skill-based project recommendations

\- Password hashing

\- Notifications

\- Team chat

\- Project task management

\- Student portfolio integration

\- Admin moderation

