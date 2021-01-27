<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆页面</title>
<script src="${pageContext.request.contextPath }/js/jquery-1.8.3.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/style.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style_grey.css" />
<style>
input[type=text] {
	width: 80%;
	height: 25px;
	font-size: 12pt;
	font-weight: bold;
	margin-left: 45px;
	padding: 3px;
	border-width: 0;
}

input[type=password] {
	width: 80%;
	height: 25px;
	font-size: 12pt;
	font-weight: bold;
	margin-left: 45px;
	padding: 3px;
	border-width: 0;
}

#loginform\:codeInput {
	margin-left: 1px;
	margin-top: 1px;
}

#loginform\:vCode {
	margin: 0px 0 0 60px;
	height: 34px;
}
</style>
<script type="text/javascript">
	if (window.self != window.top) {
		window.top.location = window.location;
	}
</script>
</head>
<body>
	<div class="main-inner" id="mainCnt"
		style="width: 100%; height: 100%; overflow: hidden; position: absolute;  background-image: url('${pageContext.request.contextPath }/images/bg_login3.jpg')">
		<div id="loginBlock" class="login" style="margin-left: 780px; margin-top: 320px; height: 255px;">
			<div class="loginFunc">
				<div id="lbNormal" class="loginFuncMobile">员工登录</div>
			</div>
			<!-- 账号密码提交框 -->
			<div class="loginForm">
				<form id="loginform" name="loginform" method="post" class="niceform" action="userAction_login.action">
					<div id="idInputLine" class="loginFormIpt showPlaceholder" style="margin-top: 5px;">
						<input id="loginform:idInput" type="text" name="username" class="loginFormTdIpt" maxlength="50" />
						<label for="idInput" class="placeholder" id="idPlaceholder">帐号：</label>
					</div>
					<div class="forgetPwdLine"></div>
					<div id="pwdInputLine" class="loginFormIpt showPlaceholder">
						<input id="loginform:pwdInput" class="loginFormTdIpt" type="password" name="password" value="" />
						<label for="pwdInput" class="placeholder" id="pwdPlaceholder">密码：</label>
					</div>
					<div class="loginFormIpt loginFormIptWiotTh" style="margin-top: 58px;">
						<div id="codeInputLine" class="loginFormIpt showPlaceholder"
							style="margin-left: 0px; margin-top: -40px; width: 50px;">
							<input id="loginform:codeInput" class="loginFormTdIpt" type="text" name="checkcode" title="请输入验证码" />
							<!-- 调用validatecode.jsp生成验证码图片,后面的Math.random()为了防止浏览器缓存而没重新请求 -->
							<img id="loginform:vCode" src="${pageContext.request.contextPath }/validatecode.jsp"
								onclick="javascript:document.getElementById('loginform:vCode').src='${pageContext.request.contextPath }/validatecode.jsp?'+Math.random();" />
						</div>
						<!-- 点击登录后提交表单数据 -->
						<a onclick="document.getElementById('loginform').submit();" href="#" id="loginform:j_id19" name="loginform:j_id19">
							<span id="loginform:loginBtn" class="btn btn-login" style="margin-top: -36px;">登录</span>
						</a>
					</div>
					<script type="text/javascript">
						$(function() {
							$("#codeInputLine")
									.bind(
											"keydown",
											function(e) {
												var theEvent = e
														|| window.event;
												var code = theEvent.keyCode
														|| theEvent.which
														|| theEvent.charCode;
												if (code == 13) {
													//监控回车,回车就提交数据,相当于点击登录
													document.getElementById(
															'loginform')
															.submit();
												}
											});
						});
					</script>
					<div align="center">
						<br />
						<font color="red">
							<s:actionerror />
						</font>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>