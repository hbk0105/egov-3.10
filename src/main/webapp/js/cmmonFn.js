

// 동적으로 생성된 특정 id 클릭 이벤트 
$(document).on('click', '[id^=btn_]', function(event) {
  // 'btn_'로 시작하는 ID를 가진 버튼이 클릭되었을 때의 동작
  let buttonId = event.target.id;
  alert("Button with ID " + buttonId + " is clicked!");

});

// 콜백 함수
function fnCallBack(selector,event){
  console.log(selector);
  console.log(event);
}

// 태그 선택 클릭 이벤트
function fnCustomSelector(selector, callback) {
  let elements = document.querySelectorAll(selector);
  elements.forEach(function(element) {
    // 클릭 이벤트 리스너 추가
    element.addEventListener('click', function(event) {
      console.log(event.target);
      // 콜백 함수 실행
      if (callback) {
        callback(element, event);
      }
    });
  });
}

function fnUuidv4() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        let r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

function fnDynamicFormData(options, formData) {
    // 기본값 설정
    let defaultOptions = {
      'formName': 'defaultFormName',
      'url': 'your_default_url',
      'method': 'POST',
    };

    // options와 defaultOptions 병합
    //options = $.extend({}, defaultOptions, options);
    options = { ...defaultOptions, ...options };

    // 폼을 동적으로 생성
    let form = $('<form>').attr({
      'id': options.formName + '_form',
      'action': options.url,
      'method': options.method,
    });


    // 폼에 데이터 추가
    $.each(formData, function(key, value) {
      let input = $('<input>').attr({
        'type': 'hidden',
        'name': key,
        'value': value,
      });
      form.append(input); // 동적 폼
    });

    const token = $("meta[name='_csrf']").attr("content")

    let input = $('<input>').attr({
        'type': 'hidden',
        'name': '_csrf',
        'value': token,
    });
    form.append(input); // 동적 폼

    form.appendTo('body');

    // 폼을 문서에 추가하고 submit
    form.appendTo('body').submit();
}

// 동적으로 폼 데이터를 생성하고 submit하는 예제
function fnCreateAndSubmitForm() {

    // 동적으로 생성된 폼 데이터
    let formData = {
      'name': 'John Doe',
      'email': 'john@example.com',
      // 원하는 데이터 필드를 추가할 수 있습니다.
    };
    // 동적으로 설정될 옵션
    let options = {
      'formName': 'dynamicForm1',
      'url': 'your_submit_url',  // 실제 서버로 데이터를 보낼 URL을 입력하세요.
      'method': 'POST',  // 데이터 전송 방식 (POST 또는 GET)
    };

    // 동적으로 생성된 폼 데이터를 받아와서 submit
    fnDynamicFormData(options, formData);
}

/* 사용법 - fnFormDataToJson($('#formId'))*/
function fnFormDataToJson(formNm) {
    let formData = $(formNm).serializeArray();
    let jsonObject = {};

    $.each(formData, function() {
        jsonObject[this.name] = this.value;
    });

    return JSON.stringify(jsonObject);
}



