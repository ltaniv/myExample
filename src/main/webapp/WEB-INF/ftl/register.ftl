<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
${dateTime?if_exists}

<form action="/register.do" method="post">
    <fieldset>
        <legend>注册 - RegisterChecks</legend>
        用户名：<input name="name" type="text"/><br/>
        密码：<input name="password" type="password"/><br/>
        邮箱：<input name="email" type="text"/><br/>
        年龄：<input name="age" type="number"/><br/>
        生日：<input name="birthday" type="date"/><br/>
        <button type="submit">注册</button>
    </fieldset>
</form>
<hr/>
<form action="/login.do" method="post">
    <fieldset>
        <legend>登录 - LoginChecks</legend>
        用户名：<input name="name" type="text" /><br />
        密码：<input name="password" type="password" /><br />
        验证码：<input name="captcha" type="text" style="width: 40px" /><img src="/captcha" width="60" height="20" style="vertical-align: middle" /><br />
        <button type="submit">登录</button>
    </fieldset>
</form>

</body>
</html>