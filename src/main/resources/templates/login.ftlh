<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Books</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<#include 'fragments/header.ftlh'>
<div class="container">

    <form action="/login" method="post">
        <input type="text" name="username" placeholder="username">
        <input type="password" name="password" placeholder="password">
        <input type="submit" name="login" value="login">
    </form>
</div>
<#include 'fragments/footer.ftlh'>
</body>
</html>