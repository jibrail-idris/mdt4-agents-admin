<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<!-- 
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 2.3.1
Version: 1.1.1
Author: KeenThemes
Website: http://www.keenthemes.com/preview/?theme=metronic
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469
-->
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<meta charset="utf-8" />
	<title>MT4Agents Administration</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<tiles:insertAttribute name="css" />
	<tiles:insertAttribute name="custom-css" />
	<link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="fixed-top">
	<tiles:insertAttribute name="header" />
	<!-- BEGIN CONTAINER -->
	<div class="page-container row-fluid">
		<tiles:insertAttribute name="sidebar-menu" />
		<tiles:insertAttribute name="body" />
	</div>
	<!-- END CONTAINER -->
	<tiles:insertAttribute name="footer" />
	<tiles:insertAttribute name="javascripts" />
	<tiles:insertAttribute name="custom-javascripts" />
</body>
<!-- END BODY -->
</html>
