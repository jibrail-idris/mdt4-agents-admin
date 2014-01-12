<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- BEGIN PAGE -->
<div class="page-content">
	
	<!-- MT4 NEW AGENT FORM MODAL -->
	<div id="portlet-manage-clients-form" class="modal hide" style="width: 850px;">
		<div class="modal-body">
			<div class="portlet box blue">
				<div class="portlet-title">
					<h4 class="block">
						<i class="icon-reorder"></i>Manage Agent's Clients
					</h4>
				</div>
				<div class="portlet-body form">
					<div class="alert alert-error hide">
						<button class="close" data-dismiss="alert"></button>
					</div>
					<div class="control-group">
						<div class="controls">
							<table id="managed-clients" class="table table-striped table-bordered table-advance table-hover">
								<thead>
									<tr>
										<th>CLIENT</th>
										<th style="width:140px;">&nbsp;</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- MT4 UPDATE AGENT FORM MODAL -->
	<div id="portlet-update-agent-form" class="modal hide">
		<div class="modal-body">
			<div class="portlet box blue">
				<div class="portlet-title">
					<h4 class="block">
						<i class="icon-reorder"></i>Update Agent
					</h4>
				</div>
				<div class="portlet-body form">
					<div class="form-horizontal">
						<div class="alert alert-error hide">
							<button class="close" data-dismiss="alert"></button>
						</div>
						<div class="control-group">
							<label class="control-label">Commission</label>
							<div class="controls">
								<div class="input-prepend">
									<span class="add-on">$</span><input class="m-wrap " name="commission" type="text" />
								</div>
							</div>
						</div>
