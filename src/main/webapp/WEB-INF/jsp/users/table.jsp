<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- BEGIN PAGE -->
<div class="page-content">

	<!-- MT4 NEW AGENT FORM MODAL -->
	<div id="portlet-mt4-new-agent-form" class="modal hide">
		<div class="modal-body">
			<div class="portlet box blue">
				<div class="portlet-title">
					<h4 class="block">
						<i class="icon-reorder"></i><spring:message code="label.assignasagent"/>
					</h4>
				</div>
				<div class="portlet-body form">
					<div class="portlet-body form">
                        <form action="#" class="form-horizontal">
                           <div id="agent-assignment-wizard" class="form-wizard">
                              <div class="navbar steps">
                                 <div class="navbar-inner">
                                    <ul class="row-fluid">
                                       <li class="span3">
                                          <a href="#tab1" data-toggle="tab" class="step active">
                                          <span class="number">1</span>
                                          <span class="desc"><i class="icon-ok"></i> Account Setup</span>   
                                          </a>
                                       </li>
                                       <li class="span3">
                                          <a href="#tab2" data-toggle="tab" class="step">
                                          <span class="number">2</span>
                                          <span class="desc"><i class="icon-ok"></i> Agent Setup</span>   
                                          </a>
                                       </li>                                       
                                    </ul>
                                 </div>
                              </div>
                              <div id="bar" class="progress progress-success progress-striped">
                                 <div class="bar"></div>
                              </div>
                              <div class="tab-content">
                                 <div class="tab-pane active" id="tab1">
                                    <h3 class="block">Provide account details</h3>
                                    <div class="control-group">
                                       <label class="control-label"><spring:message code="label.username"/></label>
                                       <div class="controls">
                                          <input type="text" name="username" class="span2 m-wrap" />
                                          <span class="help-inline">Provide your username</span>
                                       </div>
                                    </div>
                                    <div class="control-group">
                                       <label class="control-label"><spring:message code="label.password"/></label>
                                       <div class="controls">
                                          <input type="password" name="password" class="span2 m-wrap" />
                                          <span class="help-inline">Provide your password</span>
                                       </div>
                                    </div>
                                    <div class="control-group">
                                       <label class="control-label">Confirm Password</label>
                                       <div class="controls">
                                          <input type="password" name="password2" class="span2 m-wrap" />
                                          <span class="help-inline">Confirm your password</span>
                                       </div>
                                    </div>
                                 </div>
                                 <div class="tab-pane" id="tab2">
                                    <h3 class="block">Provide agent details</h3>
                                    <div class="control-group">
										<label class="control-label">MT4 User</label>
										<div class="controls input-icon">
											<input type="text" name="mt4login" readonly="readonly"
												class="medium m-wrap" />
										</div>
									</div>
                                    <div class="control-group">
                                       <label class="control-label"><spring:message code="label.commission"/></label>
										<div class="controls input-icon">
											<div class="input-prepend">
												<span class="add-on">$</span>
												<input type="text" name="commission" class="small m-wrap" />
											</div>
										</div>
                                    </div>
                                    <div class="control-group">
                                       <label class="control-label"><spring:message code="label.upline"/></label>
										<div class="controls input-icon">
<!-- 											<select name="upline" class="upline-autocomplete medium m-wrap"> -->
<!-- 											</select> -->
												<input type="text" class="upline-autocomplete medium m-wrap" />
												<input type="hidden" name="upline" />
										</div>
                                    </div>
                                 </div>
                              </div>
                              <div class="form-actions clearfix">
                                 <a href="javascript:;" class="btn button-previous">
                                 <i class="m-icon-swapleft"></i> Back 
                                 </a>
                                 <a href="javascript:;" class="btn blue button-next">
                                 Continue <i class="m-icon-swapright m-icon-white"></i>
                                 </a>
                                 <a href="javascript:;" class="btn green button-submit">
                                 Submit <i class="m-icon-swapright m-icon-white"></i>
                                 </a>
                              </div>
                           </div>
                        </form>
                     </div>
				</div>
			</div>
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

	<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
	<div id="users-table-config" class="modal hide" style="width:400px;">
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
	<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
	<!-- BEGIN PAGE CONTAINER-->
	<div class="container-fluid">
		<!-- BEGIN PAGE HEADER-->
		<div class="row-fluid">
			<div class="span12">				
				<!-- BEGIN PAGE TITLE & BREADCRUMB-->
				<h3 class="page-title">
					<spring:message code="label.users"/>
				</h3>
				<ul class="breadcrumb">
					<li><i class="icon-home"></i> <a href="index.html">Home</a> <i
						class="icon-angle-right"></i></li>
					<li><a href="#"><spring:message code="label.users"/></a> <i class="icon-angle-right"></i>
					</li>
					<li><a href="#">All</a></li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>
		<!-- END PAGE HEADER-->
		<!-- BEGIN PAGE CONTENT-->
		<div class="row-fluid">
			<div class="span12">
				<!-- BEGIN EXAMPLE TABLE PORTLET-->
				<div id="users-portlet" class="portlet box blue">
					<div class="portlet-title">
						<h4>
							<i class="icon-reorder"></i><spring:message code="label.users"/>
						</h4>
					</div>
					<div class="portlet-body">
						<div class="clearfix">
							<div class="pull-right">
								<a href="#users-table-config" data-toggle="modal" class="btn green icn-only"><i class="icon-user icon-cog"></i></a>
							</div>
						</div>
						<table class="table table-striped table-hover table-bordered hide" style="width:100% !important;"
							id="table-mt4users">
							<thead>
								<tr>
									<th><spring:message code="label.login"/></th>
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
									<th><spring:message code="label.id"/></th>
									<th><spring:message code="label.status"/></th>
									<th><spring:message code="label.regdate"/></th>
									<th><spring:message code="label.lastdate"/></th>
									<th><spring:message code="label.leverage"/></th>
									<th><spring:message code="label.agentaccount"/></th>
									<th><spring:message code="label.timestamp"/></th>
									<th><spring:message code="label.balance"/></th>
									<th><spring:message code="label.prevmonthbalance"/></th>
									<th><spring:message code="label.prevbalance"/></th>
									<th><spring:message code="label.credit"/></th>
									<th><spring:message code="label.interestrate"/></th>
									<th><spring:message code="label.taxes"/></th>
									<th><spring:message code="label.sendreports"/></th>
									<th><spring:message code="label.usercolor"/></th>
									<th><spring:message code="label.equity"/></th>
									<th><spring:message code="label.margin"/></th>
									<th><spring:message code="label.marginlevel"/></th>
									<th><spring:message code="label.marginfree"/></th>
									<th><spring:message code="label.modifytime"/></th>
									<th><spring:message code="label.upline"/></th>
									<th><spring:message code="label.status"/></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
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