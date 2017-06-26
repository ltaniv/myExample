<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>登录</h1>
    <form action="/login.do" method="post">
        用户名：<input name="name" type="text" /><br />
        密码：<input name="password" type="password" /><br />
        验证码：<input name="captcha" type="text" style="width: 40px" /><img src="/captcha" width="60" height="20" style="vertical-align: middle" /><br />
        <button type="submit">登录</button>
    </form>
</body>
</html>