<!-- 						<div class="control-group"> -->
<!-- 							<label class="control-label">Upline</label> -->
<!-- 							<div class="controls"> -->
<!-- 								<div class="input-prepend input-append"> -->
<!-- 									<select name="upline" class="medium m-wrap"> -->
<!-- 										<option value="">Loading...</option> -->
<!-- 									</select> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
						<div class="form-actions">
							<button type="button" class="btn blue save">Save</button>
							<button type="button" data-dismiss="modal" class="btn cancel">Cancel</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- MT4 RESET PASSWORD FORM MODAL -->
	<div id="portlet-reset-password-form" class="modal hide">
		<div class="modal-body">
			<div class="portlet box blue">
				<div class="portlet-title">
					<h4 class="block">
						<i class="icon-reorder"></i>Reset Password
					</h4>
				</div>
				<div class="portlet-body form">
					<div class="form-horizontal">
						<div class="alert alert-error hide">
							<button class="close" data-dismiss="alert"></button>
						</div>
						<div class="control-group">
							<label class="control-label">Password</label>
							<div class="controls">
								<div>
									<input class="m-wrap" name="password" type="text" />
									<button class="generate-random-password btn green">Generate Password</button>
								</div>
							</div>
						</div>
						<div class="form-actions">
							<button type="button" class="btn blue reset-password">Reset Password</button>
							<button type="button" data-dismiss="modal" class="btn cancel">Cancel</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 	MT4 AGENT UNASSIGNED MODAL -->
	<div id="mt-agent-unassign" class="modal hide" tabindex="-1" style="width: auto !important;"
		role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true"></button>
			<h3 id="myModalLabel2">MT4 Agent Unassign</h3>
		</div>
		<div class="modal-body">
			<div class="alert alert-error hide">
				<button class="close" data-dismiss="alert"></button>
			</div>
			<p>Are you sure you want to unassign this agent?</p>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn blue save">Unassign</button>
			<button data-dismiss="modal" type="button" class="btn cancel">Cancel</button>
		</div>
	</div>
	<!-- MT4 UPDATE AGENT ERROR MODAL -->
	<div id="mt-agent-update-error" class="modal hide fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true"></button>
			<h3 id="myModalLabel2">MT4 Agent Update Error</h3>
		</div>
		<div class="modal-body">
			<p></p>
		</div>
		<div class="modal-footer">
			<button data-dismiss="modal" class="btn green">OK</button>
		</div>
	</div>
	<!-- MT4 AGENT ASSIGNMENT MODAL -->
	<div id="mt-agent-assignment" class="modal hide fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true"></button>
			<h3 id="myModalLabel2">User Assignment</h3>
		</div>
		<div class="modal-body">
			<p>Assigning user as agent.</p>
		</div>
	</div>
	<!-- BEGIN PAGE CONTAINER-->
	<div class="container-fluid">
		<!-- BEGIN PAGE HEADER-->
		<div class="row-fluid">
			<div class="span12">				
				<!-- BEGIN PAGE TITLE & BREADCRUMB-->
				<h3 class="page-title">
					<spring:message code="label.agents"/>
				</h3>
				<ul class="breadcrumb">
					<li><i class="icon-home"></i> <a href="index.html">Home</a> <i
						class="icon-angle-right"></i></li>
					<li><a href="#"><spring:message code="label.agents"/></a> <i class="icon-angle-right"></i>
					</li>
					<li><a href="#">All</a></li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>
		<!-- END PAGE HEADER-->
		<!-- BEGIN PAGE CONTENT-->
		
		<div id="table-agents-config" class="modal hide" style="width:400px;">
			<div class="modal-header">
				<button data-dismiss="modal" class="close" type="button"></button>
				<h3>Show / Hide Table Columns</h3>
			</div>
			<div class="modal-body">
				<table style="margin: auto;width:200px;">
					<tbody>
						<tr><td><input type="checkbox" class="column-toggle" checked="checked" />&nbsp;<spring:message code="label.login"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" checked="checked" />&nbsp;<spring:message code="label.upline"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" checked="checked" />&nbsp;<spring:message code="label.group"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" />&nbsp;<spring:message code="label.enable"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" />&nbsp;<spring:message code="label.enablechangepass"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" />&nbsp;<spring:message code="label.enablereadonly"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" />&nbsp;<spring:message code="label.passwordphone"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" checked="checked" />&nbsp;<spring:message code="label.name"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" checked="checked" />&nbsp;<spring:message code="label.country"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" checked="checked" />&nbsp;<spring:message code="label.city"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" checked="checked" />&nbsp;<spring:message code="label.state"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" />&nbsp;<spring:message code="label.zipcode"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" />&nbsp;<spring:message code="label.address"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" checked="checked" />&nbsp;<spring:message code="label.phone"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" checked="checked" />&nbsp;<spring:message code="label.email"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" />&nbsp;<spring:message code="label.comment"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" />&nbsp;<spring:message code="label.regdate"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" />&nbsp;<spring:message code="label.lastdate"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" />&nbsp;<spring:message code="label.leverage"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" checked="checked" />&nbsp;<spring:message code="label.clients"/></td></tr>
						<tr><td><input type="checkbox" class="column-toggle" checked="checked" />&nbsp;<spring:message code="label.comm"/></td></tr>
					</tbody>
				</table>
			</div>
		</div>
		
		<div id="clients-table-config" class="modal hide" style="width:400px;">
			<div class="modal-header">
				<button data-dismiss="modal" class="close" type="button"></button>
				<h3>Show / Hide Table Columns</h3>
			</div>
			<div class="modal-body">
				<table style="margin: auto;width:200px;">
					<tbody>
						<tr>
							<td><input type="checkbox" class="column-toggle" checked="checked" />&nbsp;<spring:message code="label.login"/></td>
						</tr>
						<tr>
							<td><input type="checkbox" class="column-toggle" checked="checked" />&nbsp;<spring:message code="label.name"/></td>
						</tr>
						<tr>
							<td><input type="checkbox" class="column-toggle"  />&nbsp;<spring:message code="label.country"/></td>
						</tr>
						<tr>
							<td><input type="checkbox" class="column-toggle" />&nbsp;<spring:message code="label.city"/></td>
						</tr>
						<tr>
							<td><input type="checkbox" class="column-toggle" />&nbsp;<spring:message code="label.state"/></td>
						</tr>
						<tr>
							<td><input type="checkbox" class="column-toggle" />&nbsp;<spring:message code="label.zipcode"/></td>
						</tr>
						<tr>
							<td><input type="checkbox" class="column-toggle" />&nbsp;<spring:message code="label.address"/></td>
						</tr>
						<tr>
							<td><input type="checkbox" class="column-toggle" />&nbsp;<spring:message code="label.phone"/></td>
						</tr>
						<tr>
							<td><input type="checkbox" class="column-toggle" checked="checked" />&nbsp;<spring:message code="label.email"/></td>
						</tr>
						<tr>
							<td><input type="checkbox" class="column-toggle" />&nbsp;<spring:message code="label.comment"/></td>
						</tr>
						<tr>
							<td><input type="checkbox" class="column-toggle" checked="checked" />&nbsp;<spring:message code="label.registrationdate"/></td>
						</tr>
						<tr>
							<td><input type="checkbox" class="column-toggle" checked="checked" />&nbsp;<spring:message code="label.totalvolume"/></td>
						</tr>
						<tr>
							<td><input type="checkbox" class="column-toggle" checked="checked" />&nbsp;<spring:message code="label.totalcommission"/></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		
		<div id="clients-open-trades-table-config" class="modal hide" style="width:400px;">
			<div class="modal-header">
				<button data-dismiss="modal" class="close" type="button"></button>
				<h3>Show / Hide Table Columns</h3>
			</div>
			<div class="modal-body">
				<table style="margin: auto;width:200px;">
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
		
		<div id="clients-close-trades-table-config" class="modal hide" style="width:400px;">
			<div class="modal-header">
				<button data-dismiss="modal" class="close" type="button"></button>
				<h3>Show / Hide Table Columns</h3>
			</div>
			<div class="modal-body">
				<table style="margin: auto;width:200px;">
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
		
		<div id="clients-balance-table-config" class="modal hide" style="width:400px;">
			<div class="modal-header">
				<button data-dismiss="modal" class="close" type="button"></button>
				<h3>Show / Hide Table Columns</h3>
			</div>
			<div class="modal-body">
				<table style="margin: auto;width:200px;">
					<tbody>
					</tbody>
				</table>
			</div>
		</div>

		<div class="agent-clients-dropdown btn-toolbar hide">
			<div class="btn-group">
				<a class="btn green mini" href="#" data-toggle="dropdown"> <i
					class="icon-user"></i> Agent <i class="icon-angle-down"></i>
				</a>
				<ul class="dropdown-menu">
					<li><a href="javascript:;" class="manage-clients-btn"><i class="icon-user"></i> Manage
							Clients</a></li>
					<li><a href="javascript:;" class="view-clients"><i
							class="icon-list"></i> View Clients</a></li>
					<li><a href="javascript:;" class="edit-agent-btn"><i class="icon-edit"></i>
							Edit</a></li>
					<li><a href="javascript:;" class="delete-agent-btn"><i
							class="icon-trash"></i> Unassign</a></li>
					<li><a href="javascript:;" class="reset-password-btn"><i
							class="icon-trash"></i> Reset Password</a></li>
				</ul>
			</div>
		</div>

		<div class="row-fluid">
			<div id="tables-container" class="span12">
				<!-- BEGIN EXAMPLE TABLE PORTLET-->
				<div class="portlet box blue">
					<div class="portlet-title">
						<h4>
							<i class="icon-reorder"></i><spring:message code="label.agents"/>
						</h4>
					</div>
					<div class="portlet-body">
						<div class="clearfix">
							<div class="pull-right">
								<a href="#table-agents-config" data-toggle="modal" class="btn green icn-only"><i class="icon-user icon-cog"></i></a>
							</div>
						</div>
						<table class="table table-striped table-hover table-bordered hide"
							id="table-agents">
							<thead>
								<tr>
									<th><spring:message code="label.login"/></th>
									<th><spring:message code="label.upline"/></th>
									<th><spring:message code="label.group"/></th>
									<th><spring:message code="label.enable"/></th>
									<th><spring:message code="label.enablechangepass"/></th>
									<th><spring:message code="label.enablereadonly"/></th>
									<th><spring:message code="label.passwordphone"/></th>
									<th><spring:message code="label.name"/></th>
									<th><spring:message code="label.country"/></th>
									<th><spring:message code="label.city"/></th>
									<th><spring:message code="label.state"/></th>
									<th><spring:message code="label.zipcode"/></th>
									<th><spring:message code="label.address"/></th>
									<th><spring:message code="label.phone"/></th>
									<th><spring:message code="label.email"/></th>
									<th><spring:message code="label.comment"/></th>
									<th><spring:message code="label.regdate"/></th>
									<th><spring:message code="label.lastdate"/></th>
									<th><spring:message code="label.leverage"/></th>
									<th><spring:message code="label.clients"/></th>
									<th><spring:message code="label.comm"/></th>
									<th>&nbsp;</th>
								</tr>
							</thead>
							<tbody>
