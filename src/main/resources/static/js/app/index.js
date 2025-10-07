var main = {
    init : function () {
        var _this = this;

        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function() {
            _this.update();
        });
    },
    save : function () {
        var data = {
        title: $('#title').val(),
        author: $('#author').val(),
        content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function() {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };
        var id = $('#id').val();
        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();

//게시글 등록 화면에 "등록" 버튼 기능
//해당 API를 호출하는 js 작성

//index라는 변수의 속성으로 function을 추가한 이유
// · 브라우저의 스코프(scope)는 "공용 공간"으로 사용되므로, 나중에 로딩된 js의 init, save가 먼저 로딩된 js의 function을 덮어 씀
// · 여러 사람이 참여하는 프로젝트에서는 중복된 함수 이름이 자주 발생 가능, index.js만의 유효범위를 만들어 사용하고자 한 것
// · index 객체 안에서만 function이 유효하므로 다른 js와 겹칠 위험이 사라짐
// · (추가) 이런 식의 프론트엔드의 의존성 관리, 스코프 관리 등 문제로 Es6를 비롯, 최신 js나 Angular, React, Vue는 이미 지원

//window.location.href = '/'
// · 글 등록이 성공하면 메인페이지(/)로 이동
//$('$btn-update').on('click)
// · btn-update란 id를 가진 HTML 엘리먼트에 click 이벤트가 발생하면 update function을 실행하도록 이벤트 등록
//update : function()
// · 신규로 추가될 update function
//type:'PUT'
// · 여러 HTTP 메소드 중 PUT 메소드
// · PostsApiController의 API에서 이미 @PutMapping으로 선언했으므로 PUT 사용 (∵REST 규약)
// · POST:CREATE, READ:GET, UPDATE:PUT, DELETE:DELETE
//url:'/api/v1/posts/'+id
// · 어느 게시글을 수정할지 URL Path로 구분하고자, Path에 id를 추가