(function() {
    window.fnCreateDynamicAjax = {
        init : (defaultOptions = {
                url: '/',
                method: 'GET',
                dataType: 'json',  // 기본값으로 'json' 설정 [서버에서 어떤 타입을 받을 것인지를 의미]
                processData: true,  // 기본값으로 true 설정
                contentType: 'application/json',  // 기본값으로 'application/json' 설정 [ 서버로 데이터를 보낼때에 어떤타입으로 보낼것인지 ]
            }) => {
                let isRequesting = false; // 요청 중 여부를 추적하는 변수
                let cachedData = null;

                const makeRequest = (options, callback) => {

                    // 만약 이미 요청 중이라면 새로운 요청을 막음
                    if (isRequesting) {
                        console.log('이미 요청 중입니다.');
                        return;
                    }

                    // 요청 중 플래그를 설정
                    isRequesting = true;

                    const mergedOptions = { ...defaultOptions, ...options };

                    if (cachedData) {
                        callback(cachedData);
                        return;
                    }

                    $.ajax({
                        url: mergedOptions.url,
                        method: mergedOptions.method,
                        data: mergedOptions.data,
                        dataType: mergedOptions.dataType,
                        processData: mergedOptions.processData,  // 기본값 사용
                        contentType: mergedOptions.contentType,  // 기본값 사용
                        //추가해야 하는 부분
                        beforeSend: function(xhr) {

                            const token = $("meta[name='_csrf']").attr("content")
                            const header = $("meta[name='_csrf_header']").attr("content");

                            // header와 token 값이 not null일 때만 설정
                            if (header !== null && token !== null) {
                                xhr.setRequestHeader(header, token);
                            }
                        },

                        success: (response) => {
                            cachedData = response;
                            callback(cachedData);
                        },
                        error: (request, status, error) => {
                            console.error("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);

                            // HTTP 상태 코드에 따른 처리 (필요에 따라 추가적인 로직 구현 가능)
                            if (request.status === 404) {
                                console.log('페이지를 찾을 수 없습니다.');
                            } else if (request.status === 500) {
                                console.log('서버 내부 오류가 발생하였습니다.');
                            } else {
                                console.log('오류가 발생하였습니다.');
                            }
                            callback(null);
                        }
                    });
                };
                return makeRequest;
        },
    }
    fnCreateDynamicAjax.init();
})();



    /* ajax 사용법

       // 콜백 함수 정의
        const myCallback = (response) => {
            // 동적으로 설정된 옵션으로 AJAX 요청을 보냈을 때의 콜백 동작
            console.log(response);
        };

        // AJAX 요청을 수행하는 함수 생성
        const dynamicAjax = fnCreateDynamicAjax();

        const dynamicOptions = {
            url: '/uploadDynamicFiles.do',
            method: 'POST',
            data: new FormData(document.getElementById('frm')),
            processData: false,  // 동적으로 설정 가능
            contentType:false,
        };

        dynamicAjax(dynamicOptions,myCallback);
    */

function fnFileDownLoad(fileId,boardId){
    // 동적으로 생성된 폼 데이터
    let formData = {
        'id': fileId,
        'boardId': boardId,
        // 원하는 데이터 필드를 추가할 수 있습니다.
    };
    // 동적으로 설정될 옵션
    let options = {
        'formName': 'fileDown',
        'url': '/samepl/fileDownLoad.do',  // 실제 서버로 데이터를 보낼 URL을 입력하세요.
        'method': 'POST',  // 데이터 전송 방식 (POST 또는 GET)
    };

    // 동적으로 생성된 폼 데이터를 받아와서 submit
    fnDynamicFormData(options, formData);

}

/**
 * 참고 JS / JQUERY
 *

 동적 클릭함수
 https://myhappyman.tistory.com/123
 https://brunch.co.kr/@ourlove/98


 ######### 자주 사용하는 함수 #########

 https://gangzzang.tistory.com/entry/%EC%A0%9C%EC%9D%B4%EC%BF%BC%EB%A6%ACjQuery-%EC%84%A0%ED%83%9D%EC%9E%90

 https://mjn5027.tistory.com/84

 https://velog.io/@jay_kimjh/%EC%A0%9C%EC%9D%B4%EC%BF%BC%EB%A6%AC-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%ACjQuery-Library

 $("태그명") --- 태그명으로 요소 선택
 $("#아이디명") --- 아이디명으로 요소 선택
 $(".클래스명") --- 클래스명으로 요소 선택
 ("태그명1 태그명2") --- 태그명1의 하위 요소 중 태그명2의 요소들 선택
 ("태그명1>태그명2") --- 태그명1의 자식 요소 중 태그명2의 요소들 선택
 $("선택 요소").parent() --- 선택 요소를 기준으로 부모 요소를 선택
 $("선택 요소").children("태그명") --- 선택 요소를 기준으로 자식 요소들 선택
 $("선택 요소").prev() --- 선택 요소들 기준으로 형제 요소 중 이전 요소 선택
 $("선택 요소").next() --- 선택 요소를 기준으로 형제 요소 둥 다음 요소 선택
 $("선택 요소").siblings("태그명") --- 선택 요소를 기준으로 형제 요소들 모두 선택
 $(":종류"); --- 폼의 input 요소 중 종류(type)가 일치하는 요소들 선택


 .parent()는 해당 요소의 바로 위에 존재하는 하나의 부모 요소를 반환
 .parents()  모든 부모 요소를 반환
 .children()은 어떤 요소의 자식 요소를 반환
 .find()는 어떤 요소의 하위 요소 중 특정 반환
 .attr() attribute 값이 모두 String 으로 넘어옴
 .prop() 자바스크립트의 프로퍼티 값이 넘어오기 때문에 boolean, date, function 등도 가져올 수 있음
 .siblings()	선택한 요소의 형제(sibling) 요소 중에서 지정한 선택자에 해당하는 요소를 모두 선택한다.

 .length()	요소의 개수를 알 수 있음.
 .trigger( event ) event jQuery.Event 객체. - ex) .trigger('click');
 .addClass('red')  클래스 추가
 .removeClass('test') 클래스 삭제

 .next()	선택한 요소의 바로 다음에 위치한 형제 요소를 선택한다.
 .nextAll()	선택한 요소의 다음에 위치한 형제 요소를 모두 선택한다.
 .nextUntil()	선택한 요소의 형제 요소 중에서 지정한 선택자에 해당하는 요소 바로 이전까지의 요소를 모두 선택한다.
 .prev()	선택한 요소의 바로 이전에 위치한 형제 요소를 선택한다.
 .prevAll()	선택한 요소의 이전에 위치한 형제 요소를 모두 선택한다.
 .prevUntil()	선택한 요소의 형제 요소 중에서 지정한 선택자에 해당하는 요소 바로 다음까지의 요소를 모두 선택한다.

 .setInterval(함수명,주기) 일정시간 마다 함수가 실행되도록 처리
 .clearInterval(함수명) setInterval로 설정한 작업을 취소


 $(선택자).each(function(index) {

    // this 키워드를 통해 보통 사용
    // ex) $(this).val();

});



 ######### 자주 사용하는 선택자  #########

 요소[속성=값]  속성과 값이 같은 문서 객체를 선택
 요소[속성|=값] 속성 안의 값이 특정 값과 같은 문서 객체를 선택
 요소[속성~=값] 속성 안의 값이 특정 값을 단어로 시작하는 문서 객체를 선택
 요소[속성^=값] 속성 안의 값이 특정 값으로 시작하는 문서 객체를 선택
 요소[속성$=값]  속성 안의 값이 특정 값으로 끝나는 문서 객체를 선택
 요소[속성*=값]  속성 안의 값이 특정 값을 포함하는 문서 객체를 선택


 요소:button input 태그 중 type 속성이 button인 문서 객체와 button 태그를 선택
 요소:checkbox input 태그 중 type 속성이 checkbox인 문서 객체를 선택
 요소:file input 태그 중 type 속성이 file인 문서 객체를 선택
 요소:image input 태그 중 type 속성이 image인 문서 객체를 선택
 요소:password input 태그 중 type 속성이 password인 문서 객체를 선택
 요소:radio input 태그 중 type 속성이 radio인 문서 객체를 선택
 요소:reset input 태그 중 type 속성이 reset인 문서 객체를 선택
 요소:submit nput 태그 중 type 속성이 submit인 문서 객체를 선택
 요소:text input 태그 중 type 속성이 text인 문서 객체를 선택

 요소:checked 체크되어 있는 입력 양식을 선택
 요소:disabled 비활성화된 입력 양식을 선택
 요소:enabled 활성화된 입력 양식을 선택
 요소:focus 초점이 맞추어져 있는 입력 양식을 선택
 요소:input 모든 입력 양식을 선택
 요소:selected  option 객체 중 선택된 태그를 선택


 */