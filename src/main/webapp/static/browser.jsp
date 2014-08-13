<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.List"%>
<%@page import="com.domain.POJO.DigitalItemPOJO"%>
<%@page import="com.domain.POJO.VideoFilePOJO"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<!-- saved from url=(0078)http://html.crunchpress.net/video/viduze-green/search-result.html?#prettyPhoto -->
<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">

<title>VIDEO MAGAZINE</title>

<!--// Stylesheets //-->

<link href="http://html.crunchpress.net/video/viduze-green/css/style.css" rel="stylesheet" media="screen">

<link href="http://html.crunchpress.net/video/viduze-green/css/color.css" rel="stylesheet" media="screen">

<link href="http://html.crunchpress.net/video/viduze-green/css/rtl.css" rel="stylesheet" media="screen">

<link href="http://html.crunchpress.net/video/viduze-green/css/font-awesome.min.css" rel="stylesheet" media="screen">

<link href="http://html.crunchpress.net/video/viduze-green/css/bootstrap.css" rel="stylesheet" media="screen">

<link rel="stylesheet" href="../static/VIDEO MAGAZINE_files/flexslider.css" type="text/css" media="screen">

<link rel="stylesheet" type="text/css" href="../static/VIDEO MAGAZINE_files/skin.css">

<link rel="stylesheet" href="../static/VIDEO MAGAZINE_files/prettyph.css" type="text/css" media="screen" charset="utf-8">

<link rel="stylesheet" href="../static/VIDEO MAGAZINE_files/jquery.jscrollpane.css" type="text/css" media="screen">

<!--// Responsive //-->

<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;">

<link href="http://html.crunchpress.net/video/viduze-green/css/bootstrap-responsive.css" rel="stylesheet" media="screen">

<!--[if IE]>

<link rel="stylesheet" type="text/css" href="css/ie.css" />

<![endif]-->

<style type="text/css"></style></head>

<body>

<!--WRAPPER START-->

<div class="wrapper gallery_video" id="home">

	
	<!--HEADER START-->

	<header class="border-color">

    	<div class="container">

            <div class="logo">

            <img src="../static/VIDEO MAGAZINE_files/logo.png" alt="VIDEO MAGAZINE">

            </div>

            	<div class="navbar main-navigation">

                	

                  <nav class="navbar-inner">

                      <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">

                        <span class="icon-bar"></span>

                        <span class="icon-bar"></span>

                        <span class="icon-bar"></span>

                      </a>

                      <div class="nav-collapse">

                         <ul class="nav">

                            <li><a href="http://html.crunchpress.net/video/viduze-green/index.html">Film</a>
				<ul>

                                    <li><a href="http://html.crunchpress.net/video/viduze-green/search-result.html">Da vedere</a></li>

                                    <li><a href="http://html.crunchpress.net/video/viduze-green/404.html">GiÃ  visti</a></li>

                                </ul>

			    </li>
                            <li><a href="http://html.crunchpress.net/video/viduze-green/search-result.html?#">Serie TV</a>

                               <ul>

                                    <li><a href="http://html.crunchpress.net/video/viduze-green/search-result.html">Da vedere</a></li>

                                    <li><a href="http://html.crunchpress.net/video/viduze-green/404.html">GiÃ  visti</a></li>

                                </ul>

                            </li>


                        </ul>

                      </div>

                  </nav>

				</div>

        </div>

    </header>

    <!--HEADER END-->

	<jsp:useBean id="digitalItemService"  scope="application" class="com.domain.service.DigitalItemService"/>
   	<%
	   	List<DigitalItemPOJO> listaVideo = digitalItemService.getAll();
	%>
    <!--FEATURED VIDEOS SECTION START-->

    <section class="container">

    	<div class="row">

        	<div class="span8">

            	<header class="header-style">

                    <h2 class="h-style">Elenco Video</h2>

                </header>

                <div class="widget-bg">

                    <article>

                    <ul class="videos">

					<%
	   					for (DigitalItemPOJO item: listaVideo)
	   					{
	   				
	   				%>
                	<!--LIST ITEMS START-->

                    <li>

                    	<figure>

                        	<div class="thumb">

                        		<a href="<% out.print("../static/transcode.jsp?digitalItemId=" + item.getId()  ); %>">
                        			<img src="<% out.print("../rest/file?path=" + item.getVideoFile().get(0).getThumbnail()); %>" alt="">
                        		</a>

                            </div>

                            <figcaption>

                            	<h5><% out.print(item.getTitolo() ); %></h5>

                                <ul class="views">

									<% for (VideoFilePOJO videoFile: item.getVideoFile()) { %>
	                                	<li>
											<a href="<% out.print("../rest/stream?digitalItemId=" + item.getId() + "&videoId=" + videoFile.getId() ); %>">
												<% out.print( videoFile.getWidth() +"x"+ videoFile.getHeight()); %>
											</a>
										</li>
									<% }%>

                                </ul>

                            </figcaption>

                        </figure>

                    </li>

                    <!--LIST ITEMS END-->
					<%
	   					}
	   				%>

                </ul>

                    </article>

                </div>

            </div>

            <!--SIDEBAR START-->

            <aside class="span4">

            	<div class="side-bar">


                	<!--SEARCH WIDGET START-->

                	<div class="widget search-widget">

                    	<header class="header-style">

                            <h2 class="h-style">Search</h2>

                        </header>

                        <div class="widget-bg">

                           <form class="form-search">

                               <input type="text" class="input-medium search-query">

                               <button type="submit" class="hover-style">Search</button>

                           </form>

                       </div>

                    </div>

                    <!--SEARCH WIDGET END-->

                 
                </div>

            </aside>

            <!--SIDEBAR END-->

        </div>

    </section>

    <!--FEATURED VIDEOS SECTION END-->

    
              

</div>

<!--WRAPPER END-->

<!--// Javascript //-->

<script type="text/javascript" src="../static/VIDEO MAGAZINE_files/jquery-1.8.3.js"></script>

<script type="text/javascript" src="../static/VIDEO MAGAZINE_files/bootstrap.min.js"></script>

<script defer="" src="../static/VIDEO MAGAZINE_files/jquery.flexslider.js"></script>

<script defer="" src="../static/VIDEO MAGAZINE_files/jquery.jcarousel.min.js"></script>

<script defer="" src="../static/VIDEO MAGAZINE_files/jquery.jscrollpane.min.js"></script>

<script defer="" src="../static/VIDEO MAGAZINE_files/jquery.scrollTo-min.js"></script>

<script type="text/javascript" src="../static/VIDEO MAGAZINE_files/functions.js"></script>

<script type="text/javascript" src="../static/VIDEO MAGAZINE_files/prettyph.js"></script>





</body></html>
