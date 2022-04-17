import http from "./http-common";
const URL_ADMIN = "/admin/products";
const URL_GUEST = "/guest/products"
class ProductService {
    //Guest
    getAllProductsHot(){
        return http.get(URL_GUEST+"/hot");
    }
    
    //Admin
    getProductsPagingAdmin(page,keyword){
        return http.get(URL_ADMIN+"?page="+page+"&keyword="+keyword);
    }

    createProduct(product){
        return http.post(URL_ADMIN, product);
    }

    getProductById(productId){
        return http.get(URL_ADMIN + "/" + productId);
    }

    updateProduct(productId, product){
        return http.put(URL_ADMIN + '/' + productId, product);
    }

    deleteProduct(productId){
        return http.delete(URL_ADMIN + '/' + productId);
    }
    createOrUpdateImage(productId, avatar, images){
        let formData = new FormData();
        formData.append("avatar", avatar)

        if(images.length <= 0){
            formData.append("images", images);
        } else {
            for (let i = 0 ; i < images.length ; i++) {
                formData.append("images", images[i]);
            }
        }
        return http.post(URL_ADMIN + '/' + 'uploadfile'+'/' + productId, formData)
    }

}
export default new ProductService()