import http from "./http-common";
const URL_ADMIN = "/admin/colors";
class ColorService{
    getColorsPagingAdmin(page,keyword){
        return http.get(URL_ADMIN+"?page="+page+"&keyword="+keyword);
    }
    getAllColorsAdmin(){
        return http.get(URL_ADMIN +"/all");
    }
    deleteColorAdmin(colorId){
        return http.delete(URL_ADMIN+"/"+ colorId);
    }
    updateColorAdmin(colorId, color){
        return http.put(URL_ADMIN +"/" + colorId, color);
    }
    createColorAdmin(color){
        return http.post(URL_ADMIN, color);
    }
    getColorAdminById(colorId){
        return http.get(URL_ADMIN + "/"+ colorId);
    }
}
export default new ColorService();

