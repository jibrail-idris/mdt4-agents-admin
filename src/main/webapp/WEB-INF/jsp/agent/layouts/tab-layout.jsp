<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN PAGE CONTAINER-->
			<div class="container-fluid">
				<!-- BEGIN PAGE HEADER-->
				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN PAGE TITLE & BREADCRUMB-->			
						<h3 class="page-title">
							<spring:message code="label.mytrades"/>
						</h3>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->
				<div class="row-fluid profile">
					<div class="span12">
						<div class="row-fluid">
							<div class="span4">
								<div class="portlet sale-summary clients-open-trades-summary">
									<div class="portlet-title">
										<h4><spring:message code="label.myopentrades"/></h4>
									</div>
									<ul class="unstyled">
										<li>
											<span class="sale-info"><spring:message code="label.totalvolume"/></span> 
											<span class="sale-num volume"></span>
										</li>
										<li>
											<span class="sale-info"><spring:message code="label.totalcommission"/></span> 
											<span class="sale-num commission">-</span>
										</li>
									</ul>
								</div>
							</div>
							<div class="span4">
								<div class="portlet sale-summary clients-close-trades-summary">
									<div class="portlet-title">
										<h4><spring:message code="label.myclosetrades"/></h4>
									</div>
									<ul class="unstyled">
										<li>
											<span class="sale-info"><spring:message code="label.totalvolume"/></span> 
											<span class="sale-num volume"></span>
										</li>
										<li>
											<span class="sale-info"><spring:message code="label.totalcommission"/></span> 
											<span class="sale-num commission"></span>
										</li>
									</ul>
								</div>
							</div>
							<div class="span4">
								<div class="portlet sale-summary downline-clients-trades-summary">
									<div class="portlet-title">
										<h4><spring:message code="label.downlineclientstrades"/></h4>
									</div>
									<ul class="unstyled">
										<li>
											<span class="sale-info"><spring:message code="label.totalvolume"/></span> 
											<span class="sale-num volume"></span>
										</li>
										<li>
											<span class="sale-info"><spring:message code="label.totalcommission"/></span> 
											<span class="sale-num commission"></span>
										</li>
									</ul>
								</div>
							</div>
							
						</div>
						<!--end row-fluid-->
						<div class="tabbable tabbable-custom tabbable-custom-profile">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#tab-open-orders" data-toggle="tab"><spring:message code="label.myopentrades"/></a></li>
								<li><a href="#tab-close-orders" data-toggle="tab"><spring:message code="label.myclosetrades"/></a></li>
								<li><a href="#tab-balance-trades" data-toggle="tab"><spring:message code="label.mybalancetrades"/></a></li>
								<li><a href="#tab-downline-open-trades" data-toggle="tab"><spring:message code="label.mydownlinesclientsopentrades"/></a></li>
								<li><a href="#tab-downline-close-trades" data-toggle="tab"><spring:message code="label.mydownlinesclientsclosetrades"/></a></li>
								<li><a href="#tab-downline-balance-trades" data-toggle="tab"><spring:message code="label.mydownlinesclientsbalancetrades"/></a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane active" id="tab-open-orders">
									<div class="portlet-body" style="display: block;">
										<tiles:insertAttribute name="clients-open-trades" />
									</div>
								</div>
								<div class="tab-pane" id="tab-close-orders">
									<div class="portlet-body" style="display: block;">
										<tiles:insertAttribute name="clients-close-trades" />
									</div>
								</div>
								<div class="tab-pane" id="tab-balance-trades">
									<div class="portlet-body" style="display: block;">
										<tiles:insertAttribute name="balance-trades" />
									</div>
								</div>
								<div class="tab-pane" id="tab-downline-open-trades">
									<div class="portlet-body" style="display: block;">
										<tiles:insertAttribute name="downline-clients-open-trades" />
									</div>
								</div>
								<div class="tab-pane" id="tab-downline-close-trades">
									<div class="portlet-body" style="display: block;">
										<tiles:insertAttribute name="downline-clients-close-trades" />
									</div>
								</div>
								<div class="tab-pane" id="tab-downline-balance-trades">
									<div class="portlet-body" style="display: block;">
										<tiles:insertAttribute name="downline-clients-balance-trades" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- END PAGE CONTENT-->
			</div>
			<!-- END PAGE CONTAINER-->	
		</div>
		<!-- END PAGE -->