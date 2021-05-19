let joinPage = {
    init : function () {
        let _this = this;
    },

    errorMessage : function () {
        alert('서버 오류가 발생하였습니다.');
    },

    // 계정 ID 유효성 검사
    usernameCheck : function () {
        const regexp = /^[a-z]{2,10}$/;
        let username = $('#username').val();

        if (regexp.test(username)) {
            $.ajax({
                type: 'post',
                url: `/users/exists/${username}`,
            }).done(function (result) {
                if (! result) {
                    $('#usernameTooltip').html("사용할 수 있는 ID입니다.").css('color', '#4e73df');
                } else {
                    $('#usernameTooltip').html("이미 등록된 ID입니다.").css('color', '#e83e8c');
                }
            }).fail(function (error) {
                joinPage.errorMessage();
            })
        } else {
            $('#usernameTooltip').html("2 ~ 10자의 영문 소문자로 입력해주세요.").css('color', '#e83e8c');
        }
        $('#usernameValidation').hide();
    },

    // 계정 비밀번호 유효성 검사
    passwordCheck : function () {
        const regexp = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,10}$/;
        let password = $('#password').val();

        if (regexp.test(password)) {
            const source = password.split(''); // 계정 비밀번호 배열
            const target = new Set(source); // 계정 비밀번호 세트 (크기가 다를 경우, 중복 문자 존재)
            if (source.length === target.size) {
                $('#passwordTooltip').html("사용할 수 있는 비밀번호입니다.").css('color', '#4e73df');
            } else {
                $('#passwordTooltip').html("계정 비밀번호에 동일한 문자를 사용할 수 없습니다.").css('color', '#e83e8c');
            }
        } else {
            $('#passwordTooltip').html("8 ~ 10자의 영문, 숫자, 특수 문자를 포함하여 입력해주세요. (중복 문자 불가)").css('color', '#e83e8c');
        }
        $('#passwordValidation').hide();
    },

    // 사용자 이름 유효성 검사
    nameCheck : function () {
        const regexp = /^[가-힣]{2,5}$/;
        let name = $('#name').val();

        if (regexp.test(name)) {
            $('#nameTooltip').html("사용할 수 있는 이름입니다.").css('color', '#4e73df');
        } else {
            $('#nameTooltip').html("2 ~ 5자의 국문으로 입력해주세요.").css('color', '#e83e8c');
        }
        $('#nameValidation').hide();
    },
}