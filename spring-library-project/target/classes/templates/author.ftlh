<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Books</title>
    <link rel="stylesheet" href="author.css">
</head>
<body>
<#include 'fragments/header.ftlh'>
<div class="container">
    <h2>Authors</h2>
    <div class="author_list">
        <div class="author">
            <span>Id</span>
            <span>Name</span>
            <span>Surname</span>
        </div>

        <#list authors as author>
            <div class="author">
                <span>${author.id}</span>
                <span>${author.name}</span>
                <span>${author.surname}</span>
            </div>
        </#list>

    </div>

</div>
<#include 'fragments/footer.ftlh'>
</body>
</html>