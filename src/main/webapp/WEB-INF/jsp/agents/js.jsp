<%@ page language="java" contentType="application/javascript; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
var agents = {};
<c:forEach items="${agents}" var="agent">
	agents[${agent.login}] = {
		"id": ${agent.agentId}, 
		"login": ${agent.login},
		"upline": '${agent.parentAgentId}'
	};
</c:forEach>