<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.List"%>
<%@page import="com.domain.POJO.DigitalItemPOJO"%>
<%@page import="com.domain.POJO.VideoFilePOJO"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Lista dei Video</title>
	</head>

	<body>
	
	   <jsp:useBean id="digitalItemService"  scope="application" class="com.domain.service.DigitalItemService"/>
	   <%
	   		List<DigitalItemPOJO> listaVideo = digitalItemService.getAll();
	   %>
	   
	   <table>
	   		<thead>
	   			<tr>
	   				<td>Id</td>
	   				<td>Nome</td>
	   				<td>Link</td>
	   			</tr>
	   		</thead>
	   	
	   		<%
	   			for (DigitalItemPOJO item: listaVideo)
	   			{
	   				
	   		%>
	   		<tr>
   				<td><%out.println ( item.getId()); %></td>
   				<td><%out.println ( item.getTitolo()); %></td>
   				<td>
   					<%
   						List<VideoFilePOJO> listaFile = item.getVideoFile();
   						if (listaFile.size() > 0) 
   							out.println ( listaFile.get(0).getPath()); 
   					%>
   				</td>
   			</tr>
   			<%
   				}
   			%>
	   
	   </table>
	</body>
</html>
