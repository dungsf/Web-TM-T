import http from "./http-common";
const URL_ADMIN = "/admin/categories";
const URL_GUEST = "/guest/categories";
class CategoryService{
    getCategoriesPagingAdmin(page,keyword){
        return http.get(URL_ADMIN+"?page="+page+"&keyword="+keyword);
    }
    getAllCategoriesAdmin(){
        return http.get(URL_ADMIN+"/all");
    }
    deleteCategoryAdmin(id){
        return http.delete(URL_ADMIN+"/"+id);
    }
    updateCategoryAdmin(id, category){
        return http.put(URL_ADMIN + "/" + id, category);
    }
    createCategoryAdmin(category){
        return http.post(URL_ADMIN, category);
    }
    getCategoryAdminById(id){
        return http.get(URL_ADMIN + "/"+id);
    }

    getCategoryParent(){
        return http.get(URL_GUEST + "/parent");
    }

    getProductsByCategory(seo){
        return http.get(URL_GUEST + "/seo/"+seo);
    }
}
export default new CategoryService();