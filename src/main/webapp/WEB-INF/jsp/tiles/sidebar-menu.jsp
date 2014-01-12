<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib  prefix="spring" uri="http://www.springframework.org/tags" %>

    	<c:set var="page" scope="request" value="${requestScope['javax.servlet.forward.servlet_path']}" />
		<!-- BEGIN SIDEBAR -->
		<div class="page-sidebar nav-collapse collapse">
			<!-- BEGIN SIDEBAR MENU -->        	
			<ul>
				<li>
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
					<div class="sidebar-toggler hidden-phone"></div>
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
				</li>
<!-- 				<li class="start active "> -->
<!-- 					<a href="index.html"> -->
<!-- 					<i class="icon-home"></i>  -->
<!-- 					<span class="title">Dashboard</span> -->
<!-- 					<span class="selected"></span> -->
<!-- 					</a> -->
<!-- 				</li> -->
				<sec:authorize access="hasRole('admin')">
					<li class='has-sub <c:if test="${fn:startsWith(page, '/user')}">active</c:if>'>
						<a href="javascript:;">
						<i class="icon-table"></i> 
						<span class="title"><spring:message code="label.users"/></span>
						<span class="arrow "></span>
						</a>
						<ul class="sub">
							<li class='<c:if test="${page == '/user/all'}">active</c:if>'><a href="/user/all"><spring:message code="label.viewall"/></a></li>
						</ul>
					</li>
					<li class='has-sub <c:if test="${fn:startsWith(page, '/agent')}">active</c:if>'>
						<a href="javascript:;">
						<i class="icon-table"></i> 
						<span class="title"><spring:message code="label.agents"/></span>
						<span class="arrow "></span>
						</a>
						<ul class="sub">
							<li class='<c:if test="${page == '/agent/all'}">active</c:if>'><a href="/agent/all"><spring:message code="label.viewall"/></a></li>
						</ul>
					</li>
				</sec:authorize>
				<sec:authorize access="hasRole('agent')">
					<li class='has-sub <c:if test="${fn:startsWith(page, '/agent')}">active</c:if>'>
						<a href="javascript:;">
						<i class="icon-table"></i> 
						<span class="title"><spring:message code="label.trades"/></span>
						<span class="arrow "></span>
						</a>
						<ul class="sub">
							<li class='<c:if test="${page == '/agent/my'}">active</c:if>'><a href="/agent/my"><spring:message code="label.viewmytrades"/></a></li>
						</ul>
					</li>
				</sec:authorize>
				<li class='has-sub <c:if test="${fn:startsWith(page, '/profile')}">active</c:if>'>
					<a href="javascript:;">
					<i class="icon-table"></i> 
					<span class="title"><spring:message code="label.profile"/></span>
					<span class="arrow "></span>
					</a>
					<ul class="sub">
						<li class='<c:if test="${page == '/profile'}">active</c:if>'><a href="/profile"><spring:message code="label.viewmyprofile"/></a></li>
					</ul>
				</li>
			</ul>
			<!-- END SIDEBAR MENU -->
		</div>
		<!-- END SIDEBAR -->