<%-- 								<c:forEach items="${agents}" var="agent"> --%>
<%-- 									<c:set var="clientsCount" value="${agent.clients}" /> --%>
<!-- 									<tr> -->
<%-- 										<td>${agent.login}</td> --%>
<%-- 										<td>${agent.parentAgentLabel}</td> --%>
<%-- 										<td>${agent.mt4User.group}</td> --%>
<%-- 										<td>${agent.mt4User.enable}</td> --%>
<%-- 										<td>${agent.mt4User.enableChangePass}</td> --%>
<%-- 										<td>${agent.mt4User.enableReadOnly}</td> --%>
<%-- 										<td>${agent.mt4User.passwordPhone}</td> --%>
<%-- 										<td>${agent.mt4User.name}</td> --%>
<%-- 										<td>${agent.mt4User.country}</td> --%>
<%-- 										<td>${agent.mt4User.city}</td> --%>
<%-- 										<td>${agent.mt4User.state}</td> --%>
<%-- 										<td>${agent.mt4User.zipcode}</td> --%>
<%-- 										<td>${agent.mt4User.address}</td> --%>
<%-- 										<td>${agent.mt4User.phone}</td> --%>
<%-- 										<td>${agent.mt4User.email}</td> --%>
<%-- 										<td>${agent.mt4User.comment}</td> --%>
<%-- 										<td>${agent.mt4User.regDate}</td> --%>
<%-- 										<td>${agent.mt4User.lastDate}</td> --%>
<%-- 										<td>${agent.mt4User.leverage}</td> --%>
<%-- 										<td>${fn:length(clientsCount)}</td> --%>
<%-- 										<td>${agent.commission}</td> --%>
<!-- 										<td> -->
<%-- 											<c:set var="clients" value="" /> --%>
<%-- 											<c:forEach var="entry" items="${agent.clientsByLogin}"> --%>
<%-- 												<c:set var="clients" value="${entry.key},${clients}" /> --%>
<%-- 											</c:forEach> --%>
<!-- 											<div class="btn-toolbar"> -->
<!-- 												<div class="btn-group"> -->
<!-- 													<a class="btn green mini" href="#" data-toggle="dropdown"> -->
<!-- 													<i class="icon-user"></i> Agent -->
<!-- 													<i class="icon-angle-down"></i> -->
<!-- 													</a> -->
<!-- 													<ul class="dropdown-menu"> -->
<%-- 														<li><a href="javascript:;" class="manage-clients-btn" data-agentid="${agent.agentId}" data-login="${agent.login}" data-clients="${clients}"><i class="icon-user"></i> Manage Clients</a></li>														 --%>
<%-- 														<li><a href="javascript:;" data-agentid="${agent.agentId}" data-name="${agent.label}" class="view-clients"><i class="icon-list"></i> View Clients</a></li> --%>
<%-- 														<li><a href="javascript:;" class="edit-agent-btn" data-agentid="${agent.agentId}" data-login="${agent.login}" data-commission="${agent.commission}"><i class="icon-edit"></i> Edit</a></li> --%>
<%-- 														<li><a href="javascript:;" class="delete-agent-btn" data-agentid="${agent.agentId}" data-login="${agent.login}"><i class="icon-trash"></i> Unassign</a></li> --%>
<%-- 														<li><a href="javascript:;" class="reset-password-btn" data-agentid="${agent.agentId}" data-login="${agent.login}"><i class="icon-trash"></i> Reset Password</a></li> --%>
<!-- 													</ul> -->
<!-- 												</div> -->
<!-- 											</div> -->
<!-- 										</td> -->
<!-- 									</tr> -->
<%-- 								</c:forEach> --%>
							</tbody>
						</table>
					</div>
				</div>
				<div id="table-view-clients" class="portlet box green hide">
					<div class="portlet-title">
						<h4>
							<i class="icon-reorder"></i><spring:message code="label.clients"/>
						</h4>
					</div>
					<div class="portlet-body">
						<div class="grand-figures clearfix pull-left">
							<div class="clearfix pull-left">
								<div class="pull-right"><spring:message code="label.grandtotalvol"/> <span id="total-vol"></span></div>
							</div>
							<div class="clearfix pull-left">
								<div class="pull-right"><spring:message code="label.grandtotalcomm"/> <span id="total-comm"></span></div>
							</div>
						</div>
						<div class="clearfix clients-date-range-container">							
							<div class="btn-group pull-right">
								<div id="clients-date-range" class="btn pull-right datepickerrange">
									<i class="icon-calendar"></i> &nbsp;<span></span> <b
										class="caret"></b>
								</div>
							</div>
							<div class="pull-right">
								<a href="#clients-table-config" data-toggle="modal" class="btn green icn-only"><i class="icon-user icon-cog"></i></a>
							</div>
						</div>
						<table class="table table-striped table-hover table-bordered"
							id="table-clients">
							<thead>
								<tr>
									<th><spring:message code="label.login"/></th>
									<th><spring:message code="label.name"/></th>
									<th><spring:message code="label.country"/></th>
									<th><spring:message code="label.city"/></th>
									<th><spring:message code="label.state"/></th>
									<th><spring:message code="label.zipcode"/></th>
									<th><spring:message code="label.address"/></th>
									<th><spring:message code="label.phone"/></th>
									<th><spring:message code="label.email"/></th>
									<th><spring:message code="label.comment"/></th>
									<th><spring:message code="label.registrationdate"/></th>
									<th><spring:message code="label.totalvol"/></th>
									<th><spring:message code="label.totalcommission"/></th>
								</tr>
							</thead>
							<tbody>
								
							</tbody>
						</table>
					</div>
				</div>
				<div id="table-view-trades" class="portlet box green hide">
					<div class="portlet-title">
						<h4>
							<i class="icon-reorder"></i><spring:message code="label.trades"/>
						</h4>
					</div>
					<div class="portlet-body">
						<div class="tabbable tabbable-custom tabbable-custom-profile">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#tab-open-orders" data-toggle="tab"><spring:message code="label.openorders"/></a></li>
								<li><a href="#tab-close-orders" data-toggle="tab"><spring:message code="label.closeorders"/></a></li>
								<li><a href="#tab-balance" data-toggle="tab"><spring:message code="label.balance"/></a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane active" id="tab-open-orders">
									<div class="portlet-body" style="display: block;">
										<div class="clearfix">    														
											<div class="btn-group pull-right">
												<div id="clients-open-trades-date-range" class="btn pull-right datepickerrange">
													<i class="icon-calendar"></i> &nbsp;<span></span> <b
														class="caret"></b>
												</div>
											</div>
											<div class="pull-right">
												<a href="javascript:;" title="Click here to export data to excel" id="clients-open-excel-export" class="btn green icn-only"><i class="icon-user icon-bar-chart"></i></a>
												<a href="#clients-open-trades-table-config" title="Click here to toggle table columns" data-toggle="modal" class="btn green icn-only"><i class="icon-user icon-cog"></i></a>
											</div>
										</div>
										<table class="table table-striped table-hover table-bordered"
											id="table-open-trades">
											<thead>
												<tr>
													<th><spring:message code="label.ticket"/></th>
													<th><spring:message code="label.login"/></th>
													<th><spring:message code="label.symbol"/></th>
													<th><spring:message code="label.digits"/></th>
													<th><spring:message code="label.cmd"/></th>
													<th><spring:message code="label.volume"/></th>
													<th><spring:message code="label.opentime"/></th>
													<th><spring:message code="label.openprice"/></th>
													<th><spring:message code="label.sl"/></th>
													<th><spring:message code="label.tp"/></th>
													<th><spring:message code="label.taxes"/></th>
													<th><spring:message code="label.profit"/></th>
													<th><spring:message code="label.comment"/></th>
													<th><spring:message code="label.internalid"/></th>
													<th><spring:message code="label.marginrate"/></th>
													<th><spring:message code="label.timestamp"/></th>
													<th><spring:message code="label.modifytime"/></th>
												</tr>
											</thead>
											<tbody>
												
											</tbody>
										</table>
									</div>
								</div>
								<div class="tab-pane" id="tab-close-orders">
									<div class="portlet-body" style="display: block;">
										<div class="clearfix">    														
											<div class="btn-group pull-right">
												<div id="clients-close-trades-date-range" class="btn pull-right datepickerrange">
													<i class="icon-calendar"></i> &nbsp;<span></span> <b
														class="caret"></b>
												</div>
											</div>
											<div class="pull-right">
												<a href="javascript:;" title="Click here to export data to excel" id="clients-close-excel-export" class="btn green icn-only"><i class="icon-user icon-bar-chart"></i></a>
												<a href="#clients-close-trades-table-config" title="Click here to toggle table columns" data-toggle="modal" class="btn green icn-only"><i class="icon-user icon-cog"></i></a>
											</div>
										</div>
										<table class="table table-striped table-hover table-bordered"
											id="table-close-trades">
											<thead>
												<tr>
													<th><spring:message code="label.ticket"/></th>
													<th><spring:message code="label.login"/></th>
													<th><spring:message code="label.symbol"/></th>
													<th><spring:message code="label.digits"/></th>
													<th><spring:message code="label.cmd"/></th>
													<th><spring:message code="label.volume"/></th>
													<th><spring:message code="label.opentime"/></th>
													<th><spring:message code="label.openprice"/></th>
													<th><spring:message code="label.sl"/></th>
													<th><spring:message code="label.tp"/></th>
													<th><spring:message code="label.closetime"/></th>
													<th><spring:message code="label.closeprice"/></th>
													<th><spring:message code="label.taxes"/></th>
													<th><spring:message code="label.profit"/></th>
													<th><spring:message code="label.comment"/></th>
													<th><spring:message code="label.internalid"/></th>
													<th><spring:message code="label.marginrate"/></th>
													<th><spring:message code="label.timestamp"/></th>
													<th><spring:message code="label.modifytime"/></th>
												</tr>
											</thead>
											<tbody>
												
											</tbody>
										</table>
									</div>
								</div>
								<div class="tab-pane" id="tab-balance">
									<div class="portlet-body" style="display: block;">
										<div class="clearfix">    														
											<div class="btn-group pull-right">
												<div id="clients-balance-date-range" class="btn pull-right datepickerrange">
													<i class="icon-calendar"></i> &nbsp;<span></span> <b
														class="caret"></b>
												</div>
											</div>
											<div class="pull-right">
												<a href="javascript:;" title="Click here to export data to excel"  id="clients-balance-excel-export" class="btn green icn-only"><i class="icon-user icon-bar-chart"></i></a>
												<a href="#clients-balance-table-config" title="Click here to toggle table columns" data-toggle="modal" class="btn green icn-only"><i class="icon-user icon-cog"></i></a>
											</div>
										</div>
										<table class="table table-striped table-hover table-bordered"
											id="table-balance">
											<thead>
												<tr>
													<th><spring:message code="label.ticket"/></th>
													<th><spring:message code="label.login"/></th>
													<th><spring:message code="label.symbol"/></th>
													<th><spring:message code="label.digits"/></th>
													<th><spring:message code="label.cmd"/></th>
													<th><spring:message code="label.volume"/></th>
													<th><spring:message code="label.opentime"/></th>
													<th><spring:message code="label.openprice"/></th>
													<th><spring:message code="label.sl"/></th>
													<th><spring:message code="label.tp"/></th>
													<th><spring:message code="label.closetime"/></th>
													<th><spring:message code="label.closeprice"/></th>
													<th><spring:message code="label.taxes"/></th>
													<th><spring:message code="label.profit"/></th>
													<th><spring:message code="label.comment"/></th>
													<th><spring:message code="label.internalid"/></th>
													<th><spring:message code="label.marginrate"/></th>
													<th><spring:message code="label.timestamp"/></th>
													<th><spring:message code="label.modifytime"/></th>
												</tr>
											</thead>
											<tbody>
												
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- END EXAMPLE TABLE PORTLET-->
			</div>
		</div>
		<!-- END PAGE CONTENT -->
	</div>
	<!-- END PAGE CONTAINER-->
</div>
<!-- END PAGE -->