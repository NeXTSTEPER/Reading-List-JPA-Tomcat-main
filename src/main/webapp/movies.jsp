<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*,movies.Movies, books.Books"%>

<!DOCTYPE html>
<html>
    <head>
        <title>JPA Movie List</title>
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
            form {
                margin-bottom: 20px;
                display: flex;
                flex-direction: column;
                align-items: center;
            }
            input[type="text"] {
                margin: 5px 0;
                padding: 10px;
                width: 19%;
                border-radius: 5px;
                border: 1px solid #ccc;
                color: #000;
                background-color: #fff;
            }
            input[type="submit"] {
                margin: 5px 0;
                padding: 10px;
                width: 20%;
                border-radius: 5px;
                border: 1px solid #ccc;
                background-color: #ff4b3e;
                color: #000;
                cursor: pointer;
                transition: background-color 0.5s;
            }
            input[type="submit"]:hover {
                background-color: #fff;
            }
            .movie {
                border-bottom: 1px solid #fff;
                padding-bottom: 10px;
                margin-bottom: 20px;
                font-size: 2.8em;
                text-align: center;
            }
            p#error {
                color: #ff4b3e;
            }
            div {
                padding: 0 20px;
            }
            ol, li {
                margin: 0;
                padding: 0;
                list-style-position: inside;
            }
            a.back-to-index {
                display: inline-block;
                margin-bottom: 20px;
                padding: 10px;
                color: white;
                text-decoration: none;
                border: 1px solid #EC5A49;
                border-radius: 5px;
                transition: background-color 0.3s, color 0.3s;
            }
            a.back-to-index:hover {
                background-color: #EC5A49;
                color: #000000;
            }        </style>
    </head>
     <body>
        <div class="container">
            <br>
            <a href="index.jsp" class="back-to-index">Back</a>
            <h1>JPA Movie List</h1>
            <form method="POST" action="MoviesServlet" onsubmit="return validateForm()">
                Movie Title: <input type="text" id="movieTitle" name="movieTitle" />
                Movie Director: <input type="text" id="movieDirector" name="movieDirector" />
                Based on Book:
               <select id="bookId" name="bookId">
                   <option value="-1">None</option>
                    <% @SuppressWarnings("unchecked")
                       List<Books> books = (List<Books>)request.getAttribute("books");
                       for (Books book : books) { %>
                        <option value="<%= book.getId() %>"><%= book.getBookTitle() %></option>
                    <% } %>
                </select>
                <input type="submit" value="Add" />
                <p id="error" style="color:red; display:none">Please enter a movie title and director.</p>
            </form>
            <hr>
            <ol> 
                <%
                 @SuppressWarnings("unchecked")
                 List<Movies> movies = (List<Movies>)request.getAttribute("movies");
                 for (Movies movie : movies) {
                %>
                    <div class="movie">
                        <li> <%= movie.getMovieTitle() %> 
                        <% if (movie.getBook() != null) { %>
                            (Based on: <%= movie.getBook().getBookTitle() %>)
                        <% } else { %>
                            (Not based on a book)
                        <% } %>
                        </li>
                        <form method="POST" action="MoviesServlet">
                            <input type="hidden" name="id" value="<%=movie.getId()%>" />
                            <input type="text" name="movieTitle" value="<%=movie.getMovieTitle()%>" />
                            <input type="text" name="movieDirector" value="<%=movie.getMovieDirector()%>" />
                            <input type="hidden" name="operation" value="update" />
                            <input type="submit" value="Update" />
                        </form>
                        <form method="POST" action="MoviesServlet">
                            <input type="hidden" name="id" value="<%=movie.getId()%>" />
                            <input type="hidden" name="operation" value="delete" />
                            <input type="submit" value="Delete" />
                        </form>
                    </div>
                <% } %>
            </ol>
            <hr>
        </div>
        <script>
            function validateForm() {
                var movieTitle = document.getElementById('movieTitle').value;
                var movieDirector = document.getElementById('movieDirector').value;
                if (movieTitle == "" || movieDirector == "") {
                    document.getElementById('error').style.display = 'block';
                    return false;
                } else {
                    return true;
                }
            }
        </script>
    </body>
</html>