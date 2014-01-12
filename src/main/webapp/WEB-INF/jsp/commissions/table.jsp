<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- BEGIN PAGE -->
<div class="page-content">
	<!-- BEGIN PAGE CONTAINER-->
	<div class="container-fluid">
		<!-- BEGIN PAGE HEADER-->
		<div class="row-fluid">
			<div class="span12">
				<!-- BEGIN PAGE TITLE & BREADCRUMB-->
				<h3 class="page-title">
					Agents' Commissions
				</h3>
				<ul class="breadcrumb">
					<li><i class="icon-home"></i> <a href="index.html">Home</a> <i
						class="icon-angle-right"></i></li>
					<li><a href="#">Commissions</a> <i class="icon-angle-right"></i>
					</li>
					<li><a href="#">View All</a></li>
					<li class="pull-right no-text-shadow">
						<div id="commissions-report-range" class="dashboard-date-range tooltips no-tooltip-on-touch-device responsive" data-tablet="" data-desktop="tooltips" data-placement="top" data-original-title="Change commissions filter date range">
							<i class="icon-calendar"></i>
							<span></span>
							<i class="icon-angle-down"></i>
						</div>
					</li>
				</ul>
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>
		<!-- END PAGE HEADER-->
		<!-- BEGIN PAGE CONTENT-->
		<div class="row-fluid">
			<div class="span12">
				<!-- BEGIN EXAMPLE TABLE PORTLET-->
				<div id="commissions-portlet" class="portlet box blue">
					<div class="portlet-title">
						<h4>
							<i class="icon-reorder"></i>Editable Table
						</h4>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a> <a
								href="#portlet-config" data-toggle="modal" class="config"></a> <a
								href="javascript:;" class="reload"></a> <a href="javascript:;"
								class="remove"></a>
						</div>
					</div>
					<div class="portlet-body">
						<div class="clearfix">
							<div class="btn-group pull-right">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="icon-angle-down"></i>
								</button>
								<ul class="dropdown-menu">
									<li><a href="#">Print</a></li>
									<li><a href="#">Save as PDF</a></li>
									<li><a href="#">Export to Excel</a></li>
								</ul>
							</div>
						</div>
						<table class="commissions-table-template table table-striped table-hover table-bordered hide">
							<thead>
								<tr>
									<th>LOGIN</th>
									<th>NAME</th>
									<th>EMAIL</th>
									<th>VOLUME</th>
									<th>COMMISSION</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td colspan="5">LOADING</td>
								</tr>
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