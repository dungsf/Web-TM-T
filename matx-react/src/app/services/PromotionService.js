import http from "./http-common";
const URL_ADMIN = "/admin/promotions";
class PromotionService{
    getPromotionsPagingAdmin(page,keyword){
        return http.get(URL_ADMIN+"?page="+page+"&keyword="+keyword);
    }
    getAllPromotionsAdmin(){
        return http.get(URL_ADMIN+"/all");
    }
    deletePromotionAdmin(id){
        return http.delete(URL_ADMIN+"/"+id);
    }
    updatePromotionAdmin(id, promotion){
        return http.put(URL_ADMIN + "/" + id, promotion);
    }
    createPromotionAdmin(promotion){
        return http.post(URL_ADMIN, promotion);
    }
    getPromotionAdminById(id){
        return http.get(URL_ADMIN + "/"+id);
    }
}
export default new PromotionService();