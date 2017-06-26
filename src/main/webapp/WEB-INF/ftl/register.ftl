<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
${dateTime?if_exists}
    <form action="/register.do" method="post">
        用户名：<input name="name" type="text" /><br />
        密码：<input name="password" type="password" /><br />
        邮箱：<input name="email" type="text" /><br />
        年龄：<input name="age" type="number" /><br />
        生日：<input name="birthday" type="date" /><br />
        <button type="submit">注册</button>
    </form>
</body>
</html>