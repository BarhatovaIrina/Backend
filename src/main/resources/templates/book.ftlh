<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Books</title>
    <link rel="stylesheet" href="css/book.css"  type="text/css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<#include 'fragments/header.ftlh'>
<div class="container">
    <h2>Books</h2>
    <div class="book_list">
        <div class="book">
            <span>Id</span>
            <span>Name</span>
            <span>Genre</span>
        </div>

    <#list books as book>
        <div class="book">
            <span>${book.id}</span>
            <span>${book.name}</span>
            <span>${book.genre}</span>
        </div>
    </#list>

    </div>

</div>
<#include 'fragments/footer.ftlh'>
</body>
</html>