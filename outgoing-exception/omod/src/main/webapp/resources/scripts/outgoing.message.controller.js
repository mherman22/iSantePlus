jQuery(document).ready(function() {
    var messageType = window.location.pathname.substring(
        window.location.pathname.lastIndexOf('/') + 1,
        window.location.pathname.lastIndexOf(".")
    );
    $("#jsGrid").jsGrid({
        height: "auto",
        width: "100%",

        sorting: true,
        paging: true,
        pageLoading: true,
        autoload: true,
        pageSize: 10,
        pageButtonCount: 5,
        pageIndex: getPageIndex(),

        controller: {
            loadData: function (filter) {
                var d = $.Deferred();

                jQuery.ajax({
                    url: "/openmrs/ws/rest/isanteplus/messages?type=" + messageType,
                    type: "GET",
                    dataType: "json",
                    data: filter
                }).done(function (response) {
                    d.resolve(response);
                });

                return d.promise();
            }
        },
        fields: [
            {name: 'id', type: "number", visible: false},
            {title: titles[0], name: "timestamp", type: "text", sorting: true, filtering: true, width: "50"},
            {title: titles[1], name: "destination" , type: "text", sorting: true, filtering: true},
            {title: titles[2], name: "user.name" , type: "text", textField: "uuid", filtering: true, width: "30"},
            {
                title: titles[3], name: "failure" , type: "text", sorting: true, filtering: true, align: "center", width: "50",
                itemTemplate: function(failure) {
                    var result;
                    if (failure === false) {
                        result = $("<div>").prepend('<img id="successImage" src="/openmrs/ms/uiframework/resource/outgoing-message-exceptions/images/icons8-Ok-48.png" />');
                    } else {
                        result = $("<div>").prepend('<img id="failureImage" src="/openmrs/ms/uiframework/resource/outgoing-message-exceptions/images/icons8-Cancel-48.png" />');
                    }
                    return result;
                }
            }
        ],
        rowClick: function(args) {
            $("#jsGrid").jsGrid("fieldOption", "id", "visible", true);
            var $row = this.rowByItem(args.item);
            var messageId = $row.children().first().text();
            var pageIndex = $("#jsGrid").jsGrid("option", "pageIndex");
            window.location.href="details.page?messageId=" + messageId + "&backPage=" + messageType + "&backPageIndex=" + pageIndex;
            $("#jsGrid").jsGrid("fieldOption", "id", "visible", false);
        }

    });
});

function getPageIndex(){
    var url = new URL(window.location.href);
    var param = url.searchParams.get("pageIndex");
	if (param == null) {
		return 1;
	}
	return parseInt(param);
}