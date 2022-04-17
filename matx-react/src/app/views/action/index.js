import localStorageService from 'app/services/localStorageService'
import { useNavigate } from 'react-router-dom'


export const hasProductInList = (productId,colorId,sizeId,product) => {
    if(product.productId == productId && product.colorId == colorId && product.sizeId == sizeId){
        return true
    } else {
        return false
    }
}

export const addToCart = (product,size,color) => {
    let listCart = localStorageService.getItem('listCart')
    let check = true
    if(!listCart){
        listCart = []
    }
    const cartItem = {
        productId: product.id,
        colorId: color.id,
        sizeId: size.id,
        productName: product.name,
        colorCode: color.code,
        sizeName: size.name,
        avatar: product.avatar,
        seo: product.seo,
        price: product.price,
        quantity: 1
    }
    listCart.forEach(i=>{
        if(hasProductInList(product.id, color.id, size.id, i)){
            i.quantity++
            check = false
        }
    })
    if(check){
        listCart.push(cartItem)
    }
    localStorageService.setItem('listCart', listCart)
}

export const createListCart = (listCart) => {
    localStorageService.setItem('listCart', listCart)
}

export const showError = (status) => {
    switch (status) {
        case 403:
            return "/login"
    }
}

export { default as Notify } from './Notify'
export { default as AlertDialog} from './Confirm'