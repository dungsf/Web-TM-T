import http from "./http-common";
const URL_Base = "/guest/sizes";
class SizeService{
    getAllSizes(){
        return http.get(URL_Base+"/all");
    }
}
export default new SizeService();