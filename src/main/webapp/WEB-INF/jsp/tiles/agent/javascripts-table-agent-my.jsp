<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
	<script type="text/javascript">
		var agentId = ${sessionScope.agent.agentId};
	</script>
	<script type="text/javascript" src="assets/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript" src="assets/data-tables/DT_bootstrap.js"></script>
	<script type="text/javascript" src="assets/gritter/js/jquery.gritter.js"></script>
	<script type="text/javascript" src="assets/mt4/js/table-agent-my.js?ts=<%=new java.util.Date().getTime()%>"></script>
	<script type="text/javascript" src="assets/mt4/js/global-daterangepicker-settings.js"></script>
	<script type="text/javascript" src="http://www.bendewey.com/code/formatcurrency/jquery.formatCurrency-1.4.0.js"></script>
	