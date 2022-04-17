import http from "./http-common";
const URL_AUTHOR = "/auth";
class AuthService {
    login(auth) {
        return http.post(URL_AUTHOR + "/login", auth);
    }
    register(auth) {
        return http.post(URL_AUTHOR + "/register", auth);
    }
    resetPassword(email) {
        return http.post(URL_AUTHOR + "/reset-password?email=" + email);
    }
    infor() {
        return http.get(URL_AUTHOR + "/infor");
    }
}
export default new AuthService();