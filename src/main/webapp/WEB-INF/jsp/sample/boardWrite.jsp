<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2024-01-16
  Time: 오후 2:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/jsp/cmmn/header.jsp" %>
<link href="/css/validate/error.css" rel="stylesheet">
<style>


    .filebox .upload-name {
        display: inline-block;
        height: 40px;
        padding: 0 10px;
        vertical-align: middle;
        border: 1px solid #dddddd;
        width: 50%;
        color: #999999;
    }

    .filebox label {
        display: inline-block;
        padding: 10px 20px;
        color: #fff;
        vertical-align: middle;
        background-color: #999999;
        cursor: pointer;
        height: 40px;
        margin-left: 10px;
    }

    .filebox input[type="file"] {
        position: absolute;
        width: 0;
        height: 0;
        padding: 0;
        overflow: hidden;
        border: 0;
    }

</style>
<div class="contact">
    <div class="container">
        <div class="section-header text-center">
            <p>Notice</p>
            <h2>Ace Car Wash</h2>
        </div>

        <div class="comment-form">
            <h2>Board Read Page</h2>
            <form name="board" id="board" action="/sample/boardExec.do" method="post" enctype="multipart/form-data">
                <input type="hidden" name="pageIndex" id="pageIndex" value="${paramMap.pageIndex}">
                <input type="hidden" name="id" id="id" value="${paramMap.id}">
                <div class="form-group">
                    <label for="title">TITLE *</label>
                    <input type="text" name="title" class="form-control" value="${result.TITLE}" id="title">
                </div>

                <div class="form-group">
                    <label for="content">CONTENT *</label>
                    <textarea id="content" name="content" cols="30" rows="5" class="form-control">${result.CONTENT}</textarea>
                </div>


                <div class="form-group" STYLE="margin-bottom: 0px;">
                    <label>FILE *</label>
                </div>

                <div class="form-group mb-lg-5 filebox">
                    <c:choose>
                        <c:when test="${fn:length(fileList) > 0}">
                            <ul class="list-group">
                                <c:forEach var="result" items="${fileList}" varStatus="st">
                                    <li class="list-group-item">
                                        <a href="#" onclick="fnFileDownLoad('${result.ID}','${result.BOARD_ID}')">${result.ORIGINAL_FILE_NAME}</a>
                                        <button type="button" onclick="fnDel('${result.ID}','${result.BOARD_ID}');" class="btn btn-danger btn-sm">X</button>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <input class="upload-name" value="첨부파일" placeholder="첨부파일">
                            <label for="file" style="margin-bottom: 0px;">파일찾기</label>
                            <input type="file" name="file" id="file">
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="form-group">
                    <button type="button" onclick="fnList();" class="btn btn-outline-dark">LIST</button>
                    <c:if test="${ not empty result.ID}">
                        <button type="button" onclick="fnDelete('${result.ID}');" class="btn btn-outline-danger">DELETE</button>
                    </c:if>
                    <button type="button" id="btnSave" class="btn btn-outline-primary">SAVE</button>
                </div>
            </form>
        </div>

    </div>


</div>
<%@ include file="/WEB-INF/jsp/cmmn/footer.jsp" %>
<%--
<link rel="stylesheet" type="text/css" media="screen" href="/common/bootstrap/css/bootstrap.min.css">--%>

<script src="/common/www/bootstrap/js/bootstrap.min.js"></script>
<script src="/js/validate/validate.js" ></script>
<script src="/js/validate/validateMin.js" ></script>
<script src="/js/validate/customModule.js" ></script>


<!-- SmartEditor2 텍스트편집기 -->
<script src="/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script>
    $(document).ready(function () {

        <!-- SmartEditor2 텍스트편집기 -->
        var oEditors = [];
        function smartEditorIFrame() {

            nhn.husky.EZCreator.createInIFrame({
                oAppRef : oEditors,
                elPlaceHolder : "content",
                sSkinURI : "/smarteditor/SmartEditor2Skin.html",
                fCreator : "createSEditor2",
                htParams : {
                    // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
                    bUseToolbar : true,
                    // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
                    bUseVerticalResizer : true,
                    // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
                    bUseModeChanger : true,
                }
            });
        }
        smartEditorIFrame();
    });
</script>

<script type="text/javascript">

    var html = '';
   html += '<input class="upload-name" value="첨부파일" placeholder="첨부파일"> ';
   html += '<label for="file" style="margin-bottom: 0px;">파일찾기</label>';
   html += '<input type="file" name="file" id="file">';

    function fnList(){
        // 동적으로 생성된 폼 데이터
        let formData = {
            'pageIndex': 1,
        };
        // 동적으로 설정될 옵션
        let options = {
            'formName': 'fnList',
            'url': '/sample/boardList.do',  // 실제 서버로 데이터를 보낼 URL을 입력하세요.
        };
        // 동적으로 생성된 폼 데이터를 받아와서 submit
        fnDynamicFormData(options, formData);


    }

    function fnDel(id , boardId){

        if(confirm("파일을 삭제 하시겠습니까?")){

            // AJAX 요청을 수행하는 함수 생성
            const dynamicAjax = fnCreateDynamicAjax.init();

            const dynamicOptions = {
                url: '/samepl/ajaxFileDelete.do',
                method: 'POST',
                data: {id : id , boardId : boardId},
                contentType:"application/x-www-form-urlencoded; charset=UTF-8",
            };

            dynamicAjax(dynamicOptions, (response) => {
                console.log(response.msg);
                if(response.msg === 'SUCCESS'){
                    alert('삭제 되었습니다.');
                    $('.filebox').html('');
                    $('.filebox').html(html);
                }else{
                    alert('파일 삭제 중 오류가 발생하였습니다.');
                }
            });

            /*
             // 콜백 함수 정의
            const myCallback = (response) => {
                // 동적으로 설정된 옵션으로 AJAX 요청을 보냈을 때의 콜백 동작
                console.log(response);
            };
            dynamicAjax(dynamicOptions,myCallback);

            */
        }
    }

    function fnDelete(id){
        if(confirm("게시글을 삭제 하시겠습니까?")){

            // AJAX 요청을 수행하는 함수 생성
            const dynamicAjax = fnCreateDynamicAjax.init();

            const dynamicOptions = {
                url: '/samepl/ajaxBoardDelete.do',
                method: 'POST',
                data : {id : id},
                contentType:"application/x-www-form-urlencoded; charset=UTF-8",
            };

            const fnCallback = (response) => {
                // 동적으로 설정된 옵션으로 AJAX 요청을 보냈을 때의 콜백 동작
                if(response.msg === 'SUCCESS'){
                    alert('삭제 되었습니다.');
                }else{
                    alert('삭제 중 오류가 발생하였습니다.');
                }
                fnList();
            };
            dynamicAjax(dynamicOptions,fnCallback);

        }
    }


    $(function(){

        // 사용 예시:
        customRules = {
            title:  { required : true },
            content:{ required : true},
        };

        customMsgs = {
            title: {required:"제목을 입력해주세요."},
            content:{ required : "내용을 입력해주세요."},
        };

        customModule.updateDefaultRule($('#board'),customRules,customMsgs,null);

        $("#btnSave").click(function(){
            $("#board").submit();
        });

        $(document).on('change', '.filebox [id=file]', function(event) {
            let buttonId = event.target.id;
            $(this).siblings('.upload-name').val($(this).val());

        });


    });


</script>