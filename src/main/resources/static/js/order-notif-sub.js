//after all page load
$( document ).ready(function() {
    //stompClient.debug = null;
    connect();

});

function updateProgress(status){

    if(status === "ACCEPTED"){

        $("#ordered").removeClass("current");
        $("#accepted").addClass("current");

    }else if(status === "DELIVERED"){

        $("#ordered").addClass("prev");
        $("#accepted").removeClass("current");
        $("#delivered").addClass("current");
        //add timer and redirect after 2 minutes and make endpoint not accessible

    }else if(status === "DECLINED") {
        //
        alert("YOUR ORDER WAS DECLINED");
    }
}

function connect() {
    var socket = new SockJS('/secured/order-progress-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe(`/user/${refId}/order/progress`, function (data) {
            var audio = new Audio('/audio/notif-restaurant.mp3');
            audio.play();
            console.log("DATA : ",data);
            updateProgress(data.body);
        });
    });
}
