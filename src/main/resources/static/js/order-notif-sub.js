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

    }else if(status.split("=")[0] === "DECLINED") {
        //
        alert(status.split("=")[1]);
        window.location.href =  window.location.protocol + "//" + window.location.host + "/home";
    }
}

function connect() {
    var socket = new SockJS('/secured/order-progress-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {// if server lets us in this func is executed
        console.log('Connected: ' + frame);
        stompClient.subscribe(`/user/${refId}/order/progress`, function (data) {
            var audio = new Audio('/audio/notif-restaurant.mp3');
            audio.play();
            console.log("DATA : ",data);
            updateProgress(data.body);
        });
    });
}
