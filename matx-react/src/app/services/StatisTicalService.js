import http from "./http-common";
const URL_BASE = "/admin/home";
const URL_BASES = "/admin/orders";
class HomeService{
    getUserSum(name){
        return http.get(URL_BASE+"/"+name);
    }
    getYear(year){
        return http.get(URL_BASE+"/getRevenueByYear?year="+year);
    }

    getOrderByMonth(page, year, month){
        return http.get(URL_BASE +"/getOrderByMonth"+"?page="+page+"&year="+year+"&month="+month);
    }
    
}
export default new HomeService();