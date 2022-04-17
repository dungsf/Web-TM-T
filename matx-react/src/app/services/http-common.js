import axios from "axios";
import localStorageService from 'app/services/localStorageService'

let accessToken = localStorageService.getItem('accessToken') || ''

export default axios.create({
  baseURL: "http://localhost:8080/api/v1",
  headers: {
    "Content-type": "application/json",
    'Authorization': `Bearer ${accessToken}`
  }
});

export const URL_IMG = "http://localhost:8080/api/v1/guest/image/"