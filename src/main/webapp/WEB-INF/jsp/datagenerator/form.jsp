<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- BEGIN PAGE -->
<div class="page-content">
	<!-- BEGIN PAGE CONTAINER-->
	<div class="container-fluid">
		<!-- BEGIN PAGE HEADER-->
		<div class="row-fluid">
			<div class="span12">
				<!-- BEGIN PAGE TITLE & BREADCRUMB-->
				<h3 class="page-title">Test Only - Data Generation</h3>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>
		<!-- END PAGE HEADER-->
		<!-- BEGIN PAGE CONTENT-->
		<div class="row-fluid">
			<div class="span12">
				<!-- BEGIN EXAMPLE TABLE PORTLET-->
				<!-- BEGIN SAMPLE FORM PORTLET-->
				<div class="portlet box blue">
					<div class="portlet-title">
						<h4>
							<i class="icon-reorder"></i>MT4 Login Generator
						</h4>
					</div>
					<div class="portlet-body form">
						<!-- BEGIN FORM-->
						<form action="/datagenerator/create/login" method="post"
							class="form-horizontal">
							<div class="control-group">
								<label class="control-label">Login</label>
								<div class="controls">
									<input type="text" name="login" class="span6 m-wrap" /> <span
										class="help-inline">MT4 Login</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Name</label>
								<div class="controls">
									<input type="text" name="name" class="span6 m-wrap" /> <span
										class="help-inline">Login Full Name</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Country</label>
								<div class="controls">
									<input type="text" name="country" class="span6 m-wrap" /> <span
										class="help-inline">Country of origin</span>
								</div>
							</div>
							<div class="form-actions">
								<button type="submit" class="btn blue">Submit</button>
								<button type="button" class="btn">Cancel</button>
							</div>
						</form>
					</div>
				</div>
				<!-- END EXAMPLE TABLE PORTLET-->
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<!-- BEGIN EXAMPLE TABLE PORTLET-->
				<!-- BEGIN SAMPLE FORM PORTLET-->
				<div class="portlet box blue">
					<div class="portlet-title">
						<h4>
							<i class="icon-reorder"></i>Bulk MT4 Login Generator
						</h4>
					</div>
					<div class="portlet-body form">
						<!-- BEGIN FORM-->
						<form action="/datagenerator/create/login/bulk" method="post"
							class="form-horizontal">
							<div class="control-group">
								<label class="control-label">Bulk Count</label>
								<div class="controls">
									<input type="text" name="count" class="span6 m-wrap" /> <span
										class="help-inline">No of logins to create in bulk</span>
								</div>
							</div>
							<div class="form-actions">
								<button type="submit" class="btn blue">Submit</button>
								<button type="button" class="btn">Cancel</button>
							</div>
						</form>
					</div>
				</div>
				<!-- END EXAMPLE TABLE PORTLET-->
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<!-- BEGIN PORTLET-->
				<div class="portlet box blue">
					<div class="portlet-title">
						<h4>
							<i class="icon-reorder"></i>MT4 Trade Generator
						</h4>
					</div>
					<div class="portlet-body form">
						<!-- BEGIN FORM-->
						<form action="/datagenerator/create/trades" method="post"
							class="form-horizontal">
							<div class="control-group">
								<label class="control-label">Login</label>
								<div class="controls">
									<select name="login">
										<c:forEach items="${users}" var="user">
											<option value="${user.login}">${user.login} -
												${user.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">CMD</label>
								<div class="controls">
									<input type="text" name="cmd" class="span6 m-wrap" /> <span
										class="help-inline">CMD</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Trade Type</label>
								<div class="controls">
									<select name="tradeType">
										<option value="tt:o">OPEN</option>
										<option value="tt:c">CLOSE</option>
										<option value="tt:b">BALANCE</option>
									</select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">No Of Trades</label>
								<div class="controls">
									<input type="text" name="noOfTrades" class="span6 m-wrap" /> <span
										class="help-inline">No of Trades</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Base Date</label>
								<div class="controls">
									<input type="text" name="baseDate" class="span6 m-wrap" /> <span
										class="help-inline">Base Date</span>
								</div>
							</div>
							<div class="form-actions">
								<button type="submit" class="btn blue">Submit</button>
								<button type="button" class="btn">Cancel</button>
							</div>
						</form>
						<!-- END FORM-->
					</div>
				</div>
				<!-- END PORTLET-->
			</div>
		</div>

		<div class="row-fluid">
			<div class="span12">
				<!-- BEGIN PORTLET-->
				<div class="portlet box blue">
					<div class="portlet-title">
						<h4>
							<i class="icon-reorder"></i>MT4 Agent Tree Generator
						</h4>
					</div>
					<div class="portlet-body form">
						<!-- BEGIN FORM-->
						<form action="/datagenerator/create/tree" method="post" class="form-horizontal">
							<div class="control-group">
								<label class="control-label">Depth</label>
								<div class="controls">
									<input type="text" name="depth" class="span6 m-wrap" /> <span
										class="help-inline">Depth Of Agent Tree</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">No Of Children Per Node</label>
								<div class="controls">
									<input type="text" name="childrenCountPerNode" class="span6 m-wrap" /> <span
										class="help-inline">No of Downlines an agent is assigned to</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">No Of Clients Assigned Per Agent</label>
								<div class="controls">
									<input type="text" name="noOfClients" class="span6 m-wrap" /> <span
										class="help-inline"></span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">No of Trades Assigned Per Client</label>
								<div class="controls">
									<input type="text" name="noOfTradesPerClient" class="span6 m-wrap" /> <span
										class="help-inline">The number of trades per trade type. If 10 is given, then 30 trades will be created. 10 for close, 10 for open and 10 for balance.</span>
								</div>
							</div>
							<div class="form-actions">
								<button type="submit" class="btn blue">Submit</button>
								<button type="button" class="btn">Cancel</button>
							</div>
						</form>
						<!-- END FORM-->
					</div>
				</div>
				<!-- END PORTLET-->
			</div>
		</div>

		<!-- END PAGE CONTENT -->
	</div>
	<!-- END PAGE CONTAINER-->
</div>
<!-- END PAGE -->