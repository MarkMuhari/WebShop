import { Injectable } from '@angular/core';
import { CartItem } from '../common/cart-item';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  cartItem: CartItem[] = [];

  totalPrice: Subject<number> = new Subject<number>();

  totalQuantity: Subject<number> = new Subject<number>();

  constructor() { }

  addToCart(theCartItem: CartItem) {
    // check if we already have the item in our cart
    let alreadyExistsInCart: boolean = false;
    let existingCartItem: CartItem = this.cartItem[0];

    if (this.cartItem.length > 0) {
      // find the item in the cart based on item idű
      for (let tempCartItem of this.cartItem) {
        if (tempCartItem.id === theCartItem.id) {
          existingCartItem = tempCartItem;
          alreadyExistsInCart = true;
          break;
        }

      }
      // check if we found it
      alreadyExistsInCart = (existingCartItem != undefined);

      if (alreadyExistsInCart) {
        // increment the quantity
        existingCartItem.quantity++;
      } else {
        this.cartItem.push(theCartItem);
      }
      // compute cart total price and total quantity
      this.computeCartTotald();
    }

  }
  computeCartTotald() {
    let totalPriceValue: number = 0;
    let totalQuantityValue: number = 0;

    for (let currentCartItem of this.cartItem) {
      totalPriceValue += currentCartItem.quantity * currentCartItem.unitPrice;
      totalQuantityValue += currentCartItem.quantity;
    }

    // publish the new values
    this.totalPrice.next(totalPriceValue);
    this.totalQuantity.next(totalQuantityValue);

    // log cart data just for debugging purposes
    this.logCartData(totalPriceValue, totalQuantityValue);
  }


  logCartData(totalPriceValue: number, totalQuantityValue: number) {
    console.log('Content of the cart');
    for (const tempCartItem of this.cartItem) {
      const subTotalPrice = tempCartItem.quantity * tempCartItem.unitPrice;
      console.log(`nane: ${tempCartItem.name}, quantity=${tempCartItem.quantity}, unitPrice=${tempCartItem.unitPrice}, subTotalPrice=${totalPriceValue}`);
    }

    console.log(`totalPrice: ${totalPriceValue.toFixed(2)}, totalQuantity: ${totalQuantityValue}`);
    console.log('------------');
  }

}
