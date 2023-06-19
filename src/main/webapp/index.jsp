<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome Page</title>
          <style>
            body {
                font-family: 'Helvetica', serif;
                margin: 0;
                padding: 0;
                background-color: #000;
                color: #fff;
            }

            h1 {
                font-size: 4.5em;
                text-align: center;
                padding: 20px 0;
                border-bottom: 1px solid #fff;
            }

            p {
                text-align: center;
                font-size: 2.8em;
                padding: 15px 0;
            }

            a {
                color: #ff4b3e;
                text-decoration: none;
                transition: color 0.5s;
            }

            a:hover {
                color: #fff;
            }
        </style>
    </head>
    <body>
        <h1>Reading and Watch List</h1> 
        <p><a href="BookServlet">Reading List</a></p>
        <p><a href="MoviesServlet">Movie Watchlist</a></p>        
            
      
    </body>
</html>
