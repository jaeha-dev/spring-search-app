let hostPage = {
    init : function () {
        let _this = this;
        $('#btnRefresh').on('click', function () {
            _this.refresh();
        });
    },

    refresh: function () {
        window.location.replace('/keywords/host');
    },
};