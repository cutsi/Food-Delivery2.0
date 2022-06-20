
$( document ).ready(function() {//ucitana

    connect();
    $("#notif-bell").on("click",function (){
        if($(this).hasClass("notif-bell-on")){
            $("#notif-bell").removeClass("notif-bell-on");
            appendOrders();
            orders = [];
        }

    });

});

var stompClient = null;
var orders = [];

function appendOrders() {
    let orderHtml = ``;
    orders.forEach((o,index)=>{

        orderHtml += `
       
                            <tr class="order-${o.id} NotAcceptedOrder">
                                        <td class="bill-id-cell-new-orders">
                                        #${o.id}
                                        </td>
                                        <td class="address-cell">
                                        ${o.createdAt}
                                        </td>
                                    </tr>
                                    <tr class="order-${o.id} NotAcceptedOrder">
                                        <td colspan=2 class="details-cell">
                                            <div class="details-cell">
                                                <b>Adresa</b>: ${o.address}
                                            </div>

                                            <div class="details-cell">
                                                <b>Ime:</b> ${o.customer.name}
                                            </div>

                                            <div class="details-cell">
                                                <b>Tel:</b> ${o.phone}
                                            </div>

                                            <div class="details-cell">
                                                <b>Napomena restoranu:</b> ${o.restaurantNote}
                                            </div>

                                            <div class="details-cell">
                                                <b>Napomena dostavljacu:</b> ${o.deliveryNote}
                                            </div>
                                            <div class="">
                                                <div class="details-btn-form">
                                                    <input
                                                            data-order-id="${o.id}"
                                                            onclick="showDetails(this.getAttribute('data-order-id'),1)"
                                                            class="details-btn button-content-cell" type="submit" value="Detalji" />
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
       
       `;

        $(".left-side .bill-table").prepend(orderHtml);
        orderHtml = ``;
        g_orders.push(o);

    });
}

function notif_bell_animate(){
    if(!$("#notif-bell").hasClass("notif-bell-on"))
        $("#notif-bell").addClass("notif-bell-on");
}

function connect() {
    var socket = new SockJS('/secured/notif-websocket');
    stompClient = Stomp.over(socket);//stomp protocol
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe(`/user/${notifRef}${restaurant_id}/restaurant-notifications/new-order`, function (data) {//restaurant queue
            var audio = new Audio('/audio/notif-restaurant.mp3');
            audio.play();
            notif_bell_animate();
            orders.push(JSON.parse(data.body));
            //appendOrders(JSON.parse(data.body));
        });
    });
}
