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