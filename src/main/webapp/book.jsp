<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*,books.Books"%>

<!DOCTYPE html>
<html>
    <head>
        <title>JPA Reading List </title>
        <style>
            @import url('https://fonts.googleapis.com/css2?family=Indie+Flower&display=swap');

            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f1f1f1;
            }
            .container {
                width: 80%;
                margin: 0 auto;
                background-color: #fff;
                padding: 20px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            }
            form {
                margin-bottom: 20px;
            }
            .book {
                border-bottom: 1px solid #eee;
                padding-bottom: 10px;
                margin-bottom: 20px;
            }
            h1 {
                font-family: 'Indie Flower', cursive;
                text-align: center;
            }
        </style>
    </head>

  <body>
        <div class="container">
            <h1>JPA Reading List</h1>
            <form method="POST" action="BookServlet" onsubmit="return validateForm()">
                Book Title: <input type="text" id="bookTitle" name="bookTitle" />
                Book Author: <input type="text" id="bookAuthor" name="bookAuthor" />
                <input type="submit" value="Add" />
                <p id="error" style="color:red; display:none">Please enter a book title and author.</p>
            </form>
            <hr>
            <ol> 
                <%
                 @SuppressWarnings("unchecked")
                                 List<Books> books = (List<Books>)request.getAttribute("books");
                                 for (Books book : books) {
                 %>
                    <div class="book">
                        <li> <%= book %> </li>

                        <form method="POST" action="BookServlet">
                            <input type="hidden" name="id" value="<%=book.getId()%>" />
                            <input type="text" name="bookTitle" value="<%=book.getBookTitle()%>" />
                            <input type="text" name="bookAuthor" value="<%=book.getBookAuthor()%>" />
                            <input type="hidden" name="operation" value="update" />
                            <input type="submit" value="Update" />
                        </form>

                        <form method="POST" action="BookServlet">
                            <input type="hidden" name="id" value="<%=book.getId()%>" />
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
                var bookTitle = document.getElementById('bookTitle').value;
                var bookAuthor = document.getElementById('bookAuthor').value;
                if (bookTitle == "" || bookAuthor == "") {
                    document.getElementById('error').style.display = 'block';
                    return false;
                } else {
                    return true;
                }
            }
        </script>
    </body>
</html>