<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/default.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>

<script type="text/javascript">
	nowname = null;//全局声明当前用户名
	$(function(){
		$("body").css({visibility:"visible"});
		
		
		$('#save').click(function(){
			var v = $('#userForm').form("validate");
			if (v) {
				
				$('#userForm').submit();
			} else {
				$.messager("提示","不符合规则","warning");
			}
		});
		
	});
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
		<div class="datagrid-toolbar">
			<a id="save" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true" >修改</a>
		</div>
	</div>
    <div region="center" style="overflow:auto;padding:5px;" border="false">
       <form id="userForm" method="post" action="userAction_edit.action">
           <input type="hidden" name="id">
           <table class="table-edit"  width="95%" align="center">
				<tr class="title">
					<td colspan="4">基本信息</td>
				</tr>
				<tr>
					<td>用户名:</td>
					<td>
						<input type="text" data-options="validType:'username'" name="username" id="username" class="easyui-validatebox" required="true" />
						<script type="text/javascript">
						$(function() {
							var arrayname = null;
							$.post('userAction_findAllName.action',function(dataname){//获取所有用户名
								arrayname = dataname.split(",");//用,分割取出数组
							});
							$.extend($.fn.validatebox.defaults.rules, {
								username : {
									validator : function(value, param) {
										var result = true;
										for(var i=0;i<arrayname.length;i++){
											if(value!=nowname && value==arrayname[i]){//排除当前用户名
												result = false;//如果用户名已被使用过
											}
										}
										return result;
									},
									message : '该用户名已被使用'
								}
							});
						});
						</script>
					</td>

					<td>口令:</td>
					<td>
						<input type="password" name="password" id="password" class="easyui-validatebox" required="true"
							validType="minLength[3]" />
					</td>
				</tr>
				<tr class="title">
					<td colspan="4">其他信息</td>
				</tr>
				<tr>
					<td>工资:</td>
					<td>
						<input type="text" name="salary" id="salary" class="easyui-numberbox" />
					</td>
					<td>生日:</td>
					<td>
						<input type="text" name="birthdayString" id="birthdayString" class="easyui-datebox" />
					</td>
				</tr>
				<tr>
					<td>性别:</td>
					<td>
						<select name="gender" id="gender" class="easyui-combobox" style="width: 150px;">
							<option value="">请选择</option>
							<option value="男">男</option>
							<option value="女">女</option>
						</select>
					</td>
					<td>单位:</td>
					<td>
						<select name="station" id="station" class="easyui-combobox" style="width: 150px;">
							<option value="">请选择</option>
							<option value="总公司">总公司</option>
							<option value="分公司">分公司</option>
							<option value="厅点">厅点</option>
							<option value="基地运转中心">基地运转中心</option>
							<option value="营业所">营业所</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>联系电话</td>
					<td colspan="3">
						<input type="text" name="telephone" data-options="validType:'telephone'" id="telephone" class="easyui-validatebox" required="true" />
					</td>
				</tr>
	           	<tr><td>备注:</td><td colspan="3"><textarea name="remark" style="width:80%"></textarea></td></tr>
	           	<tr>
	           		<td>选择角色:</td>
	           		<td colspan="3" id="roleTD">
	           		<script type="text/javascript">
	           			$(function () {
							//获取角色数据
							$.post("roleAction_listajax.action",function(data){
								for (var i = 0; i < data.length; i++) {
									var id = data[i].id;
									var name = data[i].name;
									var description = data[i].description;
									$("#roleTD").append('<input type="checkbox" name="roleIds" value="'+id+'" id="'+id+'"><label for="'+id+'">'+name+'---------'+description+'</label><br/>');
								}
								
								//获取选中了的角色
								var urlinfo = window.location.href;//获取传递来的url
								var idd = urlinfo.split("?")[1].split("=")[1];//获取传递来的id
								$("input[name='id']").val(idd);
								$.post('userAction_findById.action',{'userId':idd},function(data2){
								nowname = data2.username;//设定全局当前用户名
								$('#userForm').form("load",data2);
								var array = data2.authRoles;//数组
								for(var i=0;i<array.length;i++){
									$("#"+array[i].id).prop("checked", true);
								}
								});
							});
							
							//手机号校验
							var reg = /^1[34578][0-9]{9}$/;
							$.extend($.fn.validatebox.defaults.rules, {
								telephone : {
									validator : function(value, param) {
										return reg.test(value);
									},
									message : '手机号有误'
								}
							});
						})
	           		</script>
	           		<script type="text/javascript">
	           			$(function(){
	           				
	           			});
	           		</script>
	           			<!-- <input type="checkbox" name="roleIds" value="xx" id="zzz">
	           			<label for="zzz">
	           				xxx----------xxx
	           			</label> 
	           			<br/>-->
	           		</td>
	           	</tr>
           </table>
       </form>
	</div>
</body>
</html>