
# Foody Chatbot version 1.0
###### epl361.winter17.team6

 CONTENTS OF THIS FILE
---------------------

  ### 1.Introduction
  ### 2.Requirements
  ### 3.Configuration



 ## 1.Introduction
    This program was created by a UCY student team as a project that was assigned to them by 361 Cource.


 ## 2.Requirements
   In order for the Foody Chatbot to run you will need the following modules installed on your machine:
   
      
  #### http-server
   Download & Install the module from the following link:

    https://www.npmjs.com/package/http-server
    
   >Via npm
            
    npm install http-server -g
         
    
    
  #### SQLite
   Download & Install the module from the following link :
         
    https://sqlite.org/download.html
    
  
  Windows:
  
   > Download precompiled binaries from Windows section.
    
   > Download sqlite-shell-win32-*.zip and sqlite-dll-win32-*.zip zipped files.
  
    Create a folder C:\>sqlite and unzip above two zipped files in this folder, which will give you sqlite3.def, sqlite3.dll and sqlite3.exe files.
  
    Add C:\>sqlite in your PATH environment variable and finally go to the command prompt and issue sqlite3 command, which should display the following result.

  
  Linux:
   
  >Download sqlite-autoconf-3210000.tar.gz
  
    
    $tar xvfz sqlite-autoconf-3210000.tar.gz
    $cd sqlite-autoconf-3210000
    $./configure --prefix = /usr/local
    $make
    $make install
    
  Mac OS X:
  
  >Download sqlite-autoconf-3210000.tar.gz
  
    $tar xvfz sqlite-autoconf-3071502.tar.gz
    $cd sqlite-autoconf-3071502
    $./configure --prefix=/usr/local
    $make
    $make install

 ## 3.Configuration
    Extract the git repository on your machine.
   Run the following commands: 
   
    $  http-server --cors
          
   Proceed to the following url on the localhost: 
   
    http://127.0.0.1:8081/epl361.winter17.team6/ChatbotUI/Public


Copyright 2017  © University of Cyprus