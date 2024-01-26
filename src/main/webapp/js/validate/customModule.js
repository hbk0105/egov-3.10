
  const customModule = (function() {

      // 영문 대소문자 8글자 이상 /^[a-zA-Z]{8,}$/
      // 한글 2자 이상 /^[가-힣]{2,10}$/K
      // ^[a-zA-Z가-힣]{2,9}$
      // ^[a-zA-Z][a-zA-Z0-9]{4,15}$

      let userIdRules = {required : true , regex : /^[a-zA-Z][a-zA-Z0-9]{4,15}$/};
      let userIdMsg = {required : '아이디를 입력해주세요.' , regex : '영문자로 시작하며, 숫자포함 4글자이상 16글자 미만으로 입력해주세요.'};


      let nameRules = { required : true, maxlength: 30, regex: /^[a-zA-Z]{8,}$/ };
      let nameMsg = { required:"이름을 입력해주세요.", maxlength: "입력 가능한 최대 글자수를 초과하였습니다.", regex:"이름 형식이 잘못되었습니다."};

      let passwordChRuls = { required : true, maxlength:16, regex : /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/};
      let passwordMsg = { required:"비밀번호를 입력해주세요.", regex : "비밀번호는 숫자+대소문자+특수문자 포함 8~16자 내로 입력해주세요."};

      let passwordVerifyRuls = { required : true, passwordCheck: "#passwordCh" };
      let passwordVerifyMsg = {  required: "비밀번호를 다시 한번 입력해주세요.", passwordCheck: "비밀번호가 일치하지 않습니다."};


      let phoneRules = { required : true, regex : "^(010|011)[-\\s]?\\d{3,4}[-\\s]?\\d{4}$" };
      let phoneMsg = { required:"휴대전화번호를 입력해주세요.", regex : "휴대전화번호 양식이 잘못되었습니다."};

      let emailRules = { required : true, regex : /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/};
      let emailMsg = { required:"이메일을 입력해주세요.", regex : "이메일 양식이 잘못되었습니다."};

      let isNullRules = { required : true };
      let isNullMsg = { required:"입력해주세요."};


      let agreeRules = { required : true };
      let agreeMsg = {  required:"필수 선택항목 입니다." };



      // 기본 formRules와 formMessages 설정
      let defaultFormRules = {
          userId:userIdRules,
          name:nameRules,
          password : passwordChRuls,
          passwordCh:passwordChRuls,
          passwordVerify:passwordVerifyRuls,
          phone:phoneRules,
          email:emailRules,
          isNull:isNullRules,
          agree:agreeRules,
         
      };


      let defaultFormMessages = {
          userId:userIdMsg,
          name:nameMsg,
          password:passwordMsg,
          passwordCh:passwordMsg,
          passwordVerify:passwordVerifyMsg,
          phone:phoneMsg,
          email:emailMsg,
          isNull:isNullMsg,
          agree:agreeMsg,
      };

      function fnRegexLoad(){
        $.validator.addMethod("regex", function(value, element, regexp) {
            let re = new RegExp(regexp);
            let res = re.test(value);
            // console.log(res, value, regexp, re);
            return res;
        });


        $.validator.addMethod("passwordCheck", function(value1, element, value2) {
            const targetValue = $(value2).val(); // 다른 필드의 값을 가져옵니다.
            return value1 === targetValue;
        }, "값이 일치하지 않습니다.");


        // 파일 확장자 및 크기 체크를 위한 사용자 정의 규칙 추가
        $.validator.addMethod("fileExtension", function(value, element, param) {
            param = typeof param === "string" ? param.replace(/,/g, '|') : "png|jpe?g|gif|pdf|zip"; // 기본 확장자 (png, jpg, jpeg, gif)
            return this.optional(element) || value.match(new RegExp(".(" + param + ")$", "i"));
        }, $.validator.format("올바른 파일 확장자가 아닙니다."));

        $.validator.addMethod("fileSize", function(value, element, param) {
            /* 
              5MB는 바이트 단위로 5,242,880 바이트입니다 (1MB = 1,048,576 바이트)
              10MB 10485760
            */
            return this.optional(element) || (element.files[0].size <= param);  // param은 KB 단위로 크기를 나타냅니다.
        },  $.validator.format("파일 크기가 너무 큽니다. 최대 크기: 5MB")); 
        /*$.validator.format("파일 크기가 너무 큽니다. 최대 크기: {0}KB"));*/


      }

      // https://jsfiddle.net/vickyshinde/58n7jmgx/
    function showErrors(errorMap, errorList) {
      // Clean up any tooltips for valid elements
      $.each(this.validElements(), function (index, element) {
          let $element = $(element);
          $element.removeData("title") // Clear the title - there is no error associated anymore
              .tooltip("destroy");

          $element.closest('.form-group').removeClass('has-error').addClass('has-success');
      });

      // Create new tooltips for invalid elements
      $.each(errorList, function (index, error) {
          let $element = $(error.element);
          if(!$element.data('title')){
              $element.data("title", error.message)
                  .tooltip({trigger: 'manual'}) // Create a new tooltip based on the error messsage we just set in the title
                  .tooltip('show'); // tooltip show
          }else if($element.data('title') != error.message){
              $element.attr('data-original-title', error.message).data("title", error.message) // change title
                  .tooltip('show');
          }

          $element.closest('.form-group').removeClass('has-success').addClass('has-error');
      });
    }

    function errorPlacement(error, element) {

          offset = element.offset();
          error.insertBefore(element)
          error.addClass('message');  // add a class to the wrapper
          error.css('color', 'red');
          error.appendTo(element.parent());


    }

    function addAttachmentRulesAndMsg(names){
      let resultRules = {};
      let resultMessages = {};

      names.forEach(name => {
          resultRules[name] = {
              required: true,
              fileExtension: "png,jpg,jpeg,gif,pdf|zip",
              fileSize: 5242880 /*5MB*/
          };

          resultMessages[name] = {
              required: "첨부파일을 업로드해주세요.",
              fileExtension: "올바른 파일 확장자가 아닙니다.",
              fileSize: "파일 크기가 너무 큽니다. 최대 크기: {0}KB"
          };
      });

      return {
          rules: resultRules,
          messages: resultMessages
      };
    }

        // 기본 submitHandler로 사용될 콜백 함수
    function defaultSubmitHandler(form) {
        // 폼이 제출될 때 기본적으로 수행되어야 할 로직을 여기에 작성
        //form.submit(); // 폼 제출
        form.submit();
    }


      /* 기본에 신규 룰+메시지 추가 init 함수로 대체 */
    function updateDefaultRule(formSelector = "#frm_user_info",newRules,newMsgs,submitCallback) {
        //const updatedRules = { ...defaultFormRules, ...newRules };
        //const updatedMags = { ...defaultFormMessages, ...newMsgs };
        fnRegexLoad();
        $(formSelector).validate({
            rules: { ...defaultFormRules, ...newRules },
            messages: { ...defaultFormMessages, ...newMsgs },
            //showErrors : showErrors,
            errorElement: "div",
            wrapper: "div",  // a wrapper around the error message
            errorPlacement: errorPlacement,
            submitHandler : function(form) {
                if (submitCallback) {
                    submitCallback(form);
                } else {
                    defaultSubmitHandler(form);
                }
            }
        });
    }


      function updateUserRule(formSelector = "#frm_user_info",customRules = {},customMsgs={},newRules = {},newMsgs={}, additionalFields = [],submitCallback) {
      // 커스텀 규칙과 신규 규칙을 병합(defaultFormRules의 값을 customRules통해 커스텀하고,newRules을 통해 신규 룰 적용  )
        //const mergedRules = { ...defaultFormRules, ...customRules, ...newRules };
        //const mergedMsgs = { ...defaultFormMessages, ...customMsgs, ...newMsgs };

          fnRegexLoad();
          const attach = addAttachmentRulesAndMsg(additionalFields);


          // 지정된 폼에 대해 유효성 검사를 다시 초기화
          $(formSelector).validate({
              rules: { ...defaultFormRules, ...customRules, ...newRules, ...attach.rules },
              messages: { ...defaultFormMessages, ...customMsgs, ...newMsgs, ...attach.messages },
              //showErrors : showErrors,
              errorElement: "div",
              wrapper: "div",  // a wrapper around the error message
              errorPlacement: errorPlacement,
              submitHandler : function(form) {
                if (submitCallback) {
                    submitCallback(form);
                } else {
                    defaultSubmitHandler(form);
                }
            }

          });


          /*
        // 사용 예시:
        const customRules = {
            user_name: { required: true, minlength: 3 }
        };

        const newRules = {
            new_field: { required: true, minlength: 3 }
        };

        jQueryValidateModule.updateUserRule(customRules, newRules);

          */
      }

      function init(formSelector = "#frm_user_info",customRules = {}, customMessages = {},submitCallback) {
          fnRegexLoad();
          $(formSelector).validate({
              rules: { ...defaultFormRules, ...customRules },
              messages: { ...defaultFormMessages, ...customMessages },
              //showErrors : showErrors,
              errorElement: "div",
              wrapper: "div",  // a wrapper around the error message
              errorPlacement: errorPlacement,
              submitHandler : function(form) {
                if (submitCallback) {
                    submitCallback(form);
                } else {
                    defaultSubmitHandler(form);
                }
            }
          });
      }

      // 클로저 내부에서 공개할 API
      return {
          init: init,
          updateDefaultRule: updateDefaultRule,
          updateUserRule: updateUserRule,
          /*getDefaultFormRules: function() {
            return defaultFormRules;
        }, getDefaultFormMessages : function(){
            return defaultFormMessages;
        },*/
      };
  })();

  /*
    // 기본적인 유효성 검사 규칙 및 메시지 정의
    const customRules = {
        username: {
            required: true,
            minlength: 3
        },
        password: {
            required: true,
            minlength: 6
        }
    };

    const customMessages = {
        username: {
            required: "사용자 이름을 입력해주세요.",
            minlength: "사용자 이름은 최소 3자 이상이어야 합니다."
        },
        password: {
            required: "비밀번호를 입력해주세요.",
            minlength: "비밀번호는 최소 6자 이상이어야 합니다."
        }
    };

    // validateModule을 사용하여 유효성 검사 초기화
    customModule.init("#myForm", customRules, customMessages);


    
    $(function(){
      
      customModule.init();

      
      $("#btn_save").click(function(){
          $("#frm_user_info").submit();
      });
      // 실행할 기능을 정의해주세요.
    });

  */