    // Decrease
       $('.quantity_inner .bt_minus').click(function() {
       let $input = $(this).parent().find('.quantity');
       let $id = $(this).parent().data('product-id');
       console.log("Current:" + parseInt($input.val()))
       //console.log($(this).parent().data('product-id'));
         if (parseInt($input.val()) === 1) {
           Swal.fire({
             title: 'Do you really want to remove this item from your cart?',
             text: "You won't be able to revert this!",
             icon: 'warning',
             showCancelButton: true,
             confirmButtonColor: '#3085d6',
             cancelButtonColor: '#d33',
             confirmButtonText: 'Yes, delete it!'
           }).then((result) => {
             if (result.isConfirmed) {
               Swal.fire(
                   'Removed!',
                   'Product has been removed.',
                   'success'
               );
               // Do something after "Confirm" button clicked

               let url = "/api/cart/removeItem/" + $id;
               fetch(url)
               .then((response) => {
                 return response.text();
               })
               .then((data) => {
                 console.log(data);
                 $(this).parent().parent().parent().hide();
                 document.getElementById("totalPrice").textContent = data;
               });

             }
           })
         }
       let count = parseInt($input.val()) - 1;
       count = count < 1 ? 1 : count;
       $input.val(count);

        decreaseProductInCart($id);
   });
   // Increase
   $('.quantity_inner .bt_plus').click(function() {
       let $input = $(this).parent().find('.quantity');
       let $id = $(this).parent().data('product-id');
       let count = parseInt($input.val()) + 1;
       count = count > parseInt($input.data('max-count')) ? parseInt($input.data('max-count')) : count;
       $input.val(parseInt(count));
     //console.log($(this).parent().parent().parent().hide()); - get tr
        increaseProductInCart($id);
   });
   // Delete all that isn't needed
   $('.quantity_inner .quantity').bind("change keyup input click", function() {
       console.log(this.value);
       if (this.value.match(/[^0-9]/g)) {
           this.value = this.value.replace(/[^0-9]/g, '');
       }
       if (this.value === "") {
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
              let url = "/api/cart/add/" + id;
              fetch(url)
              .then((response) => {
                return response.text();
              })
              .then((data) => {
                console.log(data);
              });
          }

   function increaseProductInCart(id) {
        let url = "/api/cart/increaseItem/" + id;
             fetch(url)
             .then((response) => {
                return response.text();
             })
             .then((data) => {
                document.getElementById("totalPrice").textContent = data;
             });
   }

    function decreaseProductInCart(id) {
      let url = "/api/cart/decreaseItem/" + id;
      fetch(url)
      .then((response) => {
        return response.text();
      })
      .then((data) => {
        document.getElementById("totalPrice").textContent = data;
      });
    }



