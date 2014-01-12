<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
      <!-- BEGIN PAGE -->  
      <div class="page-content">
         <!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
         <div id="portlet-config" class="modal hide">
            <div class="modal-header">
               <button data-dismiss="modal" class="close" type="button"></button>
               <h3>portlet Settings</h3>
            </div>
            <div class="modal-body">
               <p>Here will be a configuration form</p>
            </div>
         </div>
         <!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
         <!-- BEGIN PAGE CONTAINER-->
         <div class="container-fluid">
            <!-- BEGIN PAGE HEADER-->   
            <div class="row-fluid">
               <div class="span12">
                  <!-- BEGIN STYLE CUSTOMIZER -->
                  <div class="color-panel hidden-phone">
                     <div class="color-mode-icons icon-color"></div>
                     <div class="color-mode-icons icon-color-close"></div>
                     <div class="color-mode">
                        <p>THEME COLOR</p>
                        <ul class="inline">
                           <li class="color-black current color-default" data-style="default"></li>
                           <li class="color-blue" data-style="blue"></li>
                           <li class="color-brown" data-style="brown"></li>
                           <li class="color-purple" data-style="purple"></li>
                           <li class="color-white color-light" data-style="light"></li>
                        </ul>
                        <label class="hidden-phone">
                        <input type="checkbox" class="header" checked value="" />
                        <span class="color-mode-label">Fixed Header</span>
                        </label>                    
                     </div>
                  </div>
                  <!-- END BEGIN STYLE CUSTOMIZER -->     
                  <h3 class="page-title">
                     Form Validation
                     <small>form validation states</small>
                  </h3>
                  <ul class="breadcrumb">
                     <li>
                        <i class="icon-home"></i>
                        <a href="index.html">Home</a> 
                        <span class="icon-angle-right"></span>
                     </li>
                     <li>
                        <a href="#">Form Stuff</a>
                        <span class="icon-angle-right"></span>
                     </li>
                     <li><a href="#">Form Validation</a></li>
                  </ul>
               </div>
            </div>
            <!-- END PAGE HEADER-->
            <!-- BEGIN PAGE CONTENT-->
            <div class="row-fluid">
               <div class="span12">
                  <!-- BEGIN VALIDATION STATES-->
                  <div class="portlet box blue">
                     <div class="portlet-title">
                        <h4 class="block"><i class="icon-reorder"></i>Validation States</h4>
                        <div class="tools">
                           <a href="javascript:;" class="collapse"></a>
                           <a href="#portlet-config" data-toggle="modal" class="config"></a>
                           <a href="javascript:;" class="reload"></a>
                           <a href="javascript:;" class="remove"></a>
                        </div>
                     </div>
                     <div class="portlet-body form">
                        <!-- BEGIN FORM-->
                        <h3 class="block">Validation states with icon and tooltip on hover</h3>
                        <form:errors path="*" cssClass="error" />
                        <form:form modelAttribute="agent" action="submit" cssClass="form-horizontal">
                           <div class="control-group">
                              <label class="control-label">MT4 User</label>
                              <div class="controls">
                                 <form:select path="login" cssClass="span6 m-wrap" data-placeholder="Please Select MT4User" tabindex="1">
									<form:option value="" />
									<form:options items="${mt4Users}" itemValue="login" itemLabel="name" />
								</form:select>
                              </div>
                           </div>
                           <div class="control-group">
                              <label class="control-label">Commission</label>
                              <div class="controls">
                                 <form:input path="commission" cssClass="span6 m-wrap" />
                                 <span class="help-inline">%</span>
                              </div>
                           </div>
                           <div class="control-group">
                              <label class="control-label">Upline</label>
                              <div class="controls">
								<form:select path="parentAgentId" cssClass="span6 m-wrap" data-placeholder="Please Select Upline" tabindex="1">
									<form:option value="" />									
									<c:forEach var="agent" items="${agents}">
								        <form:option value="${agent.agentId}"><c:out value="ID [${agent.agentId}][${agent.parentAgentId}] - ${agent.name} (${agent.login})"/></form:option>
								    </c:forEach>
								</form:select>
                              </div>
                           </div>
                           <div class="form-actions">
                              <button type="submit" class="btn blue">Save</button>
                              <button type="button" class="btn">Cancel</button>
                           </div>
                        </form:form>
                        <!-- END FORM-->
                     </div>
                  </div>
                  <!-- END VALIDATION STATES-->
               </div>
            </div>
            <!-- END PAGE CONTENT-->         
         </div>
         <!-- END PAGE CONTAINER-->
      </div>
      <!-- END PAGE -->