class OrderItem {
    constructor(product) {
        this.productId = product.id;
        this.productTitle = product.title;
        this.quantity = 1;
        this.pricePerProduct = product.price;
        this.price = this.quantity * this.pricePerProduct;
    }

    increment() {
        this.quantity++;
        this.price = this.quantity * this.pricePerProduct;
    }

    decrement() {
        this.quantity--;
        this.price = this.quantity * this.pricePerProduct;
    }
}