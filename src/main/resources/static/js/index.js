    // Decrease
       $('.quantity_inner .bt_minus').click(function() {
       let $input = $(this).parent().find('.quantity');
       let $id = $(this).parent().data('product-id');

       console.log($(this).parent().data('product-id'));
       let count = parseInt($input.val()) - 1;
       count = count < 1 ? 1 : count;
       $input.val(count);


   });
   // Increase
   $('.quantity_inner .bt_plus').click(function() {
       let $input = $(this).parent().find('.quantity');
       let $id = $(this).parent().data('product-id');
       let count = parseInt($input.val()) + 1;
       count = count > parseInt($input.data('max-count')) ? parseInt($input.data('max-count')) : count;
       $input.val(parseInt(count));

        increaseProductInCart($id);
   });
   // Delete all that isn't needed
   $('.quantity_inner .quantity').bind("change keyup input click", function() {
       if (this.value.match(/[^0-9]/g)) {
           this.value = this.value.replace(/[^0-9]/g, '');
       }
       if (this.value == "") {
           this.value = 1;
       }
       if (this.value > parseInt($(this).data('max-count'))) {
           this.value = parseInt($(this).data('max-count'));
       }
   });


// -------------------------------------------------------------------------------------
function getRequestHello() {
          fetch('/api/hello')
            .then((response) => {
              return response.text();
            })
            .then((data) => {
              console.log(data);
            });
}

   function addProductToCart(id) {
              var url = "/api/cart/add/" + id;
              fetch(url)
              .then((response) => {
                return response.text();
              })
              .then((data) => {
                console.log(data);
              });
          }

   function increaseProductInCart(id) {
        var url = "/api/cart/increaseItem/" + id;
             fetch(url)
             .then((response) => {
                return response.text();
             })
             .then((data) => {
                console.log(data);
                document.getElementById("totalPrice").textContent = data;
             });
   }