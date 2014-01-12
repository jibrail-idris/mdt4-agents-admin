<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
														<div style="height: auto;" id="password-change-form-div" class="accordion collapse">
															<div class="alert alert-error hide"></div>
															<div class="alert alert-success hide">Password change is successful.</div>
															<form id="password-change-form" action="/profile/password/submit" method="post">
																<label class="control-label"><spring:message code="label.currentpassword"/></label>
																<input type="password" name="current_password" class="m-wrap span8" />
																<label class="control-label"><spring:message code="label.newpassword"/></label>
																<input type="password" name="new_password1" class="m-wrap span8" />
																<label class="control-label"><spring:message code="label.retypepassword"/></label>
																<input type="password" name="new_password2" class="m-wrap span8" />
																<div class="submit-btn">
																	<button type="button" id="password-change-submit" class="btn green"><spring:message code="label.changepassword"/></button>
																	<a href="/profile" class="btn">Cancel</a>
																</div>
															</form>
														</div>