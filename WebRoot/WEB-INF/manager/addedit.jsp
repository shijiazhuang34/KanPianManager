<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="taglib.jsp"%>
<link rel="stylesheet" href="css/addedit.css">
<div class="addeditdiv mdl-shadow--3dp">
	<div class="addedit">
		<c:if test="${param.isedit ne 1}">
		<h3>新增资源</h3>
		</c:if>
		<c:if test="${param.isedit eq 1}">
		<h3>编辑资源</h3>
		</c:if>
		<form id="postsrc">
		<ul class="addeditlist">
			<li><div class="fl dl">标题:</div><div class="fr dr">
				<div class="inputdiv mdl-textfield mdl-js-textfield is-upgraded is-dirty" data-upgraded=",MaterialTextfield">
					<input class="mdl-textfield__input formparam" type="text" id="sample1"  value="${javobj.title}" name="javsrc.title"/>
					<label class="mdl-textfield__label" for="sample1"></label>
				</div>
			</div></li>
			<c:if test="${param.isedit eq 1}">
				<input value="${javobj.id}" type="hidden" class="formparam" name="mgid" >
			</c:if>
			<c:if test="${param.isedit ne 1}">
			<li><div class="fl dl">类型:</div><div class="fr dr pr">
				<select class="" name="tags" id="typevideo">
				<option value="CENSORED">有码</option>
				<option value="UNCENSORED">无码</option>
				<option value="AVLIST">系列合集</option>
				</select>
				<div class="selectclip"></div>
			</div></li>	
			</c:if>
			<li><div class="fl dl">识别码:</div><div class="fr dr">
				<div class="inputdiv mdl-textfield mdl-js-textfield is-upgraded is-dirty" data-upgraded=",MaterialTextfield">
					<input class="mdl-textfield__input formparam" type="text" value="${javobj.sbm}" name="javsrc.sbm" />
					<label class="mdl-textfield__label" for="sample1"></label>
				</div>
			</div></li>
			<li><div class="fl dl">图片:</div><div class="fr dr">
				<div class="inputdiv mdl-textfield mdl-js-textfield is-upgraded is-dirty" data-upgraded=",MaterialTextfield">
					<input class="mdl-textfield__input formparam" type="text" id="imgpath"  value="${javobj.imgsrc}" name="javsrc.imgsrc" readonly="readonly"/>
					<label class="mdl-textfield__label" for="sample1"></label>
				</div>
				<span id="upimg" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">
					上传图片
				</span>
			</div></li>
			<c:if test="${param.isedit ne 1}">
			<li><div class="fl dl">种子:</div><div class="fr dr">
				<div class="inputdiv mdl-textfield mdl-js-textfield is-upgraded is-dirty" data-upgraded=",MaterialTextfield">
					<input class="mdl-textfield__input formparam" type="text" id="torpath"   name="torsrc" readonly="readonly"/>
					<label class="mdl-textfield__label" for="sample1"></label>
				</div>
				<span id="uptor" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">
					上传种子
				</span>
			</div></li>
			<li><div class="fl dl">发片商:</div><div class="fr dr">
				<div class="inputdiv mdl-textfield mdl-js-textfield is-upgraded is-dirty" data-upgraded=",MaterialTextfield">
					<input class="mdl-textfield__input formparam" type="text"   name="tags" />
					<label class="mdl-textfield__label" for="sample1"></label>
				</div>
			</div></li>
			<li><div class="fl dl">番号:</div><div class="fr dr">
				<div class="inputdiv mdl-textfield mdl-js-textfield is-upgraded is-dirty" data-upgraded=",MaterialTextfield">
					<input class="mdl-textfield__input formparam" type="text"   name="tags" />
					<label class="mdl-textfield__label" for="sample1"></label>
				</div>
			</div></li>
			</c:if>
			<li><div class="fl dl">是否存档:</div><div class="fr dr pr">
				<select class="" name="javsrc.isdown">
				<option value="0" ${javobj.isdown eq 0 ? 'selected' : ''}>未存档</option>
				<option value="1" ${javobj.isdown eq 1 ? 'selected' : ''}>硬盘存档</option>
				<option value="2" ${javobj.isdown eq 2 ? 'selected' : ''}>网络存档</option>
				</select>
				<div class="selectclip"></div>
			</div></li>
			<li><div class="fl dl">标签:</div><div class="fr dr">
				<div class="inputdiv tagspan">
					<div id="tags">
						<c:forEach var="tag" items="${javobj.tagslist}">
							<span  title="${tag}">${tag}<i onclick="removetag(this)">x</i></span>
						</c:forEach>
					</div>
					<span id="addtag">+</span>
				</div>
			</div></li>
		</ul>
		</form>
		<div class="btnbottom">
			<button id="savesrc" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">
				保存
			</button>
			<button id="clearsrc" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">
				重置
			</button>
		</div>

		<div class="uploadimg" style="display: none">
			<form id="postimg">
				<input name="imgtorFile" id="imgFile" type="file" class="formelements" style="margin-bottom: 20px;"/> 
				<br>
				<span id="tjimg" onclick="postimg()" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">
					上传
				</span>
				<div id="result" ></div> 
			</form>
		</div>

		<div class="uploadtor" style="display: none">
			<form id="posttor">
				<input name="imgtorFile" id="torFile" class="formelements" type="file" style="margin-bottom: 20px;"/> 
				<br>
				<span id="tjtor" onclick="posttor()" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">
					上传
				</span>
			</form>
		</div>	
	</div>
	
	<c:if test="${param.isedit ne 1}">
	<div class="importExl">
		<span id="importExlBtn"  class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">
					导入excle数据</span>
		<div class="importExlDiv" style="display: none">
			<form id="postexl">
				<input name="theExlFile" id="exlFile" class="formelements" type="file" style="margin-bottom: 20px;"/>
				<br>
				<span id="tjexl" onclick="postexl()" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">
					上传</span>
			</form>
		</div>
	</div>	
	</c:if>
</div>
<script type="text/javascript">
	var isedit="${param.isedit eq 1}";
	if(isedit=="true"){
		isedit=true;
	}else{
		isedit=false;
	}
</script>
<script type="text/javascript" src="js/addedit.js"></script>
