let searchPage = {
    init : function () {
        let _this = this;
        $('#btnRefresh').on('click', function () {
            _this.refresh();
        });
    },

    refresh: function () {
        window.location.replace('/keywords/search');
    },

    openLink: function (url) {
        window.open(url,'_blank');
    }
};