<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    													<div id="clients-trade-table-config" class="modal hide" style="width:400px;">
															<div class="modal-header">
																<button data-dismiss="modal" class="close" type="button"></button>
																<h3>Show / Hide Table Columns</h3>
															</div>
															<div class="modal-body">
																<table style="margin: auto;width:200px;">
																	<tbody>
																		<tr>
																			<td>
																				<input type="checkbox" class="column-toggle" id="open-time" />&nbsp;OPEN_TIME 
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<input type="checkbox" class="column-toggle" id="open-price" />&nbsp;OPEN_PRICE 
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<input type="checkbox" class="column-toggle" id="open-time" />&nbsp;CLOSE_TIME 
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<input type="checkbox" class="column-toggle" id="open-price" />&nbsp;CLOSE_PRICE 
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<input type="checkbox" class="column-toggle" id="symbol" />&nbsp;SYMBOL 
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<input type="checkbox" class="column-toggle" id="volume" />&nbsp;VOLUME 
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<input type="checkbox" class="column-toggle" id="sl" />&nbsp;SL 
																			</td>
																		</tr>
																		<tr>
																			<td>
																				<input type="checkbox" class="column-toggle" id="tp" />&nbsp;TP 
																			</td>
																		</tr>
																	</tbody>
																</table>
															</div>
														</div>
															
    													<div class="clearfix">    														
															<div class="btn-group pull-right">
																<div id="clients-date-range" class="btn pull-right datepickerrange">
																	<i class="icon-calendar"></i> &nbsp;<span></span> <b
																		class="caret"></b>
																</div>
															</div>
															<div class="pull-right">
    															<a href="#clients-trade-table-config" data-toggle="modal" class="btn green icn-only"><i class="icon-user icon-cog"></i></a>
    														</div>
														</div>
														<table id="clients-trades-table" class="table table-striped table-bordered table-advance table-hover">
															<thead>
																<tr>
																	<th>TICKET</th>
																	<th>CLIENT LOGIN</th>
																	<th>CLOSE_TIME</th>
																	<th>CLOSE_PRICE</th>
																	<th>SYMBOL</th>
																	<th>VOLUME</th>
																	<th>COMM $</th>
																</tr>
															</thead>
															<tbody>
																
															</tbody>
														</table>