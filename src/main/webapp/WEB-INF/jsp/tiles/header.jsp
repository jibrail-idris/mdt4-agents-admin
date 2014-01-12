<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<!-- BEGIN HEADER -->
	<div class="header navbar navbar-inverse navbar-fixed-top">
		<!-- BEGIN TOP NAVIGATION BAR -->
		<div class="navbar-inner">
			<div class="container-fluid">
				<!-- BEGIN LOGO -->
				<a class="brand" href="/agent/all">
				<img src="assets/img/iwc.jpg" style="height:30px;" alt="logo" />
				</a>
				<!-- END LOGO -->
				<!-- BEGIN RESPONSIVE MENU TOGGLER -->
				<a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse">
				<img src="assets/img/menu-toggler.png" alt="" />
				</a>          
				<!-- END RESPONSIVE MENU TOGGLER -->				
				<!-- BEGIN TOP NAVIGATION MENU -->					
				<ul class="nav pull-right">
					<li class="dropdown user">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
<!-- 						<img alt="" src="assets/img/avatar.png" width="29" height="29"/> -->
						<span class="username">Language Settings</span>
						<i class="icon-angle-down"></i>
						</a>
						<ul class="dropdown-menu">
							<li><a href="?lang=en"><i class="icon-user"></i> English</a></li>
							<li><a href="?lang=zh_CN"><i class="icon-user"></i> Chinese Simplified</a></li>
							<li><a href="?lang=zh_TW"><i class="icon-user"></i> Chinese Traditional</a></li>
						</ul>
					</li>
					<!-- BEGIN USER LOGIN DROPDOWN -->
					<li class="dropdown user">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
<!-- 						<img alt="" src="assets/img/avatar.png" width="29" height="29"/> -->
						<span class="username"><sec:authentication property="principal.username" /></span>
						<i class="icon-angle-down"></i>
						</a>
						<ul class="dropdown-menu">
							<li><a href="/profile"><i class="icon-user"></i> My Profile</a></li>
<!-- 							<li><a href="calendar.html"><i class="icon-calendar"></i> My Calendar</a></li> -->
<!-- 							<li><a href="#"><i class="icon-tasks"></i> My Tasks</a></li> -->
<!-- 							<li class="divider"></li> -->
							<li><a href="<c:url value="/logout" />"><i class="icon-key"></i> Log Out</a></li>
						</ul>
					</li>
					<!-- END USER LOGIN DROPDOWN -->
				</ul>
				<!-- END TOP NAVIGATION MENU -->	
			</div>
		</div>
		<!-- END TOP NAVIGATION BAR -->
	</div>
	<!-- END HEADER -->