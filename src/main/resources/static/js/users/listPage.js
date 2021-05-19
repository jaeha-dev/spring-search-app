let listPage = {
    init : function () {
        let _this = this;
        $('#btnRefresh').on('click', function () {
            _this.refresh();
        });
    },

    errorMessage : function () {
        alert('서버 오류가 발생하였습니다.');
    },

    refresh: function () {
        window.location.replace('/admin/users');
    },

    removeUser : function (id, username) {
        if (confirm(`${id}(${username}) 계정을 삭제하시겠습니까?`)) {
            $.ajax({
                type: 'post',
                url: `/admin/users/${id}`,
            }).done(function (result) {
                console.log(result)
                if (result) {
                    alert('계정이 삭제되었습니다.');
                    window.location.href='/admin/users';
                } else {
                    alert('계정을 삭제할 수 없습니다.');
                }
            }).fail(function (error) {
                listPage.errorMessage();
            })
        } else {
            return false;
        }
    }
}