<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.List"%>
<%@page import="com.domain.POJO.DigitalItemPOJO"%>
<%@page import="com.domain.POJO.VideoFilePOJO"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<body>

<div>
	<jsp:useBean id="digitalItemService"  scope="application" class="com.domain.service.DigitalItemService"/>
   	<%
   		int id = new Integer(request.getParameter("digitalItemId"));
	   	DigitalItemPOJO item = digitalItemService.getFromId(id);
	%>
	<form action="../rest/video/transcode" method="get"> 
		<p>Selezionare una larghezza del nuovo video </p>
		<input type="hidden" name="digitalItemId" value="<%out.print(id);%>">
		<input type="text" name="width" value="<%out.print( item.getVideoFile().get(0).getWidth() );%>">
		<input type="submit" value="ok">
	</form>
</div>


</body></html>
