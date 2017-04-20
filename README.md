# JavaWebServer - What is it?
Simply put it's a low level Java WebServer using no dependencies. It utilizes NIO channels in order to receive requests, process and respond to the client (Webbrowser).

# What does it demonstrate?
Non-Blocking I/O (NIO): This project demonstrates my understanding of NIO and ability to utilize it. By using NIO it allows me to create a server to handle multiple connections without the need to make new threads; this flexibility optimizes the efficiency of the server which ultimately allows for more connections. 

Test Driven Development (TDD): This project was developed using TDD to create automated tests to minimize the number of bugs found in the code. By creating the test and then developing the class around the tests it ensures the code's modularity and allows flexibility for other developers to test and debug.  

SOLID: By structuring the code's design to follow the SOLID principle it allows for easy testing and decouples classes from each other to allow for re-usable code. For example if I needed to create a different kind of server I can take out the nioserver package and build a new application based on that due to it's flexibility and modularity created by following SOLID. 

HTTP: By making a low level Java WebServer that handles the protocol directly using channels it demonstrates a thorough understanding of how HTTP actually works. By reading the data and responding appropriately to it I gained insight of how an HTTP Request is structured and how the server responds. 

# How to
Take the jar and run it on the command line (haven't made a gui for it). Initially it creates a folder called "htdocs" located in the same directory the jar is in, then the command line should exit. After this develop or insert your html/css/js files into the folder and then restart the server. Now connect to it using your web browser. 
