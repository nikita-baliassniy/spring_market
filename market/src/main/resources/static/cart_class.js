class Cart {
    constructor() {
        this.items = [];
        this.totalPrice = 0;
    }

    clear() {
        this.items = [];
        this.totalPrice = 0;
    }

    add(product) {
        for (let i = 0; i < this.items.length; i++) {
            let oi = this.items[i];
            if (oi.productId === product.id) {
                oi.increment();
                this.recalculate();
                return;
            }
        }
        this.items.push(new OrderItem(product));
        this.recalculate();
    }

    recalculate() {
        let cartPrice = 0;
        for (let i = 0; i < this.items.length; i++) {
            let oi = this.items[i];
            cartPrice += oi.price;
        }
        this.totalPrice = cartPrice;
    }
}