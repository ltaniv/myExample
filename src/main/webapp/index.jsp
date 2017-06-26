<html>
<body>
<h3>Request Scope (key==values)</h3>
<%
    java.util.Enumeration<String> reqEnum = request.getAttributeNames();
    while (reqEnum.hasMoreElements()) {
        String s = reqEnum.nextElement();
        out.print(s);
        out.print("==" + request.getAttribute(s)+"<br />");
%>
<%
    }
%>
</body>
</html>
