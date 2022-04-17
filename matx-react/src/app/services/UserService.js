import http from "./http-common";
const URL_ADMIN = "/admin/users";
const URL_USER = "/user/profile";
const URL_CART = "/user/cart";
class UserService{
    getUsersPagingAdmin(page,keyword){
        return http.get(URL_ADMIN+"?page="+page+"&keyword="+keyword);
    }
    getAllUsersAdmin(){
        return http.get(URL_ADMIN+"/all");
    }
    deleteUserAdmin(id){
        return http.delete(URL_ADMIN+"/"+id);
    }
    updateUserAdmin(id, user){
        return http.put(URL_ADMIN + "/" + id, user);
    }
    createUserAdmin(user){
        return http.post(URL_ADMIN, user);
    }
    getUserAdminById(id){
        return http.get(URL_ADMIN + "/"+id);
    }

    updateUser(username, user){
        return http.put(URL_USER+"/edit" + "/" + username, user);
    }

    updatePassword(username, user){
        return http.put(URL_USER+"/edit/password" + "/" + username, user);
    }

    getUserByUsername(username){
        return http.get(URL_USER+"/" + username);
    }

    getOrdersByUserName(username, status){
        return http.get(URL_CART+"/"+username+"?orderStatus="+status);
    }

    getOrderDetailsByCode(code){
        return http.get(URL_CART+"/detail"+"/"+code);
    }

}
export default new UserService();