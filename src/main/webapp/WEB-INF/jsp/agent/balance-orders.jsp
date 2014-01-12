<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
															
    													<div class="clearfix">    														
															<div class="btn-group pull-right">
																<div id="clients-balance-date-range" class="btn pull-right datepickerrange">
																	<i class="icon-calendar"></i> &nbsp;<span></span> <b
																		class="caret"></b>
																</div>
															</div>
															<div class="pull-right">
    															<a href="#clients-balance-table-config" data-toggle="modal" class="btn green icn-only"><i class="icon-user icon-cog"></i></a>
    														</div>
														</div>
														<table id="clients-balance-table" class="table table-striped table-bordered table-advance table-hover hide">
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