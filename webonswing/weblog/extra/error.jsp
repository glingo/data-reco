<HTML>

<BODY>

<B>Status code:</B> <%= request.getAttribute("javax.servlet.error.status_code") %>
<BR><B>Message</B>: <%= request.getAttribute("javax.servlet.error.message") %>
<BR><B>Error type</B>: <%= request.getAttribute("javax.servlet.error.exception_type") %>
<BR><B>Request URI</B>: <%= request.getAttribute("javax.servlet.error.request_uri") %>
<HR><PRE>

</BODY>

</HTML>
